package com.proximitychat.plugin;

import com.proximitychat.plugin.events.OnJoin;
import com.proximitychat.plugin.events.OnQuit;
import com.proximitychat.plugin.task.TaskScheduler;
import com.proximitychat.plugin.task.impl.TaskSchedulerImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProximityChat extends JavaPlugin {

    public static final boolean DEBUG = true;

    public static final double DISTANCE_THRESHOLD = 1.0D;

    private TaskScheduler taskScheduler;

    private static ProximityChat instance;

    @Override
    public void onEnable() {
        this.taskScheduler = new TaskSchedulerImpl(this);
        registerEvents();

        instance = this;
    }

    @Override
    public void onDisable() {
        taskScheduler.destroy();
    }

    public static ProximityChat getInstance() {
        return instance;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnQuit(), this);
    }
}
