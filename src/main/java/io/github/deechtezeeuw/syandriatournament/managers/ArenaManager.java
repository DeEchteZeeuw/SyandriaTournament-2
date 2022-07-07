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
        this.fighterOne = fighterOne;
    }

    public Location getFighterTwo() {
        return fighterTwo;
    }

    public void setFighterTwo(Location fighterTwo) {
        this.fighterTwo = fighterTwo;
    }

    public Location getFighterThree() {
        return fighterThree;
    }

    public void setFighterThree(Location fighterThree) {
        this.fighterThree = fighterThree;
    }

    public Location getFighterFour() {
        return fighterFour;
    }

    public void setFighterFour(Location fighterFour) {
        this.fighterFour = fighterFour;
    }
}
