package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class OnlineCMD extends Command {
    public OnlineCMD(String online) {
        super(online);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
        sender.sendMessage(BungeeSystem.getPrefix()+"§7Es sind §c"+BungeeSystem.getBungeeCord().getPlayers().size()+" §eSpieler §7online§8!");
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
    }
}
