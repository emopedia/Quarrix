package io.github.quarrix.commands.player;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private FileConfiguration config;

    public SpawnCommand() {
        this.config = Quarrix.getInstance().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (config.contains("spawn")) {
                World world = Bukkit.getWorld(config.getString("spawn.world"));
                double x = config.getDouble("spawn.x");
                double y = config.getDouble("spawn.y");
                double z = config.getDouble("spawn.z");
                float yaw = (float) config.getDouble("spawn.yaw");
                float pitch = (float) config.getDouble("spawn.pitch");
                Location spawn = new Location(world, x, y, z, yaw, pitch);

                player.teleport(spawn);
                player.sendMessage("Teleported to spawn.");
            } else {
                player.sendMessage("Spawn has not been set yet.");
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("setspawn")) {
            if (!player.hasPermission("quarrix.setspawn")) {
                player.sendMessage(ColorTranslate.translate("&cYou do not have permission to set the spawn location."));
                return true;
            }

            Location location = player.getLocation();
            config.set("spawn.world", location.getWorld().getName());
            config.set("spawn.x", location.getX());
            config.set("spawn.y", location.getY());
            config.set("spawn.z", location.getZ());
            config.set("spawn.yaw", location.getYaw());
            config.set("spawn.pitch", location.getPitch());
            Quarrix.getInstance().saveConfig();

            player.sendMessage(ColorTranslate.translate("&aSpawn location set successfully."));
        } else if (args[0].equalsIgnoreCase("delspawn")) {
            if (!player.hasPermission("quarrix.delspawn")) {
                player.sendMessage(ColorTranslate.translate("&cYou do not have permission to delete the spawn location."));
                return true;
            }

            config.set("spawn", null);
            Quarrix.getInstance().saveConfig();

            player.sendMessage(ColorTranslate.translate("&aSpawn location deleted successfully."));
        } else {
            player.sendMessage(ColorTranslate.translate("&cInvalid usage. /spawn <setspawn|delspawn>"));
        }

        return true;
    }
}
