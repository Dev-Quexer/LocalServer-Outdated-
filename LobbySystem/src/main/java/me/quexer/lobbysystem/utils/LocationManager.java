package me.quexer.lobbysystem.utils;

import org.bukkit.Location;

public class LocationManager {

    private Location Spawn;
    private Location QuickSG;
    private Location Chest;


    public LocationManager(Location spawn, Location quickSG, Location chest) {
        Spawn = spawn;
        QuickSG = quickSG;
        Chest = chest;
    }

    public Location getSpawn() {
        return Spawn;
    }

    public Location getQuickSG() {
        return QuickSG;
    }

    public Location getChest() {
        return Chest;
    }
}
