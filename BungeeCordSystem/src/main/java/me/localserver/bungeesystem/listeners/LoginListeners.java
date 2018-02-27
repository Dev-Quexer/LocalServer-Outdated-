package me.localserver.bungeesystem.listeners;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.BanManager;
import me.localserver.bungeesystem.utils.UUIDFetcher;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.localserver.bungeesystem.utils.BanManager.getBanPoints;
import static me.localserver.bungeesystem.utils.BanManager.getGrund;

public class LoginListeners implements Listener {

    @EventHandler
    public void onLogin(LoginEvent e) {
        e.registerIntent(BungeeSystem.getInstance());
        BanManager.runAsync(new Runnable() {
            @Override
            public void run() {
                String name = UUIDFetcher.getName(e.getConnection().getUniqueId());
                if (BanManager.isBanned(name)) {
                    if (System.currentTimeMillis() <= BanManager.getEnd(name) || BanManager.getEnd(name) == -1) {
                        e.setCancelled(true);
                        e.setCancelReason("§7Du wurdest vom §eLocalServer§7.§enet §7Netzwerk §cgebannt§8!\n" +
                                "\n" +
                                "§8§m-----------------------------------------------------------------------------\n" +
                                "\n" +
                                "§7Grund§8: §e" + getGrund(name) + "\n" +
                                "§7Von§8: §a" + BanManager.getVonwem(name) + "\n" +
                                "§7Verbleibende Zeit§8: " + BanManager.getEndAsString(name) + "\n" +
                                "§7Ban-ID§8: §e" + BanManager.getID(name) + "\n" +
                                "§7Deine BanPoints§8: §e" + getBanPoints(name) + "\n" +
                                "\n" +
                                "§8§m-----------------------------------------------------------------------------\n" +
                                "§7Du kannst einen Entbannungsantrag im §aForum §7erstellen §8(§7IP§8: §eLocalServer§7.§enet§8)"
                        );


                    } else {
                        BanManager.unBanPlayer(name, "System [Automatischer Unban]", "Konsole");
                    }
                }
                e.completeIntent(BungeeSystem.getInstance());

            }
        });
    }

}
