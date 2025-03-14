package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.PluginInfo;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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

        List<String> allCategoryList = new ArrayList<>(); // 所有的分类
        // key: 分类 vault: 模块名
        Map<String, String> allName = new HashMap<>();
        Map<String, String> allChange = new HashMap<>();
        Map<String, String> allMode = new HashMap<>();
        Map<String, String> allSound = new HashMap<>();
        Map<String, String> allSoundMode = new HashMap<>();
        Map<String, List<String>> allListMessage = new HashMap<>();

        ConfigurationSection configurationSection = pl.getConfig().getConfigurationSection("Message");

        if (configurationSection == null) {
            sender.sendMessage(PluginInfo.getPluginPrefix() + "&c配置文件错误，请检查配置文件");
            return true;
        }

        for (String customListKey : configurationSection.getKeys(false)) {
            Set<String> allKeys = Objects.requireNonNull(pl.getConfig().getConfigurationSection("Message." + customListKey)).getKeys(true);

            Map<String, String> allVault = new HashMap<>();
            Map<String, List<String>> allMessage = new HashMap<>();

            // 分别打印每个模块下的配置项键的值
            for (String key : allKeys) {
                switch (key) {
                    case "name", "category", "change", "mode", "sound", "sound_mode" -> {
                        String vault = pl.getConfig().getString("Message." + customListKey + "." + key);
                        allVault.put(key, vault);
                    }
                    case "list" -> {
                        List<String> listMsg = pl.getConfig().getStringList("Message." + customListKey + "." + key);
                        allMessage.put(key, listMsg);
                    }
                }
            }
            String nowCategory = allVault.get("category");

            allCategoryList.add(nowCategory);

            allName.put(nowCategory, allVault.get("name"));
            allChange.put(nowCategory, allVault.get("change"));
            allMode.put(nowCategory, allVault.get("mode"));
            allSound.put(nowCategory, allVault.get("sound"));
            allSoundMode.put(nowCategory, allVault.get("sound_mode"));
            allListMessage.put(nowCategory, allMessage.get("list"));
        }


        // 注册所有分类打开命令
        for (String category : allCategoryList) {
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (args[1].equalsIgnoreCase("category") && args[2].equalsIgnoreCase(category)) {

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
                                "",
                                "修改name"

                        );
                        sendClickableMessage(
                                (Player) sender,
                                "权重：",
                                "",
                                allChange.get(category),
                                "",
                                "",
                                "",
                                "修改权重"

                        );
                        sendClickableMessage(
                                (Player) sender,
                                "模式：",
                                "",
                                allMode.get(category),
                                "",
                                "",
                                "",
                                "修改模式"

                        );
                        sendClickableMessage(
                                (Player) sender,
                                "声音：",
                                "",
                                allSound.get(category),
                                "",
                                "",
                                "",
                                "修改声音"

                        );
                        sendClickableMessage(
                                (Player) sender,
                                "声音模式：",
                                "",
                                allSoundMode.get(category),
                                "",
                                "",
                                "",
                                "修改声音模式"

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
                                    "修改该消息"
                            );
                        }

                        return true;
                    }
                }
            }
        }

        // 所有分类
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GOLD + "   DeathTaunt 菜单  ");
        sender.sendMessage(ChatColor.WHITE + "-------------------");
        sender.sendMessage(ChatColor.GREEN + "当前位于：" + ChatColor.WHITE + "所有分类");
        for (String category : allCategoryList) {
            sendClickableMessage(
                    (Player) sender,
                    "",
                    "【",
                    category,
                    "】",
                    "",
                    "/dt list category " + category,
                    category
            );

        }
        return true;
    }

    // 发送可点击的聊天消息
    public void sendClickableMessage(Player sender, String prefixMsg, String staticLeft, String clickableText, String staticRight, String suffixMsg, String command, String hoverText) {

        TextComponent prefix = new TextComponent(prefixMsg);
        TextComponent leftComponent = new TextComponent(staticLeft);
        TextComponent middleComponent = new TextComponent(clickableText);
        TextComponent rightComponent = new TextComponent(staticRight);
        TextComponent suffix = new TextComponent(suffixMsg);

        prefix.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        leftComponent.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        rightComponent.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        suffix.setColor(net.md_5.bungee.api.ChatColor.BLUE);

        middleComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        middleComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                command
        ));
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
