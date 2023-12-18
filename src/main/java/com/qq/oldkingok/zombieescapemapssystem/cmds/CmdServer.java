package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.Place;
import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import com.qq.oldkingok.zombieescapemapssystem.sourceapi.GameInfo;
import org.bukkit.entity.Player;

public class CmdServer implements CmdRunnable{
    @Override
    public boolean run(Player player, String... args) {
        var instance = ZombieEscapeMapsSystem.getInstance();
        var specifyCommunity = (args.length >= 1);
        if (!specifyCommunity) {
            for (String s : instance.message.getMsgList("ze-server-list")) {
                player.sendMessage(new OkFormat(s)
                        .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                        .toString());
            }
            return true;
        }
        // 指定了社区名
        var community = args[0];
        var isCommunityValid = false;

        var sb = new StringBuilder();
        for (String s : instance.message.getMsgList("ze-server-map-list.head")) {
            sb.append(new OkFormat(s)
                    .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                    .toString());
            sb.append("\n");
        }
        for (GameInfo gameInfo : ZombieEscapeMapsSystem.getInstance().serverManager.getOnlineServer()) {
            if (!gameInfo.getCommunityName().equals(community)) continue;
            if (!gameInfo.getServerName().contains("僵尸逃跑")) continue;
            isCommunityValid = true;

            var ok = new OkFormat(instance.message.getMsg("ze-server-map-list.body"))
                    .format(Place.PREFIX, instance.message.getMsg("ze-prefix"))
                    .format(Place.COMMUNITY, gameInfo.getCommunityName())
                    .format(Place.SERVER, gameInfo.getServerName())
                    .format(Place.MAP_NAME, gameInfo.getMapName())
                    .format(Place.MAP_ZH, gameInfo.getMapNameZh())
                    .format(Place.PLAYERS, gameInfo.getPlayers())
                    .format(Place.MAX_PLAYER, gameInfo.getMaxPlayer());
            var str = ok.toString();

            sb.append(str);
            sb.append("\n");
        }
        if (!isCommunityValid) {
            sb.append(new OkFormat(instance.message.getMsg("ze-must-be-a-community"))
                    .format(Place.PREFIX, instance.message.getMsg("ze-prefix")));
        }
        for (String s : instance.message.getMsgList("ze-server-map-list.ass")) {
            sb.append(s);
            sb.append("\n");
        }
        player.sendMessage(sb.toString());

        return true;
    }
}
