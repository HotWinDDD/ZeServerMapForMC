package com.qq.oldkingok.zombieescapemapssystem.server;

import com.qq.oldkingok.okapi.OkConfigAPI;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import com.qq.oldkingok.zombieescapemapssystem.sourceapi.GameInfo;
import com.qq.oldkingok.zombieescapemapssystem.sourceapi.SourceAPI;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerManager {
    ZombieEscapeMapsSystem instance;
    BukkitRunnable refresher;
    @Getter
    private Set<GameInfo> gameInfos = new HashSet<>();
    @Getter
    private Set<GameInfo> onlineGameInfos = new HashSet<>();
    private OkConfigAPI.OkConfig serversConfig;


    public ServerManager(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        serversConfig = instance.configAPI.getOkConfig("servers.yml");
        gameInfos = getServersFromConfig();

        final int perTick = instance.configManager.okConfig.getConfig().getInt("update-per-tick");
        refresher = new BukkitRunnable() {
            @Override
            public void run() {
                for (GameInfo gameInfo : gameInfos) {
                    if (gameInfo.isChecking()) continue;
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            gameInfo.setChecking(true);

                            try {
                                var isChangeMap = SourceAPI.updateInfo(gameInfo);
                                if (isChangeMap) {
                                    instance.dataManager.resetHasAlarmed(gameInfo);
                                    // 通知订阅的玩家
                                    instance.playerAlarmer.alarmPlayers(gameInfo);
                                }
                            } catch (IOException e) {
                                gameInfo.setDisable();
                            }
                            gameInfo.setChecking(false);
                        }
                    }.runTaskAsynchronously(instance);
                }
            }
        };
        refresher.runTaskTimerAsynchronously(instance, 0, perTick);
    }

    /**
     * 获取配置文件中的所有地址
     * @return
     */
    public Set<GameInfo> getServersFromConfig() {
        Set<GameInfo> gameInfos = new HashSet<>();
        List<Map<?, ?>> serversMap = serversConfig
                .getConfig().getMapList("servers");
        for (Map<?, ?> map : serversMap) {
            gameInfos.add(GameInfo.builder()
                    .communityName((String) map.get("cname"))
                    .serverName((String) map.get("name"))
                    .ip((String) map.get("host"))
                    .port((int) map.get("port"))
                    .canDetect(false)
                    .build());
        }
        return gameInfos;
    }

    public Set<GameInfo> getOnlineServer() {
        Set<GameInfo> servers = new HashSet<>();
        for (GameInfo gameInfo : gameInfos) {
            if (gameInfo.getMapName() == null) continue;
            servers.add(gameInfo);
        }
        return servers;
    }

    public void disable() {
        refresher.cancel();
    }
}
