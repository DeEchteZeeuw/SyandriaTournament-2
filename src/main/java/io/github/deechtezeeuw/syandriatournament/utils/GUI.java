package io.github.deechtezeeuw.syandriatournament.utils;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GUI {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    public void openCalender(Player player, int offset) {
        Inventory gui = Bukkit.getServer().createInventory(
                player,
                45,
                plugin.getColor().colorPrefix("&9&lKalender")
        );

        // Black background
        for (int i = 0; i < 45;i++) {
            gui.setItem(i, item(" ", "STAINED_GLASS_PANE", 1, 15, new ArrayList<>()));
        }

        // Set current Tournament
        if (plugin.getTournamentManager().isActiveTournament()) {
            // Check if tournament is busy or not
            if (plugin.getTournamentManager().getCurrentTournament().isBusy()) {
                gui.setItem(13, item(plugin.getColor().color("&6&lHet toernooi is al bezig!"), "WOOL", 1, 1, new ArrayList<>()));
            } else {
                ArrayList<String> lore = new ArrayList<>();
                lore.add(plugin.getColor().color("&2&lDatum: &a" + plugin.getTournamentManager().getCurrentTournament().dateString()));
                lore.add(plugin.getColor().color("&2&lDuel: &a" + (plugin.getTournamentManager().getCurrentTournament().getTeams() ? "2vs2" : "1vs1")));
                lore.add(plugin.getColor().color("&2&lTijd: &a" + plugin.getTournamentManager().getCurrentTournament().timeString()));

                gui.setItem(13, item(plugin.getColor().color("&a&lJe kan je nog inschrijven!"), "WOOL", 1, 13, lore));
            }
        } else {
            gui.setItem(13, item(plugin.getColor().color("&c&lGeen actief toernooi"), "WOOL", 1, 14, new ArrayList<>()));
        }

        // Show coming 7 tournaments
        int x = 28;
        for (Tournament tournament : plugin.getTournamentManager().getArrayRegistreredTournaments()) {
            if (x > 34) break;
            ArrayList<String> lore = new ArrayList<>();
            lore.add(plugin.getColor().color("&2&lDatum: &a" + tournament.dateString()));
            lore.add(plugin.getColor().color("&2&lDuel: &a" + (tournament.getTeams() ? "2vs2" : "1vs1")));
            lore.add(plugin.getColor().color("&2&lTijd: &a" + tournament.timeString()));

            gui.setItem(x, item(plugin.getColor().color("&2&lGeregistreerd toernooi"), "WOOL", 1, 4, lore));
            x++;
        }

        while (x <= 34) {
            gui.setItem(x, item(" ", "WOOL", 1, 7, new ArrayList<>()));
            x++;
        }

        player.openInventory(gui);
    }

    public ItemStack item(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ArrayList<String> guiList() {
        ArrayList<String> list = new ArrayList<>();

        list.add(plugin.getColor().colorPrefix("&9&lKalender"));

        return list;
    }
}
