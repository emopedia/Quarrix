package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DamageManager implements Listener {
    private Map<UUID, BukkitTask> tasks;

    public DamageManager() {
        tasks = new HashMap<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = event.getTo().getBlock();

        if (block.getType() == Material.COBWEB) {
            if (!tasks.containsKey(player.getUniqueId())) {
                BukkitTask task = Bukkit.getScheduler().runTaskTimer(Quarrix.getInstance(), () -> {
                    player.damage(1);
                }, 0L, 20L);
                tasks.put(player.getUniqueId(), task);
            }
        } else if (tasks.containsKey(player.getUniqueId())) {
            tasks.get(player.getUniqueId()).cancel();
            tasks.remove(player.getUniqueId());
        }
    }
}
