package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.configurations.Arena;
import io.github.deechtezeeuw.syandriatournament.configurations.Kits;
import io.github.deechtezeeuw.syandriatournament.configurations.Npc;
import io.github.deechtezeeuw.syandriatournament.configurations.Tournaments;

import java.io.File;

public class ConfigurationManager {
    private Tournaments tournaments;
    private Npc npc;
    private Kits kits;
    private Arena arena;

    public ConfigurationManager() {
        // Check if configuration folder exists
        File dataFolder = new File(SyandriaTournament.getInstance().getDataFolder()+"/configuration");
        if (!dataFolder.exists()) dataFolder.mkdir();

        this.arena = new Arena();
        this.tournaments = new Tournaments();
        this.npc = new Npc();
        this.kits = new Kits();
    }

    public Tournaments getTournaments() {
        return tournaments;
    }

    public Npc getNpc() {
        return npc;
    }

    public Arena getArena() {
        return arena;
    }

    public Kits getKits() {
        return kits;
    }
}
