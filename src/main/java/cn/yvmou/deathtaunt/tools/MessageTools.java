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
        sender.sendMessage(ChatColor.WHITE + "/arktools setworldborder " + ChatColor.GRAY + "设置世界边界");
        sender.sendMessage(ChatColor.WHITE + "/arktools deathpunish setmaxhealth " + ChatColor.GRAY + "设置玩家生命上限");
        sender.sendMessage(ChatColor.WHITE + "/arktools deathpunish add " + ChatColor.GRAY + "增加玩家生命上限");
        sender.sendMessage(ChatColor.WHITE + "/arktools deathpunish get " + ChatColor.GRAY + "获取玩家生命上限");
        sender.sendMessage(ChatColor.WHITE + "/arktools hideOnMap get " + ChatColor.GRAY + "获取隐藏地图物品");
        sender.sendMessage(ChatColor.WHITE + "/arktools reload " + ChatColor.GRAY + "重载插件");
        sender.sendMessage(ChatColor.WHITE + "/arktools help " + ChatColor.GRAY + "查看帮助");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
    }
}
