package io.github.deechtezeeuw.syandriatournament.commands;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn extends Commands {
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

        Player player = (Player) sender;

        if (args.length != 2 || (!args[1].equalsIgnoreCase("first") && !args[1].equalsIgnoreCase("second") && !args[1].equalsIgnoreCase("third") && !args[1].equalsIgnoreCase("fourth"))) {
            sender.sendMessage(plugin.getColor().colorPrefix("&4&l/syandriatournament setspawn <first/second/third/fourth>"));
            return;
        }

        if (args[1].equalsIgnoreCase("first")) {
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterOne.blockX", player.getLocation().getBlockX());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterOne.blockY", player.getLocation().getBlockY());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterOne.blockZ", player.getLocation().getBlockZ());
        }

        if (args[1].equalsIgnoreCase("second")) {
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterTwo.blockX", player.getLocation().getBlockX());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterTwo.blockY", player.getLocation().getBlockY());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterTwo.blockZ", player.getLocation().getBlockZ());
        }

        if (args[1].equalsIgnoreCase("third")) {
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterThree.blockX", player.getLocation().getBlockX());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterThree.blockY", player.getLocation().getBlockY());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterThree.blockZ", player.getLocation().getBlockZ());
        }

        if (args[1].equalsIgnoreCase("fourth")) {
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterFour.blockX", player.getLocation().getBlockX());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterFour.blockY", player.getLocation().getBlockY());
            plugin.getConfigurationManager().getArena().getConfig().set("arena.fighterFour.blockZ", player.getLocation().getBlockZ());
        }

        try {
            plugin.getConfigurationManager().getArena().getConfig().save(plugin.getConfigurationManager().getArena().getConfigFile());
            plugin.getConfigurationManager().getArena().reload();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        sender.sendMessage(plugin.getColor().colorPrefix("&aSuccesvol de locatie aangepast"));
    }

    @Override
    public String name() {
        return "setspawn";
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
        return false;
    }
}
