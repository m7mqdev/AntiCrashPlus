package dev.m7mqd.anticrashplus.utils;

import org.bukkit.ChatColor;

public class TextHelper {


    public static String format(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
