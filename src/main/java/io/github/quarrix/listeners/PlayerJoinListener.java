package io.github.quarrix.listeners;

import io.github.quarrix.managers.BossBarManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;

        ItemStack bread = new ItemStack(Material.BREAD, 4);
        player.getInventory().addItem(bread);
    }
}
