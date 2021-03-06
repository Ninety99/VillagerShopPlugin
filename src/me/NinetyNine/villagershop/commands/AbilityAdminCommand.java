package me.NinetyNine.villagershop.commands;

import me.NinetyNine.villagershop.VillagerShop;
import me.NinetyNine.villagershop.ability.Ability;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityAdminCommand implements CommandExecutor {

    private VillagerShop plugin;

    public AbilityAdminCommand(VillagerShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("aability")) {
            if (!(sender.hasPermission("ability.admin"))) {
                sender.sendMessage(ChatColor.RED + "You do not have permissions to execute this command!");
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.BOLD + "Usage: /aability <give/remove/list> <player> <ability name>");
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("spawn")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        plugin.getManager().spawnVillager(player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "Successfully spawned merchant!");
                        return true;
                    } else {
                        sender.sendMessage("Only players can execute this command!");
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("despawn")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        plugin.getManager().despawn(plugin.getUtil().getTarget(player));
                        player.sendMessage(ChatColor.GREEN + "Successfully despawned merchant!");
                        return true;
                    } else {
                        sender.sendMessage("Only players can execute this command!");
                        return true;
                    }
                }
            }

            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("price")) {
                    Ability ability = getAbility(sender, args[1]);

                    if (ability == null) return true;

                    if (args.length == 3) {
                        int cost = getCost(sender, args[2]);

                        if (cost == 0)
                            return true;

                        plugin.getPConfig().getConfig().set(ability.getAbilityName() + ".price", cost);
                        VillagerShop.getInstance().saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Successfully set the price of " +
                                ability.getAbilityName() + " to " + cost);
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.BOLD + "Usage: /aability price <ability> <price>");
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("list")) {
                    Player player = getPlayer(sender, args[1]);
                    if (player == null) return true;

                    StringBuilder builder = new StringBuilder();
                    String[] abilities = null;

                    if (this.plugin.getAManager().hasAbilities(player)) {
                        for (Ability a : this.plugin.getAManager().getAbilities(player))
                            builder.append(a.getAbilityName());

                        abilities = builder.toString().split(",");
                    }

                    String msg = abilities != null ? StringUtils.join(abilities) : "None";

                    sender.sendMessage(ChatColor.BOLD + "Abilities of " + player.getName() + ":" + "\n"
                            + msg);
                    return true;
                }

                if (args[0].equalsIgnoreCase("give")) {
                    Player player = getPlayer(sender, args[1]);

                    if (player == null) return true;

                    if (args.length == 3) {
                        Ability ability = getAbility(sender, args[2]);

                        if (ability == null) return true;

                        this.plugin.getAManager().buy(ability, player);
                        player.sendMessage(ChatColor.GREEN + "Successfully given "
                                + ability.getAbilityName() + " to " + player.getName() + "!");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.BOLD + "Usage: /aability give <player> <ability>");
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    Player player = getPlayer(sender, args[1]);

                    if (player == null) return true;

                    if (args.length == 3) {
                        Ability ability = getAbility(sender, args[2]);

                        if (ability == null) return true;

                        this.plugin.getAManager().remove(ability, player);
                        player.sendMessage(ChatColor.GREEN + "Successfully removed "
                                + ability.getAbilityName() + " from " + player.getName() + "!");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.BOLD + "Usage: /aability remove <player> <ability>");
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private Player getPlayer(CommandSender sender, String arg) {
        Player player = Bukkit.getPlayer(arg);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return null;
        }

        return player;
    }

    private Ability getAbility(CommandSender sender, String arg) {
        Ability a = Ability.get(arg);

        if (a == null) {
            sender.sendMessage(ChatColor.RED + "Ability not found!");
            return null;
        }

        return a;
    }

    private int getCost(CommandSender sender, String arg) {
        int cost = 0;

        try {
            cost = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid number!");
        }

        return cost;
    }
}
