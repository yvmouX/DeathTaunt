package cn.yvmou.deathtaunt.ui;

import cn.yvmou.deathtaunt.DeathTaunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import dev.neuralnexus.taterlib.inventory.PlayerInventory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainUi implements Listener{

    private DeathTaunt pl;

    public MainUi(DeathTaunt pl) {
        this.pl = pl;
    }


    // 打开配置编辑主界面
    public void openConfigUI(Player player) {
        Inventory configInventory = Bukkit.createInventory(null, 9, "配置编辑");
        FileConfiguration config = pl.getConfig();

        // 显示版本
        ItemStack versionItem = new ItemStack(Material.PAPER);
        ItemMeta versionMeta = versionItem.getItemMeta();
        versionMeta.setDisplayName(ChatColor.GREEN + "版本");
        versionMeta.setLore(Arrays.asList(ChatColor.YELLOW + "当前版本: " + config.getString("version")));
        versionItem.setItemMeta(versionMeta);
        configInventory.setItem(0, versionItem);

        // 显示调试模式(logger)
        ItemStack loggerItem = new ItemStack(Material.LEVER);
        ItemMeta loggerMeta = loggerItem.getItemMeta();
        loggerMeta.setDisplayName(ChatColor.GREEN + "调试模式");
        loggerMeta.setLore(Arrays.asList(ChatColor.YELLOW + "状态: " + config.getBoolean("logger")));
        loggerItem.setItemMeta(loggerMeta);
        configInventory.setItem(1, loggerItem);

        // 显示自定义信息前缀
        ItemStack prefixItem = new ItemStack(Material.NAME_TAG);
        ItemMeta prefixMeta = prefixItem.getItemMeta();
        prefixMeta.setDisplayName(ChatColor.GREEN + "自定义信息前缀");
        prefixMeta.setLore(Arrays.asList(ChatColor.YELLOW + "前缀: " + config.getString("custom_message_prefix")));
        prefixItem.setItemMeta(prefixMeta);
        configInventory.setItem(2, prefixItem);

        // 显示艾特玩家设置
        ItemStack atItem = new ItemStack(Material.BEACON);
        ItemMeta atMeta = atItem.getItemMeta();
        atMeta.setDisplayName(ChatColor.GREEN + "艾特玩家设置");
        boolean atEnable = config.getBoolean("at.enable");
        atMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "启用: " + atEnable,
                ChatColor.YELLOW + "音效: " + config.getString("at.sound")));
        atItem.setItemMeta(atMeta);
        configInventory.setItem(3, atItem);

        // 显示配置检查设置
        ItemStack checkItem = new ItemStack(Material.REDSTONE);
        ItemMeta checkMeta = checkItem.getItemMeta();
        checkMeta.setDisplayName(ChatColor.GREEN + "配置检查设置");
        boolean checkEnable = config.getBoolean("config_check.enable");
        boolean strictMode = config.getBoolean("config_check.strict_mode");
        checkMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "检查: " + checkEnable,
                ChatColor.YELLOW + "严格模式: " + strictMode));
        checkItem.setItemMeta(checkMeta);
        configInventory.setItem(4, checkItem);

        // 显示消息设置（Message部分），点击后进入子界面编辑
        ItemStack messageItem = new ItemStack(Material.BOOK);
        ItemMeta messageMeta = messageItem.getItemMeta();
        messageMeta.setDisplayName(ChatColor.GREEN + "消息设置");
        messageMeta.setLore(Arrays.asList(ChatColor.YELLOW + "点击编辑所有消息"));
        messageItem.setItemMeta(messageMeta);
        configInventory.setItem(8, messageItem);

        player.openInventory(configInventory);
    }

    // 监听玩家在GUI中的点击事件
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
//        // 使用反射获取 InventoryView 的标题，避免直接引用 InventoryView 类型
//        Object view = event.getView();
//        String title = "";
//        try {
//            Method getTitle = view.getClass().getMethod("getTitle");
//            title = (String) getTitle.invoke(view);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //String title = event.getView().getTitle();
        String title = "配置编辑";

        if (title.equals("配置编辑")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            Player player = (Player) event.getWhoClicked();
            // 根据点击的槽位调用不同功能
            switch (slot) {
                case 0:
                    player.sendMessage(ChatColor.AQUA + "你点击了版本项。编辑功能可扩展为聊天输入等方式。");
                    break;
                case 1:
                    player.sendMessage(ChatColor.AQUA + "你点击了调试模式项。");
                    break;
                case 2:
                    player.sendMessage(ChatColor.AQUA + "你点击了自定义信息前缀项。");
                    break;
                case 3:
                    player.sendMessage(ChatColor.AQUA + "你点击了艾特玩家设置项。");
                    break;
                case 4:
                    player.sendMessage(ChatColor.AQUA + "你点击了配置检查设置项。");
                    break;
                case 8:
                    openMessageUI(player);
                    break;
                default:
                    break;
            }
        } else if (title.equals("消息设置")) {
            event.setCancelled(true);
            // 示例：玩家点击某个消息条目后，可做进一步处理（例如打开编辑详情界面）
            int slot = event.getRawSlot();
            playerMessage(event, slot);
        }
    }

    // 打开消息设置界面（编辑 Message 节点）
    public void openMessageUI(Player player) {
        Inventory messageInventory = Bukkit.createInventory(null, 9, "消息设置");
        FileConfiguration config = pl.getConfig();
        ConfigurationSection messageSection = config.getConfigurationSection("Message");
        int index = 0;
        if (messageSection != null) {
            for (String key : messageSection.getKeys(false)) {
                ConfigurationSection msgConfig = messageSection.getConfigurationSection(key);
                ItemStack msgItem = new ItemStack(Material.PAPER);
                ItemMeta msgMeta = msgItem.getItemMeta();
                msgMeta.setDisplayName(ChatColor.AQUA + "消息 " + key);
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.YELLOW + "名称: " + msgConfig.getString("name"));
                lore.add(ChatColor.YELLOW + "分类: " + msgConfig.getString("category"));
                lore.add(ChatColor.YELLOW + "模式: " + msgConfig.getString("mode"));
                lore.add(ChatColor.YELLOW + "音效: " + msgConfig.getString("sound"));
                msgMeta.setLore(lore);
                msgItem.setItemMeta(msgMeta);
                messageInventory.setItem(index, msgItem);
                index++;
            }
        }
        player.openInventory(messageInventory);
    }

    // 示例方法：处理在消息设置界面中的点击操作
    private void playerMessage(InventoryClickEvent event, int slot) {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage(ChatColor.AQUA + "你点击了消息设置中的第 " + (slot+1) + " 项。编辑功能可自行扩展。");
    }


}