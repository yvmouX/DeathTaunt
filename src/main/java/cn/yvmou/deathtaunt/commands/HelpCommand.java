package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.tools.MessageTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("deathtaunt.help")) {
            MessageTools.sendHelpMessage(sender);
        } else {
            sender.sendMessage("§f[§bDeathTaunt§f]§c 你没有权限执行此命令.");
        }
        return true;
    }
}
