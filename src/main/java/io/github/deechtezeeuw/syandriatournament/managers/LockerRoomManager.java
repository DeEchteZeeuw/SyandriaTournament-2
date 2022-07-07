package io.github.deechtezeeuw.syandriatournament.managers;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class LockerRoomManager {
    private HashMap<UUID, ItemStack[]> inventory = new HashMap<>();
    private HashMap<UUID, Location> location = new HashMap<>();

    public void storeInventory(UUID uuid, ItemStack[] items) {
        inventory.put(uuid, items);
    }

    public boolean hasInventory(UUID uuid) {
        return inventory.containsKey(uuid);
    }

    public ItemStack[] getInventory(UUID uuid) {
        return inventory.get(uuid);
    }

    public void removeInventory(UUID uuid) {
        inventory.remove(uuid);
    }

    public void storeLocation(UUID uuid, Location location) {
        this.location.put(uuid, location);
    }

    public boolean hasLocation(UUID uuid) {
        return location.containsKey(uuid);
    }

    public Location getLocation(UUID uuid) {
        return location.get(uuid);
    }

    public void removeLocation(UUID uuid) {
        this.location.remove(uuid);
    }

    public HashMap<UUID, ItemStack[]> getInventory() {
        return inventory;
    }

    public HashMap<UUID, Location> getLocation() {
        return location;
    }
}
