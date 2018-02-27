package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PremiumCMD extends Command {
    public PremiumCMD(String premium) {
        super(premium);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
        sender.sendMessage(BungeeSystem.getPrefix()+"§7Du kannst dir §6Premium§8, §eLegende§8, §5Supreme§8 §7kaufen:");
        sender.sendMessage(BungeeSystem.getPrefix()+"§cLocalServer§8.§cnet§8/§cForum");
        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
    }
}
