package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import me.clip.autosell.events.SignSellEvent;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AdditionalMultiplierAbility extends Ability {

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.DIAMOND, 1);
    }

    @Override
    public String getAbilityName() {
        return "AdditionalMultiplier";
    }

    @Override
    public String getDescription() {
        return "&bA 10% chance to gain\n an extra 0.25 sell multiplier\n when selling in the sign";
    }

    @Override
    public int getCost() {
        return 10000;
    }

    @EventHandler
    public void onSignSell(SignSellEvent e) {
        if (!plugin.getAManager().hasAbility(this, e.getPlayer())) return;

        if (this.getChance() <= 0.10) {
            e.setTotalCost(e.getTotalCost() * 1.25);
            e.getPlayer().sendMessage("Total cost is " + e.getTotalCost());
            addTotalCost(e.getPlayer(), e.getTotalCost());
        }
    }

    private void addTotalCost(Player player, double cost) {
        EconomyResponse r = plugin.getEconomy().depositPlayer(player, cost);

        if (!r.transactionSuccess()) {
            player.sendMessage(ChatColor.RED + "Transaction failed! Contact an administrator.");
            return;
        } else {
            player.sendMessage(ChatColor.GREEN + "Successfully gained 0.25 sell multiplier! "
                    + ChatColor.AQUA + "(" + getAbilityName() + ")");
            return;
        }
    }
}
