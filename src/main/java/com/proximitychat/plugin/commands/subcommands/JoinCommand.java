package com.proximitychat.plugin.commands.subcommands;

import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.commands.AbstractSubCommand;
import com.proximitychat.plugin.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JoinCommand extends AbstractSubCommand {

    public JoinCommand() {
        super("join", "proximitychat.join", "", "Join proximity chat", "Join proximity chat time", "j");
    }

    @Override
    public void executePlayer(Player player, List<String> args) {
        final String url = buildUrl(ProximityChat.getInstance().getServerId());

//        AtomicReference<Player> playerSupplier = new AtomicReference<>(player);
//
//        ListenableFuture<Response> response = requests.registerNewPlayer(player.getUniqueId());
//
//        response.addListener(() -> {
//            try {
//                String body = response.get().getResponseBody();
//                ServerResponse serverResponse = ProximityChat.GSON.fromJson(body, ServerResponse.class);
//                sendJoinMessage(playerSupplier, url, serverResponse.code, serverResponse.expirationPolicy, serverResponse.alreadyGenerated);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }, requests.getExecutorService());
    }

    private String buildUrl(String id) {
        return "http://localhost:3000/server/" + id;
    }

    private void sendJoinMessage(AtomicReference<Player> playerSupplier, String link, String code, String expirationPolicy, boolean alreadyGenerated) {
        Player player = playerSupplier.get();
        player.sendMessage(getHeader());
        // player.sendMessage(" ");
        player.sendMessage(ChatColor.AQUA + (alreadyGenerated ? "You already have a code generated!" : "Your code has been generated!"));
        player.sendMessage(" ");
        player.sendMessage(Component.text(ChatColor.AQUA + "To join, please click ").append(Component.text().clickEvent(ClickEvent.openUrl(link)).hoverEvent(player).content(ChatColor.YELLOW + "here").build()));
        player.sendMessage(" ");
        player.sendMessage(Component.text().content(ChatColor.AQUA + "Your code is: ").append(Component.text().clickEvent(ClickEvent.copyToClipboard(code)).content(ChatColor.YELLOW + code).append(Component.text().content(ChatColor.GRAY + " (Click to copy to clipboard)")).build()));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.AQUA + "This code will expire in: " + ChatColor.YELLOW + expirationPolicy);
        // player.sendMessage(" ");
        player.sendMessage(getFooter());
    }

    private String getBorder() {
        return ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + StringUtil.repeat('-', 20);
    }

    private String getHeader() {
        return getBorder() + ChatColor.DARK_AQUA + " Proximity Chat " + getBorder();
    }

    private String getFooter() {
        return ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + StringUtil.repeat('-', 53);
    }

    private static class ServerResponse {
        private String uuid;

        private String code;

        private String expirationPolicy;

        private Boolean alreadyGenerated;
    }
}
