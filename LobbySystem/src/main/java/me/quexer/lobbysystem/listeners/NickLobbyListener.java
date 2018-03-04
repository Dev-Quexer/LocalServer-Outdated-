package me.quexer.lobbysystem.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.nick.NickAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NickLobbyListener implements Listener {

    private static ItemStack nick_Active = new ItemBuilder(Material.NAME_TAG).setName("§8➜ §5AutoNick §8× §aAktiviert").toItemStack();
    private static ItemStack nick_Not_Active = new ItemBuilder(Material.NAME_TAG).setName("§8➜ §5AutoNick §8× §cDeaktiviert").toItemStack();
    private List<Player> used = new ArrayList<>();


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§8➜ §5AutoNick §8× ")) {
                if (!used.contains(e.getPlayer())) {
                    if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§aAktiviert")) {
                        ServerAPI.getSoundManager().playNormal(e.getPlayer());
                        e.getPlayer().getInventory().setItem(4, nick_Not_Active);
                        Bukkit.getOnlinePlayers().forEach(p -> e.getPlayer().showPlayer(p));
                        used.add(e.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(e.getPlayer());
                        }, 20 * 3);
                        NickAPI.toggleAutoNick(e.getPlayer());
                    } else {
                        ServerAPI.getSoundManager().playNormal(e.getPlayer());
                        e.getPlayer().getInventory().setItem(4, nick_Active);
                        Bukkit.getOnlinePlayers().forEach(p -> e.getPlayer().hidePlayer(p));
                        used.add(e.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(e.getPlayer());
                        }, 20 * 3);
                        NickAPI.toggleAutoNick(e.getPlayer());
                    }
                } else {
                    e.getPlayer().sendMessage(Lobby.getPrefix() + "§cBitte warte einen §eMoment§8!");
                    ServerAPI.getSoundManager().playBad(e.getPlayer());
                }
            }
        }
        } catch (Exception ex) {

        }
    }

    public static ItemStack getNick_Active() {
        return nick_Active;
    }

    public static ItemStack getNick_Not_Active() {
        return nick_Not_Active;
    }

    public List<Player> getUsed() {
        return used;
    }
}
