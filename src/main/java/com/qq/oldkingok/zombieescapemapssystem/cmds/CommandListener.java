package com.qq.oldkingok.zombieescapemapssystem.cmds;

import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {
    ZombieEscapeMapsSystem instance;

    public CommandListener(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        instance.getCommand("ze").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("你必须是一个玩家！");
            return true;
        }
        var player = (Player) sender;
        if (args.length == 0) {
            Cmds.HELP.run(player);
            return true;
        }

        for (Cmds value : Cmds.values()) {
            if (value.getLabel().equalsIgnoreCase(args[0])) {
                value.run(player, args);
                return true;
            }
        }
        Cmds.HELP.run(player);
        return true;
    }
}
