package com.proximitychat.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.DARK_AQUA + "Broadcast" + ChatColor.DARK_GRAY + " | " + ChatColor.AQUA + message);
        }
    }
}
