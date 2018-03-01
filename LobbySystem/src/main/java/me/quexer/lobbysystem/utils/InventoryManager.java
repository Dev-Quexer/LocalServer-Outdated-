package me.quexer.lobbysystem.utils;

import me.quexer.serverapi.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class InventoryManager {

    public void setStandartInv(Player p) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§bNavigator §8➜").setLore("§8§m----------", "§§7Bringt dich zu","§7den §eSpielmodies").toItemStack());

    }

}
