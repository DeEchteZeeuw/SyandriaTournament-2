package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.listeners.InventoryClick;
import io.github.deechtezeeuw.syandriatournament.listeners.OnDeath;
import io.github.deechtezeeuw.syandriatournament.listeners.PlayerInteractEntity;
import io.github.deechtezeeuw.syandriatournament.listeners.PlayerQuit;

public class EventManager {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    public EventManager() {
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OnDeath(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuit(), plugin);
    }
}
