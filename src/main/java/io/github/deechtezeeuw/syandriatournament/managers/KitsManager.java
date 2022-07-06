package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.configurations.Kits;
import io.github.deechtezeeuw.syandriatournament.models.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class KitsManager {
    private HashMap<String, Kit> kits = new HashMap<>();
    private Kit defaultKit = new Kit().defaultKit();

    public void addKit(String name, ItemStack[] items) {
        Kit kit = new Kit();
        kit.dumpItems(items);
        kits.put(name, kit);
    }

    public void getKit(Player player, String kit) {
        if (kits.containsKey(kit)) {
            kits.get(kit).setInventory(player);
        } else {
            // Give default kit
            defaultKit.setInventory(player);
        }
    }
}
