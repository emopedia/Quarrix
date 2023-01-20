package io.github.quarrix.items;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ExplosivePickaxe implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.DIAMOND_PICKAXE) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getDisplayName().equals("Explosive Pickaxe")) {
                List<String> lore = meta.getLore();
                if (lore != null && lore.contains("Explosive:1")) {
                    Block block = event.getBlock();
                    block.getWorld().createExplosion(block.getLocation(), 3, false, true);
                    event.setDropItems(true);
                }
            }
        }
    }
}