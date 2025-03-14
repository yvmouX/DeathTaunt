package cn.yvmou.deathtaunt.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GOLD  + "   DeathTaunt 帮助  ");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt list " + ChatColor.GRAY + "打开菜单");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt reload " + ChatColor.GRAY + "重载插件");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt help " + ChatColor.GRAY + "查看帮助");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        return true;
    }
}
