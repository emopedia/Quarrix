package io.github.quarrix.listeners;

import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.util.Random;

public class BlockBreakListener implements Listener {
    //private FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(new File("stats.yml"));
    //private Random rand = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        if (!player.isOp()) {
            event.setCancelled(true);
        }

        //int chance = rand.nextInt(1000);
        //if (chance < 3) {
            //int tickets = statsConfig.getInt(playerUUID + ".tickets");
            //statsConfig.set(playerUUID + ".tickets", tickets + 2);
            //player.sendMessage(ColorTranslate.translate("&aYou received 2 tickets!"));
        //}
    }
}
