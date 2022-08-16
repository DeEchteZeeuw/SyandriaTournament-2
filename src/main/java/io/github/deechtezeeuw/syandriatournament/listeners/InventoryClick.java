package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import io.github.deechtezeeuw.syandriatournament.utils.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class InventoryClick implements Listener {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getClickedInventory().getName() == null || plugin.getGui() == null || plugin.getGui().guiList() == null) return;

        // Check if inventory is gui
        if (plugin.getGui().guiList().contains(e.getClickedInventory().getName())) {
            e.setCancelled(true);
        }

        // Check if inventory is register
        if (e.getClickedInventory().getName().equalsIgnoreCase(plugin.getColor().colorPrefix("&9&lRegistratie"))) {
            // Check if item is wool
            if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.WOOL) && e.getCurrentItem().getData().getData() == 13) {
                // Get tournament
                Tournament tournament;
                if (plugin.getTournamentManager().getCurrentTournament().getUuid().equals(UUID.fromString(plugin.getColor().strip(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size() - 1))))) {
                    // Check if the game is busy or not
                    if (!plugin.getTournamentManager().getCurrentTournament().isBusy()) {
                        // Check if you can register
                        if (plugin.getTournamentManager().getCurrentTournament().canRegister()) {
                            // Check if signed in
                            if (plugin.getTournamentManager().getCurrentTournament().isSignedIn(player.getUniqueId())) {
                                plugin.getTournamentManager().getCurrentTournament().signOut(player.getUniqueId());
                                player.sendMessage(plugin.getColor().colorPrefix("&cJe bent &4&luitgeschreven &cvoor dit toernooi."));
                            } else {
                                plugin.getTournamentManager().getCurrentTournament().signIn(player.getUniqueId());
                                player.sendMessage(plugin.getColor().colorPrefix("&aJe bent &2&lingeschreven &avoor dit toernooi."));
                            }
                        } else {
                            player.sendMessage(plugin.getColor().colorPrefix("&cJe kan je nog niet inschrijven voor dit toernooi."));
                        }
                    } else {
                        player.sendMessage(plugin.getColor().colorPrefix("&cHet toernooi is al bezig."));
                    }
                } else {
                    UUID uuid = UUID.fromString(plugin.getColor().strip(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size() - 1)));
                    // Check if tournament exists
                    if (plugin.getTournamentManager().getRegisteredTournament(uuid) != null) {
                        // Check if the game is busy or not
                        if (!plugin.getTournamentManager().getRegisteredTournament(uuid).isBusy()) {
                            // Check if you can register
                            if (plugin.getTournamentManager().getRegisteredTournament(uuid).canRegister()) {
                                // Check if signed in
                                if (plugin.getTournamentManager().getRegisteredTournament(uuid).isSignedIn(player.getUniqueId())) {
                                    plugin.getTournamentManager().getRegisteredTournament(uuid).signOut(player.getUniqueId());
                                    player.sendMessage(plugin.getColor().colorPrefix("&cJe bent &4&luitgeschreven &cvoor dit toernooi."));
                                } else {
                                    plugin.getTournamentManager().getRegisteredTournament(uuid).signOut(player.getUniqueId());
                                    player.sendMessage(plugin.getColor().colorPrefix("&aJe bent &2&lingeschreven &avoor dit toernooi."));
                                }
                            } else {
                                player.sendMessage(plugin.getColor().colorPrefix("&cJe kan je nog niet inschrijven voor dit toernooi."));
                            }
                        } else {
                            player.sendMessage(plugin.getColor().colorPrefix("&cHet toernooi is al bezig."));
                        }
                    } else {
                        player.sendMessage(plugin.getColor().colorPrefix("&cHet toernooi bestaat niet!"));
                        player.closeInventory();
                        if (plugin.getTournamentManager().isThereAnTournamentToday()) {
                            new GUI().openRegistry(player);
                        }
                    }
                }
            }
        }
    }
}
