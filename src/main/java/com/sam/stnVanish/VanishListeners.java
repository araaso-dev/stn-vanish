package com.sam.stnVanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class VanishListeners implements Listener {

    private VanishCommand vanishCommand;

    public VanishListeners(VanishCommand vanishCommand) {
        this.vanishCommand = vanishCommand;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joiningPlayer = event.getPlayer();

        if (vanishCommand.getVanishedPlayers().contains(joiningPlayer.getUniqueId())) {
            event.setJoinMessage(null);
            vanishCommand.startVanishMessage(joiningPlayer);

            for (Player onlinePlayer : vanishCommand.getPlugin().getServer().getOnlinePlayers()) {
                if (!onlinePlayer.isOp()) {
                    onlinePlayer.hidePlayer(vanishCommand.getPlugin(), joiningPlayer);
                }
            }
        }

        updatePlayerVisibility(joiningPlayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player quittingPlayer = event.getPlayer();
        if (vanishCommand.getVanishedPlayers().contains(quittingPlayer.getUniqueId())) {
            event.setQuitMessage(null);
        }
    }

    public void updatePlayerVisibility(Player player) {
        if (player.isOp()) {
            for (UUID vanishedUUID : vanishCommand.getVanishedPlayers()) {
                Player vanishedPlayer = vanishCommand.getPlugin().getServer().getPlayer(vanishedUUID);
                if (vanishedPlayer != null) {
                    player.showPlayer(vanishCommand.getPlugin(), vanishedPlayer);
                }
            }
        } else {
            for (UUID vanishedUUID : vanishCommand.getVanishedPlayers()) {
                Player vanishedPlayer = vanishCommand.getPlugin().getServer().getPlayer(vanishedUUID);
                if (vanishedPlayer != null) {
                    player.hidePlayer(vanishCommand.getPlugin(), vanishedPlayer);
                }
            }
        }
    }

    public VanishCommand getVanishCommand() {
        return vanishCommand;
    }
}