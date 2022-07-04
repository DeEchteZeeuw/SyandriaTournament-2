package io.github.deechtezeeuw.syandriatournament.models;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Tournament {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    // Tournament general settings
    private boolean busy = false;
    private boolean teams = false;

    private int price = 1;

    private int minimumPlayers = 8;
    private int maximumPlayers = 16;

    private int round = 0;

    private LocalDateTime date = LocalDateTime.now();

    // Participants room (Players who are competing in the tournament)
    private ArrayList<UUID> participantPlayers = new ArrayList<>();

    // Waiting room (Players waiting for a dual)
    private ArrayList<UUID> waitingPlayers = new ArrayList<>();

    // Current battle (Players who are in the current battle)
    private ArrayList<UUID> fightingTeamOne = new ArrayList<>();
    private ArrayList<UUID> fightingTeamTwo = new ArrayList<>();
    private ArrayList<UUID> fightingTeamThree = new ArrayList<>();
    private ArrayList<UUID> fightingTeamFour = new ArrayList<>();

    // Battle winners (Players who won there fight and are waiting to go to the next round)
    private ArrayList<UUID> battleWinners = new ArrayList<>();

    /* Change tournament settings */
    // Set busy of the tournament
    public void setBusy(boolean value) {
        this.busy = value;
    }

    // Get busy of the tournament
    public boolean isBusy() {
        return busy;
    }

    // Set teams (2vs2) in the tournament
    public void setTeams(boolean value) {
        this.teams = value;
    }

    // Get teams value of the tournament
    public boolean getTeams() {
        return teams;
    }

    // Set price of the tournament
    public void setPrice(int amount) {
        this.price = amount;
    }

    // Get price of the tournament
    public int getPrice() {
        return this.price;
    }

    // Set minimum players of the tournament
    public void setMinimumPlayers(int amount) {
        this.minimumPlayers = amount;
    }

    // Get minimum players
    public int getMinimumPlayers() {
        return this.minimumPlayers;
    }

    // Set maximum players of the tournament
    public void setMaximumPlayers(int amount) {
        this.maximumPlayers = amount;
    }

    // Get maximum players of the tournament
    public int getMaximumPlayers() {
        return this.maximumPlayers;
    }

    // Upper round
    public void nextRound() {
        this.round = this.round + 1;
    }

    // Set game date
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Get game date
    public LocalDateTime getDate() {
        return date;
    }

    // Get date string
    public String dateString() {
        String text = "";

        if (date.getDayOfMonth() < 10) {
            text += "0" + date.getDayOfMonth();
        } else {
            text += date.getDayOfMonth();
        }

        text += "-";

        if (date.getMonthValue() < 10) {
            text += "0" + date.getMonthValue();
        } else {
            text += date.getMonthValue();
        }

        text += "-" + date.getYear();

        return text;
    }

    public String timeString() {
        String text = "";

        if (date.getHour() < 10) {
            text += "0" + date.getHour();
        } else {
            text += date.getHour();
        }

        text += ":";

        if (date.getMinute() < 10) {
            text += "0" + date.getMinute();
        } else {
            text += date.getMinute();
        }

        return text;
    }

    /* Signing in/out functions */
    // Check if player is signed in
    public boolean isSignedIn(UUID player) {
        return (this.participantPlayers.contains(player));
    }

    // Sign player in as participant
    public void signIn(UUID player) {
        this.participantPlayers.add(player);
    }

    // Sign player out as participant
    public void signOut(UUID player) {
        this.participantPlayers.remove(player);
    }

    // Reset participants
    public void resetParticipants() {
        this.participantPlayers = new ArrayList<>();
    }

    /* Waiting functions */
    // Set player in waiting
    public void setParticipantInWaiting(UUID player) {
        this.waitingPlayers.add(player);
    }

    // Remove player from waiting
    public void removeFromWaiting(UUID player) {
        this.waitingPlayers.remove(player);
    }

    // Get random from waiting
    public UUID randomWaiting() {
        return this.waitingPlayers.get((int)(Math.random() * waitingPlayers.size()));
    }

    // Get waiting players
    public ArrayList<UUID> getWaitingPlayers() {
        return waitingPlayers;
    }

    // Get size of waiting players
    public int getWaitingSize() {
        return waitingPlayers.size();
    }

    // Reset waiting
    public void resetWaiting() {
        this.waitingPlayers = new ArrayList<>();
    }

    /* Fighting team one functions */
    // Add to fighting team one
    public void addToFightingTeamOne(UUID player) {
        this.fightingTeamOne.add(player);
    }

    // Remove from fighting team one
    public void removeFromFightingTeamOne(UUID player) {
        this.fightingTeamOne.remove(player);
    }

    // Check if player is in fighting team one
    public boolean isInFightingTeamOne(UUID player) {
        return this.fightingTeamOne.contains(player);
    }

    // Get all fighters from fighting team one
    public ArrayList<UUID> getFightingTeamOne() {
        return fightingTeamOne;
    }

    // Reset fighting team one
    public void resetFightingTeamOne() {
        this.fightingTeamOne = new ArrayList<>();
    }

    /* Fighting team two functions */
    // Add to fighting team two
    public void addToFightingTeamTwo(UUID player) {
        this.fightingTeamTwo.add(player);
    }

    // Remove from fighting team two
    public void removeFromFightingTeamTwo(UUID player) {
        this.fightingTeamTwo.remove(player);
    }

    // Check if player is in fighting team two
    public boolean isInFightingTeamTwo(UUID player) {
        return this.fightingTeamTwo.contains(player);
    }

    // Get all fighters from fighting team two
    public ArrayList<UUID> getFightingTeamTwo() {
        return fightingTeamTwo;
    }

    // Reset fighting team one
    public void resetFightingTeamTwo() {
        this.fightingTeamTwo = new ArrayList<>();
    }

    /* Fighting team three functions */
    // Add to fighting team three
    public void addToFightingTeamThree(UUID player) {
        this.fightingTeamThree.add(player);
    }

    // Remove from fighting team three
    public void removeFromFightingTeamThree(UUID player) {
        this.fightingTeamThree.remove(player);
    }

    // Check if player is in fighting team three
    public boolean isInFightingTeamThree(UUID player) {
        return this.fightingTeamThree.contains(player);
    }

    // Get all fighters from fighting team three
    public ArrayList<UUID> getFightingTeamThree() {
        return fightingTeamThree;
    }

    // Reset fighting team three
    public void resetFightingTeamThree() {
        this.fightingTeamThree = new ArrayList<>();
    }

    /* Fighting team four functions */
    // Add to fighting team four
    public void addToFightingTeamFour(UUID player) {
        this.fightingTeamFour.add(player);
    }

    // Remove from fighting team four
    public void removeFromFightingTeamFour(UUID player) {
        this.fightingTeamFour.remove(player);
    }

    // Check if player is in fighting team four
    public boolean isInFightingTeamFour(UUID player) {
        return this.fightingTeamFour.contains(player);
    }

    // Get all fighters from fighting team four
    public ArrayList<UUID> getFightingTeamFour() {
        return fightingTeamFour;
    }

    // Reset fighting team four
    public void resetFightingTeamFour() {
        this.fightingTeamFour = new ArrayList<>();
    }

    /* Battle winner functions */
    // Add to winners list
    public void addBattleWinner(UUID player) {
        this.battleWinners.add(player);
    }

    // Remove from winners list
    public void removeBattleWinner(UUID player) {
        this.battleWinners.add(player);
    }

    // Get all battle winners in bulk
    public ArrayList<UUID> getBattleWinners() {
        return battleWinners;
    }

    // Reset battle winners in bulk
    public void resetBattleWinners() {
        this.battleWinners = new ArrayList<>();
    }
}
