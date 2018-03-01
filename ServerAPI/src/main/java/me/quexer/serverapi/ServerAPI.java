package me.quexer.serverapi;

import me.quexer.serverapi.api.LocationAPI;
import me.quexer.serverapi.manager.SoundManager;
import me.quexer.serverapi.nick.NickAPI;
import me.quexer.serverapi.nick.commands.NickCMD;
import me.quexer.serverapi.database.AsyncMySQL;
import me.quexer.serverapi.nick.listener.NickListener;
import me.quexer.serverapi.rank.Tablist;
import me.quexer.serverapi.rank.listener.PrefixListener;
import me.quexer.serverapi.stats.QuickSG;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Iterator;

public final class ServerAPI extends JavaPlugin {


    /*
    PremiumKick
    CoinsAPI
     */
    private static ServerAPI instance;
    private static LocationAPI locationAPI;
    private static AsyncMySQL mySQL;
    private static QuickSG quickSG;
    private static String nickPrefix;
    private static SoundManager soundManager;




    @Override
    public void onEnable() {
        setInstance(this);

                init();


        NickAPI.NAMES.addAll(Arrays.asList("Baelish", "Beecher77", "benstone", "Borox", "Bunkham", "Canedew", "Canedis",
                "clackson", "concot", "Cootsy", "CraftyLop", "dandilion", "darkpoet", "daypig", "dookie", "Fallacy",
                "gamemusic", "Grenden", "Huggles", "ImNotVino", "kalfin", "kazuod", "keichan", "kevinbo", "KillerO",
                "Kinorana", "klette", "Knuckel", "Lamanch", "laotao", "lasercam", "Leadpipe", "Lederjacke", "leftone",
                "Leslee", "lukeboss", "MarinePinguin", "mattikott", "Messy_Turkey", "Michelle", "mmerlin", "MrPicard",
                "neonxp", "NightScope", "oldroland", "omegaprime", "Passes", "philbob", "PieGuy", "pinky_muffy",
                "pipola", "pokejacko", "psychoanimal", "Rawn", "rebelcletus", "reddeadrex", "Robablob", "robdee",
                "rockpee", "Romulad", "rumblefish", "saipan", "sakux", "samromer", "sellbram", "shrader", "Singular",
                "snikerfreak", "Snoxhzuni", "starklaw", "statiikfury", "Stimyz", "superfes", "sureynot", "sweenyb",
                "tbrynner", "theSero", "Thijscream", "TheTwig", "tornmage", "tupitupa", "wildii", "Wondwi",
                "WilliamBoo" ));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {

        setLocationAPI(new LocationAPI());
        setMySQL(new AsyncMySQL());
        setQuickSG(new QuickSG());
        getQuickSG().createTable();
        initCommands();
        initStrings();
        initListeners();
        setSoundManager(new SoundManager(Sound.CLICK, Sound.PISTON_EXTEND, Sound.WOOD_CLICK,
                                         Sound.LEVEL_UP, Sound.SUCCESSFUL_HIT, Sound.NOTE_PLING,
                                         Sound.NOTE_BASS, Sound.NOTE_BASS_DRUM, Sound.NOTE_SNARE_DRUM));
        initTables();
        Tablist.load();
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player all = (Player)var3.next();
            Tablist.setPrefix(all);
        }

    }
    private void initTables() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () ->{
        getMySQL().update("CREATE TABLE IF NOT EXISTS NICK(UUID VARCHAR(100), Ja VARCHAR(100))");
        });
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new NickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrefixListener(), this);
    }

    private void initCommands() {
        Bukkit.getPluginCommand("nick").setExecutor(new NickCMD());
    }
    private void initStrings() {
        setNickPrefix("§8✖ §5Nick §8§l➜ ");
    }


    public static ServerAPI getInstance() {
        return instance;
    }

    public static void setInstance(ServerAPI instance) {
        ServerAPI.instance = instance;
    }

    public static LocationAPI getLocationAPI() {
        return locationAPI;
    }

    public static String getNickPrefix() {
        return nickPrefix;
    }

    public static void setNickPrefix(String nickPrefix) {
        ServerAPI.nickPrefix = nickPrefix;
    }

    public static void setLocationAPI(LocationAPI locationAPI) {
        ServerAPI.locationAPI = locationAPI;
    }

    public static AsyncMySQL getMySQL() {
        return mySQL;
    }

    public static void setMySQL(AsyncMySQL mySQL) {
        ServerAPI.mySQL = mySQL;
    }

    public static QuickSG getQuickSG() {
        return quickSG;
    }

    public static void setQuickSG(QuickSG quickSG) {
        ServerAPI.quickSG = quickSG;
    }

    public static SoundManager getSoundManager() {
        return soundManager;
    }

    public static void setSoundManager(SoundManager soundManager) {
        ServerAPI.soundManager = soundManager;
    }
}
