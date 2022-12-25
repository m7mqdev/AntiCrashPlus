package dev.m7mqd.anticrashplus.bukkit.commands;

import dev.m7mqd.anticrashplus.utils.TextHelper;
import dev.m7mqd.anticrashplus.utils.ConfigurationSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.plugin.Plugin;

public class ReloadCommand implements CommandExecutor{
	private final Plugin plugin;
	public ReloadCommand(Plugin plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
			sender.sendMessage(TextHelper.format(ConfigurationSettings.getPrefix() + " &cUsage: /AntiCrashPlus reload"));
			return true;
		}
		if(!sender.hasPermission("anticrashplus.reload")) {
			sender.sendMessage(ConfigurationSettings.getNoPermissionMessage(sender.getName()));
			return true;
		}
		sender.sendMessage(TextHelper.format(ConfigurationSettings.getPrefix() + " &aReloading the configuration..."));
		plugin.reloadConfig();
		ConfigurationSettings.reload(plugin.getConfig());
		sender.sendMessage(TextHelper.format(ConfigurationSettings.getPrefix() + " &aThe configuration has been reloaded successfully."));
		return true;
	}
	
}
