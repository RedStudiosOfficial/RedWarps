package it.r3dd0yt.redWarps.commands;

import it.r3dd0yt.redWarps.RedWarps;
import it.r3dd0yt.redWarps.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WarpCommand implements CommandExecutor {

    private final RedWarps plugin = RedWarps.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (args.length == 0) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("warp.usage")));
            return true;
        }
        String warpName = args[0];
        File warp = new File(plugin.getDataFolder() + "/warps", warpName + ".yml");
        if (!warp.exists()) {
            p.sendMessage(CC.translate(plugin.getConfig().getString("warp.no-exists")));
            return true;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(warp);
        if (!Objects.equals(config.getString(warpName + ".permission"), "none")) {
            if (p.hasPermission(config.getString(warpName + ".permission"))) {
                String worldName = config.getString(warpName + ".world");
                double x = config.getInt(warpName + ".x"), y = config.getInt(warpName + ".y"), z = config.getInt(warpName + ".z");
                float yaw = (float) config.getDouble(warpName + ".yaw"), pitch = (float) config.getDouble(warpName + ".pitch");
                World world = Bukkit.getWorld(worldName);
                Location warpLocation = new Location(world, x, y, z, yaw, pitch);
                p.teleport(warpLocation);
            } else{
                p.sendMessage(CC.translate(plugin.getConfig().getString("warp.no-permission")));
                return true;
            }
        }
        String worldName = config.getString(warpName + ".world");
        double x = config.getInt(warpName + ".x"), y = config.getInt(warpName + ".y"), z = config.getInt(warpName + ".z");
        float yaw = (float) config.getDouble(warpName + ".yaw"), pitch = (float) config.getDouble(warpName + ".pitch");
        World world = Bukkit.getWorld(worldName);
        Location warpLocation = new Location(world, x, y, z, yaw, pitch);
        p.teleport(warpLocation);
        return false;
    }

}
