package com.proximitychat.plugin.commands.subcommands;

import com.proximitychat.plugin.commands.AbstractSubCommand;
import com.proximitychat.plugin.requests.Requests;
import com.proximitychat.plugin.requests.requests.JoinServerRequest;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinCommand extends AbstractSubCommand {

    public JoinCommand() {
        super("join", "proximitychat.join", "", "Join proximity chat", "Join proximity chat time", "j");
    }

    @Override
    public void executePlayer(Player player, List<String> args) {
        JoinServerRequest request = Requests.Server.JOIN_SERVER;
        request.execute(player, new JoinServerRequest.Request(player.getUniqueId().toString()));
    }
}
