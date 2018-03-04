package me.quexer.serverapi.stats;

import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Stats {

    private Game game;
    private String name;
    private String mySQLTable;
    private HashMap<Integer, String> top;


    public Stats(Game game, String name, String mySQLTable) {
        this.game = game;
        this.name = name;
        this.mySQLTable = mySQLTable;
        this.top = new HashMap<>();
    }

    public void createTable() {
        ServerAPI.getMySQL().update("CREATE TABLE IF NOT EXISTS "+getMySQLTable()+"(UUID VARCHAR(100), KILLS VARCHAR(100), DEATHS VARCHAR(100), WIN VARCHAR(100), POINTS INT)");
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
    public void loadTop() {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), new Runnable() {
            int i = 1;
            @Override
            public void run() {
                try {
                    ResultSet rs = ServerAPI.getMySQL().prepare("SELECT UUID FROM "+mySQLTable+" ORDER BY POINTS DESC LIMIT 5").executeQuery();

                    while(rs.next()) {
                        String name = UUIDFetcher.getName(UUID.fromString(rs.getString("UUID")));
                        top.put(i, name);
                        i++;
                    }
                    for(int i = 1; i < 6; i++) {
                        String name = top.get(i);
                        Skull s = (Skull) ServerAPI.getLocationAPI().getLocation("Stats."+mySQLTable+"."+i).getBlock().getState();
                        s.setOwner(name);
                        s.update();

                        Sign sign = (Sign) ServerAPI.getLocationAPI().getLocation("Stats."+mySQLTable+"."+i).subtract(0,1,0).getBlock().getState();
                        sign.setLine(0, name);
                        sign.setLine(1, "§7Platz§8: §c#"+i);
                        sign.setLine(2,"§7Points§8: §c"+getStat(name, Stat.POINTS));
                        sign.setLine(3, "§7Wins§8: §c"+getStat(name, Stat.WIN));
                        sign.update();
                        System.out.println(i);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }


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
