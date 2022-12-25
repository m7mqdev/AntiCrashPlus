package dev.m7mqd.anticrashplus.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.valuga.spigot.packet.PacketManager;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spigotmc.SpigotConfig;

import java.lang.reflect.InvocationTargetException;


public class KickSafely {

    public static void kick(Plugin plugin, Player player, CrashType type){
        String kickMessage = ConfigurationSettings.getKickMessage(player.getName(), type);
        if(SpigotConfig.bungee && !Bukkit.getOnlineMode()) {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            output.writeUTF("KickPlayer");
            output.writeUTF(player.getName());
            output.writeUTF(kickMessage);
            player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
            return;
        }
        if(!player.isOnline()) return;
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.KICK_DISCONNECT);
        packetContainer.getChatComponents().write(0, WrappedChatComponent.fromText(kickMessage));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException ignored) {
            player.kickPlayer(kickMessage);
        }
    }
}
