package me.NinetyNine.villagershop.ability;

import lombok.Getter;
import me.NinetyNine.villagershop.VillagerShop;
import me.NinetyNine.villagershop.ability.abilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Ability implements Listener {

    @Getter
    private static final List<Ability> abilities = Arrays.asList(new DoubleOreAbility(), new TankAbility(),
            new AttackerAbility(), new LegacyAbility(), new AdditionalMultiplierAbility());

    public static void initialize() {
        for (Ability a : getAbilities()) {
            Bukkit.getServer().getPluginManager().registerEvents(a, VillagerShop.getInstance());
            System.out.println("Loaded " + a.getAbilityName() + "!");
        }

        System.out.println("Loaded " + getAbilities().size() + " abilities!");
    }

    public static Ability get(String name) {
        for (Ability a : getAbilities()) {
            if (a.getAbilityName().equalsIgnoreCase(name.toLowerCase()))
                return a;
        }

        return null;
    }

    protected double getChance() {
        double random = ThreadLocalRandom.current().nextDouble(1);
        return random;
    }

    public ItemStack getItem() {
        ItemStack item = getItemShop();
        ItemMeta meta = item.getItemMeta();

        List<String> newLore = new ArrayList<>();

        for (String l : meta.getLore()) {
            if (l.toLowerCase().contains("cost"))
                continue;

            newLore.add(l);
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getItemShop() {
        ItemStack item = this.getIcon();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.getAbilityName());
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', this.getDescription()));
        lore.add(" ");
        lore.add(ChatColor.RED + "Price: $" + this.getCost());
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public abstract ItemStack getIcon();

    public abstract String getAbilityName();

    public abstract String getDescription();

    public abstract int getCost();
}
