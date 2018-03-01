package me.quexer.serverapi.game;

public enum GameState {

    LOBBY ("§aLobby"),
    PREINGAME ("§cIngame"),
    INGAME ("§cIngame"),
    AFTERINGAME ("§cIngame"),
    PREDEATHMATCH ("§eDeathmatch"),
    DEATHMATCH ("§eDeathmatch"),
    AFTERDEATHMATCH ("§eDeathmatch"),
    PRERESTART ("§4Restart"),
    RESTART ("§4Restart");

    private String name;

    private GameState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
