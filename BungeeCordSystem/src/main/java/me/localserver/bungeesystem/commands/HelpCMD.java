package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class HelpCMD extends Command {

    public HelpCMD(String name) {
        super(name);
    }


    @Override
    public void execute(CommandSender s, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) s;
        p.sendMessage(BungeeSystem.getPrefix()+"§8§m--------------------------");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/Help §8- §7Sendet dir alle Commands");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/Report [Name] [Grund] §8- §7Meldet einen Spieler");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/TeamSpeak §8- §7Gibt dir unsere §eTeamSpeak §7Adresse");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/Premium §8- §7zeigt dir, wo du dir §6Premium §7usw. kaufen kannst");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/Online §8- §7Zeigt dir an wie viele §eSpieler §7online sind");
        p.sendMessage(BungeeSystem.getPrefix()+"§e/Team §8- §7Zeigt dir eine §eListe §7von allen aktiven Teammitgliedern");
        p.sendMessage(BungeeSystem.getPrefix()+"§8§m--------------------------");
    }
}
