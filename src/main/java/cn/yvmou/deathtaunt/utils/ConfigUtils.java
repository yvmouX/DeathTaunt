package cn.yvmou.deathtaunt.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ConfigUtils {
    private ConfigUtils() {
        throw new UnsupportedOperationException("无法实例化 ConfigUtils");
    }

    private static FileConfiguration config;

    /**
     * 初始化配置工具类，必须在插件加载时调用
     * @param config FileConfiguration 对象
     */
    public static void init(FileConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("配置不能为 null");
        }
        ConfigUtils.config = config;
    }

    /**
     * 检查固定键是否存在
     *
     * @param path         固定键路径
     * @param errorMessage 是否显示错误消息
     * @return 是否存在
     */
    public static boolean checkConfigKey(String path, boolean errorMessage) {
        if (config == null) {
            throw new IllegalArgumentException("ConfigUtils 未初始化");
        }

        boolean exists = config.isSet(path);
        if (!exists && errorMessage) {
            MessageUtils.resetAnsiColorMessage(ChatColor.RED, "该键不存在或没有赋值: " + path);
        }
        return exists;
    }

    /**
     * 检测自定义键是否存在
     *
     * @param path 父路径
     * @return 是否存在
     */
    public static boolean checkCustomConfigKey(String path) {
        return !getKeys(path).isEmpty();
    }

    /**
     * 检测自定义键下的子键是否存在
     *
     * @param path    父路径的父路径
     * @param allKeys 需要检查的子键列表
     * @return 是否全部存在
     */
    public static boolean checkBelowCustomConfigKey(String path, List<String> allKeys) {
        if (allKeys == null || allKeys.isEmpty()) {
            return false;
        }

        List<String> existingKeys = getKeys(path);

        if (existingKeys.isEmpty()) {
            return false;
        }

        for (String key : existingKeys) {
            List<String> nowExistingKeys = getKeys(path + "." + key);
            // 检测 existingKeys 是否包含 allKeys
            if (!new HashSet<>(nowExistingKeys).containsAll(allKeys)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 忽略配置路径的指定层级（使用 * 通配符），获取到值
     * @param path 配置路径，例如 "key1.*.key3.*.key5"
     * @return 返回所有匹配路径下的值
     */

    public static List<String> superGetAllStrings(String path) {
        List<String> values = new ArrayList<>();
        fetchValuesRecursive(config, path.split("\\."), 0, "", values);
        return values;
    }
    private static void fetchValuesRecursive(ConfigurationSection section, String[] keys, int depth, String currentPath, List<String> values) {
        if (depth >= keys.length) {
            // 到达最后一层，尝试获取值
            String value = config.getString(currentPath);
            if (value != null) {
                values.add(value);
            }
            return;
        }

        String key = keys[depth];

        if ("*".equals(key)) {
            // 通配符，遍历当前层的所有子节点
            if (section != null) {
                for (String subKey : section.getKeys(false)) {
                    ConfigurationSection nextSection = section.getConfigurationSection(subKey);
                    fetchValuesRecursive(nextSection, keys, depth + 1, currentPath.isEmpty() ? subKey : currentPath + "." + subKey, values);
                }
            }
        } else {
            // 普通 key，继续深入
            ConfigurationSection nextSection = section != null ? section.getConfigurationSection(key) : null;
            fetchValuesRecursive(nextSection, keys, depth + 1, currentPath.isEmpty() ? key : currentPath + "." + key, values);
        }
    }
//    public static String superGetString(String path) {
//        //  key1.key2.key3.key4
//        // 获取路径的第一个层级
//        int dotIndex = path.indexOf(".");
//        String key1 = null;
//        if (dotIndex != -1) {
//            key1 = path.substring(0, dotIndex);
//        }
//        // 获取路径的最后一个层级
//        int lastDotIndex = path.lastIndexOf(".");
//        String key2 = path.substring(lastDotIndex + 1);
//
//        ConfigurationSection section = config.getConfigurationSection(key1);
//        String value = null;
//        if (section != null) {
//            for (String subKey : section.getKeys(false)) {
//                value = section.getString(subKey + ".key2");
//                return value;
//            }
//        }
//        return value;
//    }

    /**
     * 获取路径下的键集合
     *
     * @param path 配置路径
     * @return 键集合，如果不存在则返回空列表
     */
    public static List<String> getKeys(String path) {
        if (checkConfigKey(path, false)) {
            Set<String> keys = Objects.requireNonNull(config.getConfigurationSection(path)).getKeys(false); // 确保 keys 不为 null
            return new ArrayList<>(keys);
        } else {
            return new ArrayList<>();
        }
    }

}
