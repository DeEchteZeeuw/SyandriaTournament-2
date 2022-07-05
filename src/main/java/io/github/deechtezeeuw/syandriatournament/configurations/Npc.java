package io.github.deechtezeeuw.syandriatournament.configurations;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

public class Npc extends Configuration{
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private File configFile;
    private FileConfiguration config;

    public Npc() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder() + "/configuration", "npc.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("configuration/npc.yml", false);
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

    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder() + "/configuration", "npc.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("configuration/npc.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }
}
