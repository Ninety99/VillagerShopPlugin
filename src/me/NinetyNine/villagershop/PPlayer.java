package me.NinetyNine.villagershop;

import lombok.Getter;
import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.ability.AbilityManager;
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

    public PPlayer(Player player) {
        this.player = player;
        this.abilityInventory = Bukkit.createInventory(null, 27, player.getName() + "'s Abilities");

        if (!playerToPP.containsKey(player))
            playerToPP.put(player, this);
    }

    public static PPlayer getPlayer(Player player) {
        return playerToPP.getOrDefault(player, null);
    }

    public List<Ability> getAbilities() {
        return AbilityManager.getInstance().hasAbilities(player) ? AbilityManager.getInstance().getAbilities(player) : null;
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
