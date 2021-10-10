package warning.warning.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import warning.warning.Util;
import warning.warning.warnings.User;

import java.util.Arrays;
import java.util.UUID;

public class UserGUI {
    public static Inventory getInv(Player player) {
        final User user = User.getUser(player.getUniqueId());
        final Inventory inv = Bukkit.createInventory(null,54,"§l경고 목록");
        int[] slot = {45, 46, 47, 48, 50, 51, 52, 53};

        Util.settingItem(inv, Material.GRAY_STAINED_GLASS_PANE, 1, 1, " ", null, slot);
        inv.setItem(49,Util.getHead(player,null));

        user.getWarningMap().forEach((key,value) -> {
            String cause = value[0];
            String time = value[1];

            inv.addItem(getItem(key,cause,time));
        });
        return inv;
    }

    public static Inventory getInv(UUID uuid) {
        final User user = User.getUser(uuid);
        final Inventory inv = Bukkit.createInventory(null,54,"§l경고 목록");
        int[] slot = {45, 46, 47, 48, 50, 51, 52, 53};

        Util.settingItem(inv, Material.GRAY_STAINED_GLASS_PANE, 1, 1, " ", null, slot);
        inv.setItem(49,Util.getHeadAdapter(uuid,null));

        user.getWarningMap().forEach((key,value) -> {
            String cause = value[0];
            String time = value[1];

            inv.addItem(getItem(key,cause,time));
        });
        return inv;
    }

    private static ItemStack getItem(int warning,String cause,String time) {
        final ItemStack itemStack = new ItemStack(Material.PAPER,1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "§l경고 : " + ChatColor.BOLD + warning);
        meta.setLore(Arrays.asList(
                ChatColor.YELLOW + "§l사유 : " + ChatColor.WHITE + "" + ChatColor.BOLD + cause,
                ChatColor.YELLOW + "§l처리 일시 : " + ChatColor.WHITE + "" + ChatColor.BOLD + time
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
