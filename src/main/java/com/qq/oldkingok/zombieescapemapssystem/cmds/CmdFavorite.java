package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.Place;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import org.bukkit.entity.Player;

public class CmdFavorite implements CmdRunnable{
    @Override
    public boolean run(Player player, String... args) {
        var instance = ZombieEscapeMapsSystem.getInstance();
        if (args.length < 1) {
            var sb = new StringBuilder();
            for (String s : instance.dataManager.getPlayerFavorite(player)) {
                sb.append(s);
                sb.append(" ");
            }
            player.sendMessage(sb.toString());
            return true;
        }
        // 带参数的
        var word = args[0];
        player.sendMessage(new OkFormat(instance.message.getMsg("ze-favorite"))
                .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                .format(Place.WORD, word)
                .toString());
        instance.dataManager.editPlayerFavorite(player,
                word, true);
        return true;
    }
}
