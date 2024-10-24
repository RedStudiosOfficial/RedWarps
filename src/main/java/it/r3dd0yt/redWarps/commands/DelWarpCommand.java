package it.r3dd0yt.redWarps.commands;

import it.r3dd0yt.redWarps.RedWarps;
import it.r3dd0yt.redWarps.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class DelWarpCommand implements CommandExecutor {

    private final RedWarps plugin = RedWarps.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("redwarps.delwarp")) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("no-permissions")));
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("delwarp.usage")));
            return true;
        }
        String warpName = args[0];
        File warp = new File(plugin.getDataFolder() + "/warps", warpName + ".yml");
        if (!warp.exists()) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("delwarp.no-exists")));
            return true;
        }
        CompletableFuture.runAsync(warp::delete, RedWarps.THREADS);
        p.sendMessage(CC.translate(plugin.getConfig().getString("delwarp.success").replace("%warp%", warpName)));
        return false;
    }
}
