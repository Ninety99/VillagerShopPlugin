package me.NinetyNine.villagershop;

import lombok.Getter;
import me.NinetyNine.villagershop.ability.Ability;
import me.NinetyNine.villagershop.commands.AbilityAdminCommand;
import me.NinetyNine.villagershop.commands.AbilityCommand;
import me.NinetyNine.villagershop.listeners.InventoryListener;
import me.NinetyNine.villagershop.listeners.PlayerListener;
import me.NinetyNine.villagershop.listeners.VillagerListener;
import me.Tibo442.MineShop.Main;
import me.Tibo442.MineShop.SQL;
import me.Tibo442.MineShop.SQLAPI;
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

import java.lang.reflect.Field;

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
    private Config pConfig;

    @Getter
    private Util util;

    @Getter
    private SQLAPI sqlAPI;

    @Override
    public void onEnable() {
        this.setupEconomy();

        if (this.economy != null)
            this.getLogger().info("Successfully hooked into Vault!");

        instance = this;

        this.manager = new VillagerManager(this);
        this.pConfig = new Config(this);
        this.util = new Util();
        this.sqlAPI = this.sqlAPI();
        this.registerListeners();
        this.registerCommands();
        this.setupShop();
        this.setupConfirmation();

        Ability.initialize();
        pConfig.init();
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
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new InventoryListener(this), this);
    }

    private void registerCommands() {
        getCommand("aability").setExecutor(new AbilityAdminCommand(this));
        getCommand("ability").setExecutor(new AbilityCommand());
    }

    private void setupShop() {
        for (Ability a : Ability.getAbilities()) {
            if (shop.contains(a.getItemShop().getType())) continue;

            shop.addItem(a.getItemShop());
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

    private SQLAPI sqlAPI() {
        if (Bukkit.getServer().getPluginManager().getPlugin("MineShop") == null)
            return null;

        try {
            Field fieldSQL = Main.class.getDeclaredField("sql");
            fieldSQL.setAccessible(true);

            SQL sql = (SQL) fieldSQL.get(Main.class);

            Field sqlAPI = Main.class.getDeclaredField("api");
            sqlAPI.setAccessible(true);

            return (SQLAPI) sqlAPI.get(sql);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().warning("API field not found.");
            return null;
        }
    }
}
