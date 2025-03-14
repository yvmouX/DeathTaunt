package cn.yvmou.deathtaunt.listener;

import cn.yvmou.deathtaunt.DeathTaunt;
import cn.yvmou.deathtaunt.utils.ConfigUtils;
import cn.yvmou.deathtaunt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDeathListener implements Listener {
    private final DeathTaunt pl;

    private final boolean isLogger;
    //private boolean isSendKillChat = false;
    private final Map<UUID, Boolean> isSendKillChat = new ConcurrentHashMap<>(); // 默认应为false
    private final String customMessagePrefix;
    private String finallyKey; // 最终随机到的


    public PlayerDeathListener(DeathTaunt deathTaunt) {
        pl = deathTaunt;
        isLogger = pl.getConfig().getBoolean("logger", false);
        customMessagePrefix = pl.getConfig().getString("custom_message_prefix", "");
    }


    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (isSendKillChat.getOrDefault(uuid, false)) {
            //if (isSendKillChat) {
            String message = event.getMessage();
            String message2 = ChatColor.translateAlternateColorCodes('&', message);
            String message3 = "§b@" + message2;
            event.setMessage(message3);
            isSendKillChat.put(uuid, false);
            //isSendKillChat = false;
        } else {
            // AT
            if (pl.getConfig().getBoolean("at.enable")) {
                String message = event.getMessage();
                String newMessage = atPlayer(message, event.getPlayer());
                event.setMessage(newMessage.replace("@", "§b@"));
            }
        }
    }

    // 实现玩艾特功能
    private String atPlayer(String message, Player player) {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player onlinePlayer : onlinePlayers) {
            if (message.contains("@" + onlinePlayer.getName())) {
                if (!message.contains(player.getName())) {
                    String atSound = pl.getConfig().getString("at.sound", "entity.experience_orb.pickup");
                    onlinePlayer.playSound(onlinePlayer.getLocation(), atSound, 1.0f, 1.0f);

                    return message.replace(onlinePlayer.getName(), onlinePlayer.getName() + " §r");
                }

            }
        }
        return message;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deathPlayer = event.getEntity().getPlayer();
        Player killerPlayer = event.getEntity().getKiller();

        event.setDeathMessage("");
        sendMessageAndSound(deathPlayer, killerPlayer);
    }

    // 发送消息和播放音效
    private void sendMessageAndSound(Player deathPlayer, Player killerPlayer) {
        if (deathPlayer != null && killerPlayer != null) {

            Map<String, String> map = new HashMap<>();
            UUID uuid = killerPlayer.getUniqueId();
            String message = randomMessage(killerPlayer.getName(), deathPlayer.getName());
            List<String> allKeys = ConfigUtils.getKeys("Message");
            String mode = pl.getConfig().getString("Message." + finallyKey + ".mode");
            String soundMode = pl.getConfig().getString("Message." + finallyKey + ".sound_mode");

            if (mode != null) {
                switch (mode) {
                    // public 模式，向被杀死的玩家发送消息，所有人可见
                    case "public" -> {
                        if (message.contains("public")) {
                            String newMessage = message.replace("public", "");
                            // 如果自定义前缀为空
                            if (customMessagePrefix.isEmpty()) {
                                isSendKillChat.put(uuid, true);
                                //isSendKillChat = true;
                                // 发送消息
                                killerPlayer.chat(deathPlayer.getName() + " " + newMessage);
                                // 发送音效
                                sendSound(deathPlayer, killerPlayer, map, allKeys, soundMode);
                            } else {
                                // 使用sendMessage为所有玩家发送消息
                                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                                for (Player player : onlinePlayers) {
                                    String resetMessage = newMessage.replace("&", "§");
                                    player.sendMessage(customMessagePrefix + "§b@" + deathPlayer.getName() + " " + resetMessage);
                                }
                                // 发送音效
                                sendSound(deathPlayer, killerPlayer, map, allKeys, soundMode);
                            }
                        }
                    }
                    case "private" -> {
                        // private 模式，向被杀死的玩家发送消息，仅双方可见
                        if (message.contains("private")) {
                            String newMessage = message.replace("private", "");
                            String resetMessage = newMessage.replace("&", "§");
                            // 发送消息
                            deathPlayer.sendMessage(customMessagePrefix + "§b@" + deathPlayer.getName() + " " + resetMessage);
                            killerPlayer.sendMessage(customMessagePrefix + "§b@" + deathPlayer.getName() + " " + resetMessage);
                            // 发送音效
                            sendSound(deathPlayer, killerPlayer, map, allKeys, soundMode);
                        }
                    }
                    case "toDead" -> {
                        // toDead 模式，向被杀死的玩家发送消息，仅对方可见
                        if (message.contains("toDead") || message.contains("todead")) {
                            String newMessage = message.replace("toDead", "");
                            String resetMessage = newMessage.replace("&", "§");
                            // 发送消息
                            deathPlayer.sendMessage(customMessagePrefix + "§b@" + deathPlayer.getName() + " " + resetMessage);
                            // 发送音效
                            sendSound(deathPlayer, killerPlayer, map, allKeys, soundMode);
                        }
                    }
                    default -> {
                        MessageUtils.sendAnsiColorMessage(ChatColor.RED, "未知模式: " + mode + ",请检测仔细配置文件");
                    }
                }
            }
        }
    }

    private void sendSound(Player deathPlayer, Player killerPlayer, Map<String, String> map, List<String> allKeys, String soundMode) {
        for (String key : allKeys) {
            map.put(key, pl.getConfig().getString("Message." + key + ".sound"));
        }
        String atSound = map.getOrDefault(finallyKey, "entity.experience_orb.pickup");
        if (soundMode != null) {
            switch (soundMode) {
                case "toDead" -> {
                    deathPlayer.playSound(deathPlayer.getLocation(), atSound, 1.0f, 1.0f);
                }
                case "toKiller" -> {
                    killerPlayer.playSound(killerPlayer.getLocation(), atSound, 1.0f, 1.0f);
                }
                case "all" -> {
                    deathPlayer.playSound(deathPlayer.getLocation(), atSound, 1.0f, 1.0f);
                    killerPlayer.playSound(killerPlayer.getLocation(), atSound, 1.0f, 1.0f);
                }
                default -> {
                    MessageUtils.sendAnsiColorMessage(ChatColor.RED, "未知音效播放模式: " + soundMode + ",请检测仔细配置文件");
                }
            }
        }
    }


    // 随机消息
    private String randomMessage(String killerPlayer, String deathPlayer) { //
        // 获取配置文件 Message 下所有的自定义键
        List<String> allKeys = ConfigUtils.getKeys("Message");

        // 获取配置文件 Message 下所有的自定义键下的 change 、 list
        Map<String, Double> change = new HashMap<>();
        Map<String, List<String>> list = new HashMap<>();
        for (String key : allKeys) {
            Double probability = pl.getConfig().getDouble("Message." + key + ".change");
            List<String> messages = pl.getConfig().getStringList("Message." + key + ".list");
            change.put(key, probability);
            list.put(key, messages);
        }

        // 总概率
        double totalProbability = 0.0;
        for (double probability : change.values()) {
            totalProbability += probability;
        }

        // 如果总概率为 0 ，提出代码
        if (totalProbability == 0) {
            MessageUtils.sendAnsiColorMessage(ChatColor.RED, "错误：概率和不能等于0");
            return "";
        }

        // 如果总概率不为 1，进行归一化处理
        if (totalProbability != 1.0) {
            if (isLogger) {
                MessageUtils.sendAnsiColorMessage(ChatColor.YELLOW, "警告：概率和不为1，将自动归一化概率");
            }
            for (String key : change.keySet()) {
                change.put(key, change.get(key) / totalProbability);
            }
            totalProbability = 1.0; // 归一化后总概率为1
        }

        // 随机选择消息
        Random random = new Random();
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0; // 累积概率

        for (String key : change.keySet()) {
            cumulativeProbability += change.get(key);
            if (randomValue <= cumulativeProbability) {
                List<String> messages = list.get(key);
                // System.out.println("选择的消息: " + selectedMessage);
                // System.out.println("选择的列表: " + key);
                String selectedMessage = messages.get(random.nextInt(messages.size()));

                if (selectedMessage.contains("%deathPlayer%")) {
                    selectedMessage = selectedMessage.replace("%deathPlayer%", deathPlayer);
                } else if (selectedMessage.contains("%killerPlayer%")) {
                    selectedMessage = selectedMessage.replace("%killerPlayer%", killerPlayer);
                }
                // 获取发送模式
                String mode = pl.getConfig().getString("Message." + key + ".mode");

                // 给选择到的key赋值
                finallyKey = key;

                return selectedMessage + mode;
            }
        }
        return "";
    }

}
