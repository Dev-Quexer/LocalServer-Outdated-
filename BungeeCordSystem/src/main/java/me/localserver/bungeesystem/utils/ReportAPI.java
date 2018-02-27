package me.localserver.bungeesystem.utils;


import me.localserver.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportAPI {

  /*
    Reporten -> ReportGUI -> Bearbeiten(StateSetzen) -> Reports Finish

    UUID,Reason,vonWem,Count
  */

  public static boolean isReportet(String name) {
      String UUID = UUIDFetcher.getUUID(name).toString();

      try {
         ResultSet rs =  BungeeSystem.getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();
         return rs.next();
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return false;

  }
    public static boolean isInProgress(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();

        try {
            ResultSet rs =  BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();
            return rs.getString("InProgress") == "Ja";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }


  public static void reportPlayer(String name, String vonWem, String Grund) {

      BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), new Runnable() {
          @Override
          public void run() {
              String UUID = UUIDFetcher.getUUID(name).toString();
                    boolean warning = false;




                    String newGründeString = null;
                    String newVonWemString = null;

                    if(isReportet(name)) {

                        String gründe = getGründeString(name);
                        String von = getVonWemString(name);
                        if(!gründe.contains(Grund.toUpperCase())) {
                            newGründeString = gründe + Grund.toUpperCase() + ";";
                        }
                        if(!von.contains(Grund.toUpperCase())) {
                            newVonWemString = von + vonWem.toUpperCase() + ";";
                        }
                        BungeeSystem.getInstance().getMySQL().update("UPDATE REPORT SET Reason='"+newGründeString+"' WHERE UUID='"+UUID+"' AND UPDATE REPORT SET vonWem='"+newVonWemString+"' WHERE UUID='"+UUID+"' AND UPDATE REPORT SET Count='"+0+"' WHERE UUID='"+UUID+"'");
                    } else {

                        newGründeString = Grund.toUpperCase()+";";
                        newVonWemString = vonWem.toUpperCase()+";";

                        BungeeSystem.getInstance().getMySQL().update("INSERT INTO REPORT(UUID,Reason,vonWem,Count,InProgress) VALUES ('"+UUID+"','"+newGründeString+"','"+newVonWemString+"','"+0+"','Nein')");

                    }

              boolean finalWarning = warning;
              BungeeSystem.getInstance().getProxy().getPlayers().forEach(p -> {
                        if(p.hasPermission("report.notify")) {
                            p.sendMessage("");
                            p.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
                            p.sendMessage(BungeeSystem.getPrefix()+(finalWarning ? "§4" : "§7")+"Der Spieler §e"+name+" "+(finalWarning ? "§4wurde sehr oft gemeldet§8!" : "§7wurde gemeldet§8!"));
                            p.sendMessage(new TextBuilder(BungeeSystem.getPrefix()+"§7Report bearbeiten §8[§a§lBearbeiten§8]").setClick("reportss erstawerd "+name).build());
                            p.sendMessage(BungeeSystem.getPrefix()+"§8§m-------------------------");
                            p.sendMessage("");
                        }
                    });
          }
      });


  }


  public static List<String> getGründe(String name) {
      String UUID = UUIDFetcher.getUUID(name).toString();

      try {
          ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();
          if(rs.next()) {
              String get = rs.getString("Reason");
              List<String> names = new ArrayList<>();

              names.addAll(Arrays.asList(get.split(";")));
              return names;
          }

      } catch (SQLException e) {
          e.printStackTrace();
      }
    return new ArrayList<>();
  }
  public static String getGründeString(String name) {
      String UUID = UUIDFetcher.getUUID(name).toString();

      try {
          ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();

          if(rs.next()){
              return rs.getString("Reason");
          }




      } catch (SQLException e) {
          e.printStackTrace();
      }
      return "§ERROR";
  }

    public static String getVonWemString(String name) {
        String UUID = UUIDFetcher.getUUID(name).toString();

        try {
            ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return rs.getString("vonWem");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

  public static List<String> getVonWem(String name) {
      String UUID = UUIDFetcher.getUUID(name).toString();

      try {
          ResultSet rs = BungeeSystem.getInstance().getMySQL().prepare("SELECT * FROM REPORT WHERE UUID='"+UUID+"'").executeQuery();
          if(rs.next()) {
              String get = rs.getString("vonWem");
              List<String> names = new ArrayList<>();

              names.addAll(Arrays.asList(get.split(";")));
              return names;
          }

      } catch (SQLException e) {
          e.printStackTrace();
      }
      return new ArrayList<>();
  }

  public static void deleteReport(String name) {
      BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
          if (isReportet(name)) {
                String UUID = UUIDFetcher.getUUID(name).toString();
                BungeeSystem.getInstance().getMySQL().update("DELETE FROM REPORT WHERE UUID='"+UUID+"'");
          }
      });
  }

    public static void setInProgress(String name) {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
            if(!isInProgress(name)) {
                BungeeSystem.getInstance().getMySQL().update("UPDATE REPORT SET InProgress='Ja' WHERE UUID='"+UUIDFetcher.getUUID(name).toString()+"'");
            }
        });

    }

    public static void acceptReport(String name, ProxiedPlayer player) {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
            if(!isInProgress(name)) {
                player.sendMessage(BungeeSystem.getPrefix()+"§aDu hast den Report an §e"+name+" §aangenommen§8!");
                player.sendMessage(BungeeSystem.getPrefix()+"§7Gründe§8: §e"+getGründeString(name).replace(";", "§8, §e"));
                player.connect(BungeeSystem.getInstance().getProxy().getPlayer(name).getServer().getInfo());
                deleteReport(name);
            }
        });

    }



}
