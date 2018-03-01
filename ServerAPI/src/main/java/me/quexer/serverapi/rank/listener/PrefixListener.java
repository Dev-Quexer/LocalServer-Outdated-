package me.quexer.serverapi.rank.listener;

import javafx.scene.control.Tab;
import me.quexer.serverapi.nick.NickAPI;
import me.quexer.serverapi.rank.Rank;
import me.quexer.serverapi.rank.Tablist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PrefixListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Tablist.isSetTablist()) {
            Tablist.setPrefix(e.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Tablist.isSetTablist()) {
            if (Tablist.t.containsKey(p.getUniqueId())) {
                Tablist.sb.getTeam((String) Tablist.t.get(p.getUniqueId())).removePlayer(p);
                Tablist.t.remove(p.getUniqueId());
            }
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(Tablist.isSetChatPrefix()) {
            if(NickAPI.hasNick(e.getPlayer())) {
                e.setFormat(Rank.PREMIUM.getPrefix() +e.getPlayer().getName()+ " §8➜ §7" + e.getMessage());
            } else {
                e.setFormat(e.getPlayer().getDisplayName() + " §8➜ §7" + e.getMessage());
            }
        }
    }

}
