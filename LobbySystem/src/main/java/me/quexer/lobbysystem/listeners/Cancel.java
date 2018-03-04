package me.quexer.lobbysystem.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.lobbysystem.commands.BuildCMD;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Cancel implements Listener {
    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {

        e.setCancelled(true);

    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        if(!Lobby.getBuild().contains(e.getPlayer())) {
            e.setCancelled(true);
            e.setBuild(false);
        }

    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!Lobby.getBuild().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInv(InventoryClickEvent e) {
        if(!e.getWhoClicked().hasPermission("build")) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        e.setCancelled(true);
        e.setDamage(0);

    }


}
