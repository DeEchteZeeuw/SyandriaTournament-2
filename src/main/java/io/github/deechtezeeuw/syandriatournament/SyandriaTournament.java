package io.github.deechtezeeuw.syandriatournament;

import io.github.deechtezeeuw.syandriatournament.managers.*;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import io.github.deechtezeeuw.syandriatournament.utils.GUI;
import io.github.deechtezeeuw.syandriatournament.utils.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class SyandriaTournament extends JavaPlugin {
    private static SyandriaTournament instance;
    private ConsoleCommandSender console;

    // Utils
    private TextColor color;

    // Managers
    private TournamentManager tournamentManager;
    private CommandManager commandManager;
    private ConfigurationManager configurationManager;
    private ArenaManager arenaManager;
    private EventManager eventManager;
    private KitsManager kitsManager;

    // GUI
    private GUI gui;

    @Override
    public void onEnable() {
        instance = this;
        console = getServer().getConsoleSender();

        // Utils
        color = new TextColor();

        // Managers
        tournamentManager = new TournamentManager();
        commandManager = new CommandManager();
        commandManager.setup();
        configurationManager = new ConfigurationManager();
        arenaManager = new ArenaManager();
        eventManager = new EventManager();
        kitsManager = new KitsManager();

        // GUI util
        gui = new GUI();

        console.sendMessage(color.color("&7&m----------------------------------------"));
        console.sendMessage(color.color("&9&l" + getDescription().getName() + " &aenabled!"));
        console.sendMessage(color.color("&7Version: &b&l" + getDescription().getVersion()));
        console.sendMessage(color.color("&7&m----------------------------------------"));
    }

    @Override
    public void onDisable() {
        // Give peoples inventory back
        if (getTournamentManager().isActiveTournament()) {
            for (UUID keys : getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).getInventory().clear();
                    Bukkit.getServer().getPlayer(keys).getInventory().setContents(getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory(keys));
                }
            }

            for (UUID keys : getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).teleport(getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation(keys));
                }
            }
        }

        for (Tournament tournament : getTournamentManager().getArrayRegistreredTournaments()) {
            for (UUID keys : tournament.getLockerRoomManager().getInventory().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).getInventory().clear();
                    Bukkit.getServer().getPlayer(keys).getInventory().setContents(tournament.getLockerRoomManager().getInventory(keys));
                }
            }

            for (UUID keys : tournament.getLockerRoomManager().getLocation().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).teleport(tournament.getLockerRoomManager().getLocation(keys));
                }
            }
        }
        // Plugin shutdown logic
        console.sendMessage(color.color("&7&m----------------------------------------"));
        console.sendMessage(color.color("&9&l" + getDescription().getName() + " &cdisabled!"));
        console.sendMessage(color.color("&7Version: &b&l" + getDescription().getVersion()));
        console.sendMessage(color.color("&7&m----------------------------------------"));
    }

    public static SyandriaTournament getInstance() {
        return instance;
    }

    public TextColor getColor() {
        return color;
    }

    public TournamentManager getTournamentManager() {
        return tournamentManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public GUI getGui() {
        return gui;
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public KitsManager getKitsManager() {
        return kitsManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
