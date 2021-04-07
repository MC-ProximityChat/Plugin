package com.proximitychat.plugin.requests.requests;

import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.requests.RequestBody;
import com.proximitychat.plugin.requests.ResponseBody;
import com.proximitychat.plugin.requests.RestRequest;
import com.proximitychat.plugin.requests.routes.Route;
import com.proximitychat.plugin.util.StringUtil;
import io.netty.handler.codec.http.HttpMethod;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.asynchttpclient.AsyncHttpClient;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class JoinServerRequest extends RestRequest<JoinServerRequest.Request, JoinServerRequest.Response> {

    public JoinServerRequest(AsyncHttpClient client, Route route) {
        super(client, route, HttpMethod.POST, DEFAULT_HEADERS, Response.class);
    }

    @Override
    protected void registerListeners() {
        registerListener(response -> {
            Response body = response.getBody();
            sendJoinMessage((Player) response.getSender(), buildUrl(ProximityChat.getInstance().getServerId()), body.getCode(), body.getExpirationPolicy(), body.getAlreadyGenerated());
        });
    }

    private String buildUrl(String id) {
        return "http://localhost:3000/server/" + id;
    }

    private void sendJoinMessage(Player player, String link, String code, String expirationPolicy, boolean alreadyGenerated) {
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

    public static class Request implements RequestBody {

        private String uuid;

        public Request(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public String toJson() {
            return "{\"uuid\": \"" + uuid + "\"}";
        }
    }

    public static class Response implements ResponseBody {

        private String uuid;

        private String code;

        private String expirationPolicy;

        private Boolean alreadyGenerated;

        public String getUuid() {
            return uuid;
        }

        public String getCode() {
            return code;
        }

        public String getExpirationPolicy() {
            return expirationPolicy;
        }

        public Boolean getAlreadyGenerated() {
            return alreadyGenerated;
        }
    }
}
