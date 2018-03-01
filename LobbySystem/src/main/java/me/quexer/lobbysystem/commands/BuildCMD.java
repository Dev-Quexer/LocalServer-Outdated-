package me.quexer.lobbysystem.commands;

import me.quexer.lobbysystem.Lobby;
import me.quexer.serverapi.ServerAPI;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(sender.hasPermission("lobby.build")) {
          if (Lobby.getBuild().contains(p)) {
              Lobby.getBuild().remove(p);
              Lobby.getInventoryManager().setStandartInv(p);
              p.sendMessage(Lobby.getPrefix()+"§7Du hast den §eBaumodus §cverlassen§8!");
              p.setLevel(2018);
              p.setExp(0);
              ServerAPI.getSoundManager().playBad(p);
              p.setGameMode(GameMode.ADVENTURE);
          } else {
              Lobby.getBuild().add(p);
              p.getInventory().clear();
              p.getInventory().setArmorContents(null);
              p.sendMessage(Lobby.getPrefix()+"§7Du hast den §eBaumodus §abetreten§8!");
              ServerAPI.getSoundManager().playGood(p);
              p.setGameMode(GameMode.CREATIVE);
          }
        } else {
            p.sendMessage(Lobby.getPrefix()+"§cDazu hast du keine Rechte§8!");
            ServerAPI.getSoundManager().playBad(p);
        }

        return true;
    }

}
