package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.models.Tournament;

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
}
