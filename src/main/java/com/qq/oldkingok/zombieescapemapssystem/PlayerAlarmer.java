package com.qq.oldkingok.zombieescapemapssystem;

import com.qq.oldkingok.okapi.OkFormat;
import com.qq.oldkingok.zombieescapemapssystem.sourceapi.GameInfo;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerAlarmer {
    private ZombieEscapeMapsSystem instance;

    public PlayerAlarmer(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
    }

    public void alarmPlayers(GameInfo gameInfo) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            var needAlarm = false;
            var needTitle = false;
            for (String fav : instance.dataManager.getPlayerFavorite(onlinePlayer)) {
                if (instance.dataManager.hasAlarmed(onlinePlayer, gameInfo)) break;
                instance.dataManager.setHasAlarmed(onlinePlayer, gameInfo);

                if (gameInfo.getMapName().contains(fav)) {
                    needAlarm = true;
                    needTitle = true;
                    break;
                }
            }

            if (instance.dataManager.hasAlarmed(onlinePlayer, gameInfo)) continue;
            if (instance.dataManager.getPlayerAlarm(onlinePlayer)) needAlarm = true;

            if (needAlarm) sendMessage(onlinePlayer,gameInfo);
            if (needTitle) sendTitle(onlinePlayer, gameInfo);
        }
    }

    private void sendTitle(Player player, GameInfo gameInfo) {
        var title = instance.message.getMsg("ze-favorite-title");
        var subTitle = new OkFormat(instance.message.getMsg("ze-favorite-subtitle"))
                .format(Place.COMMUNITY, gameInfo.getCommunityName())
                .format(Place.SERVER, gameInfo.getServerName())
                .toString();
        var config = instance.configManager.okConfig.getConfig();
        player.sendTitle(title, subTitle,
                config.getInt("title.fadeIn"),
                config.getInt("title.stay"),
                config.getInt("title.fadeOut"));
    }

    private void sendMessage(Player player, GameInfo gameInfo) {
        var address = gameInfo.getIp()+ ":" + gameInfo.getPort();

        for (String s : instance.message.getMsgList("ze-maps")) {
            var str = new OkFormat(s)
                    .format(Place.COMMUNITY, gameInfo.getCommunityName())
                    .format(Place.SERVER, gameInfo.getServerName())
                    .format(Place.PLAYERS, gameInfo.getPlayers())
                    .format(Place.MAX_PLAYER, gameInfo.getMaxPlayer())
                    .format(Place.MAP_NAME, gameInfo.getMapName())
                    .format(Place.MAP_ZH, gameInfo.getMapNameZh())
                    .format(Place.ADDRESS, address)
                    .toString();

            if (s.contains(Place.ADDRESS.toString())) {
                TextComponent textComponent = new TextComponent(str);
                var url = "steam://rungameid/730//+connect " + address;
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,url));
                player.spigot().sendMessage(textComponent);
                continue;
            }
            player.sendMessage(str);
        }
    }
}
