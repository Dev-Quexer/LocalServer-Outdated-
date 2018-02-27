package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class TeamCMD extends Command {
    public TeamCMD(String team) {
        super(team);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
        sender.sendMessage(BungeeSystem.getPrefix()+"§7Folgende §cTeammitglieder §7sind online§8:");
        BungeeSystem.getBungeeCord().getPlayers().forEach(p -> {
            if(p.hasPermission("team")) {
                sender.sendMessage(BungeeSystem.getPrefix()+"§7- §e" + p.getDisplayName());
            }
        });
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
    }
}
