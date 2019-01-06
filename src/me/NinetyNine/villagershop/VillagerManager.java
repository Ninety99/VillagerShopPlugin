package me.NinetyNine.villagershop;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.List;

public class VillagerManager {

    @Getter
    private final List<Villager> villagers = new ArrayList<>();
    private VillagerShop plugin;

    public VillagerManager(VillagerShop plugin) {
        this.plugin = plugin;
    }

    public void spawnVillager(Location location) {
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        villager.setCustomNameVisible(true);
        villager.setCustomName(ChatColor.RED + "Ability Merchant");

        if (!getVillagers().contains(villager))
            getVillagers().add(villager);

        plugin.getUtil().freezeEntity(villager);
    }

    public void despawn(LivingEntity entity) {
        if (!(entity instanceof Villager)) return;

        Villager villager = (Villager) entity;

        if (!getVillagers().contains(villager)) return;

        getVillagers().remove(villager);
        plugin.getUtil().unfreezeEntity(villager);
        entity.remove();
    }
}
