package dev.m7mqd.anticrashplus.protocol.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;



import dev.m7mqd.anticrashplus.utils.CrashType;
import dev.m7mqd.anticrashplus.utils.EventAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


public class BookPackets extends PacketAdapter{
	public BookPackets(Plugin plugin) {
		super(plugin, PacketType.Play.Client.BLOCK_PLACE);
	}
	public void onPacketReceiving(PacketEvent event) {
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		if(player == null || !player.isOnline()){
			event.setCancelled(true);
			return;
		}
		ItemStack stack = event.getPacket().getItemModifier().readSafely(0);
		if (stack == null || stack.getType() != Material.WRITTEN_BOOK) return;
		ItemStack inHand = event.getPlayer().getItemInHand();
		if (inHand == null ||
				inHand.getType() != Material.WRITTEN_BOOK ||
				(stack.hasItemMeta() && !inHand.isSimilar(stack)) ||
				checkNbtTags(stack)) {
			EventAction.apply(event, plugin, player, CrashType.NBT_TAG);
		}
	}

	private boolean checkNbtTags(ItemStack itemStack){
		NbtCompound root = (NbtCompound)NbtFactory.fromItemTag(itemStack);
		if (root == null) {
			return true;
		}
		if (!root.containsKey("pages")) {
			return true;
		}
		NbtList<Object> pages = root.getList("pages");
		if (pages.size() > 50) {
			return true;

		}
		for (Object page : pages) {
			String pagee = page.toString();
			if (pagee.length() <= 320) continue;
			return true;
		}
		return false;
	}

}
