package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.BanManager;
import me.localserver.bungeesystem.utils.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCMD extends Command {

    public UnBanCMD(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.unban")) {
            if(args.length == 3) {
                String name = args[0];
                String typ = args[1];
                String grund = args[2];


                    MuteManager.runAsync(() -> {
                        if(typ.equalsIgnoreCase("mute")) {
                            if(MuteManager.isMutened(name)) {
                                MuteManager.unMutePlayer(name, grund, s.getName());
                            } else {
                                s.sendMessage(BungeeSystem.getPrefix()+"§7Dieser Spieler ist §cnicht §7gemutet§8!");
                            }
                        } else if(typ.equalsIgnoreCase("ban")) {
                            if(BanManager.isBanned(name)) {
                                BanManager.unBanPlayer(name, grund, s.getName());
                            } else {
                                s.sendMessage(BungeeSystem.getPrefix()+"§7Dieser Spieler ist §cnicht §7gebannt§8!");
                            }
                        } else {
                            s.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/unban <Name> <Mute|Ban> <Grund>");
                        }

                    });



            } else {
                s.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/unban <Name> <Mute|Ban> <Grund>");
            }
        }
    }

}
