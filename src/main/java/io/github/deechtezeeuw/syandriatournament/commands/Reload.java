package io.github.deechtezeeuw.syandriatournament.commands;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends Commands {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command commands, String[] args) {
        if (!sender.hasPermission("syandriatournament.setspawn")) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cJe hebt hiervoor geen permissies!"));
            return;
        }

        if (!console() && !(sender instanceof Player)) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cDit kan alleen in-game worden uitgevoerd."));
            return;
        }

        plugin.getConfigurationManager().getTournaments().reloadWithoutCurrent();
        plugin.getConfigurationManager().getNpc().reload();
        plugin.getConfigurationManager().getArena().reload();
        plugin.getConfigurationManager().getKits().reload();

        sender.sendMessage(plugin.getColor().colorPrefix("&aSuccesvol alle configuraties herladen!"));
    }

    @Override
    public String name() {
        return "reload";
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
