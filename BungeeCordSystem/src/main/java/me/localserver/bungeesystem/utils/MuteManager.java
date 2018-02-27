package me.localserver.bungeesystem.utils;


import me.localserver.bungeesystem.BungeeSystem;
import me.localserver.bungeesystem.database.AsyncMySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MuteManager {

    public static AsyncMySQL MySQL = BungeeSystem.getInstance().getMySQL();
    public static ProxyServer BungeeCord = BungeeSystem.getBungeeCord();

    public static void runAsync(Runnable run) {
        BungeeCord.getScheduler().runAsync(BungeeSystem.getInstance(), run);
    }

    public static boolean isMutened(String name) {

        ResultSet rs;



        try {

            rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='"+UUIDFetcher.getUUID(name).toString().toString()+"'").executeQuery();

            if (rs.next()) {

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;

    }


    @SuppressWarnings("deprecation")
    public static void MutePlayer(String name, String grund, String vonWem, long Stundenn) {

        if (!isMutened(name)) {
            long now = System.currentTimeMillis();
            long end = now + Stundenn * 1000 * 60 * 60 + 2000;

            if (Stundenn == -1 || getBanPoints(name) >= 5) {
                end = -1;
            }
            String UUID = UUIDFetcher.getUUID(name).toString();

            if (grund.equals("Hacking")) {
                addMutenPoints(name, 3);
            } else if (grund.equals("Trolling")) {
                addMutenPoints(name, 1);
            } else if (grund.equals("Bugusing")) {
                addMutenPoints(name, 2);
            }





            int ID = getNextID();

            addMuteToHistory(name, vonWem, null, grund);

            MySQL.getMySQL().queryUpdate("INSERT INTO Mute(UUID,Grund,von,end,current,ID) VALUES ('" + UUID + "','" + grund + "','" + vonWem + "','" + end + "','" + null + "','" + ID + "')");


            for (ProxiedPlayer p : BungeeCord.getPlayers()) {
                if (p.hasPermission("Mute.notify")) {
                    p.sendMessage(" ");
                    p.sendMessage(BungeeSystem.getPrefix() + "§7Der Spieler §c" + name + " §7wurde gemutet§8!");
                    p.sendMessage(BungeeSystem.getPrefix() + "§7Von wem§8: §a" + vonWem);
                    p.sendMessage(BungeeSystem.getPrefix() + "§7Grund§8: §a" + grund);
                    p.sendMessage(BungeeSystem.getPrefix() + "§7Verbleibende Zeit§8: §a" + getEndAsString(name));
                    p.sendMessage(BungeeSystem.getPrefix() + "§7BanPoints§8: §e" + getBanPoints(name));
                    p.sendMessage(" ");
                }
            }



            if(BungeeCord.getPlayer(name) != null) {
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+" ");
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§7Du wurdest gemutet§8!");
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§7Grund§8: §e"+grund);
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§7Von§8: §e"+vonWem);
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§7MuteID§8: §e"+ID);
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§7BanPoints§8: §e"+getBanPoints(name));
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
                BungeeCord.getPlayer(UUIDFetcher.getUUID(name)).sendMessage(BungeeSystem.getPrefix()+" ");


            }
            BungeeCord.getPlayer(vonWem).sendMessage(BungeeSystem.getPrefix() + "§7Du hast den Spieler §aerfolgreich §7gemutet§8!");


        } else {
            BungeeCord.getPlayer(vonWem).sendMessage(BungeeSystem.getPrefix() + "§cDieser Spieler ist bereits gemutet§8!");
        }



    }

    public static void unMutePlayer(String name, String grund, String vonwem) {
        MySQL.getMySQL().queryUpdate("DELETE FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'");
        for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            if (all.hasPermission("Mute.notify")) {
                all.sendMessage(" ");
                all.sendMessage(BungeeSystem.getPrefix() + "§7Der Spieler §c" + name + " §7wurde von §c" + vonwem + " §7mit dem Grund §e" + grund + " §7entmutet§8!");
                all.sendMessage(" ");
            }
        }
    }

    public static long getEnd(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                if(rs.getLong("end") == -1) {

                    return -1;

                } else {
                    return rs.getLong("end");
                }
            } else {

                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getID(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getGrund(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("Grund");
            } else {
                return "§cNicht Gemutett";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutett";
    }

    public static String getVonwem(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("von");
            } else {
                return "§cNicht Gemutett";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutett";

    }

    public static String getMutenDatum(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("current");
            } else {
                return "§cNicht Gemutett";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutett";

    }

    public static int getIP(String name) {


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getInt("IP");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getEndAsString(String name) {

        if (isMutened(name)) {

            long end;
            if (getEnd(name) == -1) {
                end = -1;
            } else {
                end = getEnd(name) - System.currentTimeMillis();
            }
            int sekunden = 0;
            int minuten = 0;
            int Stunden = 0;
            int Tage = 0;


            if (end == -1) {
                return "§4§lPermanent";
            } else {
                while (end > 1000) {
                    end -= 1000;
                    sekunden++;
                }
                while (sekunden > 60) {
                    sekunden -= 60;
                    minuten++;
                }
                while (minuten > 60) {
                    minuten -= 60;
                    Stunden++;
                }
                while (Stunden > 24) {
                    Stunden -= 24;
                    Tage++;
                }
            }

            return "§c" + Tage + " §7Tage§8(§7n§8)§7, §c" + Stunden + " §7Stunde§8(§7n§8)§7, §c" + minuten + " §7Minute§8(§7n§8)§7.";


        }
        return "§cNicht Gemutett§8!";

    }
    public static int getNextID() {
        int i = 0;


        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM Mute").executeQuery();

            while (rs.next()) {
                i++;
            }

            return i +1;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getBanPoints(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();

        int activePoints = 0;

        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM BanPoints WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return rs.getInt("Points");
            } else {
                runAsync(new Runnable() {
                    @Override
                    public void run() {
                        MySQL.getMySQL().queryUpdate("INSERT INTO BanPoints(UUID,Points) VALUES ('"+UUID+"','"+0+"')");
                    }
                });
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void addMutenPoints(String name, int points) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();

        int finalPoints = getBanPoints(name) + points;
        runAsync(new Runnable() {
            @Override
            public void run() {
                MySQL.getMySQL().queryUpdate("UPDATE BanPoints SET Points='"+ finalPoints +"' WHERE UUID='"+UUID+"'");
            }
        });
    }

    public static void addMuteToHistory(String name, String vonWem, String current, String Grund) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();


        runAsync(new Runnable() {
            @Override
            public void run() {
                MySQL.getMySQL().queryUpdate("INSERT INTO BanHistory(UUID,vonWem,current,Grund) VALUES ('"+UUID+"','"+vonWem+"','"+current+"','"+Grund+"')");
            }
        });
    }

    public static List<String> getMuteHistory(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();
        List<String> history = new ArrayList<>();

        try {
            ResultSet rs = MySQL.prepare("SELECT * FROM BanHistory WHERE UUID='"+UUID+"'").executeQuery();

            while(rs.next()) {
                history.add("§7Grund§8: §c"+rs.getString("Grund")+"§8, §7Von§8: §c"+rs.getString("vonWem"));
            }
            if(history.size() == 0) {
                history.add("§aKeine Bans vorhanden§8!");
            }
            return history;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


}
