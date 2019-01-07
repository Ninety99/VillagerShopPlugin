package me.NinetyNine.villagershop;

import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private VillagerShop plugin;

    public Config(VillagerShop plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void init() {
        getConfig().options().copyDefaults(true);

        for (Ability a : this.plugin.getAManager().getAbilities()) {
            getConfig().addDefault(a.getAbilityName() + ".price", 0);
            Bukkit.getLogger().info("Adding defaults " + a.getAbilityName());
        }
        plugin.saveConfig();
    }
}
