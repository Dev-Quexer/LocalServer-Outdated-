package me.quexer.lobbysystem.commands;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setLocCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(sender.hasPermission("lobby.setloc")) {
            //setLoc location
            if(args.length == 1) {
                String name = args[0];
                ServerAPI.getLocationAPI().setLocation("Lobby."+name, p);
                ServerAPI.getSoundManager().playNormal(p);
                p.sendMessage(Lobby.getPrefix()+"§7Du hast die Location§8: §e"+name+" §7gesetzt§8!");
                ServerAPI.getLocationAPI().save();
            } else {
                p.sendMessage(Lobby.getPrefix()+"§7Benutze§8: §c/setLoc [Name]");
                ServerAPI.getSoundManager().playBad(p);
            }
        } else {
            p.sendMessage(Lobby.getPrefix()+"§cDazu hast du keine Rechte§8!");
            ServerAPI.getSoundManager().playBad(p);
        }

        return true;
    }
}
