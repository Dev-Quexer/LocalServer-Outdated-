package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.utils.BanManager;
import me.localserver.bungeesystem.utils.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import static me.localserver.bungeesystem.utils.BanManager.getBanPoints;



public class BanCMD extends Command {

    public BanCMD() {
        super("ban");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.ban")) {
            if(args.length == 2) {

                String id = args[1];
                String name = args[0];
                if(!s.hasPermission("ban.admin")) {

                    s.sendMessage("§7Der Spieler wurde §aerfolgreich §7gebannt§8!");
                    BungeeSystem.getBungeeCord().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.sendMessage(BungeeSystem.getPrefix()+"§7Der Spieler §c"+s.getName()+" §7hat versucht §e"+name+" §7zu bannen§8!"));
                    return;
                }

                int BannPoints = getBanPoints(name);
                long dauer = 1;
                if (BannPoints >= 5) {
                     dauer = -1;
                } else if (BannPoints >= 3) {
                    dauer = dauer * 3;
                } else if (BannPoints >= 2) {
                    dauer = dauer * 2;
                }

                long finalDauer = dauer;
                System.out.println(dauer);
                BanManager.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        switch (id) {


                            case "1":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Hacking", s.getName(), (finalDauer == -1) ? -1 : 24 * 30 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "2":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Trolling", s.getName(), (finalDauer == -1) ? -1 : 24 * 4 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;
                            case "3":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Teaming", s.getName(), (finalDauer == -1) ? -1 : 24 * 4 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "4":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Bugusing", s.getName(), (finalDauer == -1) ? -1 : 24 * 7 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "5":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Skin", s.getName(), (finalDauer == -1) ? -1 : 24 * 14 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "6":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Name", s.getName(), (finalDauer == -1) ? -1 : 24 * 30 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "7":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Report-Ausnutzung", s.getName(), (finalDauer == -1) ? -1 : 24 * 2 * finalDauer);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "8":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Bannumgehung", s.getName(), -1);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;

                            case "9":
                                if (s.hasPermission("ban.banban")) {
                                    BanManager.banPlayer(name, "Hausverbot", s.getName(), -1);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;


                            case "10":
                                MuteManager.MutePlayer(name, "Beleidigung", s.getName(), (finalDauer == -1)    ? -1 :  24 * 3 * finalDauer    );
                                break;

                            case "11":
                                MuteManager.MutePlayer(name, "Rassismus", s.getName(), (finalDauer == -1)    ? -1 :  24 * 7 * finalDauer    );
                                break;

                            case "12":
                                MuteManager.MutePlayer(name, "Werbung", s.getName(), (finalDauer == -1)    ? -1 :  24 * 2 * finalDauer    );
                                break;

                            case "13":
                                MuteManager.MutePlayer(name, "Spamming", s.getName(), (finalDauer == -1)    ? -1 :  12 * finalDauer    );
                                break;

                            case "14":
                                MuteManager.MutePlayer(name, "Provokation", s.getName(), (finalDauer == -1)    ? -1 :  12 * finalDauer    );
                                break;

                            case "15":
                                if (s.hasPermission("ban.banban")) {
                                    MuteManager.MutePlayer(name, "Schweigepfilcht", s.getName(), -1);
                                } else {
                                    s.sendMessage(BungeeSystem.getPrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                }
                                break;


                        }
                    }
                });


            } else {
                s.sendMessage(BungeeSystem.getPrefix()+"§7Benutze§8: §c/ban <Spieler> <Grund>");

                TextComponent text = new TextComponent();
                text.setText(BungeeSystem.getPrefix() + "§7Für eine Liste mit Gründen §8[§a§lKlicke hier§8]");
                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reasons"));
                s.sendMessage(text);

            }
        }else {
            s.sendMessage(BungeeSystem.getPrefix()+"§cDazu hast du keine Rechte!");

        }
    }
    
}
