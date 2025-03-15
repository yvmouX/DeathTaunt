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

import java.util.List;
import java.util.Map;
import java.util.Set;

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



        for (String category : allCategoryList) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("category") && args[1].equalsIgnoreCase(category)) {
                    openCategoryMenu(sender, category, allName, allChange, allMode, allSound, allSoundMode, allListMessage);
                    System.out.println("0");
                    return true;
                } else System.out.println("1");
            } else System.out.println("2");
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
                "/deathtaunt list set " + category + " name",
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
                "/deathtaunt list category " + category + " set change",
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
                "/deathtaunt list category " + category + " set mode",
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
                "/deathtaunt list category " + category + " set sound",
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
                "/deathtaunt list category " + category + " set soundmode",
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
                    "/deathtaunt list category " + category + " set message",
                    "修改该消息",
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
