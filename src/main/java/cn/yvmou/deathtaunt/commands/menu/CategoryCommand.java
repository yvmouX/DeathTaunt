package cn.yvmou.deathtaunt.commands.menu;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.PluginInfo;
import cn.yvmou.deathtaunt.commands.GetConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class CategoryCommand implements CommandExecutor {
    private final DeathTaunt pl;
    public CategoryCommand(DeathTaunt pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GetConfig getConfig = new GetConfig(pl);
        getConfig.getAll();
        Set<String> allCategoryList = getConfig.getAllCategoryList();
        Map<String, String> allName = getConfig.getAllName();
        Map<String, String> allChange = getConfig.getAllChange();
        Map<String, String> allMode = getConfig.getAllMode();
        Map<String, String> allSound = getConfig.getAllSound();
        Map<String, String> allSoundMode = getConfig.getAllSoundMode();
        Map<String, List<String>> allListMessage = getConfig.getAllListMessage();

        String nowCategory;

        if (args.length != 2 || !args[0].equalsIgnoreCase("category")) {
            sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (allCategoryList.contains(args[1])) {
            nowCategory = args[1];
        } else {
            sender.sendMessage(PluginInfo.getPluginPrefix() + ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (args[1].equalsIgnoreCase(nowCategory)) {
            openCategoryMenu(sender, args[1], allName, allChange, allMode, allSound, allSoundMode, allListMessage);
            System.out.println("成功");
            return true;
        }

        return false;
    }

    // openCategoryMenu
    private void openCategoryMenu(CommandSender sender, String category, Map<String, String> allName, Map<String, String> allChange, Map<String, String> allMode, Map<String, String> allSound, Map<String, String> allSoundMode, Map<String, List<String>> allListMessage) {
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GOLD + "   DeathTaunt 菜单  ");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GREEN + "当前位于：" + ChatColor.WHITE + category);

        sendClickableMessage(
                (Player) sender,
                "名称：",
                "",
                allName.get(category),
                "",
                "",
                "/deathtaunt modify " + category + " name",
                "修改name",
                false

        );
        sendClickableMessage(
                (Player) sender,
                "权重：",
                "",
                allChange.get(category),
                "",
                "",
                "/deathtaunt modify " + category + " set change",
                "修改权重",
                false

        );
        sendClickableMessage(
                (Player) sender,
                "模式：",
                "",
                allMode.get(category),
                "",
                "",
                "/deathtaunt modify " + category + " set mode",
                "修改模式",
                false

        );
        sendClickableMessage(
                (Player) sender,
                "声音：",
                "",
                allSound.get(category),
                "",
                "",
                "/deathtaunt modify " + category + " set sound",
                "修改声音",
                false

        );
        sendClickableMessage(
                (Player) sender,
                "声音模式：",
                "",
                allSoundMode.get(category),
                "",
                "",
                "/deathtaunt modify " + category + " set soundmode",
                "修改声音模式",
                false

        );
        sender.sendMessage(ChatColor.BLUE + "消息列表：");
        for (String listMsg : allListMessage.get(category)) {
            sendClickableMessage(
                    (Player) sender,
                    "",
                    "【",
                    listMsg,
                    "】",
                    "",
                    "",
                    "不支持游戏内修改",
                    false
            );
        }
    }

    // 发送可点击的聊天消息
    public void sendClickableMessage(Player sender, String prefixMsg, String staticLeft, String clickableText, String staticRight, String suffixMsg, String command, String hoverText, Boolean runCmd) {

        TextComponent prefix = new TextComponent(prefixMsg);
        TextComponent leftComponent = new TextComponent(staticLeft);
        TextComponent middleComponent = new TextComponent(clickableText);
        TextComponent rightComponent = new TextComponent(staticRight);
        TextComponent suffix = new TextComponent(suffixMsg);

        prefix.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        leftComponent.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        rightComponent.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        suffix.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        if (runCmd) {
            middleComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            middleComponent.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    command
            ));
        } else {
            middleComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            middleComponent.setClickEvent(new ClickEvent(
                    ClickEvent.Action.SUGGEST_COMMAND,
                    command
            ));
        }

        middleComponent.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(hoverText).create()
        ));

        sender.spigot().sendMessage(
                prefix,
                leftComponent,
                middleComponent,
                rightComponent,
                suffix
        );
    }
}
