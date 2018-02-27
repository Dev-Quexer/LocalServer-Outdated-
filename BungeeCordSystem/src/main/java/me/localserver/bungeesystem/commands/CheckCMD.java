package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.BanManager;
import me.localserver.bungeesystem.utils.MuteManager;
import me.localserver.bungeesystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CheckCMD extends Command {
    public CheckCMD (String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission("ban.ban")) {
            if(args.length == 1) {
                BanManager.runAsync(new Runnable() {
                    @Override
                    public void run() {


                        String name = args[0];

                        sender.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
                        sender.sendMessage(BungeeSystem.getPrefix()+"§7Name§8: §a"+name);
                        sender.sendMessage(BungeeSystem.getPrefix()+"§7UUID§8: §a"+ UUIDFetcher.getUUID(name));
                        if(BanManager.isBanned(name)) {
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Gebannt§8: §aJa");
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Ban-ID§8: §c"+BanManager.getID(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Verbleibende Zeit§8: "+BanManager.getEndAsString(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Von§8: §e"+BanManager.getVonwem(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Grund§8: §e"+ BanManager.getGrund(name));
                        } else {
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Gebannt§8: §cNein");
                        }
                        if(MuteManager.isMutened(name)) {
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Gemutet§8: §aJa");
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Mute-ID§8: §c"+MuteManager.getID(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Verbleibende Zeit§8: "+MuteManager.getEndAsString(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Von§8: §e"+MuteManager.getVonwem(name));
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Grund§8: §e"+MuteManager.getGrund(name));
                        } else {
                            sender.sendMessage(BungeeSystem.getPrefix()+"§7Gemutet§8: §cNein");
                        }
                        sender.sendMessage(BungeeSystem.getPrefix()+"§7BanPoints§8: §e"+String.valueOf(BanManager.getBanPoints(name)));
                        BanManager.getBanHistory(name).forEach(str -> sender.sendMessage("§8┃ §e§lHistory §8» §7"+str));


                        sender.sendMessage("§8§m-------------------------");


                    }
                });



            } else {
                sender.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/check <Name>");
            }






        }


    }
}
