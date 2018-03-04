package me.quexer.lobbysystem.utils;


import me.quexer.serverapi.coins.CoinsAPI;
import me.quexer.serverapi.rank.Tablist;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {

    private static Scoreboard sb = Tablist.sb;

    public static void setScoreboard(Player p) {
        Objective obj = sb.getObjective("aaa");
        if(obj == null) {
            obj = sb.registerNewObjective("aaa", "bbb");
        }

        obj.setDisplayName("§lLOCALSERVER.NET");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore(" ").setScore(10);
        obj.getScore("§f§lRang§8§l:").setScore(9);
        obj.getScore(updateTeam("Rang",  "§8§l➥ ", p.getDisplayName().replace(p.getName(), "").replace(" §8●", ""), ChatColor.GREEN)).setScore(8);
        obj.getScore("  ").setScore(7);
        obj.getScore("§f§lCoins§8§l:").setScore(6);
        obj.getScore(updateTeam("Coins", "§8§l➥ ", CoinsAPI.getCoins(p.getName())+"", ChatColor.YELLOW)).setScore(5);
        obj.getScore("   ").setScore(4);
        obj.getScore("§f§lTeamSpeak§8§l:").setScore(3);
        obj.getScore(updateTeam("TeamSpeak", "§8§l➥ §eLocal", "§eServer§7.§enet", ChatColor.AQUA)).setScore(2);
        obj.getScore("    ").setScore(1);
        p.setScoreboard(sb);
    }
    public static void updateScoreboard(Player p) {
        Scoreboard sb = p.getScoreboard();
        if(sb == null) {
            setScoreboard(p);
        }
        Objective obj = sb.getObjective("aaa");
        if(obj == null) {
            obj = sb.registerNewObjective("aaa", "bbb");
        }

        obj.setDisplayName("§lLOCALSERVER.NET");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore(" ").setScore(10);
        obj.getScore("§f§lRang§8§l:").setScore(9);
        obj.getScore(updateTeam("Rang",  "§8§l➥ ", p.getDisplayName().replace(p.getName(), "").replace(" §8●", ""), ChatColor.GREEN)).setScore(8);
        obj.getScore("  ").setScore(7);
        obj.getScore("§f§lCoins§8§l:").setScore(6);
        obj.getScore(updateTeam("Coins", "§8§l➥ ", CoinsAPI.getCoins(p.getName())+"", ChatColor.YELLOW)).setScore(5);
        obj.getScore("   ").setScore(4);
        obj.getScore("§f§lTeamSpeak§8§l:").setScore(3);
        obj.getScore(updateTeam("TeamSpeak", "§8§l➥ §eLocal", "§eServer§7.§enet", ChatColor.AQUA)).setScore(2);
        obj.getScore("    ").setScore(1);

    }


    private static String updateTeam(String Team, String prefix, String suffix, ChatColor entry) {
        Team team = sb.getTeam(Team);
        if(team == null) {
            team = sb.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());
        return entry.toString();
    }


}
