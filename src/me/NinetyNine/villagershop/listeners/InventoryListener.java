package me.NinetyNine.villagershop.listeners;

import me.NinetyNine.villagershop.VillagerShop;
import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.ability.AbilityManager;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

public class InventoryListener implements Listener {

    private final Map<Player, Ability> playerToAbility = new HashMap<>();
    private VillagerShop plugin;

    public InventoryListener(VillagerShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getCurrentItem() == null) return;

        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getClickedInventory().getTitle().equalsIgnoreCase("Ability Shop")) {
            if (playerToAbility.containsKey(player)) return;
            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
                default:
                    break;
                case "Tanker":
                    player.openInventory(plugin.getConfirmation());
                    playerToAbility.put(player, Ability.get("Tanker"));
                    break;
                case "DoubleOre":
                    player.openInventory(plugin.getConfirmation());
                    playerToAbility.put(player, Ability.get("DoubleOre"));
                    break;
            }
        }

        if (e.getClickedInventory().getTitle().equalsIgnoreCase("Confirmation")) {
            if (!playerToAbility.containsKey(player)) return;

            switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) {
                default:
                    break;
                case "Confirm":
                    player.closeInventory();
                    buy(player, playerToAbility.get(player));
                    playerToAbility.remove(player);
                    break;
                case "Cancel":
                    playerToAbility.remove(player);
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "Successfully canceled the purchase!");
                    break;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!e.getInventory().getTitle().equalsIgnoreCase("Confirmation")) return;

        Player player = (Player) e.getPlayer();
        playerToAbility.remove(player);
        player.sendMessage(ChatColor.RED + "Successfully canceled the purchase!");
    }

    private void buy(Player player, Ability ability) {
        if (!has(player, ability)) {
            player.sendMessage(ChatColor.RED + "You don't have enough money to buy this ability!");
            return;
        }

        EconomyResponse r = plugin.getEconomy().withdrawPlayer(player, ability.getCost());

        if (r.transactionSuccess()) {
            AbilityManager.getInstance().buy(ability, player);
            Bukkit.getLogger().info(player.getName() + " has bought " + ability.getAbilityName());
            player.sendMessage(ChatColor.GREEN + "Successfully bought " + ability.getAbilityName() + "!");
        } else
            player.sendMessage(ChatColor.RED + "Transaction failed! Please contact an administrator!");
    }

    private boolean has(Player player, Ability ability) {
        return plugin.getEconomy().has(player, ability.getCost());
    }
}
