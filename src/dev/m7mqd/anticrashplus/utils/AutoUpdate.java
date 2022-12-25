package dev.m7mqd.anticrashplus.utils;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class AutoUpdate {
    private final Plugin plugin;
    private final int resourceId;

    public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                Bukkit.getConsoleSender().sendMessage(TextHelper.format(ConfigurationSettings.getPrefix() + "&cCould not get any response from the spigot api."));
            }
        });
    }
}
