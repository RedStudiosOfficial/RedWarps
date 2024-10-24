package it.r3dd0yt.redWarps.commands;

import it.r3dd0yt.redWarps.RedWarps;
import it.r3dd0yt.redWarps.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ResetWarpsCommand implements CommandExecutor {

    private RedWarps plugin = RedWarps.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("redwarps.resetwarps")) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("no-permissions")));
            return true;
        }
        File warpsFolder = new File(plugin.getDataFolder(), "/warps");
        if (!warpsFolder.exists()) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("reset-warps.no-warps")));
            return true;
        }
        File[] warps = warpsFolder.listFiles();
        if (warps == null || warps.length == 0) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("reset-warps.no-warps")));
            return true;
        }
        CompletableFuture.runAsync(() -> {
            for (File warp : warps) warp.delete();
        }, RedWarps.THREADS);
        p.sendMessage(CC.translate(plugin.getConfig().getString("reset-warps.success")));
        return false;
    }
}
