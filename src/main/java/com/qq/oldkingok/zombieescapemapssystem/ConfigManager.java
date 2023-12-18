package com.qq.oldkingok.zombieescapemapssystem;

import com.qq.oldkingok.okapi.OkConfigAPI;

public class ConfigManager {
    ZombieEscapeMapsSystem instance;
    public OkConfigAPI.OkConfig okConfig;
    public OkConfigAPI.OkConfig okTranslation;


    ConfigManager(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        okConfig = instance.configAPI.getOkConfig("config.yml");
        okTranslation = instance.configAPI.getOkConfig("translation.yml");
    }
}
