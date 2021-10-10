package warning.warning.warnings;

import java.util.HashMap;

public class ServerConfig {
    private static ServerConfig serverConfig;
    private final HashMap<Integer,Integer> muteMap = new HashMap<>();
    private final HashMap<Integer,Integer> banMap = new HashMap<>();
    private int muteCount;
    private int banCount;
    private int muteTime;
    private int banTime;

    private ServerConfig() {
    }

    public static void setServerConfig(ServerConfig asd) {
        serverConfig = asd;
    }

    public static ServerConfig getInstance() {
        if (serverConfig == null) {
            serverConfig = new ServerConfig();
        }
        return serverConfig;
    }

    public boolean isMute(int muteCount) {
        return muteMap.containsKey(muteCount);
    }

    public boolean isBan(int banCount) {
        return banMap.containsKey(banCount);
    }

    public HashMap<Integer, Integer> getMuteMap() {
        return muteMap;
    }

    public HashMap<Integer, Integer> getBanMap() {
        return banMap;
    }

    public int getMuteCount() {
        return muteCount;
    }

    public void setMuteCount(int muteCount) {
        this.muteCount = muteCount;
    }

    public int getBanCount() {
        return banCount;
    }

    public void setBanCount(int banCount) {
        this.banCount = banCount;
    }

    public int getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(int muteTime) {
        this.muteTime = muteTime;
    }

    public int getBanTime() {
        return banTime;
    }

    public void setBanTime(int banTime) {
        this.banTime = banTime;
    }
}
