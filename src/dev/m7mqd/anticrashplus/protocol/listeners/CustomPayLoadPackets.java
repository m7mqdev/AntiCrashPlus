package dev.m7mqd.anticrashplus.protocol.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;


import dev.m7mqd.anticrashplus.utils.*;
import io.netty.buffer.ByteBuf;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;


public class CustomPayLoadPackets
extends PacketAdapter {
    private final ArrayList<UUID> uuids = new ArrayList<>();
    public CustomPayLoadPackets(Plugin plugin) {
        super(plugin, PacketType.Play.Client.CUSTOM_PAYLOAD);
    }
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled() || player == null || !player.isOnline()) return;
        String PacketType = event.getPacket().getStrings().getValues().get(0);
        if(
                PacketType == null
                        || PacketType.equalsIgnoreCase("MC|BSign")
                        || PacketType.equalsIgnoreCase("MC|BEdit")
                        || PacketType.equalsIgnoreCase("MC|BOpen")
        ) {
            takeActions(event, plugin, player, CrashType.CustomPayLoad);
            return;
        }
        if(((ByteBuf)event.getPacket().getModifier().getValues().get(1)).capacity() > ConfigurationSettings.getPacketsLimit()) {
            takeActions(event, plugin, player, CrashType.PACKETS_SPAM);
        }

    }
        // to reduce repeating code
        public void takeActions(PacketEvent event, Plugin plugin, Player player, CrashType crashType){
            if(uuids.contains(player.getUniqueId())) {
                EventAction.apply(event, plugin, player, crashType);
                uuids.remove(player.getUniqueId());
                return;
            }
            uuids.add(player.getUniqueId());
        }
    }

