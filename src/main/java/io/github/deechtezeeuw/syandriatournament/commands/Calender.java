package io.github.deechtezeeuw.syandriatournament.commands;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Calender extends Commands {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command commands, String[] args) {
        // Check if sender has permission to do this
        if (!sender.hasPermission("syandriatournament.calender")) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cJe hebt hiervoor geen permissies!"));
            return;
        }
        // Check if sender is the console
        if (sender instanceof ConsoleCommandSender && !this.console()) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cDit kan alleen in-game worden uitgevoerd."));
            return;
        }
    }

    @Override
    public String name() {
        return "calender";
    }

    @Override
    public String info() {
        return "Shows an GUI with the current and registered tournaments.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public boolean console() {
        return false;
    }
}
