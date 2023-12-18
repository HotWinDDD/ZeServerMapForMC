package com.qq.oldkingok.zombieescapemapssystem;

import com.qq.oldkingok.okapi.OkConfigAPI;

public class Translation {
    private OkConfigAPI.OkConfig translationConfig;
    private ZombieEscapeMapsSystem instance;

    public Translation(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        translationConfig = instance.configAPI.getOkConfig("translation.yml");
    }

    public String getTrans(String enMapName) {
        String zhStr = null;
        try {
            zhStr = translationConfig.getStr(enMapName);
        } catch (IllegalArgumentException e) {
            zhStr = "*没有翻译";
        }
        return zhStr;
    }
}
