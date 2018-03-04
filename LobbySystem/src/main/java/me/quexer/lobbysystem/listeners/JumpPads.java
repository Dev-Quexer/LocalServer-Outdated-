package me.quexer.lobbysystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class JumpPads implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.PHYSICAL) {
            e.setCancelled(true);
            if(e.getPlayer().getLocation().getBlock().getType() == Material.WOOD_PLATE) {
                Vector v = e.getPlayer().getLocation().getDirection().normalize().setY(0.5D).multiply(2D);

                e.getPlayer().setVelocity(v);
            }
        }
    }

}
