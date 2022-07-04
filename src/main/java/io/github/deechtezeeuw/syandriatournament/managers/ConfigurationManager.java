package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.configurations.Tournaments;

public class ConfigurationManager {
    private Tournaments tournaments = new Tournaments();

    public ConfigurationManager() {
        this.tournaments = new Tournaments();
    }

    public Tournaments getTournaments() {
        return tournaments;
    }
}
