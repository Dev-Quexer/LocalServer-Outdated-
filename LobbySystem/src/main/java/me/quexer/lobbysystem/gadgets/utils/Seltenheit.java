package me.quexer.lobbysystem.gadgets.utils;

public enum Seltenheit {

    LEGENDARY("§8[§6§lLEGENDARY§8]"),
    EPIC("§8[§5§lEPIC§8]"),
    RARE("§8[§b§lRARE§8]"),
    COMMON("§8[§a§lCOMMON§8]");

    private String name;

    Seltenheit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
