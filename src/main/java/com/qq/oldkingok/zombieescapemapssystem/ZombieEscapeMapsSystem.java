package com.qq.oldkingok.zombieescapemapssystem;

import com.qq.oldkingok.okapi.OkConfigAPI;
import com.qq.oldkingok.zombieescapemapssystem.cmds.CommandListener;
import com.qq.oldkingok.zombieescapemapssystem.data.DataManager;
import com.qq.oldkingok.zombieescapemapssystem.server.ServerManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombieEscapeMapsSystem extends JavaPlugin {
    @Getter static ZombieEscapeMapsSystem instance;
    public OkConfigAPI configAPI;
    public ConfigManager configManager;
    public Message message;
    public Translation translation;
    public ServerManager serverManager;
    public DataManager dataManager;
    public PlayerAlarmer playerAlarmer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        configAPI = new OkConfigAPI(this);
        configAPI.load("config.yml", "data.yml", "message.yml", "servers.yml", "translation.yml");
        configManager = new ConfigManager(this);

        message = new Message(this);
        translation = new Translation(this);
        serverManager = new ServerManager(this);
        dataManager = new DataManager(this);
        playerAlarmer = new PlayerAlarmer(this);
        new CommandListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        serverManager.disable();
    }
}
