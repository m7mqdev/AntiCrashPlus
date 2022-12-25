package dev.m7mqd.anticrashplus.utils;

import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EventAction {

    public static void apply(PacketEvent event, Plugin plugin, Player player, CrashType crashType){
        event.setCancelled(true);
        KickSafely.kick(plugin, player, crashType);
        Alerts.send(player.getName(), crashType);
    }
}
