package warning.warning;

import org.bukkit.plugin.java.JavaPlugin;
import warning.warning.gui.Event;

@SuppressWarnings("all")
public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("경고").setExecutor(new Command());
        getServer().getPluginManager().registerEvents(new Event(),this);
        getServer().getPluginManager().registerEvents(new warning.warning.warnings.Event(),this);
        DataAdmin.loadData();
        getLogger().info("제작 : SoonSaL_");
        getLogger().info("2차 배포 / 수정 금지");
        getLogger().info("플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        DataAdmin.saveData();
    }
}
