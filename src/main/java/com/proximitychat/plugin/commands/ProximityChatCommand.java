package com.proximitychat.plugin.commands;

import com.proximitychat.plugin.commands.subcommands.JoinCommand;

public class ProximityChatCommand extends AbstractRootCommand {


    @Override
    public void addCommands() {
        addCommand(new JoinCommand());
    }
}
