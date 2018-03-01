package me.quexer.serverapi.nick.listener;

import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.nick.NickAPI;
import me.quexer.serverapi.nick.events.PlayerNickEvent;
import me.quexer.serverapi.nick.events.PlayerRemoveNickEvent;
import me.quexer.serverapi.rank.Tablist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NickListener implements Listener {

    @EventHandler
    public void onNick(PlayerNickEvent e) {
        e.getPlayer().sendMessage(ServerAPI.getNickPrefix()+"§4Dein neuer Nickname lautet§8: §6"+e.getNick());
        ServerAPI.getSoundManager().playGood(e.getPlayer());
        if(Tablist.isSetTablist()) {
            Tablist.setPrefix(e.getPlayer());
        }

    }

    @EventHandler
    public void onNickRemove(PlayerRemoveNickEvent e) {
        e.getPlayer().sendMessage(ServerAPI.getNickPrefix()+"§4Dein Nickname wurde entfernt§8!");
        ServerAPI.getSoundManager().playBad(e.getPlayer());
        if(Tablist.isSetTablist()) {
            Tablist.setPrefix(e.getPlayer());
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(NickAPI.isNickOnThisServer()) {
            if (e.getPlayer().hasPermission("nick.nick")) {
                if(NickAPI.isMySQLNick(e.getPlayer())) {
                    NickAPI.setRandomNick(e.getPlayer());
                }
            }
        }
    }

}
