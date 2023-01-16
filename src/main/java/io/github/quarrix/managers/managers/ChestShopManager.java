package io.github.quarrix.managers;

import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class ChestShopManager implements Listener {
    private FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(new File("stats.yml"));

    private Sign getAttachedSign(Chest chest) {
        Block block = chest.getBlock();
        for (BlockFace face : BlockFace.values()) {
            Block rel = block.getRelative(face);
            if (rel.getState() instanceof Sign) {
                return (Sign) rel.getState();
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                Block block = event.getClickedBlock().getRelative(((org.bukkit.material.Sign) sign.getData()).getAttachedFace());
                if (block.getState() instanceof Chest) {
                    Chest chest = (Chest) block.getState();
                    String firstLine = sign.getLine(0);
                    String secondLine = sign.getLine(1);
                    String thirdLine = sign.getLine(2);
                    String fourthLine = sign.getLine(3);

                    if (firstLine.equalsIgnoreCase("[Sell]")) {
                        double price = Double.parseDouble(thirdLine);
                        sell(player, chest, price);
                    } else if (firstLine.equalsIgnoreCase("[Buy]")) {
                        double price = Double.parseDouble(thirdLine);
                        buy(player, chest, price);
                    }
                }
            }
        }
    }

    public void sell(Player player, Chest chest, double price) {
        double playerMoney = statsConfig.getDouble(player.getUniqueId() + ".money");
        if (playerMoney >= price) {
            Inventory chestInventory = chest.getInventory();
            if (!chestInventory.isEmpty()) {
                chestInventory.clear();
                playerMoney -= price;
                statsConfig.set(player.getUniqueId() + ".money", playerMoney);
                player.sendMessage(ColorTranslate.translate("&aSell successful! &6" + price + " &ahas been added to your balance."));
            } else {
                player.sendMessage(ColorTranslate.translate("&cThis chest is empty!"));
            }
        } else {
            player.sendMessage(ColorTranslate.translate("&cYou do not have enough money to make this purchase!"));
        }
    }

    // check if player has enough money and chest has space
// if so, remove money from player's balance in stats.yml and add items to chest
    public void buy(Player player, Chest chest, double price) {
        double playerMoney = statsConfig.getDouble(player.getUniqueId() + ".money");
        if (playerMoney >= price) {
            Inventory chestInventory = chest.getInventory();
            if (chestInventory.firstEmpty() != -1) {
// add items to chest
                playerMoney -= price;
                statsConfig.set(player.getUniqueId() + ".money", playerMoney);
                player.sendMessage(ColorTranslate.translate("&aPurchase successful! &6" + price + " &ahas been removed from your balance."));
            } else {
                player.sendMessage(ColorTranslate.translate("&cThis chest is full!"));
            }
        } else {
            player.sendMessage(ColorTranslate.translate("&cYou do not have enough money to make this purchase!"));
        }
    }
}