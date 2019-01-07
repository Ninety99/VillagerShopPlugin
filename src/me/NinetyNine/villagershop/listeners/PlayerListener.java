package me.NinetyNine.villagershop.listeners;

import me.NinetyNine.villagershop.PPlayer;
import me.NinetyNine.villagershop.VillagerShop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private VillagerShop plugin;

    public PlayerListener(VillagerShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (PPlayer.getPlayer(e.getPlayer()) == null)
            new PPlayer(e.getPlayer(), plugin);

        PPlayer p = PPlayer.getPlayer(e.getPlayer());

        if (this.plugin.getAManager().hasAbilities(e.getPlayer()))
            p.loadInventory();
    }
}
