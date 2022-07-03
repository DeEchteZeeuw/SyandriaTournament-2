package io.github.deechtezeeuw.syandriatournament.utils;

import org.bukkit.ChatColor;

public class TextColor {
    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String colorPrefix(String text) {
        return ChatColor.translateAlternateColorCodes('&', "&7&LIron&9&LKingdom &8&L» " + text);
    }

    public String blockColor(int i) {
        switch (i) {
            case 15:
                return "&0";
            case 14:
                return "&4";
            case 13:
                return "&2";
            case 12:
                return "&6";
            case 11:
                return "&9";
            case 10:
                return "&5";
            case 9:
                return "&3";
            case 8:
                return "&7";
            case 7:
                return "&8";
            case 6:
                return "&c";
            case 5:
                return "&a";
            case 4:
                return "&e";
            case 3:
                return "&b";
            case 2:
                return "&5";
            case 1:
                return "&6";
            default:
                return "&f";
        }
    }

    public String strip(String text) {
        return ChatColor.stripColor(text);
    }
}
