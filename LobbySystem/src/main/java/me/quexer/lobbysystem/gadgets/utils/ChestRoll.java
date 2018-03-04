package me.quexer.lobbysystem.gadgets.utils;

import me.quexer.lobbysystem.Lobby;
import me.quexer.lobbysystem.utils.ScoreboardAPI;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.coins.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ChestRoll {

    private String name;
    private Player player;
    private int sched;
    private int high = 60;
    private boolean hasGadget = false;

    public ChestRoll(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public void start() {

        Inventory inv = Bukkit.createInventory(null, 27, "§8➜ §6Chest");
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.BLACK).toItemStack());
            inv.setItem(4, new ItemBuilder(Material.HOPPER).setName("§8§l⬇ §bDein Gewinn §8§l⬇").toItemStack());
        }

        getPlayer().openInventory(inv);
        Gadget gadget = Lobby.getGadgetsAPI().getNextGadget();
        if(gadget == Gadget.COINS1) {
            CoinsAPI.addCoins(getName(), 50000);
        } else if(gadget == Gadget.COINS2) {
            CoinsAPI.addCoins(getName(), 10000);
        } else if(gadget == Gadget.COINS3) {
            CoinsAPI.addCoins(getName(), 5000);
        } else if(gadget == Gadget.COINS4) {
            CoinsAPI.addCoins(getName(), 2000);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), () -> {
                        if(!Lobby.getGadgetsAPI().getPlayerGadgets().get(getPlayer()).contains(gadget)) {
                            Lobby.getGadgetsAPI().addGadgetMySQL(getName(), gadget);
                            Lobby.getGadgetsAPI().loadGadgets(getPlayer());
                        } else {
                            hasGadget = true;
                        }
            });
        }

        sched = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Lobby.getInstance(), () -> {

            if (high == 0) {
                ScoreboardAPI.updateScoreboard(getPlayer());
                inv.setItem(13, gadget.getItemStack());
                if(!hasGadget) {
                    getPlayer().sendMessage(Lobby.getPrefix() + "§7§lDu hast das §eGadget§8: " + gadget.getName() + gadget.getSeltenheit().getName() + " §7§lgewonnen§8!");
                } else {
                    getPlayer().sendMessage(Lobby.getPrefix()+"§7Du besitzt dieses Gadget bereits§8, §7daher wurden dir §e2.000 Coins §7hinzugefügt§8!");
                    CoinsAPI.addCoins(getName(), 2000);
                }
                ServerAPI.getSoundManager().playGood(getPlayer());
                cancel();
            } else {

                inv.setItem(17, inv.getItem(16));
                inv.setItem(16, inv.getItem(15));
                inv.setItem(15, inv.getItem(14));
                inv.setItem(14, inv.getItem(13));
                inv.setItem(13, inv.getItem(12));
                inv.setItem(12, inv.getItem(11));
                inv.setItem(11, inv.getItem(10));
                inv.setItem(10, inv.getItem(9));
                inv.setItem(9, Lobby.getGadgetsAPI().getNextGadget().getItemStack());



                ServerAPI.getSoundManager().playNormal(getPlayer());
                high--;
            }

        }, 0, 1);

    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(sched);
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }
}
