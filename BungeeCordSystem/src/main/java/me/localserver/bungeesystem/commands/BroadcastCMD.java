package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCMD extends Command {
    public BroadcastCMD(String bc) {
        super(bc);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("broadcast")) {
            if(args.length >= 1) {
                String msg = "";

                for (int i = 0; i < args.length; i++) {
                    msg = msg + args[i] + " ";
                }

                BungeeSystem.getBungeeCord().broadcast(BungeeSystem.getPrefix() + " ");
                BungeeSystem.getBungeeCord().broadcast(BungeeSystem.getPrefix() + "§7" + ChatColor.translateAlternateColorCodes('&', msg));
                BungeeSystem.getBungeeCord().broadcast(BungeeSystem.getPrefix() + " ");
            } else {
                sender.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/bc|alert|broadcast [Nachricht]");
            }
        } else {
            sender.sendMessage(BungeeSystem.getNoPerms());
        }
    }
}
