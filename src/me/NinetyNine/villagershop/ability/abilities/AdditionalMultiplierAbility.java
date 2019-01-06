package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.Config;
import org.bukkit.Material;
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
        return "&bA 10% chance to gain\n an extra 0.25 sell multiplier";
    }

    @Override
    public int getCost() {
        return Config.getInstance().getConfig().getInt(getAbilityName() + ".price");
    }

    /*
    TODO: Listen for selling
     */
}
