package com.bnjrKemal;

import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bnjrKemal.listener.GrowListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class SonsuzCiftlik extends JavaPlugin {

    public static SonsuzCiftlik sonsuzCiftlik;

    public static HashMap<Island, Boolean> buttonGrowListener = new HashMap<>();
    public static HashMap<Island, Boolean> buttonKillMobListener = new HashMap<>();

    static File itemFile;
    static YamlConfiguration itemYaml;

    static File playerFile;
    static YamlConfiguration playerYaml;

    @Override
    public void onEnable() {
        sonsuzCiftlik = this;

        loadData();

        Bukkit.getPluginManager().registerEvents(new GrowListener(), this);

        Bukkit.getPluginManager().registerEvents(new KillMobListener(), this);

    }

    private void loadData() {

        itemFile = new File(getDataFolder(), "item-data.yml");
        if(!itemFile.exists()){
            itemFile.getParentFile().mkdirs();
            try {
                itemFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        itemYaml = YamlConfiguration.loadConfiguration(itemFile);

        playerFile = new File(getDataFolder(), "player-data.yml");
        if(!playerFile.exists()){
            playerFile.getParentFile().mkdirs();
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        playerYaml = YamlConfiguration.loadConfiguration(playerFile);

    }

    public static YamlConfiguration getItemYaml() {
        return itemYaml;
    }

    public static File getItemFile() {
        return itemFile;
    }
}
