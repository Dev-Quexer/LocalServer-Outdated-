package me.quexer.serverapi.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryHandler {
    private final HashMap<Inventory, ItemListener> listener = new HashMap();

    public InventoryHandler() {
    }

    public void registerListener(Inventory inventory, ItemListener listener) {
        if (!this.listener.containsKey(inventory)) {
            this.listener.put(inventory, listener);
        }

    }

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        if (event.getInventory() != null && event.getCurrentItem() != null && event.getWhoClicked() != null) {
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                if (this.listener.containsKey(event.getInventory())) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    ((ItemListener)this.listener.get(event.getInventory())).interact((Player)event.getWhoClicked(), event.getInventory(), event.getClick(), event.getCurrentItem());
                }

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() != null) {
            if (this.listener.containsKey(event.getInventory())) {
                this.listener.remove(event.getInventory());
            }

        }
    }
}
