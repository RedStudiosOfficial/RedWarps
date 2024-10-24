package it.r3dd0yt.redWarps.commands;

import it.r3dd0yt.redWarps.RedWarps;
import it.r3dd0yt.redWarps.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class SetWarpCommand implements CommandExecutor {
    private final RedWarps plugin = RedWarps.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("redwarps.setwarp")) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("no-permissions")));
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("setwarp.usage")));
            return true;
        }
        String warpName = args[0];
        File warp = new File(plugin.getDataFolder() + "/warps", warpName + ".yml");
        if (warp.exists()) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("setwarp.already-exists")));
            return true;
        }
        CompletableFuture.runAsync(() -> {
            try {
                warp.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create warp file", e);
            }
        }, RedWarps.THREADS);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(warp);
        CompletableFuture.runAsync(() -> {
            fileConfiguration.set(warpName + ".world", p.getLocation().getWorld().getName());
            fileConfiguration.set(warpName + ".x", p.getLocation().getX());
            fileConfiguration.set(warpName + ".y", p.getLocation().getY());
            fileConfiguration.set(warpName + ".z", p.getLocation().getZ());
            fileConfiguration.set(warpName + ".yaw", p.getLocation().getYaw());
            fileConfiguration.set(warpName + ".pitch", p.getLocation().getPitch());
            if (args.length > 2) {
                fileConfiguration.set(warpName + ".permission", args[1]);
            } else {
                fileConfiguration.set(warpName + ".permission", "none");
            }
            try {
                fileConfiguration.save(warp);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save warp file", e);
            }
        }, RedWarps.THREADS);
        p.sendMessage(CC.translate(plugin.getConfig().getString("setwarp.success").replace("%warp%", warpName)));
        return false;
    }
}
