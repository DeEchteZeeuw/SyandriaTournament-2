package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.models.Tournament;

import java.util.ArrayList;

public class TournamentManager {

    private Tournament currentTournament;

    private ArrayList<Tournament> registratedTournaments;

    /*
    Functions for CurrentTournaments
     */

    public Tournament getCurrentTournament() {
        return currentTournament;
    }

    public void setCurrentTournament(Tournament currentTournament) {
        this.currentTournament = currentTournament;
    }
}
