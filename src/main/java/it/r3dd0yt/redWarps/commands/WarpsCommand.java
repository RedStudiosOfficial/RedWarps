package it.r3dd0yt.redWarps.commands;

import it.r3dd0yt.redWarps.RedWarps;
import it.r3dd0yt.redWarps.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class WarpsCommand implements CommandExecutor {

    private RedWarps plugin = RedWarps.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("redwarps.warps")) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("no-permissions")));
            return true;
        }
        File folder = new File(plugin.getDataFolder().getAbsoluteFile(), "/warps");
        File[] files = folder.listFiles();
        StringBuilder stringBuilder = new StringBuilder();
        if (files.length == 0) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("warps.no-warps")));
            return true;
        }
        for (File f : files) {
            stringBuilder.append(f.getName().replace(".yml", "")).append(", ");
        }
        String message = stringBuilder.substring(0, stringBuilder.length() - 2);
        p.sendMessage(CC.translate(plugin.getConfig().getString("warps.list").replace("%warps%", message)));
        return false;
    }
}
