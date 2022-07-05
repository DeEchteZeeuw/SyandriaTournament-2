package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.utils.GUI;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntity implements Listener {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        // Check if entity is an npc from citizens
        if (e.getHand().equals(EquipmentSlot.HAND) && CitizensAPI.getNPCRegistry().isNPC(entity) && plugin.getConfigurationManager().getNpc().list.contains(CitizensAPI.getNPCRegistry().getNPC(entity).getUniqueId())) {
            // Open an inventory with all tournaments of today
            if (!plugin.getTournamentManager().isThereAnTournamentToday()) {
                player.sendMessage(plugin.getColor().colorPrefix("&cEr zijn geen toernooien om in te schrijven"));
                return;
            }

            new GUI().openRegistry(player);
        }
    }
}
