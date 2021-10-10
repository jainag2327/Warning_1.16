package warning.warning;

import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import warning.warning.warnings.ServerConfig;
import warning.warning.warnings.User;
import warning.warning.warnings.UserManager;

import java.io.File;
import java.util.UUID;

public class DataAdmin {
    private static final File userFile = new File(Main.instance.getDataFolder(),"UserData.yml");
    private static final File serverConfigFile = new File(Main.instance.getDataFolder(), "ServerData.yml");

    private DataAdmin() {
    }

    public static void saveData() {
        saveUser();
        saveServerConfig();
    }

    public static void loadData() {
        loadUser();
        loadServerConfig();
        loadMute();
    }

    private static void loadMute() {
        for (User user : UserManager.getUserMap().values()) {
            if(user.getMuteTime() <= 0) {
                continue;
            }
            user.startMute(user.getMuteTime());
        }
    }

    private static void saveUser() {
        try {
            final FileConfiguration config = YamlConfiguration.loadConfiguration(userFile);
            UserManager.getUserMap().forEach((uuid, user) -> {
                config.set(uuid.toString(), new Gson().toJson(user));
            });
            config.save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadUser() {
        final FileConfiguration config = YamlConfiguration.loadConfiguration(userFile);
        config.getKeys(false).forEach(key -> {
            UUID uuid = UUID.fromString(key);
            UserManager.getUserMap().put(uuid,new Gson().fromJson(config.getString(key), User.class));
        });
    }

    private static void saveServerConfig() {
        final FileConfiguration config = YamlConfiguration.loadConfiguration(serverConfigFile);
        try {
            config.set("Server",new Gson().toJson(ServerConfig.getInstance()));
            config.save(serverConfigFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadServerConfig() {
        final FileConfiguration config = YamlConfiguration.loadConfiguration(serverConfigFile);
        ServerConfig.setServerConfig(new Gson().fromJson(config.getString("Server"), ServerConfig.class));
    }
}
