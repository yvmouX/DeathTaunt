package cn.yvmou.deathtaunt.utils;

import cn.yvmou.deathtaunt.DeathTaunt;
import org.bukkit.ChatColor;

import java.util.logging.Logger;


public class MessageUtils {

    private static Logger logger;

    private MessageUtils() {
        throw new UnsupportedOperationException("无法实例化 MessageUtils");
    }

    /**
     * 初始化配置工具类，必须在插件加载时调用
     *
     * @param main        DeathTaunt 插件实例
     */
    public static void init(DeathTaunt main) {
        if (main == null) {
            throw new IllegalArgumentException("初始化参数不能为 null");
        }
        logger = main.getLogger();
    }


    /**
     * 将 Minecraft 颜色代码转换为 ANSI 颜色代码
     *
     * @param color 基础颜色
     * @return 返回 ANSI 颜色代码
     */
    public static String colorToAnsi(ChatColor color) {
        return switch (color) {
            case BLACK -> "\u001B[30m";
            case DARK_BLUE -> "\u001B[34m";
            case DARK_GREEN -> "\u001B[32m";
            case DARK_AQUA -> "\u001B[36m";
            case DARK_RED -> "\u001B[31m";
            case DARK_PURPLE -> "\u001B[35m";
            case GOLD -> "\u001B[33m";
            case GRAY -> "\u001B[37m";
            case DARK_GRAY -> "\u001B[90m";
            case BLUE -> "\u001B[94m";
            case GREEN -> "\u001B[92m";
            case AQUA -> "\u001B[96m";
            case RED -> "\u001B[91m";
            case LIGHT_PURPLE -> "\u001B[95m";
            case YELLOW -> "\u001B[93m";
            case WHITE -> "\u001B[97m";
            default -> "";
        };
    }

    /**
     * 转换消息中的 Minecraft 颜色代码为 ANSI 颜色代码
     * @param baseColor 基础颜色（可为null）
     * @param message 原始消息
     * @return 带 ANSI 颜色代码的消息
     */
    public static String resetAnsiColorMessage(ChatColor baseColor, String message) {
        if (message == null) return "";

        String processed = message
                .replace("§0", "\u001B[30m")
                .replace("§1", "\u001B[34m")
                .replace("§2", "\u001B[32m")
                .replace("§3", "\u001B[36m")
                .replace("§4", "\u001B[31m")
                .replace("§5", "\u001B[35m")
                .replace("§6", "\u001B[33m")
                .replace("§7", "\u001B[37m")
                .replace("§8", "\u001B[90m")
                .replace("§9", "\u001B[94m")
                .replace("§a", "\u001B[92m")
                .replace("§b", "\u001B[96m")
                .replace("§c", "\u001B[91m")
                .replace("§d", "\u001B[95m")
                .replace("§e", "\u001B[93m")
                .replace("§f", "\u001B[97m")
                .replace("§r", "\u001B[0m");

        String ansiBase = (baseColor != null) ? colorToAnsi(baseColor) : ""; // 如果 baseColor 为 null,赋值 "" ，否则执行colorToAnsi(baseColor)方法
        return ansiBase + processed + "\u001B[0m"; // 确保消息末尾重置颜色
    }

    /**
     * 发送使用 ANSI 颜色代码的控制台日志
     *
     * @param color   基础颜色
     * @param message 消息内容
     */
    public static void sendAnsiColorMessage(ChatColor color, String message) {
        logger.info(resetAnsiColorMessage(color, message));
    }
}
