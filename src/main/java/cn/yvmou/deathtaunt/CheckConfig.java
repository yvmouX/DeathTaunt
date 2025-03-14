package cn.yvmou.deathtaunt;

import cn.yvmou.deathtaunt.utils.ConfigUtils;
import cn.yvmou.deathtaunt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.*;


public class CheckConfig {

    private CheckConfig() {
        throw new AssertionError("无法实例化 CheckConfig");
    }

//    public static void checkConfig2(DeathTaunt pl, String path, String Default, String error) {
//        if (pl.getConfig().getString(path) == null) {
//            pl.getConfig().set(path, Default);
//            pl.getLogger().warning(error);
//            pl.saveConfig();
//        }
//    }

    public static void checkConfig(DeathTaunt pl) {

        // 严格检测模式检查
        boolean strictMode = pl.getConfig().getBoolean("config_check.strict_mode", true);
        boolean hasErrors = false;
        boolean hasVersionErrors = false;
        boolean isExist = true;

        File pluginFolder = pl.getDataFolder();
        File configFile = new File(pluginFolder, "config.yml");

        MessageUtils.sendAnsiColorMessage(ChatColor.GREEN, "插件正在启动！");

        if (strictMode) {
            MessageUtils.sendAnsiColorMessage(ChatColor.BLUE, "已启用严格检查！");
        } else {
            MessageUtils.sendAnsiColorMessage(ChatColor.BLUE, "未启用严格检查！");
        }
        MessageUtils.sendAnsiColorMessage(ChatColor.BLUE, "开始检验配置文件完整性！");

        if (!configFile.exists()) {
            MessageUtils.sendAnsiColorMessage(ChatColor.RED, "检测到配置文件缺失，以为您自动生成配置文件！");
            pl.saveDefaultConfig();
            pl.saveConfig();
            isExist = false;
        }

        if (isExist) {
            // 检测版本
            if (!Objects.equals(pl.getConfig().getString("version"), PluginInfo.getVersion())) {
                hasVersionErrors = true;
            }

            List<String> requiredKeys = new ArrayList<>();
            requiredKeys.add("name");
            requiredKeys.add("category");
            requiredKeys.add("mode");
            requiredKeys.add("change");
            requiredKeys.add("list");
            requiredKeys.add("sound");
            requiredKeys.add("sound_mode");

            if (!ConfigUtils.checkConfigKey("version", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: version");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("logger", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: logger");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("custom_message_prefix", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: custom_message_prefix");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("at", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: at");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("at.enable", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: at.enable");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("at.sound", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: at.sound");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("config_check", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: config_check");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("config_check.enable", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: config_check.enable");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("config_check.strict_mode", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: config_check.strict_mode");
                hasErrors = true;
            }
            if (!ConfigUtils.checkConfigKey("Message", false)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: Message");
                hasErrors = true;
            }
            if (!ConfigUtils.checkCustomConfigKey("Message")) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: Message 下 自定义键");
                hasErrors = true;
            }
            if (!ConfigUtils.checkBelowCustomConfigKey("Message", requiredKeys)) {
                MessageUtils.sendAnsiColorMessage(ChatColor.RED, "配置文件缺少路径: Message 下 自定义键 的子键");
                hasErrors = true;
            }
        }
        if (hasErrors && strictMode) {
            MessageUtils.sendAnsiColorMessage(ChatColor.RED, "请仔细检查配置文件或者删除配置文件重启服务器！");
            Bukkit.getPluginManager().disablePlugin(pl);
        }
        if (hasVersionErrors && strictMode) {
            MessageUtils.sendAnsiColorMessage(ChatColor.RED, "检测到配置文件版本过低！请检测更新日志后修改版本号 或 删除配置文件重启服务器");
            Bukkit.getPluginManager().disablePlugin(pl);
        }
        if (!hasErrors && !hasVersionErrors) {
            MessageUtils.sendAnsiColorMessage(ChatColor.BLUE, "未发现问题！");
            MessageUtils.sendAnsiColorMessage(ChatColor.GREEN, "插件已启用！");
        }
    }

}
