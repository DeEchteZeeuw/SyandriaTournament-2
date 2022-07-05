package io.github.deechtezeeuw.syandriatournament.configurations;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.models.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

public class Tournaments extends Configuration{
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();
    private File configFile;
    private FileConfiguration config;

    public Tournaments() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder() + "/configuration", "tournaments.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("configuration/tournaments.yml", false);
        }

        config= new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        // Check if the tournaments configuration section exists
        if (getConfig().getConfigurationSection("tournaments") == null) {
            Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cJe configuratie voor de toernooien klopt niet. Kijk goed of dit nog is, zoals het voorbeeld!"));
            return;
        }
        // Get the tournament days
        for (String day : getConfig().getConfigurationSection("tournaments").getKeys(false)) {
            // Loop through the days
            for (String tournamentNumber : getConfig().getConfigurationSection("tournaments." + day).getKeys(false)) {
                // Settings
                Tournament tournament = new Tournament();

                // Check if settings exists
                int price = getConfig().getInt("tournaments." + day + "." + tournamentNumber + ".settings.price");
                tournament.setPrice(price);

                boolean teams = getConfig().getBoolean("tournaments." + day + "." + tournamentNumber + ".settings.teams");
                tournament.setTeams(teams);

                int minimumPlayers = getConfig().getInt("tournaments." + day + "." + tournamentNumber + ".settings.players.minimum");
                if (minimumPlayers > 2) {
                    tournament.setMinimumPlayers(minimumPlayers);
                } else {
                    tournament.setMinimumPlayers(2);
                    Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cHet toernooi van &4&l" + day + " &cversie &4&l" + tournamentNumber + " &cheeft een minimum van 2 gekregen, omdat het minimum in de configuratie lager dan 2 was of niet bestond." ));
                }

                int maximumPlayers = getConfig().getInt("tournaments." + day + "." + tournamentNumber + ".settings.players.maximum");
                if (maximumPlayers > 2) {
                    tournament.setMaximumPlayers(maximumPlayers);
                } else {
                    tournament.setMaximumPlayers(2);
                    Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cHet toernooi van &4&l" + day + " &cversie &4&l" + tournamentNumber + " &cheeft een maximum van 2 gekregen, omdat het maximum in de configuratie lager dan 2 was of niet bestond." ));
                }

                String timeString = getConfig().getString("tournaments." + day + "." + tournamentNumber + ".time");
                if (timeString == null) {
                    Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cHet toernooi van &4&l" + day + " &cversie &4&l" + tournamentNumber + " &cis niet aangemaakt, omdat er geen tijd is ingevoerd"));
                    continue;
                }

                if (timeString.split(":").length < 2 || !plugin.isNumeric(timeString.split(":")[0]) || !plugin.isNumeric(timeString.split(":")[1])) {
                    Bukkit.getConsoleSender().sendMessage(plugin.getColor().colorPrefix("&cHet toernooi van &4&l" + day + " &cversie &4&l" + tournamentNumber + " &cis niet aangemaakt, omdat er geen officiele tijd is ingevoerd"));
                    continue;
                }

                int hour = Integer.parseInt(timeString.split(":")[0]);
                int minute = Integer.parseInt(timeString.split(":")[1]);

                LocalDateTime dateTime = LocalDate.now().atTime(hour, minute);

                if (day.equalsIgnoreCase("monday")) {
                    dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
                }

                switch (day.toLowerCase()) {
                    case "monday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
                        break;
                    case "tuesday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
                        break;
                    case "wednesday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
                        break;
                    case "thursday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
                        break;
                    case "friday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
                        break;
                    case "saturday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
                        break;
                    case "sunday":
                        dateTime = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                        break;
                }

                tournament.setDate(dateTime);

                UUID uuid = UUID.randomUUID();
                tournament.setUuid(uuid);

                plugin.getTournamentManager().addRegisteredTournament(uuid, tournament);
            }
        }
        plugin.getTournamentManager().pickNextTournament();
    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder() + "/configuration", "tournaments.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("configuration/tournaments.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }
}
