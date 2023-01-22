package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankUpManager implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        if (!event.getRightClicked().getName().equals("§cWarden")) return;
        Player player = event.getPlayer();

        Inventory gui = Bukkit.createInventory(null, 9, "RankUp");
        ItemStack confirm = new ItemStack(Material.LIME_WOOL, 1);
        ItemStack cancel = new ItemStack(Material.RED_WOOL, 1);
        ItemStack rankup = new ItemStack(Material.PAPER, 1);

        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§aConfirm");
        confirm.setItemMeta(confirmMeta);

        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§cCancel");
        cancel.setItemMeta(cancelMeta);

        ItemMeta rankupMeta = rankup.getItemMeta();
        rankupMeta.setDisplayName("§5RankUp");
        rankupMeta.setLore(Arrays.asList("§4Make sure your cell is empty.", "§4Cells will be cleared when ranking", "§4up to a new ward.         E1 -> D4"));
        rankup.setItemMeta(rankupMeta);

        gui.setItem(0, confirm);
        gui.setItem(8, cancel);
        gui.setItem(4, rankup);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!event.getView().getTitle().equals("RankUp")) return;
        if (event.getSlot() != 0) return; // get the slot number of the item that was clicked
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("§aConfirm")) return;

        Player player = (Player) event.getWhoClicked();
        File statsFile = new File(Quarrix.getInstance().getDataFolder(), "/stats.yml");
        YamlConfiguration stats = YamlConfiguration.loadConfiguration(statsFile);

        String rank = stats.getString(player.getUniqueId() + ".rank");
        int money = stats.getInt(player.getUniqueId() + ".money");

        Map<String, Integer> rankCost = new HashMap<>();
        rankCost.put("E4", 20000);
        rankCost.put("E3", 40000);
        rankCost.put("E2", 60000);
        rankCost.put("E1", 80000);
        rankCost.put("D4", 100000);
        rankCost.put("D3", 140000);
        rankCost.put("D2", 180000);
        rankCost.put("D1", 240000);
        rankCost.put("C4", 360000);
        rankCost.put("C3", 520000);
        rankCost.put("C2", 640000);
        rankCost.put("C1", 880000);
        rankCost.put("B4", 1200000);
        rankCost.put("B3", 1800000);
        rankCost.put("B2", 2800000);
        rankCost.put("B1", 4000000);
        rankCost.put("A4", 4400000);
        rankCost.put("A3", 6200000);
        rankCost.put("A2", 8400000);
        rankCost.put("A1", 12000000);

        Map<String, String> rankMap = new HashMap<>();
        rankMap.put("E4", "E3");
        rankMap.put("E3", "E2");
        rankMap.put("E2", "E1");
        rankMap.put("E1", "D4");
        rankMap.put("D4", "D3");
        rankMap.put("D3", "D2");
        rankMap.put("D2", "D1");
        rankMap.put("D1", "C4");
        rankMap.put("C4", "C3");
        rankMap.put("C3", "C2");
        rankMap.put("C2", "C1");
        rankMap.put("C1", "B4");
        rankMap.put("B4", "B3");
        rankMap.put("B3", "B2");
        rankMap.put("B2", "B1");
        rankMap.put("B1", "A4");
        rankMap.put("A4", "A3");
        rankMap.put("A3", "A2");
        rankMap.put("A2", "A1");
        rankMap.put("A1", "E4");

        List<String> clearCellsRanks = Arrays.asList("D4", "C4", "B4", "A4", "E4");

        if(rankCost.containsKey(rank) && rankMap.containsKey(rank)){
            int cost = rankCost.get(rank);
            String nextRank = rankMap.get(rank);
            if (money >= cost) {
                stats.set(player.getUniqueId() + ".rank", nextRank);
                stats.set(player.getUniqueId() + ".nextrank", rankMap.get(nextRank));
                stats.set(player.getUniqueId() + ".nextrankcost", rankCost.get(nextRank));
                stats.set(player.getUniqueId() + ".money", money - cost);
                Stats.save();
                Stats.reload();
                if(nextRank.equals("E4")){
                    Bukkit.broadcastMessage("§a" + player.getName() + " has prestiged!");
                    stats.set(player.getUniqueId() + ".money", 0);
                } else {
                    Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in " + nextRank + ".");
                }
                if(clearCellsRanks.contains(nextRank)) clearCell(player);
                player.closeInventory();
            } else {
                player.sendMessage("§cYou do not have enough money to rank up.");
                player.closeInventory();
            }
        }
    }
    private void clearCell(Player player) {
        // code to clear player's cell
    }
}

