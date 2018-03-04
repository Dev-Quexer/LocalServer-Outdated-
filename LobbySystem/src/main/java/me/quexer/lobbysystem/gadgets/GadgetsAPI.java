package me.quexer.lobbysystem.gadgets;

import me.quexer.lobbysystem.Lobby;
import me.quexer.lobbysystem.gadgets.utils.Gadget;
import me.quexer.lobbysystem.gadgets.utils.Seltenheit;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GadgetsAPI {

    private HashMap<Seltenheit, List<Gadget>> values = new HashMap<>();
    private HashMap<Player, Gadget> playerGadget = new HashMap<>();
    private HashMap<Player, List<Gadget>> playerGadgets = new HashMap<>();

    private List<Gadget> legendary;
    private List<Gadget> epic;
    private List<Gadget> rare;
    private List<Gadget> common;

    public GadgetsAPI() {
        List<Gadget> LEGENDARY = new ArrayList<>();
        List<Gadget> EPIC = new ArrayList<>();
        List<Gadget> RARE = new ArrayList<>();
        List<Gadget> COMMON = new ArrayList<>();

        for (Gadget gadget : Gadget.values()) {
            if(gadget.getSeltenheit() == Seltenheit.LEGENDARY) {
                LEGENDARY.add(gadget);
            } else if(gadget.getSeltenheit() == Seltenheit.EPIC) {
                EPIC.add(gadget);
            } else if(gadget.getSeltenheit() == Seltenheit.RARE) {
                RARE.add(gadget);
            } else {
                COMMON.add(gadget);
            }
        }

        values.put(Seltenheit.LEGENDARY, LEGENDARY);
        values.put(Seltenheit.EPIC, EPIC);
        values.put(Seltenheit.RARE, RARE);
        values.put(Seltenheit.COMMON, COMMON);

        legendary = values.get(Seltenheit.LEGENDARY);
        epic = values.get(Seltenheit.EPIC);
        rare = values.get(Seltenheit.RARE);
        common = values.get(Seltenheit.COMMON);


    }

    public Gadget getNextGadget() {

        Seltenheit seltenheit;
        Random random = new Random();
        int slt = random.nextInt(1000);
        if(slt <= 1000 && slt > 990) {
            return legendary.get(random.nextInt(legendary.size()));
        } else if(slt <= 989 && slt > 900) {
            return epic.get(random.nextInt(epic.size()));
        } else if(slt <= 899 && slt > 700) {
            return rare.get(random.nextInt(rare.size()));
        } else {
            return common.get(random.nextInt(common.size()));
        }

    }

    public boolean playerExists(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM GadgetsAPI WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean hasGadget(String name, Gadget gadget) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM GadgetsAPI WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return rs.getInt(gadget.toString()) == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(String name) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                String UUID = UUIDFetcher.getUUID(name).toString();

                ServerAPI.getMySQL().update("INSERT INTO GadgetsAPI(UUID, ACTIVE, HERZEN, FLAMES, NOTES, SMOKE, CLOUD, LAVA, THUNDER, RAINBOW, ENDER, MAGIC, CIRT, BOWFIRE) VALUES ('"+UUID+"','NIX','0','0','0','0','0','0','0','0','0','0','0','0')");
            }
        });
    }

    public Gadget getActiveGadgetMySQL(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM GadgetsAPI WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {

                   return Gadget.valueOf(rs.getString("ACTIVE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setActiveGadgetMySQL(String name, Gadget gadget) {

        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                String UUID = UUIDFetcher.getUUID(name).toString();

                ServerAPI.getMySQL().update("UPDATE GadgetsAPI SET ACTIVE='"+gadget.toString()+"' WHERE UUID='"+UUID+"'");
            }
        });
    }
    public void addGadgetMySQL(String name, Gadget gadget) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                String UUID = UUIDFetcher.getUUID(name).toString();

                ServerAPI.getMySQL().update("UPDATE GadgetsAPI SET "+gadget.toString()+"='1' WHERE UUID='"+UUID+"'");
            }
        });
    }

    public void setActiveGadget(Player p, Gadget gadget) {
        playerGadget.put(p, gadget);
    }
    public Gadget getActiveGadget(Player p) {
        return playerGadget.get(p);
    }
    public void clearGadget(Player p) {
        playerGadget.remove(p);
    }
    public void loadGadgets(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                 List<Gadget> gadgets = playerGadgets.get(p);
                 for (Gadget gadget : Gadget.values()) {
                     if(!gadget.toString().contains("COINS")) {
                         if (hasGadget(p.getName(), gadget)) {
                             gadgets.add(gadget);
                         }
                     }
                }
                     playerGadgets.put(p, gadgets);
            }
        });
    }

    public void startScheduler() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Lobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(playerGadget.containsKey(p)) {
                        p.getWorld().playEffect(p.getLocation(), playerGadget.get(p).getEffect(), 1, 20);
                    }
                });
                Lobby.getChestrollLocation().getBlock().getLocation().getWorld().playEffect(Lobby.getChestrollLocation(), Effect.FLAME, 1, 30);
            }
        }, 20, 3);
    }

    public HashMap<Seltenheit, List<Gadget>> getValues() {
        return values;
    }

    public HashMap<Player, Gadget> getPlayerGadget() {
        return playerGadget;
    }

    public HashMap<Player, List<Gadget>> getPlayerGadgets() {
        return playerGadgets;
    }

    public List<Gadget> getLegendary() {
        return legendary;
    }

    public List<Gadget> getEpic() {
        return epic;
    }

    public List<Gadget> getRare() {
        return rare;
    }

    public List<Gadget> getCommon() {
        return common;
    }
}
