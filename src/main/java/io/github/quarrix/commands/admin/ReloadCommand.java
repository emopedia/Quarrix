package io.github.quarrix.commands.admin;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.Stats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Stats.reload();
        Quarrix.getInstance().reloadConfig();
        player.sendMessage("Reload successful");


        return true;
    }
}
