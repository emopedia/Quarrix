package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommissaryManager implements Listener {

    private Scoreboard commissaryScoreboard;
    private Map<Player, Integer> playerCountdowns = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                String firstLine = ChatColor.stripColor(sign.getLine(0));
                String secondLine = ChatColor.stripColor(sign.getLine(1));

                if ((firstLine.equals("[Commissary]")) || (secondLine.equals("C3"))) {
                    Inventory inventory = player.getInventory();
                    ItemStack item = new ItemStack(Material.PAPER, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5C3 Shop"));
                    meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&2Temporary access to C3 shop."), ChatColor.translateAlternateColorCodes('&', "&4Costs 500 tickets.")));
                    item.setItemMeta(meta);

                    if (inventory.contains(item)) {
                        inventory.remove(item);
                        player.setScoreboard(commissaryScoreboard);
                        playerCountdowns.put(player, 120);
                        Bukkit.getScheduler().runTaskTimer(Quarrix.getInstance(), () -> {
                            int timeLeft = playerCountdowns.get(player);
                            if (timeLeft > 0) {
                                playerCountdowns.put(player, timeLeft - 1);
                                Objective objective = commissaryScoreboard.getObjective("timeleft");
                                if (objective != null) {
                                    objective.getScore(player.getName()).setScore(timeLeft);
                                }
                            } else {
                                player.teleport(player.getWorld().getSpawnLocation());
                                playerCountdowns.remove(player);
                            }
                        }, 20L, 20L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playerCountdowns.containsKey(player)) {
            playerCountdowns.remove(player);
        }
    }
}




