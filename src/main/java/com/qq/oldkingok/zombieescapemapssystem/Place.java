package com.qq.oldkingok.zombieescapemapssystem;

public enum Place {
    PREFIX("%prefix%", "向用户发消息的前缀"),
    MAP_NAME("%map-name%", "地图名称（英文名）"),
    MAP_ZH("%map-zh%", "地图中文名"),
    ADDRESS("%address%", "服务器地址"),
    COMMUNITY("%community%", "社区名"),
    SERVER("%server%", "服务器名称"),
    PLAYERS("%players%", "在线玩家数"),
    MAX_PLAYER("%max-player%", "玩家总数"),
    WORD("%word%","关键词")
    ;


    Place(String s, String description) {
        this.s = s;
        this.description = description;
    }

    @Override
    public String toString() {
        return s;
    }

    String s;
    String description;
}
