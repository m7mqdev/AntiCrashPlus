package dev.m7mqd.anticrashplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Alerts {
    public static void send(String crasherName, CrashType crashType){
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("anticrash.alert")) {
                players.sendMessage(ConfigurationSettings.getAlertMessage(players.getName(), crasherName, crashType));
            }
        }
    }
}
