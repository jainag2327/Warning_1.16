package warning.warning;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Util {
    private Util() {
    }

    public static String chatStyle(String msg) {
        return ChatColor.RED+"§l[ 경고 ] "+ChatColor.WHITE+ChatColor.BOLD+msg;
    }

    public static void settingItem(Inventory inv,Material material,int amount,int data,String name,List<String> lore,int... slot) {
        ItemStack item = new ItemStack(material,amount,(short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if(lore != null) meta.setLore(lore);
        item.setItemMeta(meta);
        for(int i : slot) inv.setItem(i,item);
    }

    public static String getChangeTime(int time) {
        int day = time / (60 * 60 * 24);
        int hour = (time - day * 60 * 60 * 24) / (60 * 60);
        int minute = (time - day * 60 * 60 * 24 - hour * 3600) / 60;
        int second = time % 60;

        if(day == 0 && hour == 0 && minute == 0) return second+"초 ";
        if(day == 0 && hour == 0) return minute+ "분 "+second+"초 ";
        if(day == 0) return hour + "시간 " +minute+ "분 "+second+"초 ";
        return day + "일 "+hour + "시간 " +minute+ "분 "+second+"초 ";
    }

    public static ItemStack getHeadAdapter(UUID uuid, List<String> list) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return getHead(player, list);
        } else {
            return getHead(Bukkit.getOfflinePlayer(uuid), list);
        }
    }

    public static ItemStack getHead(Player player, List<String> list) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.getPersistentDataContainer().set(getNameKey(), PersistentDataType.STRING,player.getName());
        skull.setDisplayName(ChatColor.BOLD + (ChatColor.WHITE + player.getName()));
        if(list != null) skull.setLore(list);
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getHead(OfflinePlayer offlinePlayer, List<String> list) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.getPersistentDataContainer().set(getNameKey(), PersistentDataType.STRING, Objects.requireNonNull(offlinePlayer.getName()));
        skull.setDisplayName(ChatColor.BOLD + (ChatColor.WHITE + offlinePlayer.getName()));
        if(list != null) skull.setLore(list);
        skull.setOwningPlayer(offlinePlayer);
        item.setItemMeta(skull);
        return item;
    }

    public static String getNameFromItemStack(ItemStack itemStack) {
        final ItemMeta meta = itemStack.getItemMeta();
        if(!meta.getPersistentDataContainer().has(getNameKey(),PersistentDataType.STRING)) return null;
        return meta.getPersistentDataContainer().get(getNameKey(),PersistentDataType.STRING);
    }

    public static void text(Player player) {
        player.sendMessage(chatStyle("/경고 보기"));
        if(player.isOp()) {
            player.sendMessage(chatStyle("/경고 주기 (플레이어) (사유)"));
            player.sendMessage(chatStyle("/경고 감소 (플레이어)"));
            player.sendMessage(chatStyle("/경고 설정 (플레이어) (횟수)"));
            player.sendMessage(chatStyle("/경고 경고보기 (플레이어)"));
            player.sendMessage(chatStyle("/경고 관리"));
            player.sendMessage(chatStyle("/경고 사유변경 (플레이어) (바꿀경고횟수) (변경할 사유)"));
            player.sendMessage(chatStyle("/경고 뮤트해제 (플레이어)"));
            player.sendMessage(chatStyle("/경고 밴해제 (플레이어)"));
            player.sendMessage(chatStyle("/경고 뮤트 (플레이어) (시간)"));
            player.sendMessage(chatStyle("/경고 밴 (플레이어) (시간)"));
        }
    }

    public static UUID getUUIDFromString(String name) {
        final Player player = Bukkit.getPlayer(name);
        if (player != null) {
            return player.getUniqueId();
        }
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    public static NamespacedKey getNameKey() {
        return new NamespacedKey(Main.instance,"NAME");
    }
}
