package me.quexer.serverapi.rank.commands;

import me.quexer.serverapi.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTopCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;

        if(p.hasPermission("top")) {
            //setTop Modus Nummer
            if(args.length == 2) {
                ServerAPI.getLocationAPI().setLocation("Stats."+args[0]+"."+args[1], p);
                p.sendMessage("§8[§fStats§8] §7Du hast die §eLocation §7gesetzt§8!");
                ServerAPI.getLocationAPI().save();
            }
        }


        return true;
    }
}
