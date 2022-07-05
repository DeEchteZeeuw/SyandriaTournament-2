package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TournamentManager {

    private Tournament currentTournament;

    private HashMap<UUID, Tournament> registeredTournaments = new HashMap<>();

    /* Functions for CurrentTournaments */
    public Tournament getCurrentTournament() {
        return currentTournament;
    }

    public void setCurrentTournament(Tournament currentTournament) {
        this.currentTournament = currentTournament;
    }

    /* Functions for registeredTournaments */
    // Get all registeredTournaments
    public HashMap<UUID, Tournament> getRegisteredTournaments() {
        return registeredTournaments;
    }

    public ArrayList<Tournament> getArrayRegistreredTournaments() {
        ArrayList<Tournament> tournaments = new ArrayList<>();

        this.registeredTournaments.forEach((UUID, Tournament) -> {
            tournaments.add(Tournament);
        });

        tournaments.sort((Tournament a , Tournament b) -> a.getDate().compareTo(b.getDate()));

        return tournaments;
    }

    // Get specific registeredTournaments
    public Tournament getRegisteredTournament(UUID uuid) {
        return this.registeredTournaments.get(uuid);
    }

    // Add to registeredTournaments
    public void addRegisteredTournament(UUID uuid, Tournament tournament) {
        this.registeredTournaments.put(uuid, tournament);
    }

    // Remove from registeredTournaments
    public void removeRegisteredTournament(UUID uuid) {
        this.registeredTournaments.remove(uuid);
    }

    // Reset registeredTournaments
    public void resetRegisteredTournaments() {
        this.registeredTournaments = new HashMap<>();
    }

    /* Tournament information functions */
    // See if there is a tournament active or registered
    public boolean isThereAnTournament() {
        return (this.currentTournament != null || this.registeredTournaments.size() > 0);
    }

    // Check if there is an tournament today
    public boolean isThereAnTournamentToday() {
        // Check if there are tournaments
        if (isThereAnTournament()) {
            if (isActiveTournament()) return true;
            for (Tournament tournament : getArrayRegistreredTournaments()) {
                if (tournament.getDate().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) return true;
            }
        }
        return false;
    }

    // Get todays tournaments
    public ArrayList<Tournament> getTodaysTournaments() {
        ArrayList<Tournament> tournaments = new ArrayList<>();

        if (isActiveTournament()) {
            if (!getCurrentTournament().isBusy()) {
                if (getCurrentTournament().getDate().getHour() > LocalDateTime.now().getHour() && getCurrentTournament().getDate().getMinute() > LocalDateTime.now().getMinute()) {
                    tournaments.add(currentTournament);
                }
            }
        }

        for (Tournament tournament : getArrayRegistreredTournaments()) {
            if (tournament.getDate().getDayOfMonth() != LocalDateTime.now().getDayOfMonth()) continue;
            if (tournament.getDate().getHour() <= LocalDateTime.now().getHour() && tournament.getDate().getMinute() <= LocalDateTime.now().getMinute()) continue;
            tournaments.add(tournament);
        }

        tournaments.sort((Tournament a , Tournament b) -> a.getDate().compareTo(b.getDate()));

        return tournaments;
    }

    // See if there is an active tournament
    public boolean isActiveTournament() {
        return (this.currentTournament != null);
    }

    public void pickNextTournament() {
        if (isThereAnTournament()) {
            if (!isActiveTournament()) {
                Tournament tournament = getArrayRegistreredTournaments().get(0);

                removeRegisteredTournament(tournament.getUuid());

                this.currentTournament = tournament;
            }
        }
    }
}
