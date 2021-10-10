package warning.warning.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import warning.warning.Util;
import warning.warning.warnings.ServerConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerGUI {
    private static final Inventory inv;

    static {
        inv = Bukkit.createInventory(null,27,"§l경고 관리");
        defaultItem();
        reload_Time();
    }

    public static Inventory getInv() {
        return inv;
    }

    public static void reload_Time() {
        inv.setItem(3,getMuteConditionItem());
        inv.setItem(5,getBanConditionItem());
        inv.setItem(12,getMuteTimeSetItem());
        inv.setItem(14,getBanTimeSetItem());
    }

    public static void reload_list(boolean what) {
        if (what) {
            inv.setItem(11, getMuteListItem());
        } else {
            inv.setItem(15, getBanListItem());
        }
    }

    private static void defaultItem() {
        int[] glassSlot = {0, 1, 2, 4, 6, 7, 8, 18, 19, 22, 24, 25, 26};
        int[] barSlot = {9, 10, 13, 16, 17};
        int[] buttonSlot = {21, 23};

        Util.settingItem(inv, Material.GRAY_STAINED_GLASS_PANE, 1, 1, " ", null, glassSlot);
        Util.settingItem(inv, Material.IRON_BARS, 1, 1, " ", null, barSlot);
        Util.settingItem(inv, Material.STONE_BUTTON, 1, 1, ChatColor.WHITE + "§l적용하기", null, buttonSlot);
        Util.settingItem(inv,Material.PAPER,1,1,ChatColor.WHITE + "§l뮤트 초기화", Collections.singletonList(
                ChatColor.YELLOW + "§l뮤트 설정값이 모두 초기화 됩니다."
        ),20);
        Util.settingItem(inv,Material.PAPER,1,1,ChatColor.WHITE + "§l밴 초기화", Collections.singletonList(
                ChatColor.YELLOW + "§l밴 설정값이 모두 초기화 됩니다."
        ),24);
        inv.setItem(11, getMuteListItem());
        inv.setItem(15, getBanListItem());
    }

    private static ItemStack getMuteListItem() {
        final ItemStack itemStack = new ItemStack(Material.END_PORTAL_FRAME,1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l적용된 경고 목록");
        List<String> list = new ArrayList<>();
        ServerConfig.getInstance().getMuteMap().forEach((key,value) -> {
            list.add(ChatColor.YELLOW + "§l경고 : " + key + "§l회 --> 뮤트 처벌시간 : " + ChatColor.WHITE + ChatColor.BOLD + Util.getChangeTime(value));
        });
        meta.setLore(list);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getBanListItem() {
        final ItemStack itemStack = new ItemStack(Material.END_PORTAL_FRAME,1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l적용된 밴 목록");
        List<String> list = new ArrayList<>();
        ServerConfig.getInstance().getBanMap().forEach((key,value) -> {
            list.add(ChatColor.YELLOW + "§l경고 : " + key + "§l회 --> 밴 처벌시간 : " + ChatColor.WHITE + ChatColor.BOLD + Util.getChangeTime(value));
        });
        meta.setLore(list);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getMuteConditionItem() {
        final ItemStack itemStack = new ItemStack(Material.PAINTING,1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l뮤트 조건 : " + ChatColor.BOLD + ServerConfig.getInstance().getMuteCount() + "회");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getBanConditionItem() {
        final ItemStack itemStack = new ItemStack(Material.PAINTING,1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l밴 조건 : " + ChatColor.BOLD + ServerConfig.getInstance().getBanCount() + "회");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getMuteTimeSetItem() {
        final ItemStack itemStack = new ItemStack(Material.WHITE_BED);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l[ 뮤트 ] 경고 " + ServerConfig.getInstance().getMuteCount() + "§l회 처벌 설정");
        meta.setLore(Collections.singletonList(
                ChatColor.YELLOW + "§l현재 처벌 시간 : " + ChatColor.WHITE + Util.getChangeTime(ServerConfig.getInstance().getMuteTime())
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getBanTimeSetItem() {
        final ItemStack itemStack = new ItemStack(Material.WHITE_BED);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "§l[ 밴 ] 경고 " + ServerConfig.getInstance().getBanCount() + "회 처벌 설정");
        meta.setLore(Collections.singletonList(
                ChatColor.YELLOW + "§l현재 처벌 시간 : " + ChatColor.WHITE + Util.getChangeTime(ServerConfig.getInstance().getBanTime())
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
