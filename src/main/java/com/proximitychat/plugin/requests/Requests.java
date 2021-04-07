package com.proximitychat.plugin.requests;

import com.google.gson.JsonSyntaxException;
import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.requests.requests.ServerInformationRequest;
import com.proximitychat.plugin.requests.routes.Routes;
import com.proximitychat.plugin.util.MessageUtil;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Requests {

    public static final AsyncHttpClient client = Dsl.asyncHttpClient(Dsl.config().setRequestTimeout(500).build());
    public static class Server {
        public static <S extends CommandSender> void SERVER_INFORMATION(S sender, ServerInformationRequest.Request request) {
            new ServerInformationRequest(client, Routes.Server.informationUrl()).execute(sender, request);
        }
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
