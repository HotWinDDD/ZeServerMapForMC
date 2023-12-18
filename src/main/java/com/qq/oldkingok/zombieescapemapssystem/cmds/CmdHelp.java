package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.Place;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class CmdHelp implements CmdRunnable{
    @Override
    public boolean run(Player player, String... args) {
        for (String s : ZombieEscapeMapsSystem.getInstance().message.getMsgList("ze-help-list")) {
            player.sendMessage(new OkFormat(s)
                    .format(Place.PREFIX, ZombieEscapeMapsSystem.getInstance()
                            .message.getMsg("ze-prefix"))
                    .toString());
        }
        return true;
    }
}
