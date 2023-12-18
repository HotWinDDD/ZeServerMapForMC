package com.qq.oldkingok.zombieescapemapssystem.cmds;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum Cmds {
    HELP("help", "显示该插件的指令列表",  new CmdHelp()),
    FAVORITE("favorite", "通过关键词订阅服务器", new CmdFavorite()),
    REMOVE("remove", "删除关键词", new CmdRemove()),
    SERVER("server", "服务器列表", new CmdServer()),
    TOGGLE("toggle", "切换是否提醒", new CmdToggle());

    @Getter
    final String label;
    final String description;
    final CmdRunnable runnable;

    Cmds(String label, String description, CmdRunnable runnable) {
        this.label = label;
        this.description = description;
        this.runnable = runnable;
    }

    public void run(Player player, String... originalArgs) {
        var args = originalArgs;
        String[] newArgs = null;
        if (originalArgs.length == 0) {
            HELP.runnable.run(player);
            return;
        }

        if (originalArgs.length == 1) {
            newArgs = new String[0];
        }else {
            newArgs = Arrays.copyOfRange(originalArgs, 1, originalArgs.length);
        }

        if (!runnable.run(player, newArgs)) {
            HELP.runnable.run(player);
        }
    }
}
