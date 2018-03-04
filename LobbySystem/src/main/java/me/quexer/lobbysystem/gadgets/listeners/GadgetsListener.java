package me.quexer.lobbysystem.gadgets.listeners;

import me.quexer.lobbysystem.Lobby;
import me.quexer.lobbysystem.gadgets.GadgetsAPI;
import me.quexer.lobbysystem.gadgets.utils.ChestRoll;
import me.quexer.lobbysystem.gadgets.utils.Gadget;
import me.quexer.lobbysystem.utils.ScoreboardAPI;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.coins.CoinsAPI;
import me.quexer.serverapi.rank.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class GadgetsListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), () -> {
            Lobby.getGadgetsAPI().getPlayerGadgets().put(e.getPlayer(), new ArrayList<>());
            if(!Lobby.getGadgetsAPI().playerExists(e.getPlayer().getName())) {
                Lobby.getGadgetsAPI().createPlayer(e.getPlayer().getName());
            }
            Lobby.getGadgetsAPI().loadGadgets(e.getPlayer());
            try {
                Lobby.getGadgetsAPI().setActiveGadget(e.getPlayer(), Lobby.getGadgetsAPI().getActiveGadgetMySQL(e.getPlayer().getName()));
                e.getPlayer().sendMessage(Lobby.getPrefix() + "§f§lDu hast aktuell das Gadget§8§l: " + Lobby.getGadgetsAPI().getPlayerGadget().get(e.getPlayer()).getName() + " §f§lausgewählt§8!");
            } catch (Exception ex) {
                e.getPlayer().sendMessage(Lobby.getPrefix()+"§f§lDu hast aktuell §c§lkein §4Gadget §f§lausgewählt§8!");
            }
        });

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(Lobby.getGadgetsAPI().getPlayerGadget().containsKey(e.getPlayer())) {
            Lobby.getGadgetsAPI().setActiveGadgetMySQL(e.getPlayer().getName(), Lobby.getGadgetsAPI().getActiveGadget(e.getPlayer()));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 27, "§8➜ §4ChestOpening");
                for (int i = 0; i < inv.getSize(); i++) {
                    inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.BLACK).toItemStack());
                }
                inv.setItem(11, new ItemBuilder(Material.STAINED_CLAY).setDyeColor(DyeColor.LIME).setName("§a§lKaufen").setLore("§a§l","§e5§7.§e000 Coins").toItemStack());
                inv.setItem(15, new ItemBuilder(Material.STAINED_CLAY).setDyeColor(DyeColor.RED).setName("§c§lAbbruch").setLore("§a§l","§cKein Verlust§8!").toItemStack());
                e.getPlayer().openInventory(inv);
            }
        } catch (Exception ex) {

        }
    }
    @EventHandler
    public void onInteract2(PlayerInteractEvent e) {
        if(e.getPlayer().getItemInHand().getType() == Material.CHEST) {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§4Gadgets")) {
                Inventory inv = Bukkit.createInventory(null, 54, "§8➜ §4Chest");
                for (int i = 0; i < inv.getSize(); i++) {
                    inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.BLACK).toItemStack());
                }

                Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), () -> {
                    int i = 18;
                    for (Gadget gadget : Gadget.values()) {
                        if (!gadget.toString().contains("COINS")) {
                            if (Lobby.getGadgetsAPI().getPlayerGadgets().get(e.getPlayer()).contains(gadget)) {
                                inv.setItem(i, new ItemBuilder(gadget.getItemStack()).setLore("§a", "§7Du §abesitzt §7dieses §4Gadget").toItemStack());
                            } else {
                                inv.setItem(i, new ItemBuilder(gadget.getItemStack()).setLore("§a", "§7Dieses §4Gadget §7besitzt du §cnicht").toItemStack());
                            }
                            i++;
                        }

                    }
                    inv.setItem(49, new ItemBuilder(Material.BARRIER).setName("§4Gadget §centfernen§8!").toItemStack());
                });
                e.getPlayer().openInventory(inv);

            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {

            if(e.getClickedInventory().getName() == "§8➜ §4Chest") {
                e.setCancelled(true);
                if(e.getSlot() == 49) {
                    if(Lobby.getGadgetsAPI().getActiveGadget((Player) e.getWhoClicked()) != null) {
                        Lobby.getGadgetsAPI().setActiveGadget((Player) e.getWhoClicked(), null);
                        e.getWhoClicked().sendMessage(Lobby.getPrefix()+"§7Du hast dein aktuelles §4Gadget §centfernt§8!");
                    } else {
                        e.getWhoClicked().sendMessage(Lobby.getPrefix()+"§cDu hast kein §4Gadget §causgewählt§8!");
                    }
                } else {
                    if(e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
                        if(e.getCurrentItem().getItemMeta().getLore().get(1).contains("§a")) {
                            Gadget g = null;

                            for (Gadget gadget : Gadget.values()) {
                                if(!gadget.toString().contains("COINS")) {
                                    if (gadget.getItemStack().getItemMeta().getDisplayName() == e.getCurrentItem().getItemMeta().getDisplayName()) {
                                        g = gadget;

                                        Lobby.getGadgetsAPI().setActiveGadget((Player) e.getWhoClicked(), gadget);
                                        ServerAPI.getSoundManager().playGood((Player) e.getWhoClicked());
                                        e.getWhoClicked().closeInventory();

                                    }
                                }
                            }



                        } else {
                            e.getWhoClicked().closeInventory();
                            ServerAPI.getSoundManager().playBad((Player) e.getWhoClicked());
                            e.getWhoClicked().sendMessage(Lobby.getPrefix()+"§cDu besitzt dieses §4Gadget §cnicht");
                        }
                    }
                }
            } else if(e.getClickedInventory().getName() == "§8➜ §6Chest") {
                e.setCancelled(true);
            } else if(e.getClickedInventory().getName() == "§8➜ §4ChestOpening") {
                e.setCancelled(true);
                if(e.getSlot() == 11) {
                    if(CoinsAPI.getCoins(e.getWhoClicked().getName()) >= 5000) {
                        CoinsAPI.removeCoins(e.getWhoClicked().getName(), 5000);
                        ServerAPI.getSoundManager().playGood((Player) e.getWhoClicked());
                        ChestRoll chestRoll = new ChestRoll(e.getWhoClicked().getName(), (Player) e.getWhoClicked());
                        chestRoll.start();
                        ScoreboardAPI.updateScoreboard((Player) e.getWhoClicked());
                    } else {
                        e.getWhoClicked().closeInventory();
                        ServerAPI.getSoundManager().playBad((Player) e.getWhoClicked());
                        e.getWhoClicked().sendMessage(Lobby.getPrefix()+"§cDu hast nicht genügend §eCoins§8!");
                    }
                } else if(e.getSlot() == 15) {
                    e.getWhoClicked().closeInventory();
                    ServerAPI.getSoundManager().playBad((Player) e.getWhoClicked());
                }
            }

        } catch (Exception ex) {

        }
    }


}
