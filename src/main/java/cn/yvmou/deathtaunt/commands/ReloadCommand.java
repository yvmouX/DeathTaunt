package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.DeathTaunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final DeathTaunt pl;

    public ReloadCommand(DeathTaunt deathTaunt) {
        pl = deathTaunt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        pl.reloadConfig();
        sender.sendMessage("§f[§bDeathTaunt§f]§a 配置文件已重新加载.");
        return true;
    }
}
