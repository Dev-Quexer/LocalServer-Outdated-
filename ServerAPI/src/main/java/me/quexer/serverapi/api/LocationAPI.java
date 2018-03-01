package me.quexer.serverapi.api;

import me.quexer.serverapi.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationAPI {

    private File file = new File(ServerAPI.getInstance().getDataFolder(), "locations.yml");

    public void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public void setLocation(String name, Player p)
    {
        Location loc = p.getEyeLocation();
        cfg.set("Location." + name + ".X", Double.valueOf(loc.getX()));
        cfg.set("Location." + name + ".Y", Double.valueOf(loc.getY()));
        cfg.set("Location." + name + ".Z", Double.valueOf(loc.getZ()));
        cfg.set("Location." + name + ".Yaw", Float.valueOf(loc.getYaw()));
        cfg.set("Location." + name + ".Pitch", Float.valueOf(loc.getPitch()));
        cfg.set("Location." + name + ".World", loc.getWorld().getName());
        save();
    }



    public boolean exist(String name)
    {
        if (cfg.get("Location." + name + ".X") == null) {
            return false;
        }
        return true;
    }


    public Location getLocation(String name)
    {
        double x = cfg.getDouble("Location." + name + ".X");
        double y = cfg.getDouble("Location." + name + ".Y");
        double z = cfg.getDouble("Location." + name + ".Z");
        double yaw = cfg.getDouble("Location." + name + ".Yaw");
        double pitch = cfg.getDouble("Location." + name + ".Pitch");
        World w = Bukkit.getWorld("world");
        Location loc = new Location(w, x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;
    }


}
