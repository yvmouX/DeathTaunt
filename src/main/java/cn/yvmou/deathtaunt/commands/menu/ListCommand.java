package cn.yvmou.deathtaunt.commands.menu;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.commands.GetConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class ListCommand implements CommandExecutor, Listener {
    private final DeathTaunt pl;

    public ListCommand(DeathTaunt pl) {
        this.pl = pl;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GetConfig getConfig = new GetConfig(pl);

        getConfig.getAll();
        Set<String> allCategoryList = getConfig.getAllCategoryList();


        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GOLD + "   DeathTaunt 菜单  ");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GREEN + "当前位于：" + ChatColor.WHITE + "所有分类");
        for (
                String category : allCategoryList) {
            sendClickableMessage(
                    (Player) sender,
                    "",
                    "【",
                    category,
                    "】",
                    "",
                    "/deathtaunt category " + category,
                    category,
                    true
            );

        }
        return true;
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
