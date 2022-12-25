package dev.m7mqd.anticrashplus;


import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.m7mqd.anticrashplus.bukkit.commands.ReloadCommand;
import dev.m7mqd.anticrashplus.protocol.listeners.BookPackets;
import dev.m7mqd.anticrashplus.protocol.listeners.*;
import dev.m7mqd.anticrashplus.logger.ConsoleFilter;
import dev.m7mqd.anticrashplus.utils.AutoUpdate;
import dev.m7mqd.anticrashplus.utils.TextHelper;
import dev.m7mqd.anticrashplus.utils.ConfigurationSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

public class AntiCrashPlusPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &aThe AntiCrashPlus plugin is now loading..."));
		if(!pluginManager.isPluginEnabled("ProtocolLib")){
			console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &cCould not find ProtocolLib, Disabling the plugin..."));
			pluginManager.disablePlugin(this);
			return;
		}
		loadConfig();
		ConfigurationSettings.reload(getConfig());
		this.getCommand("anticrashplus").setExecutor(new ReloadCommand(this));
		if(SpigotConfig.bungee && !Bukkit.getOnlineMode()) {
			Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		}
		registerConsoleFilter();
		registerListeners();
        console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &aAntiCrashPlus plugin is successfully loaded."));
		new AutoUpdate(this, 90749).getLatestVersion(version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &aThe plugin is up to date."));
				return;
			}
				console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &cThe plugin is outdated."));
				console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &7The newer version: " + version));
				console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &cYour outdated version: " + getDescription().getVersion()));
				console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &aPlease update the plugin."));
		});
	}
	public void loadConfig() {
		saveDefaultConfig();
	}
	public void registerConsoleFilter(){
		Logger logger = (Logger) LogManager.getRootLogger();
		logger.addFilter(new ConsoleFilter());
	}

	public void registerListeners(){
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new CustomPayLoadPackets(this));
		protocolManager.addPacketListener(new CreativePackets(this));
		protocolManager.addPacketListener(new BookPackets(this));
	}
	public void unregisterListeners(){
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.removePacketListeners(this);
	}

	@Override
	public void onDisable() {
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &cUnloading the plugin..."));
		this.saveConfig();
        unregisterListeners();
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
       	console.sendMessage(TextHelper.format("&8[&e&lAntiCrash+&8] &cThe plugin is successfully unloaded."));
	}
}
