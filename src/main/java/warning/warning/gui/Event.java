package warning.warning.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import warning.warning.Util;
import warning.warning.warnings.ServerConfig;

public class Event implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        if(e.getRawSlot() < 27) {
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getView().getTitle().equals("§l경고 관리")) {
                e.setCancelled(true);
                if (e.getRawSlot() == 3) {
                    int count = ServerConfig.getInstance().getMuteCount();

                    if (e.getClick() == ClickType.LEFT) {
                        ServerConfig.getInstance().setMuteCount(++count);
                    } else if (e.getClick() == ClickType.SHIFT_LEFT) {
                        ServerConfig.getInstance().setMuteCount(count + 10);
                    } else if (e.getClick() == ClickType.RIGHT) {
                        if(count <= 0) {
                            return;
                        }
                        ServerConfig.getInstance().setMuteCount(--count);
                    } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                        count -= 10;
                        if(count < 0) {
                            count = 0;
                        }
                        ServerConfig.getInstance().setMuteCount(count);
                    }
                    ServerGUI.reload_Time();
                } else if (e.getRawSlot() == 5) {
                    int count = ServerConfig.getInstance().getBanCount();

                    if (e.getClick() == ClickType.LEFT) {
                        ServerConfig.getInstance().setBanCount(++count);
                    } else if (e.getClick() == ClickType.SHIFT_LEFT) {
                        ServerConfig.getInstance().setBanCount(count + 10);
                    } else if (e.getClick() == ClickType.RIGHT) {
                        if(count <= 0) {
                            return;
                        }
                        ServerConfig.getInstance().setBanCount(--count);
                    } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                        count -= 10;
                        if(count < 0) {
                            count = 0;
                        }
                        ServerConfig.getInstance().setBanCount(count);
                    }
                    ServerGUI.reload_Time();
                } else if (e.getRawSlot() == 12) {
                    int time = ServerConfig.getInstance().getMuteTime();

                    if (e.getClick() == ClickType.LEFT) {
                        ServerConfig.getInstance().setMuteTime(time + 3600);
                    } else if (e.getClick() == ClickType.SHIFT_LEFT) {
                        ServerConfig.getInstance().setMuteTime(time + 86400);
                    } else if (e.getClick() == ClickType.RIGHT) {
                        time -= 3600;
                        if (time < 0) {
                            time = 0;
                        }
                        ServerConfig.getInstance().setMuteTime(time);
                    } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                        time -= 86400;
                        if (time < 0) {
                            time = 0;
                        }
                        ServerConfig.getInstance().setMuteTime(time);
                    }
                    ServerGUI.reload_Time();
                } else if (e.getRawSlot() == 14) {
                    int time = ServerConfig.getInstance().getBanTime();

                    if (e.getClick() == ClickType.LEFT) {
                        ServerConfig.getInstance().setBanTime(time + 3600);
                    } else if (e.getClick() == ClickType.SHIFT_LEFT) {
                        ServerConfig.getInstance().setBanTime(time + 86400);
                    } else if (e.getClick() == ClickType.RIGHT) {
                        time -= 3600;
                        if (time < 0) {
                            time = 0;
                        }
                        ServerConfig.getInstance().setBanTime(time);
                    } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                        time -= 86400;
                        if (time < 0) {
                            time = 0;
                        }
                        ServerConfig.getInstance().setBanTime(time);
                    }
                    ServerGUI.reload_Time();
                } else if (e.getRawSlot() == 21) {
                    ServerConfig.getInstance().getMuteMap().put(ServerConfig.getInstance().getMuteCount(),ServerConfig.getInstance().getMuteTime());
                    ServerGUI.reload_list(true);
                } else if (e.getRawSlot() == 23) {
                    ServerConfig.getInstance().getBanMap().put(ServerConfig.getInstance().getBanCount(),ServerConfig.getInstance().getBanTime());
                    ServerGUI.reload_list(false);
                } else if (e.getRawSlot() == 20) {
                    ServerConfig.getInstance().getMuteMap().clear();
                    ServerGUI.reload_list(true);
                } else if (e.getRawSlot() == 24) {
                    ServerConfig.getInstance().getBanMap().clear();
                    ServerGUI.reload_list(false);
                }
                return;
            }
        }
        if (e.getView().getTitle().equals("§l경고 목록")) {
            e.setCancelled(true);
        } else if (e.getView().getTitle().equals("§l경고 보유자 목록")) {
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                String name = Util.getNameFromItemStack(e.getCurrentItem());
                if(name == null) return;
                Player target = Bukkit.getPlayer(name);
                if(target != null) {
                    player.openInventory(UserGUI.getInv(target));
                } else {
                    player.openInventory(UserGUI.getInv(Bukkit.getOfflinePlayer(name).getUniqueId()));
                }

            } else if (e.getCurrentItem().getType() == Material.COMPASS) {
                player.openInventory(ServerGUI.getInv());
            }
        }
    }
}
