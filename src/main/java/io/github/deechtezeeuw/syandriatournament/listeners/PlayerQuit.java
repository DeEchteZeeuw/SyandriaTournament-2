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

        // Check if player is in current tournament playing
        if (plugin.getTournamentManager().isActiveTournament() && plugin.getTournamentManager().getCurrentTournament().isParticipant(player.getUniqueId())) {
            // Check if the player is in battle
            if (plugin.getTournamentManager().getCurrentTournament().isInBattle(player.getUniqueId())) {
                // Player is in battle check if the battle is teams
                if (plugin.getTournamentManager().getCurrentTournament().getTeams()) {
                    // Teams
                    // Check if other teams win now
                    ArrayList<UUID> teamOne = plugin.getTournamentManager().getCurrentTournament().getFightingTeamOne();
                    ArrayList<UUID> teamTwo = plugin.getTournamentManager().getCurrentTournament().getFightingTeamTwo();
                    ArrayList<UUID> teamThree = plugin.getTournamentManager().getCurrentTournament().getFightingTeamThree();
                    ArrayList<UUID> teamFour = plugin.getTournamentManager().getCurrentTournament().getFightingTeamFour();

                    // Remove temp quiting player
                    if (teamOne.contains(player.getUniqueId())) teamOne.remove(player.getUniqueId());
                    if (teamTwo.contains(player.getUniqueId())) teamTwo.remove(player.getUniqueId());
                    if (teamThree.contains(player.getUniqueId())) teamThree.remove(player.getUniqueId());
                    if (teamFour.contains(player.getUniqueId())) teamFour.remove(player.getUniqueId());

                    // Check if team one wins
                    if (teamOne.size() > 0 && teamTwo.size() == 0 && teamThree.size() == 0 && teamFour.size() == 0) {
                        // Set player as winner
                        plugin.getTournamentManager().getCurrentTournament().setBattleWinner(teamOne);
                    }

                    // Check if team two wins
                    if (teamOne.size() == 0 && teamTwo.size() > 0 && teamThree.size() == 0 && teamFour.size() == 0) {
                        // Set player as winner
                        plugin.getTournamentManager().getCurrentTournament().setBattleWinner(teamTwo);
                    }

                    // Check if team three wins
                    if (teamOne.size() == 0 && teamTwo.size() == 0 && teamThree.size() > 0 && teamFour.size() == 0) {
                        // Set player as winner
                        plugin.getTournamentManager().getCurrentTournament().setBattleWinner(teamThree);
                    }

                    // Check if team four wins
                    if (teamOne.size() == 0 && teamTwo.size() == 0 && teamThree.size() == 0 && teamFour.size() > 0) {
                        // Set player as winner
                        plugin.getTournamentManager().getCurrentTournament().setBattleWinner(teamFour);
                    }
                } else {
                    // Solo
                    // Check if the battle contains 1 more player
                    if (plugin.getTournamentManager().getCurrentTournament().battleSize() == 2) {
                        ArrayList<UUID> winner = plugin.getTournamentManager().getCurrentTournament().getBattle();
                        winner.remove(player.getUniqueId());

                        // Set player as winner
                        plugin.getTournamentManager().getCurrentTournament().setBattleWinner(winner);
                    }
                }
            }

            plugin.getTournamentManager().getCurrentTournament().removePlayer(player.getUniqueId());
        }
    }
}
