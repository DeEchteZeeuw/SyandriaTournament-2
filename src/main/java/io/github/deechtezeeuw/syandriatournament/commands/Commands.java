package io.github.deechtezeeuw.syandriatournament.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Commands {
    public abstract void onCommand(CommandSender sender, Command commands, String[] args);

    public abstract String name();
    public abstract String info();
    public abstract String[] aliases();
    public abstract boolean console();
}
