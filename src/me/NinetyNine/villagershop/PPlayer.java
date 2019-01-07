package me.NinetyNine.villagershop;

import lombok.Getter;
import me.NinetyNine.villagershop.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PPlayer {

    private static final Map<Player, PPlayer> playerToPP = new HashMap<>();

    private Player player;

    @Getter
    private Inventory abilityInventory;

    private VillagerShop plugin;

    public PPlayer(Player player, VillagerShop plugin) {
        this.player = player;
        this.abilityInventory = Bukkit.createInventory(null, 27, player.getName() + "'s Abilities");
        this.plugin = plugin;

        if (!playerToPP.containsKey(player))
            playerToPP.put(player, this);
    }

    public static PPlayer getPlayer(Player player) {
        return playerToPP.getOrDefault(player, null);
    }

    public List<Ability> getAbilities() {
        return this.plugin.getAManager().hasAbilities(player) ? this.plugin.getAManager().getAbilities(this.player) : null;
    }

    public void loadInventory() {
        if (getAbilityInventory() == null)
            return;

        for (Ability a : getAbilities()) {
            if (getAbilityInventory().contains(a.getItem().getType()))
                getAbilityInventory().remove(a.getItem());

            getAbilityInventory().addItem(a.getItem());
        }
    }
}
