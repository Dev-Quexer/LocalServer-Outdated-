package me.quexer.lobbysystem.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        e.setJoinMessage(null);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
            if(Lobby.getSpawnLocation() != null) {
                e.getPlayer().teleport(Lobby.getSpawnLocation());
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BAT_LOOP, 1, 1F);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 3);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.PISTON_EXTEND, 1, 3);
                e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1, 30);

            } else {
                e.getPlayer().sendMessage(Lobby.getPrefix()+"§cEs wurde noch kein Spawn gesetzt§8!");
                ServerAPI.getSoundManager().playBad(e.getPlayer());
            }

            Lobby.getInventoryManager().setStandartInv(e.getPlayer());
            e.getPlayer().setFoodLevel(2);
            e.getPlayer().setSaturation(2);
            e.getPlayer().setHealth(2);
            e.getPlayer().setHealthScale(2);
            e.getPlayer().setExp(0);
            e.getPlayer().setLevel(2018);


        }, 3);
        e.getPlayer().setGameMode(GameMode.ADVENTURE);

    }


}
