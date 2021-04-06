package com.proximitychat.plugin.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSubCommand {

    protected static final List<String> NO_ALIASES = new ArrayList<>();

    protected static final String NO_PERMISSION = "";

    private String name;

    private List<String> aliases;

    private String permission;

    private String usage;

    private String description;

    private String longDescription;

    public AbstractSubCommand(String name, String permission) {
        this(name, NO_ALIASES, permission, "", "", "");
    }

    public AbstractSubCommand(String name, String permission, String usage, String description, String longDescription, String... aliases) {
        this(name, Arrays.asList(aliases), permission, usage, description, longDescription);
    }

    public AbstractSubCommand(String name, List<String> aliases, String permission, String usage, String description, String longDescription) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.usage = usage;
        this.description = description;
        this.longDescription = longDescription;
    }

    public void executePlayer(Player player, List<String> args) {

    }

    public void executeConsole(ConsoleCommandSender console, List<String> args) {

    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String getLongDescription() {
        return longDescription;
    }
}
