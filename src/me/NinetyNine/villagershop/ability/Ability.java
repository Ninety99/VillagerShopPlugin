package me.NinetyNine.villagershop.ability;

import me.NinetyNine.villagershop.VillagerShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Ability implements Listener {

    public static VillagerShop plugin;

    public static void initialize(VillagerShop plugin2) {
        plugin = plugin2;

        for (Ability a : plugin.getAManager().getAbilities()) {
            Bukkit.getServer().getPluginManager().registerEvents(a, plugin);
            plugin.getPConfig().getConfig().set(a.getAbilityName() + ".price", a.getCost());
            System.out.println("Loaded " + a.getAbilityName() + "!");
        }

        plugin.saveConfig();
        System.out.println("Loaded " + plugin.getAManager().getAbilities().size() + " abilities!");
    }

    public static Ability get(String name) {
        for (Ability a : plugin.getAManager().getAbilities()) {
            if (a.getAbilityName().equalsIgnoreCase(name.toLowerCase()))
                return a;
        }

        return null;
    }

    protected double getChance() {
        return ThreadLocalRandom.current().nextDouble(1);
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
