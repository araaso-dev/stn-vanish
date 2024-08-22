package com.sam.stnVanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OpStatusChangeListener implements Listener {

    private VanishListeners vanishListeners;

    public OpStatusChangeListener(VanishListeners vanishListeners) {
        this.vanishListeners = vanishListeners;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        if (command.startsWith("/op ") || command.startsWith("/deop ")) {
            String[] parts = command.split(" ");
            if (parts.length > 1) {
                String targetPlayerName = parts[1];
                Player targetPlayer = event.getPlayer().getServer().getPlayer(targetPlayerName);
                if (targetPlayer != null) {
                    event.getPlayer().getServer().getScheduler().runTask(vanishListeners.getVanishCommand().getPlugin(), () -> {
                        vanishListeners.updatePlayerVisibility(targetPlayer);
                    });
                }
            }
        }
    }
}
