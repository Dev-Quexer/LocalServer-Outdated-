package me.quexer.serverapi.nick;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import io.netty.handler.codec.http.HttpContentEncoder;
import me.quexer.serverapi.ServerAPI;
import me.quexer.serverapi.manager.SoundManager;
import me.quexer.serverapi.nick.events.PlayerNickEvent;
import me.quexer.serverapi.nick.events.PlayerRemoveNickEvent;
import me.quexer.serverapi.utils.GameProfileBuilder;
import me.quexer.serverapi.utils.UUIDFetcher;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NickAPI {

    private static final HashMap<UUID, GameProfile> NICKED_PLAYERS = Maps.newHashMap();
    private static final Map<String, GameProfile> BY_NAME = Maps.newHashMap();
    private static final Map<UUID, GameProfile> BY_UUID = Maps.newHashMap();
    public static final List<String> NAMES = Lists.newArrayList();

    public static void add(final String name) {
        if(name.length() <= 16) {
            NAMES.add(name);
        }
    }
    public static void remove(final String name) {
        if(NAMES.contains(name)) {
            NAMES.remove(name);
        }
    }
    public static String setNick(final Player player, final String name) {
        final GameProfile profile = getProfile(player);

        if (profile != null) {
            if (NICKED_PLAYERS.containsKey(player.getUniqueId()) == false) {
                NICKED_PLAYERS.put(player.getUniqueId(), profile);
            }
            final GameProfile profile2 = getProfile(name, player.getUniqueId());
            if (profile2 != null) {
                setProfile(player, profile2);
            }
            player.setDisplayName(name);
            player.setPlayerListName(name);
            for(final Player players : Bukkit.getOnlinePlayers()) {
                if (players.equals(player) == false) {
                    players.hidePlayer(player);
                    players.showPlayer(player);
                }
            }
            Bukkit.getPluginManager().callEvent(new PlayerNickEvent(player, NAMES, NICKED_PLAYERS, name));
        }
        return name;
    }
    public static String setRandomNick(final Player player) {
        final String nick = NAMES.get(new Random().nextInt(NAMES.size()));
        return setNick(player, nick);
    }
    private static GameProfile getProfile(final Player player) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        try {
            return (GameProfile) Class.forName("org.bukkit.craftbukkit."+version+".entity.CraftPlayer").getDeclaredMethod("getProfile").invoke(player);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getRealName(final Player player) {
        if (hasNick(player)) {
            return NICKED_PLAYERS.get(player.getUniqueId()).getName();
        }
        return player.getName();
    }
    public static void removeNick(final Player player) {
        if (hasNick(player)) {


            setProfile(player, NICKED_PLAYERS.get(player.getUniqueId()));
            NICKED_PLAYERS.remove(player.getUniqueId());
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());

            for(final Player players : Bukkit.getOnlinePlayers()) {
                if (players.equals(player) == false) {
                    players.hidePlayer(player);
                    players.showPlayer(player);
                }
            }
            Bukkit.getPluginManager().callEvent(new PlayerRemoveNickEvent(player, NAMES, NICKED_PLAYERS));
        }

    }
    private static void setProfile(final Player player, final GameProfile profile) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        try {
            final Class<?> clazz = Class.forName("org.bukkit.craftbukkit."+version+".entity.CraftPlayer");
            final Field field = getField(Class.forName("net.minecraft.server."+version+".EntityPlayer"));
            if (field != null) {
                field.set(clazz.getMethod("getHandle").invoke(player), profile);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    private static Field getField(final Class<?> clazz) {
        Field field = null;
        for (final Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().equals(GameProfile.class)) {
                field = f;
            }
        }
        if (field == null) {
            for (final Field f : clazz.getFields()) {
                f.setAccessible(true);
                if (f.getType().equals(GameProfile.class)) {
                    field = f;
                }
            }
        }
        if (clazz.getSuperclass() != null && field == null) {
            field = getField(clazz.getSuperclass());
        }
        return field;
    }
    public static boolean hasNick(final Player player) {
        return NICKED_PLAYERS.containsKey(player.getUniqueId());
    }
    public static GameProfile getProfile(final String name, final UUID realUniqueId) {
        if (BY_NAME.containsKey(name)) {
            return BY_NAME.get(name);
        }
        try {
            final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            final InputStream is = url.openStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            UUID id = null;
            while((line = br.readLine()) != null) {
                final JsonObject json = (JsonObject) new JsonParser().parse(line);
                id = UUIDTypeAdapter.fromString(json.get("id").getAsString());
            }
            if (id != null) {
                return getProfile(id, realUniqueId);
            } else {
                final GameProfile profile = new GameProfile(realUniqueId, name);
                BY_NAME.put(name, profile);
                return profile;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static GameProfile getProfile(final UUID uuid, final UUID realUniqueId) {
        if (BY_UUID.containsKey(uuid)) {
            return BY_UUID.get(uuid);
        }
        try {
            final URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")+"?unsigned=false");
            final InputStream is1 = url1.openStream();
            final BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String line1;
            String name;
            while((line1 = br1.readLine()) != null) {
                final JsonObject json = (JsonObject) new JsonParser().parse(line1);
                name = json.get("name").getAsString();
                final JsonArray array = json.getAsJsonArray("properties");
                final JsonElement element = array.get(0);
                if (element.isJsonObject()) {
                    final JsonObject object = element.getAsJsonObject();
                    final Property property = new Property(object.get("name").getAsString(), object.get("value").getAsString(), object.get("signature").getAsString());
                    final GameProfile profile = new GameProfile(realUniqueId, name);
                    profile.getProperties().put(property.getName(), property);
                    BY_UUID.put(uuid, profile);
                    BY_NAME.put(name, profile);
                    return profile;
                }
            }
            br1.close();
            is1.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> getNames() {
        return NAMES;
    }
    public static void removeAll() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            removeNick(p);
        }
    }
    public static List<Player> getNickedPlayers() {
        final List<Player> players = Lists.newArrayList();
        for (final Map.Entry<UUID, GameProfile> entry : NICKED_PLAYERS.entrySet()) {
            final Player p = Bukkit.getPlayer(entry.getKey());
            if (p != null && p.isOnline()) {
                players.add(p);
            }
        }
        return players;
    }

    public static void changeSkin(CraftPlayer cp, String nameFromPlayer){
        if(nameFromPlayer.length() > 16)
            nameFromPlayer = (String) nameFromPlayer.subSequence(0, 15);
        GameProfile skingp = cp.getProfile();
        try {
            if(UUIDFetcher.getUUID(nameFromPlayer)!=null)
                skingp = GameProfileBuilder.fetch(UUIDFetcher.getUUID(nameFromPlayer));
            else
                skingp = GameProfileBuilder.fetch(UUID.fromString("8667ba71-b85a-4004-af54-457a9734eed7"));
        } catch (IOException e) {
        }
        Collection<Property> props = skingp.getProperties().get("textures");
        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures",props);
        PacketPlayOutEntityDestroy d = new PacketPlayOutEntityDestroy(cp.getEntityId());
        sendPacket(d);
        PacketPlayOutPlayerInfo i = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
        sendPacket(i);
        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutPlayerInfo i2 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
                sendPacket(i2);
                PacketPlayOutNamedEntitySpawn s = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(!cp.getName().equals(all.getName()))
                        ((CraftPlayer)all).getHandle().playerConnection.sendPacket(s);

                }
            }
        }.runTaskLater(ServerAPI.getInstance(), 4);
    }
    @SuppressWarnings("rawtypes")
    public static  void sendPacket(Packet packet){
        for(Player all : Bukkit.getOnlinePlayers()){
            ((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static boolean isMySQLNick(Player p) {
        String UUID = UUIDFetcher.getUUID(UUIDFetcher.getName(p.getUniqueId())).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM NICK WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return rs.getInt("Ja") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean playerExists(Player p) {
        String UUID = UUIDFetcher.getUUID(UUIDFetcher.getName(p.getUniqueId())).toString();
        try {
            ResultSet rs = ServerAPI.getMySQL().prepare("SELECT * FROM NICK WHERE UUID='"+UUID+"'").executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void toggleAutoNick(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), () -> {
            String UUID = UUIDFetcher.getUUID(UUIDFetcher.getName(p.getUniqueId())).toString();
            if(playerExists(p)) {
                if(!isMySQLNick(p)) {
                    ServerAPI.getMySQL().update("UPDATE NICK SET Ja='1' WHERE UUID='" + UUID + "'");
                    ServerAPI.getSoundManager().playGood(p);
                    p.sendMessage(ServerAPI.getNickPrefix()+"§4Du hast den AutoNick§8: §aAktiviert");
                } else {
                    ServerAPI.getMySQL().update("UPDATE NICK SET Ja='0' WHERE UUID='" + UUID + "'");
                    ServerAPI.getSoundManager().playBad(p);
                    p.sendMessage(ServerAPI.getNickPrefix()+"§4Du hast den AutoNick§8: §cDeaktiviert");
                }
            } else {
                ServerAPI.getMySQL().update("INSERT INTO NICK(UUID,Ja) VALUES ('"+UUID+"','1')");
            }
        });



    }
    private static boolean nickOnThisServer = true;

    public static boolean isNickOnThisServer() {
        return nickOnThisServer;
    }

    public static void setNickOnThisServer(boolean nickOnThisServer) {
        NickAPI.nickOnThisServer = nickOnThisServer;
    }
}
