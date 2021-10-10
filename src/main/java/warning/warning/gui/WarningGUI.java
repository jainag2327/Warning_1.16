package warning.warning.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import warning.warning.Main;
import warning.warning.Util;
import warning.warning.warnings.UserManager;

import java.util.ArrayList;
import java.util.List;

public class WarningGUI {
    public static Inventory getInv() {
        final Inventory inv = Bukkit.createInventory(null, 54, "§l경고 보유자 목록");
        int[] slot = {45, 46, 47, 48, 50, 51, 52, 53};

        Util.settingItem(inv, Material.GRAY_STAINED_GLASS_PANE, 1, 1, " ", null, slot);
        Util.settingItem(inv, Material.COMPASS, 1, 1, ChatColor.WHITE + "§l경고 관리창으로 이동", null, 49);

        UserManager.getUserMap().values().forEach(user -> {
            if(user.getWarning() > 0) {
                List<String> list = new ArrayList<>();
                list.add(ChatColor.YELLOW + "§l경고 횟수 : " + ChatColor.WHITE + ChatColor.BOLD + user.getWarning());
                if (user.getMuteTime() > 0 || user.getBanTime() > 0) {
                    list.add(ChatColor.YELLOW + "§l상태 : ");
                    if (user.getMuteTime() > 0) {
                        list.add(ChatColor.WHITE + "§lㄴ 뮤트 진행중");
                    }
                    if (user.getBanTime() > 0) {
                        list.add(ChatColor.WHITE + "§lㄴ 밴 진행중");
                    }
                }
                Bukkit.getServer().getScheduler().runTask(Main.instance,() -> {
                    inv.addItem(Util.getHeadAdapter(user.getUUID(), list));
                });
            }
        });

        return inv;
    }
}
