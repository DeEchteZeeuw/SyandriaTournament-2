package io.github.deechtezeeuw.syandriatournament.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ArenaManager {
    private Location fighterOne;
    private Location fighterTwo;
    private Location fighterThree;
    private Location fighterFour;

    public Location getFighterOne() {
        return fighterOne;
    }

    public void setFighterOne(Location fighterOne) {
        this.fighterOne = new Location(Bukkit.getServer().getWorlds().get(0), 350, 65, 517);

        fighterOne.
    }

    public Location getFighterTwo() {
        return fighterTwo;
    }

    public void setFighterTwo(Location fighterTwo) {
        this.fighterTwo = new Location(Bukkit.getServer().getWorlds().get(0), 350, 65, 517);
    }

    public Location getFighterThree() {
        return fighterThree;
    }

    public void setFighterThree(Location fighterThree) {
        this.fighterThree = new Location(Bukkit.getServer().getWorlds().get(0), 350, 65, 517);
    }

    public Location getFighterFour() {
        return fighterFour;
    }

    public void setFighterFour(Location fighterFour) {
        this.fighterFour = new Location(Bukkit.getServer().getWorlds().get(0), 350, 65, 517);
    }
}
