package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.ui.MainUi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenMainUiCommand implements CommandExecutor {
    private MainUi mainUi;

    public OpenMainUiCommand() {
    }

    public OpenMainUiCommand(MainUi mainUi) {
        this.mainUi = mainUi;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("deathtaunt.open")) {
            mainUi.openConfigUI((Player) sender);
        } else {
            sender.sendMessage("§f[§bDeathTaunt§f]§c 你没有权限执行此命令.");
        }
        return true;
    }
}
