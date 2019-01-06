package me.NinetyNine.villagershop.listeners;

import me.NinetyNine.villagershop.VillagerShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class VillagerListener implements Listener {
    private VillagerShop plugin;

    public VillagerListener(VillagerShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager)) return;

        if (!plugin.getManager().getVillagers().contains(e.getRightClicked())) return;

        e.setCancelled(true);
        e.getPlayer().closeInventory();
        Bukkit.getScheduler().runTaskLater(VillagerShop.getInstance(), () -> e.getPlayer().openInventory(VillagerShop.getInstance().getShop()), 20L);
    }
}
