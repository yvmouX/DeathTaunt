package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.tools.CheckConfigTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private DeathTaunt pl;

    public ReloadCommand() {
    }

    public ReloadCommand(DeathTaunt deathTaunt) {
        pl = deathTaunt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("deathtaunt.reload")) {
            pl.reloadConfig();
            sender.sendMessage("§f[§bDeathTaunt§f]§a 配置文件已重新加载.");
        } else {
            sender.sendMessage("§f[§bDeathTaunt§f]§c 你没有权限执行此命令.");
        }
        return true;
    }
}
