package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleOreAbility extends Ability {

    @Override
    public String getAbilityName() {
        return "DoubleOre";
    }

    @Override
    public String getDescription() {
        return "&bYou're given a 5% chance\nto gain &6another ore &bafter \nmining one.";
    }

    @Override
    public int getCost() {
        return 5000;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.EMERALD, 1);
    }

    @EventHandler
    public void onPlayerBreakOre(BlockBreakEvent e) {
        if (!plugin.getAManager().hasAbility(this, e.getPlayer()))
            return;

        if (e.isCancelled()) return;

        if (!e.getBlock().getType().name().endsWith("ORE")) return;

        e.getPlayer().sendMessage("chance is: " + this.getChance());

        if (this.getChance() <= 0.05) {
            e.getPlayer().sendMessage("chance is: " + this.getChance());
            e.getPlayer().getWorld()
                    .dropItemNaturally(e.getBlock().getLocation(), new ItemStack(e.getBlock().getType(), 1));
            e.getPlayer().sendMessage(ChatColor.GREEN + "You have gained another ore! " + ChatColor.AQUA + "("
                    + getAbilityName() + ")");
        }
    }
}
