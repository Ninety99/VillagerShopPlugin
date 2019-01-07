package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TankAbility extends Ability {

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
    }

    @Override
    public String getAbilityName() {
        return "Tanker";
    }

    @Override
    public String getDescription() {
        return "&b5% chance to get &6resistance 3 &bfor \n10 seconds after being hit.";
    }

    @Override
    public int getCost() {
        return 10000;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        if (e.getEntity().isDead()) return;

        if (!plugin.getAManager().hasAbility(this, (Player) e.getEntity()))
            return;

        e.getEntity().sendMessage("chance is: " + this.getChance());

        if (this.getChance() <= 0.05) {
            Player player = (Player) e.getEntity();

            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 2));
            player.sendMessage(ChatColor.GREEN + "You have been given resistance 3 for 10 seconds! "
                    + ChatColor.AQUA + "(" + getAbilityName() + ")");
        }
    }
}
