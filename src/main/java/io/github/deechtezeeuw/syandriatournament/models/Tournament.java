package io.github.deechtezeeuw.syandriatournament.models;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.managers.LockerRoomManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Tournament {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    // Tournament general settings
    private UUID uuid;
    private boolean busy = false;
    private boolean teams = false;

    private boolean fromConfig = false;

    private int price = 1;
    private String kit = "default";

    private int minimumPlayers = 8;
    private int maximumPlayers = 16;

    private int round = 1;

    private LocalDateTime date = LocalDateTime.now();

    // Participants room (Players who are competing in the tournament)
    private ArrayList<UUID> participantPlayers = new ArrayList<>();

    // Waiting room (Players waiting for a dual)
    private ArrayList<UUID> waitingPlayers = new ArrayList<>();

    // Current battle (Players who are in the current battle)
    private ArrayList<UUID> fightingTeamOne = new ArrayList<>();
    private ArrayList<UUID> deadFightingTeamsOne = new ArrayList<>();
    private ArrayList<UUID> fightingTeamTwo = new ArrayList<>();
    private ArrayList<UUID> deadFightingTeamsTwo = new ArrayList<>();
    private ArrayList<UUID> fightingTeamThree = new ArrayList<>();
    private ArrayList<UUID> deadFightingTeamsThree = new ArrayList<>();
    private ArrayList<UUID> fightingTeamFour = new ArrayList<>();
    private ArrayList<UUID> deadFightingTeamsFour = new ArrayList<>();

    // Battle winners (Players who won there fight and are waiting to go to the next round)
    private ArrayList<UUID> battleWinners = new ArrayList<>();

    // Array with IDS of message loopings
    private ArrayList<Integer> messageLoops = new ArrayList<>();

    // Attempt to start the game
    private int conditionsAttemptMessage;

    // lockerRoomManager
    private LockerRoomManager lockerRoomManager = new LockerRoomManager();

    /* Change tournament settings */
    // Set UUID of the tournament
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    // Get uuid of tournament
    public UUID getUuid() {
        return uuid;
    }

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

    public void setFromConfig(boolean fromConfig) {
        this.fromConfig = fromConfig;
    }

    public boolean isFromConfig() {
        return fromConfig;
    }

    // Set price of the tournament
    public void setPrice(int amount) {
        this.price = amount;
    }

    // Get price of the tournament
    public int getPrice() {
        return this.price;
    }

    // Set kit of the tournament
    public void setKit(String kit) {
        this.kit = kit;
    }

    // Get kit of the tournament
    public String getKit() {
        return this.kit;
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

        for (UUID uuid : getBattleWinners()) {
            setParticipantInWaiting(uuid);
        }

        resetBattleWinners();
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

    // Can register
    public boolean canRegister() {
        if (this.isBusy()) return false;

        return (ChronoUnit.MILLIS.between(LocalDateTime.now(), this.date) <= 900000);
    }

    // Get milliseconds till start
    public int getMilliSecondsTillStart() {
        if (this.isBusy()) return 0;

        return (int)ChronoUnit.MILLIS.between(LocalDateTime.now(), this.date);
    }

    // Get String of till register
    public String timeTillRegisterIsOpen() {
        String text = "";

        int s = (int)ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date) - 900;
        int sec = s % 60;
        int min = (s / 60)%60;
        int hours = (s/60)/60;

        String strSec=(sec<10)?"0"+Integer.toString(sec):Integer.toString(sec);
        String strmin=(min<10)?"0"+Integer.toString(min):Integer.toString(min);
        String strHours=(hours<10)?"0"+Integer.toString(hours):Integer.toString(hours);

        return strHours + ":" + strmin + ":" + strSec;
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

    // Add participant
    public void addParticipant(UUID uuid) {
        this.participantPlayers.add(uuid);
    }

    // Remove participant
    public void removeParticipant(UUID uuid) {
        this.participantPlayers.remove(uuid);
    }

    // Check if participant
    public boolean isParticipant(UUID uuid) {
        return this.participantPlayers.contains(uuid);
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

    // Dead people teams
    public ArrayList<UUID> getDeadTeamsOne() {
        return deadFightingTeamsOne;
    }

    public void addDeadTeamsOne(UUID uuid) {
        this.deadFightingTeamsOne.add(uuid);
    }

    public void resetDeadTeamsOne() {
        this.deadFightingTeamsOne = new ArrayList<>();
    }

    public ArrayList<UUID> getDeadTeamsTwo() {
        return deadFightingTeamsTwo;
    }

    public void addDeadTeamsTwo(UUID uuid) {
        this.deadFightingTeamsTwo.add(uuid);
    }

    public void resetDeadTeamsTwo() {
        this.deadFightingTeamsTwo = new ArrayList<>();
    }

    public ArrayList<UUID> getDeadTeamsThree() {
        return deadFightingTeamsThree;
    }

    public void addDeadTeamsThree(UUID uuid) {
        this.deadFightingTeamsThree.add(uuid);
    }

    public void resetDeadTeamsThree() {
        this.deadFightingTeamsThree = new ArrayList<>();
    }

    public ArrayList<UUID> getDeadTeamsFour() {
        return deadFightingTeamsFour;
    }

    public void addDeadTeamsFour(UUID uuid) {
        this.deadFightingTeamsFour.add(uuid);
    }

    public void resetDeadTeamsFour() {
        this.deadFightingTeamsFour = new ArrayList<>();
    }

    // Is in battle
    public boolean isInBattle(UUID uuid) {
        return (getFightingTeamOne().contains(uuid) || getFightingTeamTwo().contains(uuid) || getFightingTeamThree().contains(uuid) || getFightingTeamFour().contains(uuid));
    }

    // Get current battle players
    public ArrayList<UUID> getBattle() {
        ArrayList<UUID> battlers = new ArrayList<>();

        for (UUID uuid : getFightingTeamOne()) {
            battlers.add(uuid);
        }

        for (UUID uuid : getFightingTeamTwo()) {
            battlers.add(uuid);
        }

        for (UUID uuid : getFightingTeamThree()) {
            battlers.add(uuid);
        }

        for (UUID uuid : getFightingTeamFour()) {
            battlers.add(uuid);
        }

        return battlers;
    }

    // Get battle size
    public int battleSize() {
        return this.getFightingTeamOne().size() + this.getFightingTeamTwo().size() + this.getFightingTeamThree().size() + this.getFightingTeamFour().size();
    }

    /* Battle winner functions */
    public void setBattleWinner(ArrayList<UUID> winner) {
        // Check if battle was teams
        if (this.teams) {
            if (winner.size() == 1) {
                // Check which team the winner was from
                if (getFightingTeamOne().contains(winner)) {
                    for (UUID uuid : getDeadTeamsOne()) {
                        winner.add(uuid);
                    }
                }
                if (getFightingTeamTwo().contains(winner)) {
                    for (UUID uuid : getDeadTeamsTwo()) {
                        winner.add(uuid);
                    }
                }
                if (getFightingTeamThree().contains(winner)) {
                    for (UUID uuid : getDeadTeamsThree()) {
                        winner.add(uuid);
                    }
                }
                if (getFightingTeamFour().contains(winner)) {
                    for (UUID uuid : getDeadTeamsFour()) {
                        winner.add(uuid);
                    }
                }
            }
        }

        String winners = "";
        for (UUID uuid : winner) {
            if (winners.length() > 0) {
                winners += ", " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
            } else {
                winners += Bukkit.getServer().getPlayer(uuid).getDisplayName();
            }

            Player player = Bukkit.getServer().getPlayer(uuid);

            player.getInventory().clear();

            if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasInventory(player.getUniqueId())) {
                player.getInventory().setContents(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory(player.getUniqueId()));
                plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeInventory(player.getUniqueId());
            }

            if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasLocation(player.getUniqueId())) {
                player.teleport(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation(player.getUniqueId()));
                plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeLocation(player.getUniqueId());
            }

            addBattleWinner(uuid);
        }

        for (UUID uuid : participantPlayers) {
            if (Bukkit.getServer().getPlayer(uuid) != null) {
                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&aHet gevecht is gewonnen door &2&l" + winners + "&a!"));
            }
        }

        resetFightingTeamOne();
        resetDeadTeamsOne();
        resetFightingTeamTwo();
        resetDeadTeamsTwo();
        resetFightingTeamThree();
        resetDeadTeamsThree();
        resetFightingTeamFour();
        resetDeadTeamsFour();

        setBattle();
    }
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

    /* Array loop functions */
    // Reset array loop
    public void resetMessageLoop() {
        for (int id : this.messageLoops) {
            Bukkit.getScheduler().cancelTask(id);
        }
        this.messageLoops = new ArrayList<>();
    }

    // Create messages till start
    public void tillStartMessages() {
        if ((int)ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(30, ChronoUnit.MINUTES)) > 0) {
            this.messageLoops.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Bukkit.getServer().broadcastMessage(plugin.getColor().colorPrefix("&aHet toernooi in &2&lSyandria &abegint over 30 minuten"));
                }
            }, 20L * (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(30, ChronoUnit.MINUTES))));
        }

        if ((int)ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(15, ChronoUnit.MINUTES)) > 0) {
            this.messageLoops.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Bukkit.getServer().broadcastMessage(plugin.getColor().colorPrefix("&aHet toernooi in &2&lSyandria &abegint over 15 minuten"));
                }
            }, 20L * (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(15, ChronoUnit.MINUTES))));
        }

        if ((int)ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(5, ChronoUnit.MINUTES)) > 0) {
            this.messageLoops.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Bukkit.getServer().broadcastMessage(plugin.getColor().colorPrefix("&aHet toernooi in &2&lSyandria &abegint over 5 minuten"));
                }
            }, 20L * (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date.minus(5, ChronoUnit.MINUTES))));
        }

        if ((int)ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date) > 0) {
            this.messageLoops.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    // Start game
                    conditionCheck();
                }
            }, 20L * (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), this.date)));
        } else {
            conditionCheck();
        }
    }

    // Check if the conditions of the tournament have been met.
    public void conditionCheck() {
        // Check if the minimum of participants has been reached AND check if the battles are correct for the first round
        if (this.participantPlayers.size() < this.minimumPlayers || (this.teams ? participantPlayers.size() % 4 != 0 : this.participantPlayers.size() % 2 != 0)) {
            new BukkitRunnable() {
                int count = 0;
                @Override
                public void run() {
                    if (participantPlayers.size() >= minimumPlayers && (teams ? participantPlayers.size() % 4 == 0 : participantPlayers.size() % 2 == 0)) {
                        cancel();
                        // All good start the tournament
                        return;
                    }

                    for (UUID uuid : participantPlayers) {
                        if (Bukkit.getServer().getPlayer(uuid) != null) {
                            if (count == 0) {
                                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi kan pas beginnen bij een evenaantal spelers voor de eerste ronde en bij een minimum van &4&l" + minimumPlayers + " &cis dit na &4&l10 &cminuten nog niet het geval dan word het toernooi gestaakt."));
                            }

                            if (count == 300) {
                                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi kan pas beginnen bij een evenaantal spelers voor de eerste ronde en bij een minimum van &4&l" + minimumPlayers + " &cis dit na &4&l5 &cminuten nog niet het geval dan word het toernooi gestaakt."));
                            }

                            if (count == 480) {
                                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi kan pas beginnen bij een evenaantal spelers voor de eerste ronde en bij een minimum van &4&l" + minimumPlayers + " &cis dit na &4&l2 &cminuten nog niet het geval dan word het toernooi gestaakt."));
                            }

                            if (count == 540) {
                                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi kan pas beginnen bij een evenaantal spelers voor de eerste ronde en bij een minimum van &4&l" + minimumPlayers + " &cis dit na &4&l1 &cminuut nog niet het geval dan word het toernooi gestaakt."));
                            }

                            if (count == 600) {
                                Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi is gestaakt!"));
                            }
                        }
                    }
                    count++;
                }
            }.runTaskTimer(plugin, 0, 20L);
            return;
        }

        Bukkit.getScheduler().cancelTask(conditionsAttemptMessage);
        // All good start the tournament
        start();
    }

    // Start tournament
    public void start() {
        // Set tournament to busy
        setBusy(true);

        // Message all participants that it will start
        for (UUID uuid : participantPlayers) {
            if (Bukkit.getServer().getPlayer(uuid) != null) {
                String[] message = {
                        plugin.getColor().color("&7&m-----------------------------------------------------"),
                        plugin.getColor().colorPrefix("&aHet toernooi gaat beginnen!"),
                        plugin.getColor().colorPrefix("&aJe vecht elke ronde 1x!"),
                        plugin.getColor().colorPrefix("&aAan het einde " + (this.getTeams() ? "zijn er 2 winnaars" : "is er 1 winnaar") + ", succes!"),
                        plugin.getColor().color("&7&m-----------------------------------------------------"),
                };
                Bukkit.getServer().getPlayer(uuid).sendMessage(message);

                setParticipantInWaiting(uuid);
            } else {
                participantPlayers.remove(uuid);
            }
        }

        // Start battle
        setBattle();
    }

    // Set new battle
    public void setBattle() {
        // Check reset one
        if (!getFightingTeamOne().isEmpty() || getFightingTeamOne().size() > 0) resetFightingTeamOne();
        if (!getDeadTeamsOne().isEmpty() || getDeadTeamsOne().size() > 0) resetDeadTeamsOne();
        // Check reset two
        if (!getFightingTeamTwo().isEmpty() || getFightingTeamTwo().size() > 0) resetFightingTeamTwo();
        if (!getDeadTeamsTwo().isEmpty() || getDeadTeamsTwo().size() > 0) resetDeadTeamsTwo();
        // Check reset three
        if (!getFightingTeamThree().isEmpty() || getFightingTeamThree().size() > 0) resetFightingTeamThree();
        if (!getDeadTeamsThree().isEmpty() || getDeadTeamsThree().size() > 0) resetDeadTeamsThree();
        // Check reset four
        if (!getFightingTeamFour().isEmpty() || getFightingTeamFour().size() > 0) resetFightingTeamFour();
        if (!getDeadTeamsFour().isEmpty() || getDeadTeamsFour().size() > 0) resetFightingTeamFour();

        // Check if duos or single
        if (getTeams()) {
            // Teams
            // Check if participants is 2
            if (participantPlayers.size() <= 2) {
                tournamentWin(true);
                return;
            }
            // Check if waiting is empty
            if (getWaitingSize() == 0 && participantPlayers.size() > 0) {
                nextRound();
                return;
            }

            // Check if there are 6 participants waiting
            if (getWaitingSize() % 4 != 0) {
                // Check if the numbers are divisble by 4
                // Send 3 teams of 2 into battle
                // FighterTeamOne
                for (int i = 0; i < 2; i++) {
                    UUID fighterOne = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                    removeFromWaiting(fighterOne);
                    addToFightingTeamOne(fighterOne);

                    // Store inventory
                    lockerRoomManager.storeInventory(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getInventory().getContents());
                    Bukkit.getServer().getPlayer(fighterOne).getInventory().clear();
                    // Store location
                    lockerRoomManager.storeLocation(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getLocation());
                    // Give kit
                    plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterOne), kit);
                    // Send to the arena
                    Bukkit.getServer().getPlayer(fighterOne).teleport(plugin.getArenaManager().getFighterOne());
                }

                // fighterTeamTwo
                for (int i = 0; i < 2; i++) {
                    UUID fighterTwo = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                    removeFromWaiting(fighterTwo);
                    addToFightingTeamTwo(fighterTwo);

                    // Store inventory
                    lockerRoomManager.storeInventory(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getInventory().getContents());
                    Bukkit.getServer().getPlayer(fighterTwo).getInventory().clear();
                    // Store location
                    lockerRoomManager.storeLocation(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getLocation());
                    // Give kit
                    plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterTwo), kit);
                    // Send to arena
                    Bukkit.getServer().getPlayer(fighterTwo).teleport(plugin.getArenaManager().getFighterTwo());
                }

                // fighterTeamThree
                for (int i = 0; i < 2; i++) {
                    UUID fighterThree = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                    removeFromWaiting(fighterThree);
                    addToFightingTeamTwo(fighterThree);

                    // Store inventory
                    lockerRoomManager.storeInventory(fighterThree, Bukkit.getServer().getPlayer(fighterThree).getInventory().getContents());
                    Bukkit.getServer().getPlayer(fighterThree).getInventory().clear();
                    // Store location
                    lockerRoomManager.storeLocation(fighterThree, Bukkit.getServer().getPlayer(fighterThree).getLocation());
                    // Give kit
                    plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterThree), kit);
                    // Send to arena
                    Bukkit.getServer().getPlayer(fighterThree).teleport(plugin.getArenaManager().getFighterThree());
                }

                String teamOne = "";
                for (UUID uuid : getFightingTeamOne()) {
                    if (teamOne.length() > 0) {
                        teamOne += " & " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    } else {
                        teamOne += Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    }
                }

                String teamTwo = "";
                for (UUID uuid : getFightingTeamTwo()) {
                    if (teamTwo.length() > 0) {
                        teamTwo += " & " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    } else {
                        teamTwo += Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    }
                }

                String teamThree = "";
                for (UUID uuid : getFightingTeamThree()) {
                    if (teamThree.length() > 0) {
                        teamThree += " & " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    } else {
                        teamThree += Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    }
                }


                for (UUID uuid : participantPlayers) {
                    if (Bukkit.getServer().getPlayer(uuid) != null) {
                        Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&aRonde &2&l" + round + "&a: &2&l" + teamOne + " &avs &2&l" + teamTwo + " &avs &2&l" + teamThree + "&a!"));
                    }
                }
            } else {
                // Send 2 teams of 2 into battle
                // FighterTeamOne
                for (int i = 0; i < 2; i++) {
                    UUID fighterOne = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                    removeFromWaiting(fighterOne);
                    addToFightingTeamOne(fighterOne);

                    // Store inventory
                    lockerRoomManager.storeInventory(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getInventory().getContents());
                    Bukkit.getServer().getPlayer(fighterOne).getInventory().clear();
                    // Store location
                    lockerRoomManager.storeLocation(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getLocation());
                    // Give kit
                    plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterOne), kit);
                    // Send to the arena
                    Bukkit.getServer().getPlayer(fighterOne).teleport(plugin.getArenaManager().getFighterOne());
                }

                // fighterTeamTwo
                for (int i = 0; i < 2; i++) {
                    UUID fighterTwo = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                    removeFromWaiting(fighterTwo);
                    addToFightingTeamTwo(fighterTwo);

                    // Store inventory
                    lockerRoomManager.storeInventory(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getInventory().getContents());
                    Bukkit.getServer().getPlayer(fighterTwo).getInventory().clear();
                    // Store location
                    lockerRoomManager.storeLocation(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getLocation());
                    // Give kit
                    plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterTwo), kit);
                    // Send to arena
                    Bukkit.getServer().getPlayer(fighterTwo).teleport(plugin.getArenaManager().getFighterTwo());
                }

                String teamOne = "";
                for (UUID uuid : getFightingTeamOne()) {
                    if (teamOne.length() > 0) {
                        teamOne += " & " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    } else {
                        teamOne += Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    }
                }

                String teamTwo = "";
                for (UUID uuid : getFightingTeamTwo()) {
                    if (teamOne.length() > 0) {
                        teamTwo += " & " + Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    } else {
                        teamTwo += Bukkit.getServer().getPlayer(uuid).getDisplayName();
                    }
                }


                for (UUID uuid : participantPlayers) {
                    if (Bukkit.getServer().getPlayer(uuid) != null) {
                        Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&aRonde &2&l" + round + "&a: &2&l" + teamOne + " &avs &2&l" + teamTwo + "&a!"));
                    }
                }
            }
        } else {
            // Solos
            // Check if participants if 1
            if (participantPlayers.size() == 1) {
                tournamentWin(false);
                return;
            }

            // Check if waiting is empty
            if (getWaitingSize() == 0 && participantPlayers.size() > 0) {
                nextRound();
                return;
            }

            // Check if there are three participants waiting
            if (getWaitingSize() % 2 != 0) {
                // Set 3 into battle
                // FighterOne
                UUID fighterOne = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                removeFromWaiting(fighterOne);
                addToFightingTeamOne(fighterOne);

                // Store inventory
                lockerRoomManager.storeInventory(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getInventory().getContents());
                Bukkit.getServer().getPlayer(fighterOne).getInventory().clear();
                // Store location
                lockerRoomManager.storeLocation(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getLocation());
                // Give kit
                plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterOne), kit);
                // Send to the arena
                Bukkit.getServer().getPlayer(fighterOne).teleport(plugin.getArenaManager().getFighterOne());

                // FighterTwo
                UUID fighterTwo = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                removeFromWaiting(fighterTwo);
                addToFightingTeamTwo(fighterTwo);

                // Store inventory
                lockerRoomManager.storeInventory(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getInventory().getContents());
                Bukkit.getServer().getPlayer(fighterTwo).getInventory().clear();
                // Store location
                lockerRoomManager.storeLocation(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getLocation());
                // Give kit
                plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterTwo), kit);
                // Send to arena
                Bukkit.getServer().getPlayer(fighterTwo).teleport(plugin.getArenaManager().getFighterTwo());

                // FighterThree
                UUID fighterThree = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                removeFromWaiting(fighterThree);
                addToFightingTeamTwo(fighterThree);

                // Store inventory
                lockerRoomManager.storeInventory(fighterThree, Bukkit.getServer().getPlayer(fighterThree).getInventory().getContents());
                Bukkit.getServer().getPlayer(fighterThree).getInventory().clear();
                // Store location
                lockerRoomManager.storeLocation(fighterThree, Bukkit.getServer().getPlayer(fighterThree).getLocation());
                // Give kit
                plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterThree), kit);
                // Send to arena
                Bukkit.getServer().getPlayer(fighterThree).teleport(plugin.getArenaManager().getFighterThree());

                for (UUID uuid : participantPlayers) {
                    if (Bukkit.getServer().getPlayer(uuid) != null) {
                        Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&aRonde &2&l" + round + "&a: &2&l" + Bukkit.getServer().getPlayer(fighterOne).getDisplayName() + " &avs &2&l" + Bukkit.getServer().getPlayer(fighterTwo).getDisplayName() + " &avs &2&l" + Bukkit.getServer().getPlayer(fighterThree).getDisplayName() + "&a!"));
                    }
                }
            } else {
                // Set 2 into battle
                // FighterOne
                UUID fighterOne = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                removeFromWaiting(fighterOne);
                addToFightingTeamOne(fighterOne);

                // Store inventory
                lockerRoomManager.storeInventory(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getInventory().getContents());
                Bukkit.getServer().getPlayer(fighterOne).getInventory().clear();
                // Store location
                lockerRoomManager.storeLocation(fighterOne, Bukkit.getServer().getPlayer(fighterOne).getLocation());
                // Give kit
                plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterOne), kit);
                // Send to the arena
                Bukkit.getServer().getPlayer(fighterOne).teleport(plugin.getArenaManager().getFighterOne());

                // FighterTwo
                UUID fighterTwo = getWaitingPlayers().get( (int)(Math.random() * getWaitingSize()));
                removeFromWaiting(fighterTwo);
                addToFightingTeamTwo(fighterTwo);

                // Store inventory
                lockerRoomManager.storeInventory(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getInventory().getContents());
                Bukkit.getServer().getPlayer(fighterTwo).getInventory().clear();
                // Store location
                lockerRoomManager.storeLocation(fighterTwo, Bukkit.getServer().getPlayer(fighterTwo).getLocation());
                // Give kit
                plugin.getKitsManager().getKit(Bukkit.getServer().getPlayer(fighterTwo), kit);
                // Send to arena
                Bukkit.getServer().getPlayer(fighterTwo).teleport(plugin.getArenaManager().getFighterTwo());

                for (UUID uuid : participantPlayers) {
                    if (Bukkit.getServer().getPlayer(uuid) != null) {
                        Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&aRonde &2&l" + round + "&a: &2&l" + Bukkit.getServer().getPlayer(fighterOne).getDisplayName() + " &avs &2&l" + Bukkit.getServer().getPlayer(fighterTwo).getDisplayName() + "&a!"));
                    }
                }
            }
        }
    }

    // Remove player from tournament
    public void removePlayer(UUID uuid) {
        if (participantPlayers.contains(uuid)) removeParticipant(uuid);
        if (getWaitingPlayers().contains(uuid)) getWaitingPlayers().remove(uuid);
        if (isInFightingTeamOne(uuid)) removeFromFightingTeamOne(uuid);
        if (isInFightingTeamTwo(uuid)) removeFromFightingTeamTwo(uuid);
        if (isInFightingTeamThree(uuid)) removeFromFightingTeamThree(uuid);
        if (isInFightingTeamFour(uuid)) removeFromFightingTeamFour(uuid);
        if (getBattleWinners().contains(uuid)) removeBattleWinner(uuid);

        Player player = Bukkit.getServer().getPlayer(uuid);

        player.getInventory().clear();

        if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasInventory(player.getUniqueId())) {
            player.getInventory().setContents(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getInventory(player.getUniqueId()));
            plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeInventory(player.getUniqueId());
        }

        if (plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().hasLocation(player.getUniqueId())) {
            player.teleport(plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().getLocation(player.getUniqueId()));
            plugin.getTournamentManager().getCurrentTournament().getLockerRoomManager().removeLocation(player.getUniqueId());
        }
    }

    public LockerRoomManager getLockerRoomManager() {
        return lockerRoomManager;
    }

    public void tournamentWin(boolean teams) {
        if (teams) {
            ArrayList<Player> winners = new ArrayList<>();
            for (UUID uuid : participantPlayers) {
                Player player = Bukkit.getServer().getPlayer(uuid);
                winners.add(player);

                if (this.getLockerRoomManager().hasInventory(player.getUniqueId())) {
                    player.getInventory().setContents(this.getLockerRoomManager().getInventory(player.getUniqueId()));
                    this.getLockerRoomManager().removeInventory(player.getUniqueId());
                }

                if (this.getLockerRoomManager().hasLocation(player.getUniqueId())) {
                    player.teleport(this.getLockerRoomManager().getLocation(player.getUniqueId()));
                    this.getLockerRoomManager().removeLocation(player.getUniqueId());
                }

                player.getInventory().addItem(new Pecunia().give(price));
            }
            Bukkit.broadcastMessage(plugin.getColor().colorPrefix("&aHet toernooi in &2&lSyandria &ais gewonnen door &2&l" + winners.toString() + "&a!"));
        } else {
            Bukkit.broadcastMessage(plugin.getColor().colorPrefix("&aHet toernooi in &2&lSyandria &ais gewonnen door &2&l" + Bukkit.getServer().getPlayer(participantPlayers.get(0)).getDisplayName() + "&a!"));

            Player player = Bukkit.getServer().getPlayer(participantPlayers.get(0));

            if (this.getLockerRoomManager().hasInventory(player.getUniqueId())) {
                player.getInventory().setContents(this.getLockerRoomManager().getInventory(player.getUniqueId()));
                this.getLockerRoomManager().removeInventory(player.getUniqueId());
            }

            if (this.getLockerRoomManager().hasLocation(player.getUniqueId())) {
                player.teleport(this.getLockerRoomManager().getLocation(player.getUniqueId()));
                this.getLockerRoomManager().removeLocation(player.getUniqueId());
            }

            player.getInventory().addItem(new Pecunia().give(price));
        }

        // Check if tournament is from config
        if (isFromConfig()) {
            resetParticipants();
            resetWaiting();
            resetFightingTeamOne();
            resetDeadTeamsOne();
            resetFightingTeamTwo();
            resetDeadTeamsTwo();
            resetFightingTeamThree();
            resetDeadTeamsThree();
            resetFightingTeamFour();
            resetDeadTeamsFour();
            resetBattleWinners();

            setBusy(false);
            round = 1;

            date = date.with(TemporalAdjusters.next(date.getDayOfWeek()));

            for (int i : messageLoops) {
                Bukkit.getServer().getScheduler().cancelTask(i);
            }
            messageLoops = new ArrayList<>();

            Bukkit.getScheduler().cancelTask(conditionsAttemptMessage);
            conditionsAttemptMessage = 0;

            for (UUID keys : lockerRoomManager.getInventory().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).getInventory().clear();
                    Bukkit.getServer().getPlayer(keys).getInventory().setContents(lockerRoomManager.getInventory(keys));
                }
            }

            for (UUID keys : lockerRoomManager.getLocation().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).teleport(lockerRoomManager.getLocation(keys));
                }
            }

            lockerRoomManager = new LockerRoomManager();

            plugin.getTournamentManager().addRegisteredTournament(this.uuid, this);
        }

        plugin.getTournamentManager().pickNextTournament();
    }

    public void forceStop() {
        // Check if tournament is from config
        if (isFromConfig()) {

            for (UUID uuid : participantPlayers) {
                if (Bukkit.getServer().getPlayer(uuid) != null) {
                    Bukkit.getServer().getPlayer(uuid).sendMessage(plugin.getColor().colorPrefix("&cHet toernooi is geforceerd gestopt!"));
                }
            }

            resetParticipants();
            resetWaiting();
            resetFightingTeamOne();
            resetDeadTeamsOne();
            resetFightingTeamTwo();
            resetDeadTeamsTwo();
            resetFightingTeamThree();
            resetDeadTeamsThree();
            resetFightingTeamFour();
            resetDeadTeamsFour();
            resetBattleWinners();

            setBusy(false);
            round = 1;

            date = date.with(TemporalAdjusters.next(date.getDayOfWeek()));

            for (int i : messageLoops) {
                Bukkit.getServer().getScheduler().cancelTask(i);
            }
            messageLoops = new ArrayList<>();

            Bukkit.getScheduler().cancelTask(conditionsAttemptMessage);
            conditionsAttemptMessage = 0;

            for (UUID keys : lockerRoomManager.getInventory().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).getInventory().clear();
                    Bukkit.getServer().getPlayer(keys).getInventory().setContents(lockerRoomManager.getInventory(keys));
                }
            }

            for (UUID keys : lockerRoomManager.getLocation().keySet()) {
                if (Bukkit.getServer().getPlayer(keys) != null) {
                    Bukkit.getServer().getPlayer(keys).teleport(lockerRoomManager.getLocation(keys));
                }
            }

            lockerRoomManager = new LockerRoomManager();

            plugin.getTournamentManager().addRegisteredTournament(this.uuid, this);
        }

        plugin.getTournamentManager().pickNextTournament();
    }
}
