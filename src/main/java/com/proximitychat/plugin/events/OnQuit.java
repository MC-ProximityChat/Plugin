package com.proximitychat.plugin.events;

import com.proximitychat.plugin.Logger;
import com.proximitychat.plugin.ProximityChat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        onDisconnect(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        onDisconnect(event.getPlayer());
    }

    private void onDisconnect(Player player) {
        int taskId = ProximityChat.getInstance().getTaskScheduler().cancel(player);

        if (ProximityChat.DEBUG)
            Logger.info(player, ChatColor.YELLOW + "Task cancelled with task id" + taskId);
    }
}
