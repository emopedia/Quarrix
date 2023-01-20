package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
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

        if (rank.equals("E4") && money >= 20000) {
            stats.set(player.getUniqueId() + ".rank", "E3");
            stats.set(player.getUniqueId() + ".nextrank", "E2");
            stats.set(player.getUniqueId() + ".nextrankcost", 40000);
            stats.set(player.getUniqueId() + ".money", money - 20000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in E3.");
            player.closeInventory();
        } else if (rank.equals("E3") && money >= 40000) {
            stats.set(player.getUniqueId() + ".rank", "E2");
            stats.set(player.getUniqueId() + ".nextrank", "E1");
            stats.set(player.getUniqueId() + ".nextrankcost", 60000);
            stats.set(player.getUniqueId() + ".money", money - 40000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in E2.");
            player.closeInventory();
        } else if (rank.equals("E2") && money >= 60000) {
            stats.set(player.getUniqueId() + ".rank", "E1");
            stats.set(player.getUniqueId() + ".nextrank", "D4");
            stats.set(player.getUniqueId() + ".nextrankcost", 80000);
            stats.set(player.getUniqueId() + ".money", money - 60000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in E1.");
            player.closeInventory();
        } else if (rank.equals("E1") && money >= 80000) {
            stats.set(player.getUniqueId() + ".rank", "D4");
            stats.set(player.getUniqueId() + ".nextrank", "D3");
            stats.set(player.getUniqueId() + ".nextrankcost", 100000);
            stats.set(player.getUniqueId() + ".money", money - 80000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in D4.");
            player.closeInventory();
            clearCell(player);
        } else if (rank.equals("D4") && money >= 100000) {
            stats.set(player.getUniqueId() + ".rank", "D3");
            stats.set(player.getUniqueId() + ".nextrank", "D2");
            stats.set(player.getUniqueId() + ".nextrankcost", 140000);
            stats.set(player.getUniqueId() + ".money", money - 100000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in D3.");
            player.closeInventory();
        } else if (rank.equals("D3") && money >= 140000) {
            stats.set(player.getUniqueId() + ".rank", "D2");
            stats.set(player.getUniqueId() + ".nextrank", "D1");
            stats.set(player.getUniqueId() + ".nextrankcost", 180000);
            stats.set(player.getUniqueId() + ".money", money - 140000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in D2.");
            player.closeInventory();
        } else if (rank.equals("D2") && money >= 180000) {
            stats.set(player.getUniqueId() + ".rank", "D1");
            stats.set(player.getUniqueId() + ".nextrank", "C4");
            stats.set(player.getUniqueId() + ".nextrankcost", 240000);
            stats.set(player.getUniqueId() + ".money", money - 180000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in D1.");
            player.closeInventory();
        } else if (rank.equals("D1") && money >= 240000) {
            stats.set(player.getUniqueId() + ".rank", "C4");
            stats.set(player.getUniqueId() + ".nextrank", "C3");
            stats.set(player.getUniqueId() + ".nextrankcost", 360000);
            stats.set(player.getUniqueId() + ".money", money - 240000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in C4.");
            player.closeInventory();
            clearCell(player);
        } else if (rank.equals("C4") && money >= 360000) {
            stats.set(player.getUniqueId() + ".rank", "C3");
            stats.set(player.getUniqueId() + ".nextrank", "C2");
            stats.set(player.getUniqueId() + ".nextrankcost", 520000);
            stats.set(player.getUniqueId() + ".money", money - 360000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in C3.");
            player.closeInventory();
        } else if (rank.equals("C3") && money >= 520000) {
            stats.set(player.getUniqueId() + ".rank", "C2");
            stats.set(player.getUniqueId() + ".nextrank", "C1");
            stats.set(player.getUniqueId() + ".nextrankcost", 640000);
            stats.set(player.getUniqueId() + ".money", money - 520000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in C2.");
            player.closeInventory();
        } else if (rank.equals("C2") && money >= 640000) {
            stats.set(player.getUniqueId() + ".rank", "C1");
            stats.set(player.getUniqueId() + ".nextrank", "B4");
            stats.set(player.getUniqueId() + ".nextrankcost", 880000);
            stats.set(player.getUniqueId() + ".money", money - 640000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in C1.");
            player.closeInventory();
        } else if (rank.equals("C1") && money >= 880000) {
            stats.set(player.getUniqueId() + ".rank", "B4");
            stats.set(player.getUniqueId() + ".nextrank", "B3");
            stats.set(player.getUniqueId() + ".nextrankcost", 1200000);
            stats.set(player.getUniqueId() + ".money", money - 880000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in B4.");
            player.closeInventory();
            clearCell(player);
        } else if (rank.equals("B4") && money >= 1200000) {
            stats.set(player.getUniqueId() + ".rank", "B3");
            stats.set(player.getUniqueId() + ".nextrank", "B2");
            stats.set(player.getUniqueId() + ".nextrankcost", 1800000);
            stats.set(player.getUniqueId() + ".money", money - 1200000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in B3.");
            player.closeInventory();
        } else if (rank.equals("B3") && money >= 1800000) {
            stats.set(player.getUniqueId() + ".rank", "B2");
            stats.set(player.getUniqueId() + ".nextrank", "B1");
            stats.set(player.getUniqueId() + ".nextrankcost", 2800000);
            stats.set(player.getUniqueId() + ".money", money - 1800000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in B2.");
            player.closeInventory();
        } else if (rank.equals("B2") && money >= 2800000) {
            stats.set(player.getUniqueId() + ".rank", "B1");
            stats.set(player.getUniqueId() + ".nextrank", "A4");
            stats.set(player.getUniqueId() + ".nextrankcost", 4000000);
            stats.set(player.getUniqueId() + ".money", money - 2800000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in B1.");
            player.closeInventory();
        } else if (rank.equals("B1") && money >= 4000000) {
            stats.set(player.getUniqueId() + ".rank", "A4");
            stats.set(player.getUniqueId() + ".nextrank", "A3");
            stats.set(player.getUniqueId() + ".nextrankcost", 4400000);
            stats.set(player.getUniqueId() + ".money", money - 4000000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in A4.");
            player.closeInventory();
            clearCell(player);
        } else if (rank.equals("A4") && money >= 4400000) {
            stats.set(player.getUniqueId() + ".rank", "A3");
            stats.set(player.getUniqueId() + ".nextrank", "A2");
            stats.set(player.getUniqueId() + ".nextrankcost", 6200000);
            stats.set(player.getUniqueId() + ".money", money - 4400000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in A3.");
            player.closeInventory();
        } else if (rank.equals("A3") && money >= 6200000) {
            stats.set(player.getUniqueId() + ".rank", "A2");
            stats.set(player.getUniqueId() + ".nextrank", "A1");
            stats.set(player.getUniqueId() + ".nextrankcost", 8400000);
            stats.set(player.getUniqueId() + ".money", money - 6200000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in A2.");
            player.closeInventory();
        } else if (rank.equals("A2") && money >= 8400000) {
            stats.set(player.getUniqueId() + ".rank", "A1");
            stats.set(player.getUniqueId() + ".nextrank", "E4");
            stats.set(player.getUniqueId() + ".nextrankcost", 12000000);
            stats.set(player.getUniqueId() + ".money", money - 8400000);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has ranked up! They're now in A1.");
            player.closeInventory();
        } else if (rank.equals("A1") && money >= 12000000) {
            stats.set(player.getUniqueId() + ".rank", "E4");
            stats.set(player.getUniqueId() + ".nextrank", "E3");
            stats.set(player.getUniqueId() + ".nextrankcost", 20000);
            stats.set(player.getUniqueId() + ".money", 0);
            try {
                stats.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.broadcastMessage("§a" + player.getName() + " has prestiged!");
            player.closeInventory();
            clearCell(player);
        } else {
            player.sendMessage("§cYou do not have enough to rank up.");
            player.sendMessage("§cCost:" + stats.get(player.getUniqueId() + ".nextrankcost"));
            player.closeInventory();
        }
    }

    private void clearCell(Player player) {
        // code to clear player's cell
    }
}

