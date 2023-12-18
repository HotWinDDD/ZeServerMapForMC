package com.qq.oldkingok.zombieescapemapssystem.sourceapi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Builder
public class GameInfo {
    @Getter
    String communityName;
    @Getter
    String serverName;
    @Getter
    String ip;
    @Getter
    int port;
    @Getter
    boolean canDetect;
    @Getter @Setter
    boolean checking;

    @Getter
    String mapName;
    @Getter
    String mapNameZh;
    @Getter
    int players;
    @Getter
    int maxPlayer;

    public static GameInfo fromString(String ipAndPort) {
        var strs = ipAndPort.split(":");
        return GameInfo.builder()
                .ip(strs[0])
                .port(Integer.parseInt(strs[1]))
                .build();
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "communityName='" + communityName + '\'' +
                ", serverName='" + serverName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", mapName='" + mapName + '\'' +
                ", mapNameZh='" + mapNameZh + '\'' +
                ", players=" + players +
                ", maxPlayer=" + maxPlayer +
                '}';
    }

    public void setDisable() {
        canDetect = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return port == gameInfo.port && Objects.equals(communityName, gameInfo.communityName) && Objects.equals(serverName, gameInfo.serverName) && Objects.equals(ip, gameInfo.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(communityName, serverName, ip, port);
    }
}
