package me.quexer.serverapi.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TitleAPI {

    public TitleAPI() {
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        try {
            Object e;
            Object chatSubtitle;
            Constructor subtitleConstructor;
            Object subtitlePacket;
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(p, subtitlePacket);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object)null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle);
                sendPacket(p, subtitlePacket);
            }

            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(p, subtitlePacket);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object)null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(p, subtitlePacket);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }

        if (footer == null) {
            footer = "";
        }

        header = ChatColor.translateAlternateColorCodes('&', header);
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        try {
            Object headertext = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + header + "\"}");
            Object footertext = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + footer + "\"}");
            Object packet = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(getNMSClass("IChatBaseComponent")).newInstance(headertext);
            Field f = packet.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(packet, footertext);
            sendPacket(player, packet);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static void sendActionBar(Player p, String msg) {
        try {
            Class c_craftplayer = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".entity.CraftPlayer");
            Object cp = c_craftplayer.cast(p);
            String ver = getVersion();
            Object packet;
            Object handle;
            if ((ver.equalsIgnoreCase("v1_8_R1") || !ver.startsWith("v1_8_")) && !ver.startsWith("v1_9_")) {
                handle = getNMSClass("IChatBaseComponent").cast(getNMSClass("ChatSerializer").getDeclaredMethod("a", String.class).invoke(getNMSClass("ChatSerializer"), "{\"text\": \"" + msg + "\"}"));
                packet = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), Byte.TYPE).newInstance(handle, 2);
            } else {
                handle = getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(msg);
                packet = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), Byte.TYPE).newInstance(handle, 2);
            }

            handle = c_craftplayer.getDeclaredMethod("getHandle").invoke(cp);
            Object pc = handle.getClass().getDeclaredField("playerConnection").get(handle);
            pc.getClass().getDeclaredMethod("sendPacket", getNMSClass("Packet")).invoke(pc, packet);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }


}
