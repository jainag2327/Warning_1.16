package warning.warning.warnings;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import warning.warning.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    protected static final HashMap<UUID,User> userMap;

    static {
        userMap = new HashMap<>();
    }

    public static HashMap<UUID, User> getUserMap() {
        return userMap;
    }

    public static void addWarning(Player sender,UUID uuid,String cause) {
        final Player target = Bukkit.getPlayer(uuid);
        final User user = User.getUser(uuid);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String[] strings = new String[2];

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (!offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                return;
            }
        }

        int warning = user.getWarning();
        String time = simpleDateFormat.format(new Date());

        strings[0] = cause;
        strings[1] = time;

        user.getWarningMap().put(++warning,strings);
        user.setWarning(warning);

        Bukkit.broadcastMessage(Util.chatStyle(user.getName() + "님이 경고를 받았습니다."));
        if(cause != null) Bukkit.broadcastMessage(Util.chatStyle("사유 : " + cause));
        Bukkit.broadcastMessage(Util.chatStyle("누적 경고 : " + user.getWarning()));

        if (ServerConfig.getInstance().getMuteMap().containsKey(user.getWarning())) {
            user.startMute(ServerConfig.getInstance().getMuteMap().get(user.getWarning()));
            user.sendMessage(Util.chatStyle("경고 " + user.getWarning() + "회가 누적되어 당신은 지금부터 뮤트 상태가 활성화됩니다."));
            user.sendMessage(Util.chatStyle("남은시간 : " + Util.getChangeTime(user.getMuteTime())));
            sender.sendMessage(Util.chatStyle(user.getName() + "님이 경고 누적으로 뮤트 상태가 시작됩니다."));
        }
        if (ServerConfig.getInstance().getBanMap().containsKey(user.getWarning())) {
            user.startBan(ServerConfig.getInstance().getBanMap().get(user.getWarning()));
            sender.sendMessage(Util.chatStyle(user.getName() + "님이 경고 누적으로 정지 상태가 시작됩니다."));
        }
    }

    public static void reduceWarning(Player player,UUID uuid) {
        final Player target = Bukkit.getPlayer(uuid);
        final User user = User.getUser(uuid);

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (!offlinePlayer.hasPlayedBefore()) {
                player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                return;
            }
        }

        int warning = user.getWarning();
        user.getWarningMap().remove(warning--);
        user.setWarning(warning);

        player.sendMessage(Util.chatStyle("해당 플레이어의 경고가 감소되었습니다."));
        user.sendMessage(Util.chatStyle("경고가 감소되었습니다."));
    }

    public static void setWarning(Player sender,UUID uuid,String cause,int warning) {
        final Player target = Bukkit.getPlayer(uuid);
        final User user = User.getUser(uuid);
        int user_warning = user.getWarning();

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (!offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                return;
            }
        }

        if (user_warning < warning) {                                // 경고가 늘어나는 경우
            for (int i = ++user_warning; i <= warning; i++) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] strings = new String[2];

                strings[0] = cause;
                strings[1] = simpleDateFormat.format(new Date());

                user.getWarningMap().put(i,strings);
            }
        } else if (user_warning > warning) {                         //경고가 줄어드는 경우
            for (int i = user_warning; i >= warning; i--) {
                user.getWarningMap().remove(i);
            }
        }

        user.setWarning(warning);
        user.sendMessage(Util.chatStyle("관리자에 의하여 당신의 경고가 " + user.getWarning() + "(으)로 변경되었습니다."));

        if (ServerConfig.getInstance().getMuteMap().containsKey(user.getWarning())) {
            user.startMute(ServerConfig.getInstance().getMuteMap().get(user.getWarning()));

            user.sendMessage(Util.chatStyle("경고 " + user.getWarning() + "회가 누적되어 당신은 지금부터 뮤트 상태가 활성화됩니다."));
            user.sendMessage(Util.chatStyle("남은시간 : " + Util.getChangeTime(user.getMuteTime())));
            sender.sendMessage(Util.chatStyle(user.getName() + "님이 경고 누적으로 뮤트 상태가 시작됩니다."));
        }
        if (ServerConfig.getInstance().getBanMap().containsKey(user.getWarning())) {
            user.startBan(ServerConfig.getInstance().getBanMap().get(user.getWarning()));

            sender.sendMessage(Util.chatStyle(user.getName() + "님이 경고 누적으로 정지 상태가 시작됩니다."));
        }
    }
}