package io.github.deechtezeeuw.syandriatournament.listeners;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuit implements Listener {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (plugin.getTournamentManager().isActiveTournament()) {
            if (plugin.getTournamentManager().getCurrentTournament().isParticipant(player.getUniqueId())) {
                if (plugin.getTournamentManager().getCurrentTournament().isInBattle(player.getUniqueId())) {
                    if (plugin.getTournamentManager().getCurrentTournament().battleSize() == 1 && !plugin.getTournamentManager().getCurrentTournament().getTeams()) {
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
                } else {
                    plugin.getTournamentManager().getCurrentTournament().removePlayer(player.getUniqueId());
                }

                if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasInventory(player.getUniqueId())) {
                    player.getInventory().setContents(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory(player.getUniqueId()));
                    plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeInventory(player.getUniqueId());
                }

                if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasLocation(player.getUniqueId())) {
                    player.teleport(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation(player.getUniqueId()));
                    plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeLocation(player.getUniqueId());
                }
                return;
            }
        } else {
            for (Tournament tournament : plugin.getTournamentManager().getArrayRegistreredTournaments()) {
                tournament.removePlayer(player.getUniqueId());

                if (tournament.getLockerRoomManager().hasInventory(player.getUniqueId())) {
                    player.getInventory().setContents(tournament.getLockerRoomManager().getInventory(player.getUniqueId()));
                    tournament.getLockerRoomManager().removeInventory(player.getUniqueId());
                }

                if (tournament.getLockerRoomManager().hasLocation(player.getUniqueId())) {
                    player.teleport(tournament.getLockerRoomManager().getLocation(player.getUniqueId()));
                    tournament.getLockerRoomManager().removeLocation(player.getUniqueId());
                }
            }
        }
    }
}
