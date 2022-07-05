package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        // Check if inventory is gui
        if (plugin.getGui().guiList().contains(e.getClickedInventory().getName())) {
            e.setCancelled(true);
        }
    }
}
