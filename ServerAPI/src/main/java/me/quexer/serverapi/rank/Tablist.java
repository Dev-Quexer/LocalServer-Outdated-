package me.quexer.serverapi.rank;

import me.quexer.serverapi.nick.NickAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Tablist {

    public static Scoreboard sb;
    public static HashMap<UUID, String> t = new HashMap();
    public static boolean setTablist = true;
    public static boolean setChatPrefix = true;


    public static void load() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        sb.registerNewTeam("00001Admin");
        sb.registerNewTeam("00002SrDev");
        sb.registerNewTeam("00003SrMod");
        sb.registerNewTeam("00004SrBuilder");
        sb.registerNewTeam("00005Dev");
        sb.registerNewTeam("00006Mod");
        sb.registerNewTeam("00007Builder");
        sb.registerNewTeam("00008Content");
        sb.registerNewTeam("00009Supporter");
        sb.registerNewTeam("00010YouTuber");
        sb.registerNewTeam("00011PremiumPlus");
        sb.registerNewTeam("00012Premium");
        sb.registerNewTeam("00013Spieler");
        sb.getTeam("00001Admin");
        sb.getTeam("00002SrDev").setPrefix(net.md_5.bungee.api.ChatColor.AQUA + "SrDev " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.AQUA);
        sb.getTeam("00003SrMod");
        sb.getTeam("00004SrBuilder");
        sb.getTeam("00005Dev");
        sb.getTeam("00006Mod");
        sb.getTeam("00007Builder");
        sb.getTeam("00008Content");
        sb.getTeam("00009Supporter");
        sb.getTeam("00010YouTuber");
        sb.getTeam("00011PremiumPlus");
        sb.getTeam("00012Premium");
        sb.getTeam("00013Spieler");
    }

    public static void setPrefix(Player p) {

            String team = "";

            if (NickAPI.hasNick(p)) {
                    sb.getTeam("00012Premium").setPrefix("" + net.md_5.bungee.api.ChatColor.GOLD);
                    if (t.containsKey(p.getUniqueId())) {
                        sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                        t.remove(p.getUniqueId());
                    }

                    team = "00012Premium";
                    sb.getTeam(team).addPlayer(p);
                    t.put(p.getUniqueId(), team);
                    refresh(p);
            } else if (p.hasPermission("admin")) {
                sb.getTeam("00001Admin").setPrefix(net.md_5.bungee.api.ChatColor.DARK_RED + "Admin " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.DARK_RED);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00001Admin";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("srdev")) {
                sb.getTeam("00002SrDev").setPrefix(net.md_5.bungee.api.ChatColor.AQUA + "SrDev " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.AQUA);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00002SrDev";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("srmod")) {
                sb.getTeam("00003SrMod").setPrefix(net.md_5.bungee.api.ChatColor.RED + "SrMod " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.RED);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00003SrMod";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("srbuilder")) {
                sb.getTeam("00004SrBuilder").setPrefix(net.md_5.bungee.api.ChatColor.DARK_GREEN + "SrB " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.DARK_GREEN);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00004SrBuilder";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("dev")) {
                sb.getTeam("00005Dev").setPrefix(net.md_5.bungee.api.ChatColor.AQUA + "Dev " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.AQUA);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00005Dev";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("mod")) {
                sb.getTeam("00006Mod").setPrefix(net.md_5.bungee.api.ChatColor.RED + "Mod " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.RED);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00006Mod";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("builder")) {
                sb.getTeam("00007Builder").setPrefix(net.md_5.bungee.api.ChatColor.DARK_GREEN + "Builder " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.DARK_GREEN);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00007Builder";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("content")) {
                sb.getTeam("00008Content").setPrefix(net.md_5.bungee.api.ChatColor.AQUA + "Content " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.AQUA);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00008Content";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("supporter")) {
                sb.getTeam("00009Supporter").setPrefix(net.md_5.bungee.api.ChatColor.BLUE + "Sup " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.BLUE);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00009Supporter";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("youtuber")) {
                sb.getTeam("00010YouTuber").setPrefix(net.md_5.bungee.api.ChatColor.DARK_PURPLE + "YT " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.DARK_PURPLE);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00010YouTuber";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("premiumplus")) {
                sb.getTeam("00011PremiumPlus").setPrefix(net.md_5.bungee.api.ChatColor.YELLOW + "P+ " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.YELLOW);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00011PremiumPlus";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else if (p.hasPermission("premium")) {
                sb.getTeam("00012Premium").setPrefix(net.md_5.bungee.api.ChatColor.GOLD + "Premium " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.GOLD);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00012Premium";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);
            } else {
                sb.getTeam("00013Spieler").setPrefix(net.md_5.bungee.api.ChatColor.GREEN + "Spieler " + net.md_5.bungee.api.ChatColor.DARK_GRAY + "● " + net.md_5.bungee.api.ChatColor.GRAY);
                if (t.containsKey(p.getUniqueId())) {
                    sb.getTeam((String)t.get(p.getUniqueId())).removePlayer(p);
                    t.remove(p.getUniqueId());
                }

                team = "00013Spieler";
                sb.getTeam(team).addPlayer(p);
                t.put(p.getUniqueId(), team);
                refresh(p);

        }
    }

    public static void refresh(Player p) {

        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player all = (Player)var3.next();
            all.setScoreboard(sb);
        }

        if (NickAPI.hasNick(p)) {

                p.setPlayerListName(Rank.PREMIUM.getPrefix() + p.getName());
                p.setDisplayName(Rank.PREMIUM.getPrefix() + p.getName());

        } else if (p.hasPermission("admin")) {
            p.setPlayerListName(Rank.ADMIN.getPrefix() + p.getName());
            p.setDisplayName(Rank.ADMIN.getPrefix() + p.getName());
        } else if (p.hasPermission("srdev")) {
            p.setPlayerListName(Rank.SRDEV.getPrefix() + p.getName());
            p.setDisplayName(Rank.SRDEV.getPrefix() + p.getName());
        } else if (p.hasPermission("srmod")) {
            p.setPlayerListName(Rank.SRMOD.getPrefix() + p.getName());
            p.setDisplayName(Rank.SRMOD.getPrefix() + p.getName());
        } else if (p.hasPermission("srbuilder")) {
            p.setPlayerListName(Rank.SRBUILDER.getPrefix() + p.getName());
            p.setDisplayName(Rank.SRBUILDER.getPrefix() + p.getName());
        } else if (p.hasPermission("dev")) {
            p.setPlayerListName(Rank.DEV.getPrefix() + p.getName());
            p.setDisplayName(Rank.DEV.getPrefix() + p.getName());
        } else if (p.hasPermission("mod")) {
            p.setPlayerListName(Rank.MOD.getPrefix() + p.getName());
            p.setDisplayName(Rank.MOD.getPrefix() + p.getName());
        } else if (p.hasPermission("builder")) {
            p.setPlayerListName(Rank.BUILDER.getPrefix() + p.getName());
            p.setDisplayName(Rank.BUILDER.getPrefix() + p.getName());
        } else if (p.hasPermission("content")) {
            p.setPlayerListName(Rank.CONTENT.getPrefix() + p.getName());
            p.setDisplayName(Rank.CONTENT.getPrefix() + p.getName());
        } else if (p.hasPermission("supporter")) {
            p.setPlayerListName(Rank.SUPPORTER.getPrefix() + p.getName());
            p.setDisplayName(Rank.SUPPORTER.getPrefix() + p.getName());
        } else if (p.hasPermission("youtuber")) {
            p.setPlayerListName(Rank.YOUTUBER.getPrefix() + p.getName());
            p.setDisplayName(Rank.YOUTUBER.getPrefix() + p.getName());
        } else if (p.hasPermission("premiumplus")) {
            p.setPlayerListName(Rank.PREMIUMPLUS.getPrefix() + p.getName());
            p.setDisplayName(Rank.PREMIUMPLUS.getPrefix() + p.getName());
        } else if (p.hasPermission("premium")) {
            p.setPlayerListName(Rank.PREMIUM.getPrefix() + p.getName());
            p.setDisplayName(Rank.PREMIUM.getPrefix() + p.getName());
        } else {
            p.setPlayerListName(Rank.SPIELER.getPrefix() + p.getName());
            p.setDisplayName(Rank.SPIELER.getPrefix() + p.getName());
        }

    }

    public static boolean isSetTablist() {
        return setTablist;
    }

    public static void setSetTablist(boolean setTablist) {
        Tablist.setTablist = setTablist;
    }

    public static boolean isSetChatPrefix() {
        return setChatPrefix;
    }

    public static void setSetChatPrefix(boolean setChatPrefix) {
        Tablist.setChatPrefix = setChatPrefix;
    }
}
