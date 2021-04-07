package com.proximitychat.plugin.requests;

import com.proximitychat.plugin.requests.model.LocationModel;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationRequestDispatcher {

    private static final String BASE_URL = "http://localhost:8081/server/";

    private final String url;

    private final AtomicInteger count;

    private final AsyncHttpClient client;

    private final ExecutorService service;

    public LocationRequestDispatcher(AsyncHttpClient client, int ip) {
        this.url = BASE_URL + ip;
        this.client = client;
        this.count = new AtomicInteger(0);
        this.service = Executors.newCachedThreadPool();
    }

    public ListenableFuture<Response> sendRequest(LocationModel model) {
        // Bukkit.getConsoleSender().sendMessage(model.toString());
        ListenableFuture<Response> response = client.executeRequest(getRequestFrom(model));

        response.addListener(() -> {
            Response rep;
            try {
                rep = response.get();
                // MessageUtil.broadcast("huge penis request no " + count.incrementAndGet() + ". Response: " + rep.toString().replace("\t", "  "));
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
