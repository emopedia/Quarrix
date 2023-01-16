package io.github.quarrix;

import io.github.quarrix.commands.player.*;
import io.github.quarrix.listeners.BlockBreakListener;
import io.github.quarrix.listeners.PlayerJoinListener;
import io.github.quarrix.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Quarrix extends JavaPlugin {
    private static Quarrix plugin;

    private FileConfiguration stats;
    private File statsFile;

    RankManager rankManager = new RankManager();
    ChatManager chatManager = new ChatManager(rankManager);
    BossBarManager bossBar = new BossBarManager();
    ChestShopManager chestShopManager = new ChestShopManager();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        getServer().getPluginManager().registerEvents(new PVPManager(), this);
        getServer().getPluginManager().registerEvents(new DamageManager(), this);
        getServer().getPluginManager().registerEvents(new CommissaryManager(), this);
        getServer().getPluginManager().registerEvents(new RankManager(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(chestShopManager, this);

        saveConfig();

    }

    @Override
    public void onDisable() {
    }

    public static Quarrix getInstance() {
        return plugin;
    }
}
