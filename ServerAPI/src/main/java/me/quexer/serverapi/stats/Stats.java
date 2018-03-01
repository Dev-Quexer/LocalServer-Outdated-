package me.quexer.serverapi.stats;

import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {

    private Game game;
    private String name;
    private String mySQLTable;


    public Stats(Game game, String name, String mySQLTable) {
        this.game = game;
        this.name = name;
        this.mySQLTable = mySQLTable;
    }

    public void createTable() {
        ServerAPI.getMySQL().update("CREATE TABLE IF NOT EXISTS "+getMySQLTable()+"(UUID VARCHAR(100), KILLS VARCHAR(100), DEATHS VARCHAR(100), WIN VARCHAR(100), POINTS VARCHAR(100))");
    }

    public boolean playerExists(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();

        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM "+mySQLTable+" WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createPlayer(String name) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                String UUID = UUIDFetcher.getUUID(name).toString();

               ServerAPI.getMySQL().update("INSERT INTO "+mySQLTable+"(UUID,KILLS,DEATHS,WIN,POINTS) VALUES ('"+UUID+"','0','0','0','0')");

            }
        });
    }
    public long getStat(String name, Stat stat) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM "+mySQLTable+" WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return rs.getLong(stat.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void setStat(String name, Stat stat, long amount) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                String UUID = UUIDFetcher.getUUID(name).toString();

                if(playerExists(name)) {
                    ServerAPI.getMySQL().update("UPDATE "+mySQLTable+" SET "+stat.toString()+"='"+amount+"' WHERE UUID='"+UUID+"'");
                } else {
                    createPlayer(name);
                }

            }
        });
    }
    public void addStat(String name, Stat stat, long amount) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                setStat(name, stat, getStat(name, stat)+amount);
            }
        });
    }



    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public String getMySQLTable() {
        return mySQLTable;
    }
}
