package io.github.deechtezeeuw.syandriatournament.models;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Kit {
    private ItemStack helmet = null;
    private ItemStack chestplate = null;
    private ItemStack leggings = null;
    private ItemStack boots = null;

    private ArrayList<ItemStack> others = new ArrayList<>();

    public void dumpItems(ItemStack[] items) {
        for (ItemStack item : items) {
            // Check if it is a helmet
            if (item.getType().equals(Material.LEATHER_HELMET) ||
                item.getType().equals(Material.CHAINMAIL_HELMET) ||
                item.getType().equals(Material.IRON_HELMET) ||
                item.getType().equals(Material.GOLD_HELMET) ||
                item.getType().equals(Material.DIAMOND_HELMET)) {
                helmet = item;
            }

            // Check if it is a chestplate
            if (item.getType().equals(Material.LEATHER_CHESTPLATE) ||
                    item.getType().equals(Material.CHAINMAIL_CHESTPLATE) ||
                    item.getType().equals(Material.IRON_CHESTPLATE) ||
                    item.getType().equals(Material.GOLD_CHESTPLATE) ||
                    item.getType().equals(Material.DIAMOND_CHESTPLATE)) {
                chestplate = item;
            }

            // Check if it is a leggings
            if (item.getType().equals(Material.LEATHER_LEGGINGS) ||
                    item.getType().equals(Material.CHAINMAIL_LEGGINGS) ||
                    item.getType().equals(Material.IRON_LEGGINGS) ||
                    item.getType().equals(Material.GOLD_LEGGINGS) ||
                    item.getType().equals(Material.DIAMOND_LEGGINGS)) {
                leggings = item;
            }

            // Check if it is a boots
            if (item.getType().equals(Material.LEATHER_BOOTS) ||
                    item.getType().equals(Material.CHAINMAIL_BOOTS) ||
                    item.getType().equals(Material.IRON_BOOTS) ||
                    item.getType().equals(Material.GOLD_BOOTS) ||
                    item.getType().equals(Material.DIAMOND_BOOTS)) {
                boots = item;
            }

            // Set item in others
            others.add(item);
        }
    }

    public void setInventory(Player player) {
        player.getInventory().setHelmet((helmet != null) ? helmet : null);
        player.getInventory().setHelmet((chestplate != null) ? chestplate : null);
        player.getInventory().setHelmet((leggings != null) ? leggings : null);
        player.getInventory().setHelmet((boots != null) ? boots : null);

        player.getInventory().setContents((ItemStack[]) others.toArray());
    }

    public Kit defaultKit() {
        this.helmet = new ItemStack(Material.DIAMOND_HELMET);
        this.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        this.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        this.boots = new ItemStack(Material.DIAMOND_BOOTS);

        this.others.add(new ItemStack(Material.DIAMOND_SWORD));
        this.others.add(new ItemStack(Material.BOW));
        this.others.add(new ItemStack(Material.ARROW, 64));
        this.others.add(new ItemStack(Material.COOKED_BEEF, 16));

        return this;
    }
}
