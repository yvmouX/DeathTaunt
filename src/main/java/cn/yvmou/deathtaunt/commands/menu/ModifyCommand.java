package cn.yvmou.deathtaunt.commands.menu;

import cn.yvmou.deathtaunt.DeathTaunt;
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


        // 所有命令
        for (String category : allCategoryList) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase(category) && args[2].equalsIgnoreCase("set")) {
                    switch (args[3]) {
                        case "name" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".name", args[5]);
                        }
                        case "change" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".change", args[5]);
                        }
                        case "mode" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".mode", args[5]);
                        }
                        case "sound" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".sound", args[5]);
                        }
                        case "sound_mode" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".sound_mode", args[5]);
                        }
                        case "list" -> {
                            sender.sendMessage(ChatColor.GREEN + category + " 的 " + args[3] + "修改成功！");
                            return true;
                            //pl.getConfig().set("Message." + category + ".list", Arrays.asList(args[5].split(",")));
                        }
                    }
                }

            }
        }

        return false;
    }
}
