package io.github.deechtezeeuw.syandriatournament.configurations;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class Configuration {
    public abstract FileConfiguration getConfig();
    public abstract void createConfig();
    public abstract void load();
    public abstract void reload();
}
