package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.Place;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import org.bukkit.entity.Player;

public class CmdRemove implements CmdRunnable{
    @Override
    public boolean run(Player player, String... args) {
        if (args.length < 1) return false;

        var word = args[0];
        var instance = ZombieEscapeMapsSystem.getInstance();
        player.sendMessage(new OkFormat(instance.message.getMsg("ze-favorite-remove"))
                .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                .format(Place.WORD, word)
                .toString());
        instance.dataManager.editPlayerFavorite(player,
                word, false);
        return true;
    }
}
