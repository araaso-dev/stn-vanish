package com.sam.stnVanish;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    private List<UUID> vanishedPlayers = new ArrayList<>();
    private JavaPlugin plugin;

    public VanishCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (vanishedPlayers.contains(player.getUniqueId())) {
                vanishedPlayers.remove(player.getUniqueId());
                for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
                    onlinePlayer.showPlayer(plugin, player);
                }
                stopVanishMessage(player);
            } else {
                vanishedPlayers.add(player.getUniqueId());
                for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
                    if (!onlinePlayer.isOp()) {
                        onlinePlayer.hidePlayer(plugin, player);
                    }
                }
                startVanishMessage(player);
            }
        }
        return true;
    }

    public void startVanishMessage(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!vanishedPlayers.contains(player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aYou are currently vanished"));
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void stopVanishMessage(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
    }

    public List<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}