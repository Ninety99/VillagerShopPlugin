package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.VillagerShop;
import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.ability.AbilityManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LegacyAbility extends Ability {

    public LegacyAbility(VillagerShop plugin) {
        super(plugin);
    }

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
        return this.getPlugin().getPConfig().getConfig().getInt(getAbilityName() + ".price");
    }

    @EventHandler
    public void onPlayerMineBeacon(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Player player = e.getPlayer();

        if (!AbilityManager.getInstance().hasAbility(this, player)) return;

        if (e.getBlock().getType() != Material.BEACON) return;

        if (this.getChance() <= 0.05) {
            this.getPlugin().getServer().dispatchCommand(this.getPlugin().getServer().getConsoleSender(), "give "
                    + player.getName() + " 1");
        }
    }
}
