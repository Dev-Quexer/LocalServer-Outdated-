package me.quexer.lobbysystem.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SpielerVerstecken implements Listener {

    private static List<Player> spieler = new ArrayList<>();
    private List<Player> used = new ArrayList<>();
    private static ItemStack versteckt_Active = new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short) 10)).setName("§8➜ §6Spieler Verstecken §8× §aAktiviert").toItemStack();
    private static ItemStack versteckt_Not_Active = new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short) 1)).setName("§8➜ §6Spieler Verstecken §8× §cDeaktiviert").toItemStack();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§8➜ §6Spieler Verstecken §8× ")) {
                if (!used.contains(e.getPlayer())) {
                    if (spieler.contains(e.getPlayer())) {
                        spieler.remove(e.getPlayer());
                        e.getPlayer().sendMessage(Lobby.getPrefix() + "§6Spieler Verstecken §8× §cDeaktiviert");
                        ServerAPI.getSoundManager().playNormal(e.getPlayer());
                        e.getPlayer().getInventory().setItem(1, versteckt_Not_Active);
                        Bukkit.getOnlinePlayers().forEach(p -> e.getPlayer().showPlayer(p));
                        used.add(e.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(e.getPlayer());
                        }, 20 * 3);
                    } else {
                        spieler.add(e.getPlayer());
                        e.getPlayer().sendMessage(Lobby.getPrefix() + "§6Spieler Verstecken §8× §aAktiviert");
                        ServerAPI.getSoundManager().playNormal(e.getPlayer());
                        e.getPlayer().getInventory().setItem(1, versteckt_Active);
                        Bukkit.getOnlinePlayers().forEach(p -> e.getPlayer().hidePlayer(p));
                        used.add(e.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(e.getPlayer());
                        }, 20 * 3);
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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(getSpieler().contains(p)) {
                p.hidePlayer(e.getPlayer());
            }
        });
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(getSpieler().contains(e.getPlayer())) {
            getSpieler().remove(e.getPlayer());
        }
    }


    public static List<Player> getSpieler() {
        return spieler;
    }

    public static ItemStack getVersteckt_Active() {
        return versteckt_Active;
    }

    public static ItemStack getVersteckt_Not_Active() {
        return versteckt_Not_Active;
    }
}
