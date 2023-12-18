package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.Place;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import org.bukkit.entity.Player;

public class CmdToggle implements CmdRunnable{
    @Override
    public boolean run(Player player, String... args) {
        var instance = ZombieEscapeMapsSystem.getInstance();
        if (instance.dataManager.getPlayerAlarm(player)) {
            instance.dataManager.setPlayerAlarm(player, false);
            player.sendMessage(new OkFormat(instance.message.getMsg("ze-toggle-off"))
                    .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                    .toString());
        } else {
            instance.dataManager.setPlayerAlarm(player, true);
            player.sendMessage(new OkFormat(instance.message.getMsg("ze-toggle-on"))
                    .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                    .toString());

        }
        return true;
    }
}
