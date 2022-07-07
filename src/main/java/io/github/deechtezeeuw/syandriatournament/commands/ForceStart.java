package io.github.deechtezeeuw.syandriatournament.commands;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStart extends Commands {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command commands, String[] args) {
        if (!sender.hasPermission("syandriatournament.forcestart")) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cJe hebt hiervoor geen permissies!"));
            return;
        }

        if (!console() && !(sender instanceof Player)) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cDit kan alleen in-game worden uitgevoerd."));
            return;
        }

        if (plugin.getTournamentManager().isActiveTournament()) {
            if (!plugin.getTournamentManager().getCurrentTournament().isBusy()) {
                plugin.getTournamentManager().getCurrentTournament().conditionCheck();

                sender.sendMessage(plugin.getColor().colorPrefix("&aHet toernooi is geforceerd gestart!"));
            } else {
                sender.sendMessage(plugin.getColor().colorPrefix("&cHet toernooi is al begonnen!"));
            }
        } else {
            sender.sendMessage(plugin.getColor().colorPrefix("&cEr is op het moment geen toernooi om te force starten."));
        }
    }

    @Override
    public String name() {
        return "forcestart";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public boolean console() {
        return true;
    }
}
