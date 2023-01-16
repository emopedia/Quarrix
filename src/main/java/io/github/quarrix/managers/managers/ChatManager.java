package io.github.quarrix.managers;

import io.github.quarrix.misc.ColorTranslate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {
    private RankManager rankManager;

    public ChatManager(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    public static String getChatFormat(Player player, String message) {
        return ColorTranslate.translate("&8[${rankManager.getRank(player).getPrefix()}] &6") + player.getName() + ColorTranslate.translate("&7: &f") + message;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setMessage(ChatManager.getChatFormat(player, message));
    }
}