package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.ReportAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class ReportssCMD extends Command {
    public ReportssCMD(String reportss) {
        super(reportss);
    }

    @Override
    public void execute(CommandSender s, String[] args) {


        ProxiedPlayer p = (ProxiedPlayer)s;
        //reportss accept <Spieler>
        if(s.hasPermission("report.accept")) {
        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("erstawerd")) {
                String name = args[1];

                if (BungeeSystem.getBungeeCord().getPlayer(name) != null) {

                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
                        if (!ReportAPI.isInProgress(name) && ReportAPI.isReportet(name)) {
                            ReportAPI.acceptReport(name, p);
                        } else {
                            p.sendMessage(BungeeSystem.getPrefix() + "§cDieser Report wird bereits bearbeitet§8!");
                        }

                    });

                } else {
                    p.sendMessage(BungeeSystem.getPrefix() + "§cDieser Spieler ist offline§8!");
                }
            }
            }

        } else {
            ((ProxiedPlayer) s).sendMessage(BungeeSystem.getNoPerms());
        }

    }

}
