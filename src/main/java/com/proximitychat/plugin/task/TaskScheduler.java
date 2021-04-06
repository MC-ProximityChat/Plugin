package com.proximitychat.plugin.task;

import org.bukkit.entity.Player;

public interface TaskScheduler {

    void scheduleExistingPlayers();

    int schedule(Player player);

    int cancel(Player player);

    void destroy();
}
