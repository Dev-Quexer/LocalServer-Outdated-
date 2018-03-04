package me.quexer.serverapi.coins;

import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.utils.UUIDFetcher;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsAPI {

    public CoinsAPI() {
        ServerAPI.getMySQL().update("CREATE TABLE IF NOT EXISTS CoinsAPI(UUID VARCHAR(100), COINS VARCHAR(200))");
    }

    public static boolean playerExists(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();

        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM CoinsAPI WHERE UUID='"+UUID+"'").executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void createPlayer(String name, long Coins) {
        String UUID = UUIDFetcher.getUUID(name).toString();

        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                ServerAPI.getMySQL().update("INSERT INTO CoinsAPI(UUID,COINS) VALUES ('"+UUID+"','"+Coins+"')");
            }
        });
    }
    public static long getCoins(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        if(playerExists(name)) {
            try {
                ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM CoinsAPI WHERE UUID='" + UUID + "'").executeQuery();
                if(rs.next()) {
                    return rs.getLong("COINS");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(name, 1000);
        }
        return 1000;
    }
    public static void setCoins(String name, long Coins) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        if (playerExists(name)) {
            Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
                @Override
                public void run() {
                    ServerAPI.getMySQL().update("UPDATE CoinsAPI SET COINS='"+Coins+"' WHERE UUID='"+UUID+"'");
                }
            });
        } else {
            createPlayer(name, Coins);
        }
    }
    public static void addCoins(String name, long Coins) {
        long newCoins = getCoins(name) + Coins;
        setCoins(name, newCoins);
    }
    public static void removeCoins(String name, long Coins) {
        long newCoins = getCoins(name) - Coins;
        setCoins(name, newCoins);
    }

}
