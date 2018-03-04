package me.quexer.lobbysystem.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ItemHeld implements Listener {

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 3);
    }

}
