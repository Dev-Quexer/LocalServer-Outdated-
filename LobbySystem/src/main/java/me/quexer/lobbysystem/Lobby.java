package me.quexer.lobbysystem;

import de.dytanic.cloudnet.bridge.CloudServer;
import me.quexer.lobbysystem.commands.BuildCMD;
import me.quexer.lobbysystem.commands.setLocCMD;
import me.quexer.lobbysystem.listeners.Cancel;
import me.quexer.lobbysystem.listeners.JoinQuit;
import me.quexer.lobbysystem.listeners.Navigator;
import me.quexer.lobbysystem.utils.InventoryManager;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.FlyingItems;
import me.quexer.serverapi.api.Hologramm;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.api.LocationAPI;
import me.quexer.serverapi.game.GameAPI;
import me.quexer.serverapi.nick.NickAPI;
import me.quexer.serverapi.rank.Tablist;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Lobby extends JavaPlugin {

    private static String prefix;
    private static FlyingItems spawnHologramm;
    private static FlyingItems quickSGHologramm;
    private static Location spawnLocation;
    private static Location quickSGLocation;
    private static InventoryManager inventoryManager;
    private static Lobby instance;
    private static List<Player> build;

    @Override
    public void onEnable() {

        NickAPI.setNickOnThisServer(false);

                init();

        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            new GameAPI("Lobby", "25x1");
            CloudServer.getInstance().setMaxPlayersAndUpdate(25);
        }, 40);
        Bukkit.getScheduler().runTaskTimerAsynchronously(getInstance(), new Runnable() {
            @Override
            public void run() {
                     getSpawnHologramm().remove();
                     getQuickSGHologramm().remove();
                     getSpawnHologramm().spawn();
                     getQuickSGHologramm().spawn();
            }
        },20*3, 20*60*2);
        if(ServerAPI.getLocationAPI().exist("Lobby.Spawn")) {
            setSpawnLocation(ServerAPI.getLocationAPI().getLocation("Lobby.Spawn"));
        }
        if(ServerAPI.getLocationAPI().exist("Lobby.QuickSG")) {
            setQuickSGLocation(ServerAPI.getLocationAPI().getLocation("Lobby.QuickSG"));
        }
        if(getQuickSGLocation() != null) {
            setQuickSGHologramm(new FlyingItems());
            getQuickSGHologramm().setMaterial(new ItemBuilder(Material.CHEST).toItemStack());
            getQuickSGHologramm().setLocation(getQuickSGLocation());
            getQuickSGHologramm().setText("§eSurvivalGames §7in schnell");
            getQuickSGHologramm().spawn();
        }
        if(getSpawnLocation() != null) {
            setSpawnHologramm(new FlyingItems());
            getSpawnHologramm().setMaterial(new ItemBuilder(Material.MAGMA_CREAM).toItemStack());
            getSpawnHologramm().setLocation(getSpawnLocation());
            getSpawnHologramm().setText("§7Hier joinen die §eSpieler");
            getSpawnHologramm().spawn();

        }

    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if(!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }

    private void init() {
        setInstance(this);
        initConfig();
        initStrings();
        initLocations();
        initHologramms();
        initCommands();
        initListeners();

        Bukkit.setDefaultGameMode(GameMode.ADVENTURE);
        for (World world : Bukkit.getWorlds()) {
            world.setAmbientSpawnLimit(0);
            world.setAnimalSpawnLimit(0);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setPVP(false);
            world.setStorm(false);
            world.setThunderDuration(0);
        }
        setBuild(new ArrayList<>());
        setInventoryManager(new InventoryManager());

    }
    private void initConfig() {
        getConfig().options().header("CommunityPlugin von Quexer :)");
        getConfig().options().copyDefaults(true);
        saveConfig();


    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinQuit(), this);
        Bukkit.getPluginManager().registerEvents(new Cancel(), this);
        Bukkit.getPluginManager().registerEvents(new Navigator(), this);
    }

    private void initCommands() {
        Bukkit.getPluginCommand("setLoc").setExecutor(new setLocCMD());
        Bukkit.getPluginCommand("build").setExecutor(new BuildCMD());
    }

    private void initStrings() {
        setPrefix("§8✖ §eLobby §8§l➜ ");
    }


    private void initLocations() {


    }
    private void initHologramms() {

    }

    public static void setPrefix(String prefix) {
        Lobby.prefix = prefix;
    }

    public static FlyingItems getSpawnHologramm() {
        return spawnHologramm;
    }

    public static void setSpawnHologramm(FlyingItems spawnHologramm) {
        Lobby.spawnHologramm = spawnHologramm;
    }

    public static Location getSpawnLocation() {
        return spawnLocation;
    }

    public static void setSpawnLocation(Location spawnLocation) {
        Lobby.spawnLocation = spawnLocation;
    }
    public static String getPrefix() {
        return prefix;
    }

    public static Lobby getInstance() {
        return instance;
    }

    public static void setInstance(Lobby instance) {
        Lobby.instance = instance;
    }

    public static FlyingItems getQuickSGHologramm() {
        return quickSGHologramm;
    }

    public static void setQuickSGHologramm(FlyingItems quickSGHologramm) {
        Lobby.quickSGHologramm = quickSGHologramm;
    }

    public static Location getQuickSGLocation() {
        return quickSGLocation;
    }

    public static void setQuickSGLocation(Location quickSGLocation) {
        Lobby.quickSGLocation = quickSGLocation;
    }

    public static List<Player> getBuild() {
        return build;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static void setInventoryManager(InventoryManager inventoryManager) {
        Lobby.inventoryManager = inventoryManager;
    }

    public static void setBuild(List<Player> build) {
        Lobby.build = build;
    }
}
