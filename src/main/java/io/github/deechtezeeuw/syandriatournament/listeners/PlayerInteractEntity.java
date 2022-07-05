package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
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

        if (e.getHand().equals(EquipmentSlot.HAND) && CitizensAPI.getNPCRegistry().isNPC(entity)) {

        }
    }
}
