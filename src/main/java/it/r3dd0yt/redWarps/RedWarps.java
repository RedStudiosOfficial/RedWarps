package it.r3dd0yt.redWarps;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.r3dd0yt.redWarps.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public final class RedWarps extends JavaPlugin {

    private static RedWarps plugin;
    public static ExecutorService THREADS;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().log(Level.INFO, "RedWarps Plugin Enabled");
        saveDefaultConfig();
        THREADS = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setNameFormat("redwarps-thread-%s").build());
        setupWarps();
        setupCommands();
    }

    @Override
    public void onDisable() {
    }

    private void setupCommands() {
        getCommand("warps").setExecutor(new WarpsCommand());
        getCommand("resetwarps").setExecutor(new ResetWarpsCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("warp").setExecutor(new WarpCommand());
    }

    private void setupWarps() {
        File warpsFolder = new File(getDataFolder().getAbsolutePath(), "/warps");

        try {
            if (!warpsFolder.exists()) {
                CompletableFuture.runAsync(warpsFolder::mkdirs, THREADS);
            }
        } catch (Exception e) {
            getLogger().severe("Failed to create warps folder");
        }
    }

    public static RedWarps getPlugin() {
        return plugin;
    }
}
