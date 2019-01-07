package me.NinetyNine.villagershop.ability.abilities;

import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

public class AttackerAbility extends Ability {

    @Override
    public ItemStack getIcon() {
        ItemStack pot = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) pot.getItemMeta();
        meta.setMainEffect(PotionEffectType.INCREASE_DAMAGE);
        pot.setItemMeta(meta);
        return pot;
    }

    @Override
    public String getAbilityName() {
        return "Attacker";
    }

    @Override
    public String getDescription() {
        return "&b5% chance to increase your damage\n by 120%!";
    }

    @Override
    public int getCost() {
        return 5000;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;

        Player attacker = (Player) e.getDamager();

        if (!plugin.getAManager().hasAbility(this, attacker)) return;

        Player player = (Player) e.getEntity();

        double chance = this.getChance();
        float damage = (float) e.getDamage() * 2.2F;

        if (chance <= 0.05) {
            if ((player.getHealth() - damage) >= 0.99) {
                e.setDamage(damage);
                attacker.sendMessage(ChatColor.GREEN + "Successfully increased damage by 120%! " + ChatColor.AQUA
                        + "(" + getAbilityName() + ")");
            }
        }
    }
}
