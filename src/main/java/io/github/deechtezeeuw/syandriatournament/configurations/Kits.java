package io.github.deechtezeeuw.syandriatournament.configurations;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

public class Kits extends Configuration {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private File configFile;
    private FileConfiguration config;

    public ArrayList<UUID> list = new ArrayList<>();

    public Kits() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder() + "/configuration", "kits.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("configuration/kits.yml", false);
        }

        config= new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        this.list = new ArrayList<>();


    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder() + "/configuration", "kits.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("configuration/kits.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }
}
