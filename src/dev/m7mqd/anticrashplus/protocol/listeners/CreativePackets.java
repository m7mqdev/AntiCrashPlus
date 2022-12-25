package dev.m7mqd.anticrashplus.protocol.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev.m7mqd.anticrashplus.utils.Alerts;
import dev.m7mqd.anticrashplus.utils.CrashType;
import dev.m7mqd.anticrashplus.utils.EventAction;
import dev.m7mqd.anticrashplus.utils.KickSafely;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class CreativePackets extends PacketAdapter {
    public CreativePackets(Plugin plugin) {
        super(plugin, PacketType.Play.Client.SET_CREATIVE_SLOT);
    }
    public void onPacketReceiving(PacketEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();
        if (player == null || !player.isOnline()) {
            event.setCancelled(true);
            return;
        }
        if(player.getGameMode() != GameMode.CREATIVE)
            EventAction.apply(event, plugin, player, CrashType.CREATIVE_PACKETS);

    }
}
