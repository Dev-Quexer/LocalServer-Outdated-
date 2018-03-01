package me.quexer.lobbysystem.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.api.TitleAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Navigator implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if(e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
                Inventory inv = Bukkit.createInventory(null, 27, "§bNavigator §8➜");

                for (int i = 0; i < inv.getSize(); i++) {
                    inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.BLACK).setName("§7§m-----").toItemStack());
                }
                inv.setItem(13, new ItemBuilder(Material.MAGMA_CREAM).setName("§7Zum §eSpawn §7teleportieren§8!").toItemStack());
                inv.setItem(16, new ItemBuilder(Material.CHEST).setName("§7Zu §eQuickSG §7teleportieren§8!").setLore("§8§m--------------------","§7Zu den §eServerschildern").toItemStack());
                e.getPlayer().openInventory(inv);
                ServerAPI.getSoundManager().playGood(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {

            if(e.getClickedInventory().getName().equals("§bNavigator §8➜")) {
                e.setCancelled(true);
                if(e.getSlot() == 13) {
                    e.getWhoClicked().teleport(Lobby.getSpawnLocation());
                    e.getWhoClicked().getWorld().playEffect(e.getWhoClicked().getLocation(), Effect.ENDER_SIGNAL, 1, 30);
                    e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0.5F);
                    e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.PISTON_EXTEND, 1, 3F);
                    TitleAPI.sendTitle((Player) e.getWhoClicked(), 10, 40, 20, "§8➜ §aSpawn","§8§m---------------");
                } else  if(e.getSlot() == 16) {
                    e.getWhoClicked().teleport(Lobby.getQuickSGLocation());
                    e.getWhoClicked().getWorld().playEffect(e.getWhoClicked().getLocation(), Effect.ENDER_SIGNAL, 1, 30);
                    e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0.5F);
                    e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.PISTON_EXTEND, 1, 3F);
                    TitleAPI.sendTitle((Player) e.getWhoClicked(), 10, 40, 20, "§8➜ §aQuickSG","§8§m---------------");
                }
            }

        } catch (Exception ex) {

        }
    }


}
