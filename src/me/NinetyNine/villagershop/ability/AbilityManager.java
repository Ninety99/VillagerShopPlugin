package me.NinetyNine.villagershop.ability;

import lombok.Getter;
import me.NinetyNine.villagershop.PPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class AbilityManager {

    @Getter
    private static final AbilityManager instance = new AbilityManager();

    private final Map<String, List<Ability>> playerToAbility = new HashMap<>();

    public void buy(Ability ability, Player player) {
        if (player == null) return;
        if (ability == null) return;

        if (hasAbility(ability, player)) {
            Bukkit.getServer().getLogger().warning("Tried to give "
                    + ability.getAbilityName() + " to " + player.getName() + " but they already have it!");
            return;
        }

        PPlayer p = PPlayer.getPlayer(player);

        if (p == null) {
            Bukkit.getServer().getLogger().warning("Tried to give "
                    + ability.getAbilityName() + " to " + player.getName() + " but PPlayer is null!");
            return;
        }

        if (!hasAbilities(player))
            playerToAbility.put(player.getUniqueId().toString(), new ArrayList<>(Arrays.asList(ability)));
        else
            playerToAbility.get(player.getUniqueId().toString()).add(ability);

        p.loadInventory();
    }

    public void remove(Ability ability, Player player) {
        if (player == null) return;
        if (ability == null) return;

        if (hasAbilities(player))
            playerToAbility.get(player.getUniqueId().toString()).remove(ability);
        else
            Bukkit.getServer().getLogger().warning("Tried to remove "
                    + ability.getAbilityName() + " from " + player.getName() + " but they do not have it!");

        PPlayer p = PPlayer.getPlayer(player);

        if (p == null) {
            Bukkit.getServer().getLogger().warning("Tried to give "
                    + ability.getAbilityName() + " to " + player.getName() + " but PPlayer is null!");
            return;
        }

        if (playerToAbility.get(player.getUniqueId().toString()).size() == 0)
            playerToAbility.remove(player.getUniqueId().toString());

        p.loadInventory();
    }

    public List<Ability> getAbilities(Player player) {
        if (player == null) return null;

        return hasAbilities(player) ? playerToAbility.get(player.getUniqueId().toString()) : null;
    }

    public boolean hasAbilities(Player player) {
        if (player == null) return false;

        return playerToAbility.containsKey(player.getUniqueId().toString());
    }

    public boolean hasAbility(Ability ability, Player player) {
        if (player == null) return false;
        if (ability == null) return false;

        return hasAbilities(player) && playerToAbility.get(player.getUniqueId().toString())
                .contains(ability);
    }
}
