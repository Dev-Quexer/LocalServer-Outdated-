package me.localserver.bungeesystem.listeners;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.MuteManager;
import me.localserver.bungeesystem.utils.MuteManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.localserver.bungeesystem.utils.MuteManager.*;


public class ChatListeners implements Listener {
    
    @EventHandler
    public void onChat(ChatEvent e) {
        if (MuteManager.isMutened(((ProxiedPlayer) ((ProxiedPlayer) e.getSender())).getName())) {
            if (System.currentTimeMillis() < MuteManager.getEnd(((ProxiedPlayer) ((ProxiedPlayer) e.getSender())).getName()) || MuteManager.getEnd(((ProxiedPlayer) ((ProxiedPlayer) e.getSender())).getName()) == -1) {
                if (!e.getMessage().startsWith("/")) {
                    e.setCancelled(true);

                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + " ");
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§8§m-------------------------");
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7Du wurdest von gemutet§8!");
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7Grund§8: §e" + getGrund(((ProxiedPlayer) e.getSender()).getName()));
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7Von§8: §e" + getVonwem(((ProxiedPlayer) e.getSender()).getName()));
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7MuteID§8: §e" + getID(((ProxiedPlayer) e.getSender()).getName()));
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7BanPoints§8: §e" + getBanPoints(((ProxiedPlayer) e.getSender()).getName()));
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§7Zeit§8: §e" + getEndAsString(((ProxiedPlayer) e.getSender()).getName()));
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + "§8§m-------------------------");
                    ((ProxiedPlayer) e.getSender()).sendMessage(BungeeSystem.getInstance().getPrefix() + " ");
                }
            }
        }
    }
    
}
