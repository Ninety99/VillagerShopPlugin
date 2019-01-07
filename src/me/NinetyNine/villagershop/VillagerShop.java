package me.NinetyNine.villagershop;

import lombok.Getter;
import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.ability.AbilityManager;
import me.NinetyNine.villagershop.commands.AbilityAdminCommand;
import me.NinetyNine.villagershop.commands.AbilityCommand;
import me.NinetyNine.villagershop.listeners.InventoryListener;
import me.NinetyNine.villagershop.listeners.PlayerListener;
import me.NinetyNine.villagershop.listeners.VillagerListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagerShop extends JavaPlugin {

    @Getter
    private static VillagerShop instance;

    @Getter
    private final Inventory shop = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Ability Shop");
    @Getter
    private final Inventory confirmation = Bukkit.createInventory(null, 27, "Confirmation");
    @Getter
    private Economy economy;

    @Getter
    private VillagerManager manager;

    @Getter
    private AbilityManager aManager;

    @Getter
    private Config pConfig;

    @Getter
    private Util util;

    @Override
    public void onEnable() {
        this.setupEconomy();

        if (this.economy != null)
            this.getLogger().info("Successfully hooked into Vault!");

        instance = this;

        this.manager = new VillagerManager(this);
        this.aManager = new AbilityManager();
        this.pConfig = new Config(this);
        this.pConfig.init();
        this.util = new Util();
        this.registerListeners();
        this.registerCommands();
        this.setupShop();
        this.setupConfirmation();

        Ability.initialize(this);
        this.getLogger().info("Successfully enabled VillagerShop v"
                + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Successfully disabled VillagerShop v"
                + this.getDescription().getVersion());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new VillagerListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
    }

    private void registerCommands() {
        getCommand("aability").setExecutor(new AbilityAdminCommand(this));
        getCommand("ability").setExecutor(new AbilityCommand());
    }

    private void setupShop() {
        for (Ability a : this.getAManager().getAbilities()) {
            if (this.shop.contains(a.getItemShop().getType())) continue;

            this.shop.addItem(a.getItemShop());
        }
    }

    @SuppressWarnings("deprecation")
    private void setupConfirmation() {
        ItemStack confirm = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
        ItemMeta meta = confirm.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(meta);

        ItemStack cancel = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
        ItemMeta meta2 = confirm.getItemMeta();
        meta2.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta(meta2);

        getConfirmation().setItem(11, confirm);
        getConfirmation().setItem(15, cancel);
    }

    private void setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }

        economy = rsp.getProvider();
    }
}
