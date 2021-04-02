package com.proximitychat.plugin.http;

import com.proximitychat.plugin.http.model.LocationModel;
import com.proximitychat.plugin.util.MessageUtil;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestDispatcher {

    private static final String BASE_URL = "http://localhost:8081/server/";

    private final String url;

    private final AsyncHttpClient client;

    private final AtomicInteger count;

    private final ExecutorService service;

    public RequestDispatcher(int ip) {
        this.url = BASE_URL + ip;
        this.client = Dsl.asyncHttpClient(Dsl.config().setRequestTimeout(500).build());
        this.count = new AtomicInteger(0);
        this.service = Executors.newCachedThreadPool();
    }

    public ListenableFuture<Response> sendRequest(LocationModel model) {
        MessageUtil.broadcast(model.toString());
        Bukkit.getConsoleSender().sendMessage(model.toString());
        ListenableFuture<Response> response = client.executeRequest(getRequestFrom(model));

        response.addListener(() -> {
            Response rep;
            try {
                rep = response.get();
                MessageUtil.broadcast("huge penis request no " + count.incrementAndGet() + ". Response: " + rep.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, service);
        return response;
    }

    private Request getRequestFrom(LocationModel model) {
        return new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .addHeader("Content-Type", "application/json")
                .setBody(model.toString())
                .build();
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
