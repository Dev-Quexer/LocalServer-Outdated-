package me.localserver.bungeesystem;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import me.localserver.bungeesystem.commands.*;
import me.localserver.bungeesystem.database.AsyncMySQL;
import me.localserver.bungeesystem.listeners.ChatListeners;
import me.localserver.bungeesystem.listeners.LoginListeners;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public final class BungeeSystem extends Plugin {

    /*
    Report
    Ban
     */

    private static String prefix;
    private static String noPerms;
    private static ProxyServer bungeeCord;
    private static BungeeSystem instance;
    private static AsyncMySQL mySQL;
    private static List<String> reasons;

    public static AsyncMySQL getMySQL() {
        return mySQL;
    }

    public static void setMySQL(AsyncMySQL mySQL) {
        BungeeSystem.mySQL = mySQL;
    }

    @Override

    public void onEnable() {
        init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BungeeSystem getInstance() {
        return instance;
    }

    public static void setInstance(BungeeSystem instance) {
        BungeeSystem.instance = instance;
    }

    private void init() {
        setInstance(this);
        setBungeeCord(getProxy());
        setMySQL(new AsyncMySQL());
        initCommands();
        initListeners();
        initStrings();
        getProxy().getScheduler().runAsync(this, () ->{
                mySQL.update(mySQL.prepare("CREATE TABLE IF NOT EXISTS Ban(UUID VARCHAR(100), Grund VARCHAR(100), von VARCHAR(100), end VARCHAR(100), current VARCHAR(100), ID VARCHAR(100))"));
        mySQL.update(mySQL.prepare("CREATE TABLE IF NOT EXISTS Mute(UUID VARCHAR(100), Grund VARCHAR(100), von VARCHAR(100), end VARCHAR(100), current VARCHAR(100), ID INT(100), IP VARCHAR(100))"));
        mySQL.update(mySQL.prepare("CREATE TABLE IF NOT EXISTS BanHistory(UUID VARCHAR(100), vonWem VARCHAR(100), current VARCHAR(100), Grund VARCHAR(1000))"));
        mySQL.update(mySQL.prepare("CREATE TABLE IF NOT EXISTS BanPoints(UUID VARCHAR(100), Points VARCHAR(100))"));
        mySQL.update(mySQL.prepare("CREATE TABLE IF NOT EXISTS BanPoints(UUID VARCHAR(100), Points VARCHAR(100))"));
        getMySQL().update("CREATE TABLE IF NOT EXISTS REPORT(UUID VARCHAR(100), Reason VARCHAR(100), vonWem VARCHAR(100), Count VARCHAR(100), InProgress VARCHAR(100))");
        });

    }
    private void initStrings() {
        reasons = new ArrayList<>();
        setPrefix("§8✖ §aSystem §8§l➜ ");
        setNoPerms(getPrefix()+"§cDazu hast du keine Rechte§8! ");
        getReasons().add("Hacking".toUpperCase());
        getReasons().add("Beleidigung".toUpperCase());
        getReasons().add("Bugusing".toUpperCase());
        getReasons().add("Teaming".toUpperCase());
        getReasons().add("Trolling".toUpperCase());
        getReasons().add("Skin".toUpperCase());
        getReasons().add("Name".toUpperCase());
        getReasons().add("Chatverhalten".toUpperCase());

    }
    private void initCommands() {
        getBungeeCord().getPluginManager().registerCommand(this, new HelpCMD("help"));
        getBungeeCord().getPluginManager().registerCommand(this, new TsCMD("Ts"));
        getBungeeCord().getPluginManager().registerCommand(this, new TsCMD("TeamSpeak"));
        getBungeeCord().getPluginManager().registerCommand(this, new PremiumCMD("Premium"));
        getBungeeCord().getPluginManager().registerCommand(this, new OnlineCMD("online"));
        getBungeeCord().getPluginManager().registerCommand(this, new TeamCMD("team"));
        getBungeeCord().getPluginManager().registerCommand(this, new TeamChatCMD("tc"));
        getBungeeCord().getPluginManager().registerCommand(this, new BroadcastCMD("broadcast"));
        getBungeeCord().getPluginManager().registerCommand(this, new BroadcastCMD("bc"));
        getBungeeCord().getPluginManager().registerCommand(this, new BroadcastCMD("alert"));
        getBungeeCord().getPluginManager().registerCommand(this, new BanCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new CheckCMD("check"));
        getBungeeCord().getPluginManager().registerCommand(this, new CheckCMD("playerinfo"));
        getBungeeCord().getPluginManager().registerCommand(this, new CheckCMD("info"));
        getBungeeCord().getPluginManager().registerCommand(this, new ReasonsCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new ReportCMD("report"));
        getBungeeCord().getPluginManager().registerCommand(this, new ReportssCMD("reportss"));
        getBungeeCord().getPluginManager().registerCommand(this, new UnBanCMD("unban"));
    }

    private void initListeners() {
        getBungeeCord().getPluginManager().registerListener(this, new LoginListeners());
        getBungeeCord().getPluginManager().registerListener(this, new ChatListeners());
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        BungeeSystem.prefix = prefix;
    }

    public static String getNoPerms() {
        return noPerms;
    }

    public static void setNoPerms(String noPerms) {
        BungeeSystem.noPerms = noPerms;
    }

    public static ProxyServer getBungeeCord() {
        return bungeeCord;
    }

    public static void setBungeeCord(ProxyServer bungeeCord) {
        BungeeSystem.bungeeCord = bungeeCord;
    }

    public static List<String> getReasons() {
        return reasons;
    }
}
