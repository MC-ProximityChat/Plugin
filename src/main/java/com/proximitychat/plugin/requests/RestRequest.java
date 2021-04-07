package com.proximitychat.plugin.requests;

import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.requests.routes.QueryParams;
import com.proximitychat.plugin.requests.routes.Route;
import com.proximitychat.plugin.util.MessageUtil;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.asynchttpclient.*;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class RestRequest<B extends RequestBody, T extends ResponseBody> {

    protected static final Map<String, String> DEFAULT_HEADERS = new HashMap<>();

    static {
        DEFAULT_HEADERS.put("Content-Type", "application/json");
    }

    private final ExecutorService service;

    private final AsyncHttpClient client;

    private final Route route;

    private final HttpMethod method;

    private final Map<String, String> headers;

    private final Class<T> responseClazz;

    // the wildcard here is kinda dodgy but should be aite?
    private final List<Consumer<RestResponse<? extends CommandSender, T>>> listeners;

    public RestRequest(AsyncHttpClient client, Route route, HttpMethod method, Map<String, String> headers, Class<T> responseClazz) {
        this.client = client;
        this.route = route;
        this.method = method;
        this.headers = headers;
        this.responseClazz = responseClazz;
        this.listeners = new ArrayList<>();

        this.service = Executors.newCachedThreadPool();

        registerListeners();
    }

    protected abstract void registerListeners();

    protected void registerListener(Consumer<RestResponse<? extends CommandSender, T>> listener) {
        listeners.add(listener);
    }

    private Request buildRequest(B body) {
        RequestBuilder builder = new RequestBuilder(method.name());

        ProximityChat.getInstance().getServer().getConsoleSender().sendMessage(route.compile());
        builder.setUrl(route.compile());
        headers.forEach(builder::addHeader);

        if (!(body instanceof EmptyBody)) {
            ProximityChat.getInstance().getServer().getConsoleSender().sendMessage(body.toJson());
            builder.setBody(body.toJson());
        }

        return builder.build();
    }

    public <S extends CommandSender> ListenableFuture<RestResponse<S, T>> execute(S sender, B body) {
        Request request = buildRequest(body);
        ListenableFuture<RestResponse<S, T>> responseFuture = client.executeRequest(request, new AsyncHandler<RestResponse<S, T>>() {

            private final RestResponse.Builder<S, T> responseBuilder = RestResponse.builder(sender, responseClazz);

            @Override
            public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                responseBuilder.statusCode(responseStatus.getStatusCode());
                sender.sendMessage("PENIS");
                return State.CONTINUE;
            }

            // no need to get everything if rate limit is exceeded
            @Override
            public State onHeadersReceived(HttpHeaders headers) throws Exception {

                boolean hasRateLimitExceeded = headers.containsValue("X-RateLimit-Exceeded", "true", false);

                if (hasRateLimitExceeded)
                    throw new RateLimitExceededException();

                responseBuilder.headers(headers.entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

                return State.CONTINUE;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {

                responseBuilder.bodyStr(new String(bodyPart.getBodyPartBytes()));
                return State.CONTINUE;
            }

            @Override
            public void onThrowable(Throwable throwable) {
                if (throwable instanceof RateLimitExceededException) {
                    RateLimitExceededException exceededException = (RateLimitExceededException) throwable;
                    sender.sendMessage("rate limit exceeded");
                } else {
                    throwable.printStackTrace();
                    sender.sendMessage("unknown error has occurred");
                }
            }

            @Override
            public RestResponse<S, T> onCompleted() throws Exception {
                return responseBuilder.build();
            }
        });

        listeners.forEach(l -> responseFuture.addListener(() -> {
                    try {
                        l.accept(responseFuture.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }, service));

        return responseFuture;
    }
}
