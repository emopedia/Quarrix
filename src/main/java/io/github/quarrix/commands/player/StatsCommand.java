package io.github.quarrix.commands.player;

import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class StatsCommand implements CommandExecutor {

    private FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(new File("stats.yml"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerUUID = player.getUniqueId().toString();

            if (statsConfig.contains(playerUUID)) {
                String money = ColorTranslate.translate("&aMoney: &6" + statsConfig.getDouble(playerUUID + ".money"));
                String rank = ColorTranslate.translate("&aRank: &6" + statsConfig.getString(playerUUID + ".rank"));
                String nextRank = ColorTranslate.translate("&aNext Rank: &6" + statsConfig.getString(playerUUID + ".nextrank"));
                String nextRankCost = ColorTranslate.translate("&aNext Rank Cost: &6" + statsConfig.getDouble(playerUUID + ".nextrankcost"));
                String tickets = ColorTranslate.translate("&aTickets: &6" + statsConfig.getInt(playerUUID + ".tickets"));

                player.sendMessage(ColorTranslate.translate("&6========================"));
                player.sendMessage(ColorTranslate.translate("&aYour Stats:"));
                player.sendMessage(ColorTranslate.translate("&6========================"));
                player.sendMessage(money);
                player.sendMessage(rank);
                player.sendMessage(nextRank);
                player.sendMessage(nextRankCost);
                player.sendMessage(tickets);
            } else {
                player.sendMessage(ColorTranslate.translate("&cError: Could not find your stats in the stats.yml file."));
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}