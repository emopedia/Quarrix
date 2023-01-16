package io.github.quarrix.misc;

import org.bukkit.ChatColor;

public class ColorTranslate {
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
