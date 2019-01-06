package me.NinetyNine.villagershop.listeners;

import me.NinetyNine.villagershop.PPlayer;
import me.NinetyNine.villagershop.ability.AbilityManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (PPlayer.getPlayer(e.getPlayer()) == null)
            new PPlayer(e.getPlayer());

        PPlayer p = PPlayer.getPlayer(e.getPlayer());

        if (AbilityManager.getInstance().hasAbilities(e.getPlayer()))
            p.loadInventory();
    }
}
