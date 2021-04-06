package com.proximitychat.plugin.http;

import com.google.gson.JsonSyntaxException;
import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.util.MessageUtil;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.bukkit.ChatColor;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Requests {

    private final ProximityChat plugin;

    private final AsyncHttpClient client;

    private final ExecutorService executorService;

    public Requests(ProximityChat plugin, AsyncHttpClient client) {
        this.plugin = plugin;
        this.client = client;
        this.executorService = Executors.newCachedThreadPool();
    }

    public ListenableFuture<Response> registerNewPlayer(UUID uuid) {
        String uuidStr = uuid.toString();
        String url = "http://localhost:8081/v1/server/join/" + plugin.getServerId();
        plugin.getServer().getConsoleSender().sendMessage(url);
        Request request = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .addHeader("Content-Type", "application/json")
                .setBody("{\"uuid\": \"" + uuidStr + "\"}")
                .build();

        return client.executeRequest(request);
    }

    public void registerServerWithBackend(String serverName) {
        Request request = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl("http://localhost:8081/v1/server/")
                .addHeader("Content-Type", "application/json")
                .setBody("{\"name\": \"" + serverName + "\"}")
                .build();

        ListenableFuture<Response> response = client.executeRequest(request);

        response.addListener(() -> {
            try {
                Response rep = response.get();
                MessageUtil.broadcast(rep.toString());
                String body = rep.getResponseBody();

                plugin.getServer().getConsoleSender().sendMessage(body);

                SimplifiedServer code = ProximityChat.GSON.fromJson(body, SimplifiedServer.class);
                plugin.setServerId(code.id);

                // ProximityChat.getInstance().setServerId(("id").getAsString());
            } catch (InterruptedException | ExecutionException | JsonSyntaxException e) {
                e.printStackTrace();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to load server... disabling!");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }, executorService);
    }

    public Executor getExecutorService() {
        return executorService;
    }

    private static class SimplifiedServer {
        private String id;

        private Integer code;

        public SimplifiedServer() {

        }

        public SimplifiedServer(String id, Integer code) {
            this.id = id;
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String uuid) {
            this.id = uuid;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }
}
