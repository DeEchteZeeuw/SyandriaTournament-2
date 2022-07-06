package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.UUID;

public class OnDeath implements Listener {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (plugin.getTournamentManager().isActiveTournament()) {
            if (plugin.getTournamentManager().getCurrentTournament().isParticipant(player.getUniqueId())) {
                if (plugin.getTournamentManager().getCurrentTournament().isInBattle(player.getUniqueId())) {
                    if (plugin.getTournamentManager().getCurrentTournament().getTeams()) {

                    } else {
                        if (plugin.getTournamentManager().getCurrentTournament().battleSize() == 1) {
                            ArrayList<UUID> winner = null;
                            if (plugin.getTournamentManager().getCurrentTournament().getFightingTeamOne().size() == 1) winner = plugin.getTournamentManager().getCurrentTournament().getFightingTeamOne();
                            if (plugin.getTournamentManager().getCurrentTournament().getFightingTeamTwo().size() == 1) winner = plugin.getTournamentManager().getCurrentTournament().getFightingTeamTwo();
                            if (plugin.getTournamentManager().getCurrentTournament().getFightingTeamThree().size() == 1) winner = plugin.getTournamentManager().getCurrentTournament().getFightingTeamThree();
                            if (plugin.getTournamentManager().getCurrentTournament().getFightingTeamFour().size() == 1) winner = plugin.getTournamentManager().getCurrentTournament().getFightingTeamFour();

                            if (winner != null) {
                                plugin.getTournamentManager().getCurrentTournament().setBattleWinner(winner);
                            }
                        } else if (plugin.getTournamentManager().getCurrentTournament().battleSize() == 0) {
                            plugin.getTournamentManager().getCurrentTournament().setBattle();
                        }
                    }

                    if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasInventory(player.getUniqueId())) {
                        player.getInventory().setContents(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory(player.getUniqueId()));
                        plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeInventory(player.getUniqueId());
                    }

                    if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasLocation(player.getUniqueId())) {
                        player.teleport(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation(player.getUniqueId()));
                        plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeLocation(player.getUniqueId());
                    }

                    plugin.getTournamentManager().getCurrentTournament().removePlayer(player.getUniqueId());
                }
            }
        }
    }
}
