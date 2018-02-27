package me.localserver.bungeesystem.commands;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReasonsCMD extends Command {

    public ReasonsCMD() {
        super("reasons");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.reasons")) {
            s.sendMessage("§8§m--------------------------");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§7>> §eBan §7<<");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cHacking §8- §c#1");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cTrolling §8- §c#2");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cTeaming §8- §c#3");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cBugusing §8- §c#4");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cSkin §8- §c#5");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cName §8- §c#6");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cReport-Ausnutzung §8- §c#7");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cBannumgehung §8- §c#8");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cHausverbot §8- §c#9");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§7>> §eMute §7<<");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cBeleidigung §8- §c#10");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cRassismus §8- §c#11");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cWerbung §8- §c#12");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cSpamming §8- §c#13");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cProvokation §8- §c#14");
            s.sendMessage(BungeeSystem.getInstance().getPrefix()+"§cSchweigepflicht §8-§c#15");
            s.sendMessage("§8§m--------------------------");

        }


    }

}
