package io.github.deechtezeeuw.syandriatournament.models;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Pecunia {
    public ItemStack give(int number) {
        ItemStack item = new ItemStack(Material.GHAST_TEAR, number);

        item.getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Pecunia"));

        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&oEigendom van de Bank van Gericla"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&cOfficiële Munteenheid"));

        item.getItemMeta().setLore(lore);

        return item;
    }
}
