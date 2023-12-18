package com.qq.oldkingok.zombieescapemapssystem.data;

import com.qq.oldkingok.okapi.OkConfigAPI;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import com.qq.oldkingok.zombieescapemapssystem.sourceapi.GameInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class DataManager {
    ZombieEscapeMapsSystem instance;
    OkConfigAPI.OkConfig data;
    private final Map<GameInfo, Set<Player>> gameInfoHasAlarmPlayerMap = new HashMap<>();

    public DataManager(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        data = instance.configAPI.getOkConfig("data.yml");
    }

    public List<String> getPlayerFavorite(OfflinePlayer player) {
        var playerSection = data.getConfig().getConfigurationSection(player.getName());
        if (playerSection == null) return new ArrayList<>();
        return playerSection.getStringList("favorite");
    }

    public void editPlayerFavorite(OfflinePlayer player, String str, boolean isAdd) {
        data.edit(new OkConfigAPI.ConfigRunnable() {
            @Override
            protected void run(FileConfiguration config) {
                var playerSection = data.getConfig().getConfigurationSection(player.getName());
                if (playerSection == null) {
                    playerSection = data.getConfig().createSection(player.getName());
                }

                var strs = getPlayerFavorite(player);
                if (isAdd) {
                    strs.add(str);
                } else {
                    strs.remove(str);
                }
                playerSection.set("favorite", strs);
            }
        });
    }

    public void setPlayerAlarm(Player player,boolean on) {
        data.edit(new OkConfigAPI.ConfigRunnable() {
            @Override
            protected void run(FileConfiguration config) {
                var playerSection = data.getConfig().getConfigurationSection(player.getName());
                if (playerSection == null) {
                    playerSection = data.getConfig().createSection(player.getName());
                }

                if (on) {
                    playerSection.set("alarm", true);
                } else {
                    playerSection.set("alarm", false);
                }
            }
        });
    }

    public boolean getPlayerAlarm(Player player) {
        var playerSection = data.getConfig().getConfigurationSection(player.getName());
        if (playerSection == null) return true;
        return playerSection.getBoolean("alarm");
    }

    /**
     * 是否已经提醒过
     * @param player
     * @param gameInfo
     * @return
     */
    public boolean hasAlarmed(Player player, GameInfo gameInfo) {
        var playerSet = gameInfoHasAlarmPlayerMap.get(gameInfo);
        return playerSet.contains(player);
    }

    public void setHasAlarmed(Player player, GameInfo gameInfo) {
        var playerSet = gameInfoHasAlarmPlayerMap.get(gameInfo);
        playerSet.add(player);
    }

    public void resetHasAlarmed(GameInfo gameInfo) {
        var playerSet = gameInfoHasAlarmPlayerMap.get(gameInfo);

        if (playerSet == null) {
            playerSet = new HashSet<>();
            gameInfoHasAlarmPlayerMap.put(gameInfo, playerSet);
        } else {
            playerSet.clear();
        }
    }
}
