package io.github.quarrix;

import io.github.quarrix.commands.admin.*;
import io.github.quarrix.commands.player.*;
import io.github.quarrix.items.ExplosivePickaxe;
import io.github.quarrix.listeners.BlockBreakListener;
import io.github.quarrix.listeners.PlayerJoinListener;
import io.github.quarrix.managers.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Quarrix extends JavaPlugin {
    private static Quarrix instance;
    private Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        setupEconomy();
        registerCommands();
        registerListeners();
        registerItems();

    }

    @Override
    public void onDisable() {
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault dependency not found!");
            return;
        }
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }

    private void registerCommands() {
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("reload").setExecutor(new ReloadCommand());
    }

    private void registerListeners() {
        var pm = getServer().getPluginManager();

        pm.registerEvents(new PVPManager(), this);
        pm.registerEvents(new DamageManager(), this);
        pm.registerEvents(new CommissaryManager(), this);
        pm.registerEvents(new RankManager(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new ChestShopManager(), this);
        pm.registerEvents(new RankUpManager(), this);
    }

    private void registerItems() {
        getServer().getPluginManager().registerEvents(new ExplosivePickaxe(), this);
    }

    public static Quarrix getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }
}
