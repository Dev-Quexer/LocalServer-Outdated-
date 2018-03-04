package me.quexer.lobbysystem.utils;

import me.quexer.lobbysystem.listeners.NickLobbyListener;
import me.quexer.lobbysystem.listeners.SpielerVerstecken;
import me.quexer.serverapi.api.ItemBuilder;
import me.quexer.serverapi.nick.NickAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryManager {

    public void setStandartInv(Player p) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§8➜ §bNavigator").toItemStack());
            p.getInventory().setItem(7, new ItemBuilder(Material.CHEST).setName("§8➜ §4Gadgets").toItemStack());
            p.getInventory().setItem(8, new ItemBuilder(Material.NETHER_STAR).setName("§8➜ §aLobbySwitcher").toItemStack());
            if(SpielerVerstecken.getSpieler().contains(p)) {
                p.getInventory().setItem(1, SpielerVerstecken.getVersteckt_Active());
            } else {
                p.getInventory().setItem(1, SpielerVerstecken.getVersteckt_Not_Active());
            }
            if(p.hasPermission("nick.nick")) {
                if(NickAPI.isMySQLNick(p)) {
                    p.getInventory().setItem(4, NickLobbyListener.getNick_Active());
                } else {
                    p.getInventory().setItem(4, NickLobbyListener.getNick_Not_Active());
                }
            }

    }

}
