package me.quexer.serverapi.coins;

import me.quexer.serverapi.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;

        //keys set fhsdhiofsd <amount>
        //keys get hiwadasdHIO

        if(args.length == 0) {
            p.sendMessage("§8[§fCoins§8] §7Du hast §5"+ CoinsAPI.getCoins(p.getName())+" §7Coins");
        } else if(args.length == 2) {
            if(p.hasPermission("Coins")) {
                if(args[0].equalsIgnoreCase("get")) {
                    String name = args[1];
                    if(CoinsAPI.playerExists(name)) {
                        p.sendMessage("§7Der Spieler §e"+name+" §7hat §5"+CoinsAPI.getCoins(name)+" §7Coins");
                    } else {
                        p.sendMessage("§8[§fCoins§8] §cDieser Spieler war noch nie auf diesem Netzwerk");
                    }
                } else {
                    p.sendMessage("§8[§fCoins§8] §7Benutze§8: §7/Coins get <Spieler>");
                }
            } else {
                p.sendMessage("§8[§fCoins§8] §cDazu hast du keine Rechte!");
            }
        } else if(args.length == 3) {
            if (p.hasPermission("Coins")) {
                try {


                    if (args[0].equalsIgnoreCase("set")) {
                        String name = args[1];
                        long Coins = CoinsAPI.getCoins(name);
                        if (CoinsAPI.playerExists(name)) {
                            CoinsAPI.setCoins(name, Long.valueOf(args[2]));
                            p.sendMessage("§8[§fCoins§8] §7Du hast dem Spieler §e" + name + " §5" + Long.valueOf(args[2]) + " §7Coins gesetzt§8!");
                        } else {
                            p.sendMessage("§8[§fCoins§8] §cDieser Spieler war noch nie auf diesem Netzwerk");
                        }
                    } else if (args[0].equalsIgnoreCase("add")) {
                        String name = args[1];
                        long Coins = CoinsAPI.getCoins(name);
                        if (CoinsAPI.playerExists(name)) {
                            long Coinstoadd = Coins + Long.valueOf(args[2]);
                            CoinsAPI.addCoins(name, Coinstoadd);
                            p.sendMessage("§8[§fCoins§8] §7Du hast dem Spieler §e" + name + " §5" + Coinstoadd + " §7Coins hinzugefügt§8!");
                        } else {
                            p.sendMessage("§8[§fCoins§8] §cDieser Spieler war noch nie auf diesem Netzwerk");
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        String name = args[1];
                        long Coins = CoinsAPI.getCoins(name);
                        if (CoinsAPI.playerExists(name)) {
                            long Coinstoremove = Coins - Long.valueOf(args[2]);
                            CoinsAPI.removeCoins(name, Coinstoremove);
                            p.sendMessage("§8[§fCoins§8] §7Du hast dem Spieler §e" + name + " §5" + Coinstoremove + " §7Coins entfernt§8!");
                        } else {
                            p.sendMessage("§8[§fCoins§8] §cDieser Spieler war noch nie auf diesem Netzwerk");
                        }
                    } else {
                        p.sendMessage("§8[§fCoins§8] §7Benutze§8: §7/Coins <add|set|remove> <Spieler> <Anzahl>");
                    }
                } catch (NumberFormatException ex) {
                    p.sendMessage("§8[§fCoins§8] §cDu musst eine Zahl angeben!");
                    return true;
                }
            } else {
                p.sendMessage("§8[§fCoins§8] §cDazu hast du keine Rechte!");
            }
        } else {
            p.sendMessage("§8[§fCoins§8] §7Du hast §5"+ CoinsAPI.getCoins(p.getName())+" §7Coins");
        }
        return false;
    }
    
}
