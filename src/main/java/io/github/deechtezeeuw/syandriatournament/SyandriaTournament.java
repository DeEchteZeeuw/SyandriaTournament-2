package io.github.deechtezeeuw.syandriatournament;

import io.github.deechtezeeuw.syandriatournament.managers.CommandManager;
import io.github.deechtezeeuw.syandriatournament.utils.TextColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class SyandriaTournament extends JavaPlugin {
    private static SyandriaTournament instance;
    private ConsoleCommandSender console;

    // Utils
    private TextColor color;

    // Managers
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        console = getServer().getConsoleSender();

        // Utils
        color = new TextColor();

        // Managers
        commandManager = new CommandManager();
        commandManager.setup();

        console.sendMessage(color.color("&7&m----------------------------------------"));
        console.sendMessage(color.color("&9&l" + getDescription().getName() + " &aenabled!"));
        console.sendMessage(color.color("&7Version: &b&l" + getDescription().getVersion()));
        console.sendMessage(color.color("&7&m----------------------------------------"));
    }

    @Override
    public void onDisable() {
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
}
