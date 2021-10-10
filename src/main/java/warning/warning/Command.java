package warning.warning;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import warning.warning.gui.UserGUI;
import warning.warning.gui.WarningGUI;
import warning.warning.warnings.User;
import warning.warning.warnings.UserManager;

import java.util.UUID;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender,org.bukkit.command.Command command,String label,String[] args) {
        final Player player = (Player) sender;
        final User user = User.getUser(player.getUniqueId());
        String cause;
        UUID targetUUID;
        Player target;
        int warning;

        if (args.length == 0) {
            Util.text(player);
            return true;
        }

        try {
            switch (args[0]) {
                case "보기":
                    if (user.getWarning() == 0) {
                        player.sendMessage(Util.chatStyle("당신은 받은 경고가 없습니다."));
                        return true;
                    }
                    player.openInventory(UserGUI.getInv(player.getUniqueId()));
                    break;
                case "주기":
                    StringBuilder builder = new StringBuilder();
                    if (args.length > 2) {
                        for (int i = 2; i < args.length; i++) {
                            builder.append(args[i]);
                            builder.append(" ");
                        }
                        cause = builder.toString();
                    } else {
                        cause = "없음";
                    }
                    targetUUID = Util.getUUIDFromString(args[1]);

                    UserManager.addWarning(player, targetUUID, cause);
                    break;
                case "감소":
                    targetUUID = Util.getUUIDFromString(args[1]);

                    UserManager.reduceWarning(player, targetUUID);
                    break;
                case "설정":
                    targetUUID = Util.getUUIDFromString(args[1]);
                    warning = Integer.parseInt(args[2]);
                    builder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        builder.append(args[i]);
                        builder.append(" ");
                    }
                    cause = builder.toString();

                    UserManager.setWarning(player, targetUUID, cause, warning);
                    player.sendMessage(Util.chatStyle("해당 플레이어의 경고가 설정되었습니다."));
                    break;
                case "경고보기":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }
                    player.openInventory(UserGUI.getInv(Util.getUUIDFromString(args[1])));
                    break;
                case "관리":
                    player.openInventory(WarningGUI.getInv());
                    break;
                case "사유변경":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }
                    targetUUID = Util.getUUIDFromString(args[1]);

                    warning = Integer.parseInt(args[2]);

                    builder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        builder.append(args[i]);
                        builder.append(" ");
                    }
                    cause = builder.toString();

                    User targetUser = User.getUser(targetUUID);

                    if (!targetUser.getWarningMap().containsKey(warning)) {
                        player.sendMessage(Util.chatStyle("해당 경고가 존재하지 않습니다."));
                        return true;
                    }
                    String[] strings = targetUser.getWarningMap().get(warning);
                    strings[0] = cause;
                    player.sendMessage(Util.chatStyle("사유가 변경되었습니다."));
                    break;
                case "뮤트해제":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }

                    targetUUID = Util.getUUIDFromString(args[1]);
                    targetUser = User.getUser(targetUUID);
                    if (targetUser.getMuteTime() <= 0) {
                        player.sendMessage(Util.chatStyle("해당 플레이어는 뮤트상태가 아닙니다."));
                        return true;
                    }
                    targetUser.setMuteTime(0);

                    targetUser.sendMessage(Util.chatStyle("관리자에 의해서 뮤트가 강제로 해제되었습니다."));
                    player.sendMessage(Util.chatStyle("해당 플레이어의 뮤트가 강제로 해제되었습니다."));
                    break;
                case "밴해제":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }
                    targetUUID = Util.getUUIDFromString(args[1]);
                    targetUser = User.getUser(targetUUID);

                    if (targetUser.getBanTime() <= 0) {
                        player.sendMessage(Util.chatStyle("해당 플레이어는 밴 상태가 아닙니다."));
                        return true;
                    }

                    targetUser.setBanTime(0);
                    player.sendMessage(Util.chatStyle("밴이 해제되었습니다."));
                    break;
                case "뮤트":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }

                    targetUUID = Util.getUUIDFromString(args[1]);
                    targetUser = User.getUser(targetUUID);

                    int time = Integer.parseInt(args[2]);

                    targetUser.startMute(time);
                    player.sendMessage(Util.chatStyle("해당 플레이어에게 뮤트를 줬습니다."));
                    break;
                case "밴":
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.hasPlayedBefore()) {
                            player.sendMessage(Util.chatStyle("해당 플레이어는 존재하지 않습니다."));
                            return true;
                        }
                    }

                    targetUUID = Util.getUUIDFromString(args[1]);
                    targetUser = User.getUser(targetUUID);

                    time = Integer.parseInt(args[2]);

                    targetUser.startBan(time);
                    player.sendMessage(Util.chatStyle("해당 플레이어에게 밴을 줬습니다."));
                    break;
                default:
                    player.sendMessage(Util.chatStyle("알 수 없는 명령어"));
                    break;
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            player.sendMessage(Util.chatStyle("명령어를 제대로 기입해주세요."));
        } catch (NumberFormatException e) {
            player.sendMessage(Util.chatStyle("숫자를 입력해주세요."));
        }
        return true;
    }
}
