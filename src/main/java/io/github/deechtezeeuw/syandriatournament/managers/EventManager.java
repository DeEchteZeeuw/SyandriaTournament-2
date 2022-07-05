package io.github.deechtezeeuw.syandriatournament.managers;

import io.github.deechtezeeuw.syandriatournament.SyandriaTournament;
import io.github.deechtezeeuw.syandriatournament.listeners.InventoryClick;

public class EventManager {
    private final SyandriaTournament plugin = SyandriaTournament.getInstance();

    public EventManager() {
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
    }
}
