package cn.yvmou.deathtaunt.commands.menu;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.PluginInfo;
import cn.yvmou.deathtaunt.commands.GetConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class ModifyCommand implements CommandExecutor {
    private final DeathTaunt pl;

    public ModifyCommand(DeathTaunt pl) {
        this.pl = pl;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GetConfig getConfig = new GetConfig(pl);
        getConfig.getAll();
        Set<String> allCategoryList = getConfig.getAllCategoryList();
        Map<String, String> categoryKey = getConfig.getAllCategoryKey();

        String nowCategory;

        if (args.length != 5 || !args[0].equalsIgnoreCase("modify")) {
            sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (allCategoryList.contains(args[1])) {
            nowCategory = args[1];
        } else {
            sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (!args[1].equalsIgnoreCase(nowCategory) || !args[2].equalsIgnoreCase("set")) {
            sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        switch (args[3]) {
            case "name" -> {
                pl.getConfig().set("Message." + categoryKey.get(nowCategory) + ".name", args[4]);
                pl.saveConfig();
                sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.GREEN + "已将 " + ChatColor.GRAY + nowCategory + ChatColor.GREEN + " 下所有的 " +  ChatColor.GRAY + args[3] + ChatColor.GREEN + " 修改为 " + ChatColor.YELLOW + args[4]);
                return true;
            }
            case "change" -> {
                pl.getConfig().set("Message." + categoryKey.get(nowCategory) + ".change", args[4]);
                pl.saveConfig();
                sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.GREEN + "已将 " + ChatColor.GRAY + nowCategory + ChatColor.GREEN + " 下所有的 " +  ChatColor.GRAY + args[3] + ChatColor.GREEN + " 修改为 " + ChatColor.YELLOW + args[4]);
                return true;
            }
            case "mode" -> {
                pl.getConfig().set("Message." + categoryKey.get(nowCategory) + ".mode", args[4]);
                pl.saveConfig();
                sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.GREEN + "已将 " + ChatColor.GRAY + nowCategory + ChatColor.GREEN + " 下所有的 " +  ChatColor.GRAY + args[3] + ChatColor.GREEN + " 修改为 " + ChatColor.YELLOW + args[4]);
                return true;
            }
            case "sound" -> {
                pl.getConfig().set("Message." + categoryKey.get(nowCategory) + ".sound", args[4]);
                pl.saveConfig();
                sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.GREEN + "已将 " + ChatColor.GRAY + nowCategory + ChatColor.GREEN + " 下所有的 " +  ChatColor.GRAY + args[3] + ChatColor.GREEN + " 修改为 " + ChatColor.YELLOW + args[4]);
                return true;
            }
            case "sound_mode" -> {
                pl.getConfig().set("Message." + categoryKey.get(nowCategory) + ".sound_mode", args[4]);
                pl.saveConfig();
                sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.GREEN + "已将 " + ChatColor.GRAY + nowCategory + ChatColor.GREEN + " 下所有的 " +  ChatColor.GRAY + args[3] + ChatColor.GREEN + " 修改为 " + ChatColor.YELLOW + args[4]);
                return true;
            }
        }
        return false;
    }
}
