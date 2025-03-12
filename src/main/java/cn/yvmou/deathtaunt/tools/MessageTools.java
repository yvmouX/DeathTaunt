package cn.yvmou.deathtaunt.tools;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageTools {
    /**
     * 发送帮助信息
     * @param sender 命令执行者
     */
    public static void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GOLD  + "   DeathTaunt 帮助  ");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt open " + ChatColor.GRAY + "打开菜单");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt reload " + ChatColor.GRAY + "重载插件");
        sender.sendMessage(ChatColor.WHITE + "/deathtaunt help " + ChatColor.GRAY + "查看帮助");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
    }
}
