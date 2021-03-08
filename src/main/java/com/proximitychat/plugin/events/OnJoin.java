package com.proximitychat.plugin.events;

import com.proximitychat.plugin.Logger;
import com.proximitychat.plugin.ProximityChat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int taskId = ProximityChat.getInstance().getTaskScheduler().schedule(player);

        if (ProximityChat.DEBUG)
            Logger.info(player, ChatColor.YELLOW + "Task scheduled with task id" + taskId);
    }
}
