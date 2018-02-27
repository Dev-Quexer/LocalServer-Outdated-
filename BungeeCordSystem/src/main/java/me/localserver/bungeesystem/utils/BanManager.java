package me.localserver.bungeesystem.utils;

import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BanManager {


    public static void runAsync(Runnable run) {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), run);
    }

    public static boolean isBanned(String name) {

        ResultSet rs;



        try {

            rs = BungeeSystem.getMySQL().prepare("SELECT * FROM Ban WHERE UUID='"+UUIDFetcher.getUUID(name).toString().toString()+"'").executeQuery();

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
    public static void banPlayer(String name, String grund, String vonWem, long Stundenn) {

            if (!isBanned(name)) {
                long now = System.currentTimeMillis();
                long end = now + Stundenn * 1000 * 60 * 60 + 2000;

                if (Stundenn == -1 || getBanPoints(name) >= 5) {
                    end = -1;
                }
                String UUID = UUIDFetcher.getUUID(name).toString();

                if (grund.equals("Hacking")) {
                    addBannPoints(name, 3);
                } else if (grund.equals("Trolling")) {
                    addBannPoints(name, 1);
                } else if (grund.equals("Bugusing")) {
                    addBannPoints(name, 2);
                }





                int ID = getNextID();

                addBanToHistory(name, vonWem, null, grund);

                BungeeSystem.getMySQL().update("INSERT INTO Ban(UUID,Grund,von,end,current,ID) VALUES ('" + UUID + "','" + grund + "','" + vonWem + "','" + end + "','" + null + "','" + ID + "')");

            BungeeSystem.getBungeeCord().getScheduler().schedule(BungeeSystem.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (ProxiedPlayer p : BungeeSystem.getInstance().getProxy().getPlayers()) {
                        if (p.hasPermission("ban.notify")) {
                            p.sendMessage(" ");
                            p.sendMessage(BungeeSystem.getPrefix() + "§7Der Spieler §c" + name + " §7wurde gebannt§8!");
                            p.sendMessage(BungeeSystem.getPrefix() + "§7Von wem§8: §a" + vonWem);
                            p.sendMessage(BungeeSystem.getPrefix() + "§7Grund§8: §a" + grund);
                            p.sendMessage(BungeeSystem.getPrefix() + "§7Verbleibende Zeit§8: §a" + getEndAsString(name));
                            p.sendMessage(BungeeSystem.getPrefix() + "§7BanPoints§8: §e" + getBanPoints(name));
                            p.sendMessage(" ");
                        }
                    }
                }
            }, 2, TimeUnit.SECONDS);




                if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null) {
                    BungeeSystem.getInstance().getProxy().getPlayer(UUIDFetcher.getUUID(name)).disconnect(
                            "§7Du wurdest vom §eLocalServer §7Netzwerk §cgebannt§8!\n" +
                            "\n" +
                            "§8§m-----------------------------------------------------------------------------\n" +
                            "\n" +
                            "§7Grund§8: §e"+grund+"\n" +
                            "§7Von§8: §a"+vonWem+"\n" +
                            "§7Verbleibende Zeit§8: "+getEndAsString(name)+"\n" +
                            "§7Deine BanPoints§8: §e"+getBanPoints(name)+"\n" +
                            "\n" +
                            "§8§m-----------------------------------------------------------------------------\n" +
                            "§7Du kannst einen Entbannungsantrag im §aForum §7erstellen §8(§7IP§8: §eLocalServer§7.§enet§8)");

                }
                BungeeSystem.getInstance().getProxy().getPlayer(vonWem).sendMessage(BungeeSystem.getPrefix() + "§7Du hast den Spieler §aerfolgreich §7gebannt§8!");


            } else {
                BungeeSystem.getInstance().getProxy().getPlayer(vonWem).sendMessage(BungeeSystem.getPrefix() + "§cDieser Spieler ist bereits gebannt§8!");
            }



    }

    public static void unBanPlayer(String name, String grund, String vonwem) {
        BungeeSystem.getInstance().getMySQL().update("DELETE FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'");
        for (ProxiedPlayer all : BungeeSystem.getInstance().getProxy().getInstance().getPlayers()) {
            if (all.hasPermission("ban.notify")) {
                all.sendMessage(" ");
                all.sendMessage(BungeeSystem.getPrefix() + "§7Der Spieler §c" + name + " §7wurde von §c" + vonwem + " §7mit dem Grund §e" + grund + " §7entbannt§8!");
                all.sendMessage(" ");
            }
        }
    }

    public static long getEnd(String name) {


        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
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
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();

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
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("Grund");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";
    }

    public static String getVonwem(String name) {


        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("von");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";

    }

    public static String getBannDatum(String name) {


        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
            if (rs.next()) {
                return rs.getString("current");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";

    }

    public static int getIP(String name) {


        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name).toString() + "'").executeQuery();
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

        if (isBanned(name)) {

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
        return "§cNicht Gebannt§8!";

    }
    public static int getNextID() {
        int i = 0;


        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM Ban").executeQuery();

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
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM BanPoints WHERE UUID='"+UUID+"'").executeQuery();
        if(rs.next()) {
            return rs.getInt("Points");
            } else {
            runAsync(new Runnable() {
                @Override
                public void run() {
                    BungeeSystem.getInstance().getMySQL().getMySQL().queryUpdate("INSERT INTO BanPoints(UUID,Points) VALUES ('"+UUID+"','"+0+"')");
                }
            });
            return 0;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void addBannPoints(String name, int points) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();

        int finalPoints = getBanPoints(name) + points;
        runAsync(new Runnable() {
            @Override
            public void run() {
                BungeeSystem.getInstance().getMySQL().getMySQL().queryUpdate("UPDATE BanPoints SET Points='"+ finalPoints +"' WHERE UUID='"+UUID+"'");
            }
        });
    }

    public static void addBanToHistory(String name, String vonWem, String current, String Grund) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();


        runAsync(new Runnable() {
            @Override
            public void run() {
                BungeeSystem.getInstance().getMySQL().getMySQL().queryUpdate("INSERT INTO BanHistory(UUID,vonWem,current,Grund) VALUES ('"+UUID+"','"+vonWem+"','"+current+"','"+Grund+"')");
            }
        });
    }

    public static List<String> getBanHistory(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString().toString();
        List<String> history = new ArrayList<>();

        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM BanHistory WHERE UUID='"+UUID+"'").executeQuery();

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
