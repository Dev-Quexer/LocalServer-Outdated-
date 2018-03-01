package me.quexer.serverapi.rank;

public enum Rank {

    ADMIN("§4", "§4Admin §8● §4", "§4Administrator"),
    SRDEV("§b", "§bSrDev §8● §b", "§bSrDeveloper"),
    SRMOD("§c", "§cSrMod §8● §c", "§cSrModerator"),
    SRBUILDER("§2", "§2SrBuilder §8● §2", "§2SrBuilder"),
    DEV("§b", "§bDev §8● §b", "§bDeveloper"),
    MOD("§c", "§cMod §8● §c", "§cModerator"),
    BUILDER("§2", "§2Builder §8● §2", "§2Builder"),
    CONTENT("§b", "§bContent §8● §b", "§bContent"),
    SUPPORTER("§9", "§9Sup §8● §9", "§9Supporter"),
    YOUTUBER("§5", "§5YT §8● §5", "§5YouTuber"),
    PREMIUMPLUS("§e", "§ePremium+ §8● §e", "§ePremiumPlus"),
    PREMIUM("§6", "§6Premium §8● §6", "§6Premium"),
    SPIELER("§a", "§aSpieler §8● ", "§aSpieler");

    private String krz;
    private String prefix;
    private String full;

    private Rank(String krz, String prefix, String full) {
        this.krz = krz;
        this.prefix = prefix;
        this.full = full;
    }

    public String getKrz() {
        return this.krz;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getFull() {
        return full;
    }
}
