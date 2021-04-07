package com.proximitychat.plugin.requests;

import com.proximitychat.plugin.requests.requests.JoinServerRequest;
import com.proximitychat.plugin.requests.requests.ServerInformationRequest;
import com.proximitychat.plugin.requests.routes.Routes;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

public class Requests {

    public static final AsyncHttpClient client = Dsl.asyncHttpClient(Dsl.config().setRequestTimeout(500).build());

    public static class Server {

        public static ServerInformationRequest SERVER_INFORMATION = new ServerInformationRequest(client, Routes.Server.informationUrl());

        public static JoinServerRequest JOIN_SERVER = new JoinServerRequest(client, Routes.Server.informationUrl());
    }
//    private final ProximityChat plugin;
//
//    private final AsyncHttpClient client;
//
//    private final ExecutorService executorService;
//
//    public Requests(ProximityChat plugin, AsyncHttpClient client) {
//        this.plugin = plugin;
//        this.client = client;
//        this.executorService = Executors.newCachedThreadPool();
//    }
//
//    public ListenableFuture<Response> registerNewPlayer(UUID uuid) {
//        String uuidStr = uuid.toString();
//        String url = "http://localhost:8081/v1/server/join/" + plugin.getServerId();
//        plugin.getServer().getConsoleSender().sendMessage(url);
//        Request request = new RequestBuilder(HttpConstants.Methods.POST)
//                .setUrl(url)
//                .addHeader("Content-Type", "application/json")
//                .setBody("{\"uuid\": \"" + uuidStr + "\"}")
//                .build();
//
//        return client.executeRequest(request);
//    }

}
