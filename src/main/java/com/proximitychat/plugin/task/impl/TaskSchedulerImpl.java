package com.proximitychat.plugin.task.impl;

import com.proximitychat.plugin.task.LocationUpdaterTask;
import com.proximitychat.plugin.task.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskSchedulerImpl implements TaskScheduler {

    private static final long TASK_REPEAT_RATE = 5L;

    private Plugin plugin;

    private Map<UUID, Integer> taskMap;

    public TaskSchedulerImpl(Plugin plugin) {
        this.plugin = plugin;
        this.taskMap = new ConcurrentHashMap<>();
    }

    public int schedule(Player player) {
        int taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new LocationUpdaterTask(player), 0L, TASK_REPEAT_RATE);
        add(player, taskId);
        return taskId;
    }

    public int cancel(Player player) {
        int taskId = interrupt(player);
        remove(player);
        return taskId;
    }

    private void add(Player player, int task) {
        taskMap.put(player.getUniqueId(), task);
    }

    private int interrupt(Player player) {
        int taskId = taskMap.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(taskId);
        return taskId;
    }

    public void remove(Player player) {
        taskMap.remove(player.getUniqueId());
    }

    public void destroy() {
        Bukkit.getScheduler().cancelTasks(plugin);
        taskMap = null;
        plugin = null;
    }
}
