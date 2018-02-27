package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.ReportAPI;
import me.localserver.bungeesystem.utils.TextBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReportCMD extends Command {
    public ReportCMD(String report) {
        super(report);
    }

    public static List<ProxiedPlayer> used = new ArrayList<>();

    @Override
    public void execute(CommandSender s, String[] args) {


        ProxiedPlayer p = (ProxiedPlayer)s;
        //reportspigot <Spieler> <Grund>
        if(args.length == 2) {
            if(!used.contains(p)) {
                String name = args[0];
                String reason = args[1];
                if (BungeeSystem.getInstance().getProxy().getPlayer(name) != null) {
                    if (BungeeSystem.getReasons().contains(reason.toUpperCase())) {
                        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
                            ReportAPI.reportPlayer(name, p.getName(), reason);
                            p.sendMessage(new TextComponent(BungeeSystem.getPrefix() + "§7Du hast den Spieler §e" + name + " §7erfolgreich gemeldet§8!"));
                            used.add(p);
                            BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () -> {
                                used.remove(p);
                            }, 3, TimeUnit.MINUTES);
                        });
                    } else {
                        p.sendMessage(new TextComponent(BungeeSystem.getPrefix() + "§8§m-------------------------"));
                        BungeeSystem.getReasons().forEach(str -> {
                            new TextBuilder(BungeeSystem.getPrefix() + "§7§l-- §e" + str).setHover("§7Diesen Spieler wegen §e" + str + " §7reporten§8!").setClick("report " + name + " " + str).sendToPlayer(p);
                        });
                        p.sendMessage(new TextComponent(BungeeSystem.getPrefix() + "§8§m-------------------------"));
                    }
                }
            } else {
                p.sendMessage(BungeeSystem.getPrefix()+"§cWarte einen Moment, bis du jemanden reportest");
            }

        } else {
            p.sendMessage(new TextComponent(BungeeSystem.getPrefix()+"§7Benutze§8: §c/report <Name> <Grund>"));
        }


    }
}
