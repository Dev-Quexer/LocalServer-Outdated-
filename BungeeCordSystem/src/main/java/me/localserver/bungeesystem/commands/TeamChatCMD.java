package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class TeamChatCMD extends Command {
    public TeamChatCMD(String tc) {
        super(tc);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("teamchat")) {
            if(args.length >= 1) {
                String msg = "";

                for (int i = 0; i < args.length; i++) {
                    msg = msg + args[i] + " ";
                }

                String finalMsg = msg;
                BungeeSystem.getBungeeCord().getPlayers().forEach(p -> {
                    p.sendMessage(" ");
                    p.sendMessage("§8✖ §aTeamChat §8§l➜ §e" + p.getDisplayName() + " §8§l» §7" + ChatColor.translateAlternateColorCodes('&', finalMsg));
                    p.sendMessage(" ");
                });
            } else {
                sender.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/tc [Nachricht]");
            }
        } else {
            sender.sendMessage(BungeeSystem.getNoPerms());
        }
    }
}
