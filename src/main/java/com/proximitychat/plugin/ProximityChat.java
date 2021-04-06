package com.proximitychat.plugin;

import com.google.gson.Gson;
import com.proximitychat.plugin.commands.ProximityChatCommand;
import com.proximitychat.plugin.events.OnJoin;
import com.proximitychat.plugin.events.OnQuit;
import com.proximitychat.plugin.http.LocationRequestDispatcher;
import com.proximitychat.plugin.http.Requests;
import com.proximitychat.plugin.task.TaskScheduler;
import com.proximitychat.plugin.task.impl.TaskSchedulerImpl;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ProximityChat extends JavaPlugin {

    public static final boolean DEBUG = true;

    public static final double DISTANCE_THRESHOLD =1.0D;

    public static final Gson GSON = new Gson();

    private TaskScheduler taskScheduler;

    private AsyncHttpClient client;

    private LocationRequestDispatcher requestDispatcher;

    private Requests requests;

    private String serverId;

    private String serverName;

    private static ProximityChat instance;

    public final int HASHED_IP = getServer().getIp().hashCode();

    @Override
    public void onEnable() {
        this.taskScheduler = new TaskSchedulerImpl(this);
        this.client = Dsl.asyncHttpClient(Dsl.config().setRequestTimeout(500).build());
        this.requestDispatcher = new LocationRequestDispatcher(client, HASHED_IP);
        this.requests = new Requests(this, client);
        this.serverName = getServer().getMotd();

        registerEvents();
        registerCommands();

        requests.registerServerWithBackend(serverName);
        taskScheduler.scheduleExistingPlayers();

        instance = this;
    }

    @Override
    public void onDisable() {
        requestDispatcher.close();
        taskScheduler.destroy();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("proximitychat")).setExecutor(new ProximityChatCommand());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnQuit(), this);
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public static ProximityChat getInstance() {
        return instance;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public LocationRequestDispatcher getLocationRequestDispatcher() {
        return requestDispatcher;
    }

    public Requests getRequests() {
        return requests;
    }
    public AsyncHttpClient getClient() {
        return client;
    }
}
