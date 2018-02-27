package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class TsCMD extends Command {
    public TsCMD(String teamSpeak) {
        super(teamSpeak);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
        sender.sendMessage(BungeeSystem.getPrefix()+"§7Unsere §eTeamSpeak §7Adresse§8:");
        sender.sendMessage(BungeeSystem.getPrefix()+"§7§cLocalServer§7.§cnet");
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
    }
}
