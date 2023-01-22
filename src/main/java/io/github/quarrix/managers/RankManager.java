package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
import io.github.quarrix.enums.Rank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;

public class RankManager implements Listener {

    private final FileConfiguration rankConfig = YamlConfiguration.loadConfiguration(new File("ranks.yml"));

    public void setRank(Player player, Rank rank) {
        rankConfig.set("Ranks." + player.getUniqueId(), rank.toString());
    }

    public Rank getRank(Player player) {
        String val = rankConfig.getString("Ranks." + player.getUniqueId());
        return (val == null ? Rank.E4 : Rank.valueOf(val));
    }

    public boolean hasRank(Player player, Rank rank) {
        return (getRank(player).compareTo(rank) <= 0);
    }

}
