package me.quexer.lobbysystem.listeners;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.api.TitleAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbySwitcher implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
        if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§8➜ §aLobbySwitcher")) {
            Inventory inv = Bukkit.createInventory(null, 27, "§8➜ §aLobbySwitcher");
            for (int i = 0; i < inv.getSize(); i++) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.BLACK).toItemStack());
            }

            int serverid = 1;
            for (ServerInfo info : CloudAPI.getInstance().getServers("Lobby")) {
                if(CloudAPI.getInstance().getServerInfo("Lobby-"+serverid).getOnlineCount() == 25) {
                    inv.setItem(serverid+10, new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short)1)).setName("§8➜ §cLobby§8-§c"+serverid).addLoreLine("§7Spieler§8: §c"+CloudAPI.getInstance().getServerInfo("Lobby-"+serverid).getOnlineCount()+" §8/ §c25").toItemStack());
                } else {
                    inv.setItem(serverid+10, new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short)10)).setName("§8➜ §eLobby§8-§e"+serverid).addLoreLine("§7Spieler§8: §e"+CloudAPI.getInstance().getServerInfo("Lobby-"+serverid).getOnlineCount()+" §8/ §e25").toItemStack());
                }
                serverid++;
            }
            e.getPlayer().openInventory(inv);


        }
        } catch (Exception ex) {

        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        try {

            if (e.getClickedInventory().getName() == "§8➜ §aLobbySwitcher") {
                e.setCancelled(true);
                if(e.getSlot() > 8) {
                    Player p = (Player) e.getWhoClicked();
                    int serverid = e.getSlot()-10;
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§c")) {
                        p.sendMessage(Lobby.getPrefix()+"§7Dieser §eLobbyServer §7ist §cvoll§8!");
                        ServerAPI.getSoundManager().playBad(p);
                    } else {
                        p.sendMessage(Lobby.getPrefix()+"§7Du wirst zu §eLobby§8-§e"+serverid+" §7verbunden§8. . .");
                        ServerAPI.getSoundManager().playGood(p);
                        TitleAPI.sendTitle(p, 10, 60, 10, "§8➜ §eLobby§8-§e"+serverid, "§a");
                        Lobby.connect("Lobby-"+serverid, p);
                    }
                }

            }

        } catch (Exception ex) {

        }
    }

}
