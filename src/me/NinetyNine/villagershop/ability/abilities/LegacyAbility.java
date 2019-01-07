package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LegacyAbility extends Ability {

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.NETHER_STAR, 1);
    }

    @Override
    public String getAbilityName() {
        return "LegacyGainer";
    }

    @Override
    public String getDescription() {
        return "&bA 5% chance to gain 1\n Legacy Coin upon mining a beacon";
    }

    @Override
    public int getCost() {
        return 5000;
    }

    @EventHandler
    public void onPlayerMineBeacon(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Player player = e.getPlayer();

        if (!plugin.getAManager().hasAbility(this, player)) return;

        if (e.getBlock().getType() != Material.BEACON) return;

        if (this.getChance() <= 0.05) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "give "
                    + player.getName() + " 1");
            return;
        }
    }
}
