package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private final ArrayList<Commands> commands = new ArrayList<>();

    // Main command
    public String main = "syandriatournament";

    public void setup() {
        plugin.getCommand(main).setExecutor(this);
        plugin.getCommand(main).setTabCompleter(this);

        // register subcommands
//        commands.add(new Reload());
//        commands.add(new Start());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //create new array
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // main
//            if (sender.hasPermission("seasonfinale.reload")) {
//                completions.add("reload");
//            }
        }

        if (args.length == 2) {
            // sub of sub

        }

        return completions;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(command.getName().equalsIgnoreCase(main))) return true;

        // Check if there are no arguments
        if (args.length == 0) {
            if (plugin.getTournamentManager().isThereAnTournament()) {
                if (plugin.getTournamentManager().isActiveTournament()) {
                    sender.sendMessage(plugin.getColor().colorPrefix("&aHet volgende &2&lSyandria Toernooi &ais op &2&lMaandag 15:00&a!"));
                } else {
                    sender.sendMessage(plugin.getColor().colorPrefix("&cEr is geen volgend toernooi ingesteld! Probeer het later nog eens."));
                }
            } else {
                sender.sendMessage(plugin.getColor().colorPrefix("&cEr zijn geen toernooien geregistreerd! Probeer het later nog eens."));
            }
            return true;
        }

        Commands target = this.get(args[0]);

        if (target == null) {
            sender.sendMessage(plugin.getColor().colorPrefix("&cOnbekend commando!"));
            return true;
        }

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
        arrayList.remove(0);

        try {
            target.onCommand(sender, command, args);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error has occurred");
            e.printStackTrace();
        }

        return true;
    }

    private Commands get(String name) {

        for (Commands sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int x = 0; x < length; ++x) {
                String alias = aliases[x];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }
}