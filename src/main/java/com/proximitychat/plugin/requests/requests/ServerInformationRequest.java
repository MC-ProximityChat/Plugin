package com.proximitychat.plugin.requests.requests;

import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.requests.RequestBody;
import com.proximitychat.plugin.requests.ResponseBody;
import com.proximitychat.plugin.requests.RestRequest;
import com.proximitychat.plugin.requests.routes.Route;
import com.proximitychat.plugin.util.MessageUtil;
import io.netty.handler.codec.http.HttpMethod;
import org.asynchttpclient.AsyncHttpClient;

public class ServerInformationRequest extends RestRequest<ServerInformationRequest.Request, ServerInformationRequest.Response> {

    @Override
    protected void registerListeners() {
        registerListener((response) -> {
            MessageUtil.broadcast(response.getBodyStr());
            ProximityChat.getInstance().setServerId(response.getBody().id);
        });
    }

    public static class Request implements RequestBody {

        private final String name;

        public Request(String name) {
            this.name = name;
        }

        @Override
        public String toJson() {
            return "{\"name\": \"" + name + "\" }";
        }
    }

    public static class Response implements ResponseBody {
        private String id;

        private Integer code;

        public Response() {

        }

        public Response(String id, Integer code) {
            this.id = id;
            this.code = code;
        }
    }


    public ServerInformationRequest(AsyncHttpClient client, Route route) {
        super(client, route, HttpMethod.POST, DEFAULT_HEADERS, Response.class);
    }

}
