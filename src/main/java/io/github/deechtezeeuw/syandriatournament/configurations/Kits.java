package io.github.deechtezeeuw.syandriatournament.configurations;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Kits extends Configuration {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private File configFile;
    private FileConfiguration config;

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
        if (getConfig().getConfigurationSection("kits") != null) {
            for (String kitName : getConfig().getConfigurationSection("kits").getKeys(false)) {
                ArrayList<ItemStack> items = new ArrayList<>();

                // Loop through items
                for (String part : getConfig().getConfigurationSection("kits." + kitName).getKeys(false)) {
                    if (getConfig().getString("kits."+kitName+"."+part+".material") == null) {
                        Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cKit &4&l" + kitName + " &citem &4&l" + part + " &comdat het geen material bevatte."));
                        continue;
                    }
                    if (Material.valueOf(getConfig().getString("kits."+kitName+"."+part+".material")) == null) {
                        Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cKit &4&l" + kitName + " &citem &4&l" + part + " &comdat het geen geldig material bevatte."));
                        continue;
                    }

                    int amount = 1;
                    if (getConfig().getInt("kits."+kitName+"."+part+".amount") > 1) {
                        amount = getConfig().getInt("kits."+kitName+"."+part+".amount");
                    }

                    ItemStack item = new ItemStack(Material.valueOf(getConfig().getString("kits."+kitName+"."+part+".material")), amount);

                    ItemMeta itemMeta = item.getItemMeta();

                    if (getConfig().getString("kits."+kitName+"."+part+".displayname") != null) {
                        itemMeta.setDisplayName(plugin.getColor().color(getConfig().getString("kits."+kitName+"."+part+".displayname")));
                    }

                    item.setItemMeta(itemMeta);

                    items.add(item);
                }

                plugin.getKitsManager().addKit(kitName, items.toArray(new ItemStack[0]));
            }
        }
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
