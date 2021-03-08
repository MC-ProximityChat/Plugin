package com.proximitychat.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class Logger {

    public static void info(HumanEntity entity, String message) {
        Bukkit.getConsoleSender().sendMessage(message);
        entity.sendMessage(message);
    }
}
