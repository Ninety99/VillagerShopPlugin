package me.NinetyNine.villagershop.commands;

import me.NinetyNine.villagershop.PPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("ability")) {
            if (args.length == 0) {
                PPlayer p = PPlayer.getPlayer(player);

                if (p == null) {
                    player.sendMessage(ChatColor.RED + "Cannot find your data! Please report to an administrator!");
                    return true;
                }

                player.openInventory(p.getAbilityInventory());
                return true;
            }
        }
        return true;
    }
}
