package me.localserver.bungeesystem;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import me.localserver.bungeesystem.commands.HelpCMD;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeeSystem extends Plugin {

    /*
    Report
    Ban
    Premium
    Ts
    TeamChat
    Broadcast
    online
    team
     */

    private static String prefix;
    private static String noPerms;
    private static ProxyServer bungeeCord;

    @Override
    public void onEnable() {
        init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void init() {
        setBungeeCord(getProxy());
        initCommands();
        initListeners();
        initStrings();

    }
    private void initStrings() {
        setPrefix("§8✖ §aSystem §8§l➜ ");
        setNoPerms(getPrefix()+"§cDazu hast du keine Rechte§8! ");
    }
    private void initCommands() {
        getBungeeCord().getPluginManager().registerCommand(this, new HelpCMD("help"));
    }

    private void initListeners() {

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
}
