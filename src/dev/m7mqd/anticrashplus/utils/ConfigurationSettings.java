package dev.m7mqd.anticrashplus.utils;


import java.util.List;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationSettings {
	private @Getter static List<String> IgnoredConsoleMessages;
	private @Getter static int PacketsLimit;
	private @Getter static String prefix, AlertMessage, KickMessage,
			PacketsLimitKickMessage, PacketsLimitAlertMessage
			, NoPermissionMessage;
	public static void reload(FileConfiguration config){
		IgnoredConsoleMessages = config.getStringList("console-ignored-messages");
		PacketsLimit = config.getInt("packets-limit");
		prefix = TextHelper.format(config.getString("prefix"));
		AlertMessage = filter(config.getString("crash-alert-message"));
		KickMessage = filter(config.getString("crash-kick-message"));
		PacketsLimitAlertMessage = filter(config.getString("packets-limit-alert-message"));
		PacketsLimitKickMessage = filter(config.getString("packets-limit-kick-message"));
		NoPermissionMessage = filter(config.getString("command-no-permission-message"));

	}
	public static String getNoPermissionMessage(String player) { return NoPermissionMessage.replace("%player%", player);}
	public static String getAlertMessage(String playerName, String crasherName, CrashType type) {
		return (type == CrashType.PACKETS_SPAM ? PacketsLimitAlertMessage : AlertMessage).
				replace("%crasher%", crasherName).
				replace("%method%", type.name()).
				replace("%player%", playerName);
	}
	public static String getKickMessage(String crasherName, CrashType type) {
		return (type == CrashType.PACKETS_SPAM ? PacketsLimitKickMessage : KickMessage).
				replace("%crasher%", crasherName).
				replace("%method%", type.name());
	}
	private static String filter(String message){
		return TextHelper.format(message).replace("%nl%","\n").replace("%newline%","\n").replace("%prefix%", prefix);
	}
}
