package com.qq.oldkingok.zombieescapemapssystem.cmds;

import org.bukkit.entity.Player;

public interface CmdRunnable {
    /**
     * 执行命令
     * @param player
     * @param args
     * @return 执行是否成功，否则输出帮助信息
     */
    public boolean run(Player player, String... args);
}
