package io.github.quarrix.managers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class PVPManager implements Listener {

    private Set<UUID> pvpEnabled = new HashSet<>();
    private HashMap<Player, Boolean> playerOnCarpet = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = event.getTo().getBlock();

        if (block.getType() == Material.RED_CARPET) {
            playerOnCarpet.put(player, true);

            if (!pvpEnabled.contains(player.getUniqueId())) {
                pvpEnabled.add(player.getUniqueId());
                player.sendMessage("PvP Enabled");
            }
        } else if (playerOnCarpet.containsKey(player) && playerOnCarpet.get(player)) {
            playerOnCarpet.remove(player);

            if (pvpEnabled.contains(player.getUniqueId())) {
                pvpEnabled.remove(player.getUniqueId());
                player.sendMessage("PvP Disabled");
            }
        }
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (!pvpEnabled.contains(player.getUniqueId()) || !pvpEnabled.contains(event.getDamager().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        pvpEnabled.remove(player.getUniqueId());
    }
}
