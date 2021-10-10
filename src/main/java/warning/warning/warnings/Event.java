package warning.warning.warnings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import warning.warning.Util;

public class Event implements Listener {
    @EventHandler
    public void onTalk(AsyncPlayerChatEvent e) {
        final User user = User.getUser(e.getPlayer().getUniqueId());
        if (user.getMuteTime() <= 0) return;
        e.setCancelled(true);
        user.sendMessage(Util.chatStyle("경고누적으로 인하여 뮤트 상태입니다."));
        user.sendMessage(Util.chatStyle("남은 시간 : " + Util.getChangeTime(user.getMuteTime())));
    }
}
