package com.sam.stnVanish;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        VanishCommand vanishCommand = new VanishCommand(this);
        VanishListeners vanishListeners = new VanishListeners(vanishCommand);
        OpStatusChangeListener opStatusChangeListener = new OpStatusChangeListener(vanishListeners);

        getCommand("vanish").setExecutor(vanishCommand);
        getServer().getPluginManager().registerEvents(vanishListeners, this);
        getServer().getPluginManager().registerEvents(opStatusChangeListener, this);

        System.out.println(ChatColor.GREEN + "[i]" + ChatColor.DARK_GREEN + " STN-Vanish plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "[i]" + ChatColor.DARK_RED + " STN-Vanish plugin has been disabled.");
    }
}
