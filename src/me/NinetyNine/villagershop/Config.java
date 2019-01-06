package me.NinetyNine.villagershop;

import lombok.Getter;
import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    @Getter
    private static final Config instance = new Config();

    public FileConfiguration getConfig() {
        return VillagerShop.getInstance().getConfig();
    }

    public void init() {
        getConfig().options().copyDefaults(true);

        for (Ability a : Ability.getAbilities()) {
            getConfig().addDefault(a.getAbilityName() + ".price", 0);
            Bukkit.getLogger().info("Adding defaults " + a.getAbilityName());
        }

        VillagerShop.getInstance().saveConfig();
    }
}
