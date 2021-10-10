package warning.warning.warnings;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import warning.warning.Main;
import warning.warning.Util;

import java.util.*;

public class User {
    private final UUID uuid;
    private final HashMap<Integer,String[]> warningMap = new HashMap<>();       // 0 : 이유<String> , 1 : 시간<Date>
    private int warning;
    private int muteTime;
    private int banTime;

    public User(UUID uuid) {
        this.uuid = uuid;
        UserManager.userMap.put(uuid,this);
    }

    public static User getUser(UUID uuid) {
        final HashMap<UUID,User> userMap = UserManager.userMap;
        return userMap.containsKey(uuid) ? userMap.get(uuid) : new User(uuid);
    }

    public void startMute(int muteTime) {
        if (this.muteTime > 0 && this.muteTime != muteTime) {
            this.muteTime = muteTime;
            return;
        }
        this.muteTime = muteTime;
        new BukkitRunnable() {
            @Override
            public void run() {
                int time = getMuteTime();
                setMuteTime(--time);
                if (getMuteTime() <= 0) {
                    sendMessage(Util.chatStyle("뮤트가 해제되었습니다."));
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Main.instance,0,20);
    }

    public void startBan(int banTime) {
        if (this.banTime > 0) {
            this.banTime = banTime;
            Bukkit.getBanList(BanList.Type.NAME)
                    .addBan(getName(),
                            org.apache.commons.lang.StringUtils.repeat("\n", 35)+"경고 누적으로 인하여 계정이 정지되었습니다." +
                                    "\n기간 : "+ Util.getChangeTime(banTime) +org.apache.commons.lang.StringUtils.repeat("\n", 35),
                            new Date(System.currentTimeMillis()+banTime* 1000L),null);
            return;
        }
        this.banTime = banTime;
        Bukkit.getBanList(BanList.Type.NAME)
                .addBan(getName(),
                        org.apache.commons.lang.StringUtils.repeat("\n", 35)+"경고 누적으로 인하여 계정이 정지되었습니다." +
                                "\n기간 : "+ Util.getChangeTime(banTime) +org.apache.commons.lang.StringUtils.repeat("\n", 35),
                        new Date(System.currentTimeMillis()+banTime* 1000L),null);


        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.kickPlayer("경고 누적으로 인하여 계정이 정지되었습니다.\n기간 : " + Util.getChangeTime(banTime));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                int time = getBanTime();
                setBanTime(--time);
                if (getBanTime() <= 0) {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(getName());
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Main.instance,0,20);
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public UUID getUUID() {
        return uuid;
    }

    public HashMap<Integer, String[]> getWarningMap() {
        return warningMap;
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

    public void sendMessage(String msg) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage(msg);
        }
    }

    public String getName() {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return player.getName();
        }
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
