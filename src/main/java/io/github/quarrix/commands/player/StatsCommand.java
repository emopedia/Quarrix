package io.github.quarrix.commands.player;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.ColorTranslate;
import io.github.quarrix.misc.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class StatsCommand implements CommandExecutor {
    private Quarrix plugin = Quarrix.getPlugin(Quarrix.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File folder = new File(plugin.getDataFolder() + "");
        Config statsConfig = new Config(folder, "stats", plugin);
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        if (!statsConfig.getConfig().contains(playerUUID)) {
            statsConfig.set(playerUUID + ".money", 0);
            statsConfig.set(playerUUID + ".rank", "E4");
            statsConfig.set(playerUUID + ".nextrank", "E3");
            statsConfig.set(playerUUID + ".nextrankcost", 20000);
            statsConfig.set(playerUUID + ".tickets", 0);
            statsConfig.save();
        }
        String money = ColorTranslate.translate("&aMoney: &6" + statsConfig.getConfig().getInt(playerUUID + ".money"));
        String rank = ColorTranslate.translate("&aRank: &6" + statsConfig.getConfig().getString(playerUUID + ".rank"));
        String nextRank = ColorTranslate.translate("&aNext Rank: &6" + statsConfig.getConfig().getString(playerUUID + ".nextrank"));
        String nextRankCost = ColorTranslate.translate("&aNext Rank Cost: &6" + statsConfig.getConfig().getInt(playerUUID + ".nextrankcost"));
        String tickets = ColorTranslate.translate("&aTickets: &6" + statsConfig.getConfig().getInt(playerUUID + ".tickets"));

        player.sendMessage(ColorTranslate.translate("&6========================"));
        player.sendMessage(ColorTranslate.translate("&aYour Stats:"));
        player.sendMessage(ColorTranslate.translate("&6========================"));
        player.sendMessage(money);
        player.sendMessage(rank);
        player.sendMessage(nextRank);
        player.sendMessage(nextRankCost);
        player.sendMessage(tickets);
        statsConfig.save();
        return true;
    }
}