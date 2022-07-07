package io.github.deechtezeeuw.syandriatournament.configurations;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Arena extends Configuration {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private File configFile;
    private FileConfiguration config;

    public Arena() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder() + "/configuration", "arena.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("configuration/arena.yml", false);
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
        if (getConfig().getString("arena.world") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe hebt geen wereld ingevoerd bij &4&larena.yml &cdoe dit eerst!"));
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }

        World world = Bukkit.getServer().getWorlds().get(0);
        if (Bukkit.getServer().getWorld(getConfig().getString("arena.world")) != null) {
            world = Bukkit.getServer().getWorld(getConfig().getString("arena.world"));
        }

        Location location = new Location(world, 0,0,0);

        if (getConfig().getConfigurationSection("arena.fighterOne") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe hebt geen fighterOne ingevoerd bij &4&larena.yml &cdoe dit eerst!"));
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        } else {
            if (getConfig().getDouble("arena.fighterOne.blockX") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterOne.blockX"));
            }
            if (getConfig().getDouble("arena.fighterOne.blockY") > 0) {
                location.setY(getConfig().getDouble("arena.fighterOne.blockY"));
            }
            if (getConfig().getDouble("arena.fighterOne.blockZ") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterOne.blockZ"));
            }

            plugin.getArenaManager().setFighterOne(location);
        }

        location = new Location(world, 0,0,0);

        if (getConfig().getConfigurationSection("arena.fighterTwo") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe hebt geen fighterTwo ingevoerd bij &4&larena.yml &cdoe dit eerst!"));
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        } else {
            if (getConfig().getDouble("arena.fighterTwo.blockX") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterTwo.blockX"));
            }
            if (getConfig().getDouble("arena.fighterTwo.blockY") > 0) {
                location.setY(getConfig().getDouble("arena.fighterTwo.blockY"));
            }
            if (getConfig().getDouble("arena.fighterTwo.blockZ") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterTwo.blockZ"));
            }

            plugin.getArenaManager().setFighterTwo(location);
        }

        location = new Location(world, 0,0,0);

        if (getConfig().getConfigurationSection("arena.fighterThree") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe hebt geen fighterThree ingevoerd bij &4&larena.yml &cdoe dit eerst!"));
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        } else {
            if (getConfig().getDouble("arena.fighterThree.blockX") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterThree.blockX"));
            }
            if (getConfig().getDouble("arena.fighterThree.blockY") > 0) {
                location.setY(getConfig().getDouble("arena.fighterThree.blockY"));
            }
            if (getConfig().getDouble("arena.fighterThree.blockZ") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterThree.blockZ"));
            }

            plugin.getArenaManager().setFighterThree(location);
        }

        location = new Location(world, 0,0,0);

        if (getConfig().getConfigurationSection("arena.fighterFour") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe hebt geen fighterFour ingevoerd bij &4&larena.yml &cdoe dit eerst!"));
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        } else {
            if (getConfig().getDouble("arena.fighterFour.blockX") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterFour.blockX"));
            }
            if (getConfig().getDouble("arena.fighterFour.blockY") > 0) {
                location.setY(getConfig().getDouble("arena.fighterFour.blockY"));
            }
            if (getConfig().getDouble("arena.fighterFour.blockZ") > 0) {
                location.setZ(getConfig().getDouble("arena.fighterFour.blockZ"));
            }

            plugin.getArenaManager().setFighterFour(location);
        }
    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder() + "/configuration", "arena.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("configuration/arena.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }

    public File getConfigFile() {
        return configFile;
    }

    public void save(String path, String strValue, Boolean boolValue, Integer intValue) {
        if (strValue != null) config.set(path, strValue);
        if (boolValue != null) config.set(path, boolValue);
        if (intValue != null) config.set(path, intValue);

        try {
            config.save(this.configFile);
            this.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
