package me.quexer.lobbysystem;

import de.dytanic.cloudnet.bridge.CloudServer;
import me.quexer.lobbysystem.commands.BuildCMD;
import me.quexer.lobbysystem.commands.setLocCMD;
import me.quexer.lobbysystem.gadgets.GadgetsAPI;
import me.quexer.lobbysystem.gadgets.listeners.GadgetsListener;
import me.quexer.lobbysystem.listeners.*;
import me.quexer.lobbysystem.utils.InventoryManager;
import me.quexer.lobbysystem.utils.LocationManager;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.Hologramm;
import me.quexer.serverapi.game.GameAPI;
import me.quexer.serverapi.nick.NickAPI;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Lobby extends JavaPlugin {

    private static String prefix;

    private static Location spawnLocation;
    private static Location quickSGLocation;
    private static Location chestHologrammLocation;
    private static Location chestLocation;
    private static Hologramm chestHologramm;
    private static Location chestrollLocation;
    private static InventoryManager inventoryManager;
    private static LocationManager locationManager;
    private static GadgetsAPI gadgetsAPI;
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


        initLocations();
        Bukkit.getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");


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
        initCommands();
        initListeners();
        setGadgetsAPI(new GadgetsAPI());
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
        getGadgetsAPI().startScheduler();
        ServerAPI.getMySQL().update("CREATE TABLE IF NOT EXISTS GadgetsAPI(UUID VARCHAR(100), ACTIVE VARCHAR(100), HERZEN INT, FLAMES INT, NOTES INT, SMOKE INT, CLOUD INT, LAVA INT, THUNDER INT, RAINBOW INT, ENDER INT, MAGIC INT, CIRT INT, BOWFIRE INT)");
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
        Bukkit.getPluginManager().registerEvents(new SpielerVerstecken(), this);
        Bukkit.getPluginManager().registerEvents(new ItemHeld(), this);
        Bukkit.getPluginManager().registerEvents(new NickLobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbySwitcher(), this);
        Bukkit.getPluginManager().registerEvents(new GadgetsListener(), this);
        Bukkit.getPluginManager().registerEvents(new JumpPads(), this);
    }

    private void initCommands() {
        Bukkit.getPluginCommand("setLoc").setExecutor(new setLocCMD());
        Bukkit.getPluginCommand("build").setExecutor(new BuildCMD());
    }

    private void initStrings() {
        setPrefix("§8✖ §eLobby §8§l➜ ");
    }


    private void initLocations() {
        if(ServerAPI.getLocationAPI().exist("Lobby.Spawn")) {
            setSpawnLocation(ServerAPI.getLocationAPI().getLocation("Lobby.Spawn"));
        }
        if(ServerAPI.getLocationAPI().exist("Lobby.QuickSG")) {
            setQuickSGLocation(ServerAPI.getLocationAPI().getLocation("Lobby.QuickSG"));
        }
        if(ServerAPI.getLocationAPI().exist("Lobby.Chest")) {
            setChestLocation(ServerAPI.getLocationAPI().getLocation("Lobby.Chest"));
        }
        if(ServerAPI.getLocationAPI().exist("Lobby.Chestholo")) {
            setChestHologrammLocation(ServerAPI.getLocationAPI().getLocation("Lobby.Chestholo"));
        }
        if(ServerAPI.getLocationAPI().exist("Lobby.Chestroll")) {
            setChestrollLocation(ServerAPI.getLocationAPI().getLocation("Lobby.Chestroll"));
            setChestHologramm(new Hologramm(getChestHologrammLocation(), Arrays.asList("§8§l➡ §7§lHier ist der §e§lChest§7-§e§lRoll","§a§l", "§7§lÖffne eine §e§lKiste §7§lum §4Gadgets §7§lzu erhalten","§7§lEine §e§lKiste §7kostet §e5§7.§e000 §e§lCoins")));
            getChestrollLocation().subtract(0, 1,0).getBlock().setType(Material.ENDER_CHEST);
        }
        setLocationManager(new LocationManager(getSpawnLocation(), getQuickSGLocation(), getChestLocation()));


    }
    public static void connect(String message, Player p) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        p.sendPluginMessage(getInstance(), "BungeeCord", b.toByteArray());
    }

    public static void setPrefix(String prefix) {
        Lobby.prefix = prefix;
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void setLocationManager(LocationManager locationManager) {
        Lobby.locationManager = locationManager;
    }

    public static Location getSpawnLocation() {
        return spawnLocation;
    }

    public static GadgetsAPI getGadgetsAPI() {
        return gadgetsAPI;
    }

    public static void setGadgetsAPI(GadgetsAPI gadgetsAPI) {
        Lobby.gadgetsAPI = gadgetsAPI;
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

    public static Location getChestLocation() {
        return chestLocation;
    }

    public static void setChestLocation(Location chestLocation) {
        Lobby.chestLocation = chestLocation;
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

    public static Hologramm getChestHologramm() {
        return chestHologramm;
    }

    public static void setChestHologramm(Hologramm chestHologramm) {
        Lobby.chestHologramm = chestHologramm;
    }

    public static Location getChestrollLocation() {
        return chestrollLocation;
    }

    public static void setChestrollLocation(Location chestrollLocation) {
        Lobby.chestrollLocation = chestrollLocation;
    }

    public static Location getChestHologrammLocation() {
        return chestHologrammLocation;
    }

    public static void setChestHologrammLocation(Location chestHologrammLocation) {
        Lobby.chestHologrammLocation = chestHologrammLocation;
    }
}
