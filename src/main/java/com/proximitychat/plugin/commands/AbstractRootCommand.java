package com.proximitychat.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractRootCommand implements CommandExecutor {

    private Map<String, AbstractSubCommand> subCommandMap;

    public AbstractRootCommand() {
        this.subCommandMap = new HashMap<>();

        addCommands();
    }

    public abstract void addCommands();

    public void addCommand(AbstractSubCommand command) {
        subCommandMap.put(command.getName(), command);
        command.getAliases().forEach(a -> subCommandMap.put(a, command));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return helpCommand();
        } else {
            List<String> subCommandArgs = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
            if (sender instanceof ConsoleCommandSender) {
                getSubCommand(args[0]).ifPresent(abstractSubCommand -> abstractSubCommand.executeConsole((ConsoleCommandSender) sender, subCommandArgs));
                return true;
            }
            Optional<AbstractSubCommand> commandOptional = getSubCommand(args[0]);

            if (!commandOptional.isPresent())
                return commandDoesntExist(sender);

            Player player = (Player) sender;
            AbstractSubCommand command = commandOptional.get();

            if (!hasPermission(player, command.getPermission()))
                return noPermission(player);

            command.executePlayer(player, subCommandArgs);
        }

        return false;
    }

    private boolean noPermission(Player player) {
        player.sendMessage(ChatColor.RED + "No permission!");
        return false;
    }

    private Optional<AbstractSubCommand> getSubCommand(String head) {
        return Optional.ofNullable(subCommandMap.get(head));
    }

    private boolean helpCommand() {
        return false;
    }

    private boolean hasPermission(Player player, String permission) {
        return player.isOp() || permission.equals("") || player.hasPermission(permission);
    }

    private boolean commandDoesntExist(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Command doesn't exist!");
        return false;
    }
}
