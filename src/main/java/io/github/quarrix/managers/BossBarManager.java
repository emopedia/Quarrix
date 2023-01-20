package io.github.quarrix.managers;

import io.github.quarrix.Quarrix;
import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BarColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarManager implements Listener {
    private BossBar bossBar;
    private String[] messages = new String[]{
            "&e&oFound a bug? Tell a &4&oStaff Member!",
            "&dWant some Exclusive perks? &b&oType /donate",
            "&3There are currently &a{onlineplayers}&c/&a{maxplayers} &3inmates online"
    };
    private int currentIndex = 0;
    private int taskId;

    public void start() {
        bossBar = Bukkit.createBossBar(ColorTranslate.translate(messages[currentIndex]), BarColor.BLUE, BarStyle.SEGMENTED_10);
        bossBar.setProgress(1);
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Quarrix.getInstance(), new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                if (currentIndex >= messages.length) {
                    currentIndex = 0;
                }
                String message = messages[currentIndex];
                message = message.replace("{onlineplayers}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                message = message.replace("{maxplayers}", String.valueOf(Bukkit.getMaxPlayers()));
                bossBar.setTitle(ColorTranslate.translate(message));
            }
        }, 100L, 100L);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
        bossBar.removeAll();
    }

    public void onJoin(PlayerJoinEvent event) {
        bossBar.addPlayer(event.getPlayer());
    }
    public void onLeave(PlayerQuitEvent event) {
        bossBar.addPlayer(event.getPlayer());
    }
}
