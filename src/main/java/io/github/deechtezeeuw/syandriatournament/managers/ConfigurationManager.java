package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.configurations.Tournaments;

import java.io.File;

public class ConfigurationManager {
    private Tournaments tournaments;

    public ConfigurationManager() {
        // Check if configuration folder exists
        File dataFolder = new File(SyandriaTournament.getInstance().getDataFolder()+"/configuration");
        if (!dataFolder.exists()) dataFolder.mkdir();

        this.tournaments = new Tournaments();
    }

    public Tournaments getTournaments() {
        return tournaments;
    }
}
