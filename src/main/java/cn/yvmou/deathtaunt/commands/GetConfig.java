package cn.yvmou.deathtaunt.commands;

import cn.yvmou.deathtaunt.DeathTaunt;

import java.util.*;

public class GetConfig {
    private final DeathTaunt pl;
    public GetConfig(DeathTaunt pl) {
        this.pl = pl;
    }

    // 存储所有的分类
    private Set<String> allCategoryList = new HashSet<>(); // 所有的分类

    // 存储分类-属性键值 映射关系
    private Map<String, String> allName = new HashMap<>();
    private Map<String, String> allChange = new HashMap<>();
    private Map<String, String> allMode = new HashMap<>();
    private Map<String, String> allSound = new HashMap<>();
    private Map<String, String> allSoundMode = new HashMap<>();
    private Map<String, List<String>> allListMessage = new HashMap<>();

    public void getAll() {
        // 获取 属性键-属性值 映射关系
        Map<String, String> allVault = new HashMap<>();
        Map<String, List<String>> allMessage = new HashMap<>();
        for (String key : (Objects.requireNonNull(pl.getConfig().getConfigurationSection("Message"))).getKeys(false)) {
            for (String key2 : Objects.requireNonNull(pl.getConfig().getConfigurationSection("Message." + key)).getKeys(false)) {
                switch (key2) {
                    case "name", "category", "change", "mode", "sound", "sound_mode" -> {
                        String vault = pl.getConfig().getString("Message." + key + "." + key2);
                        allVault.put(key2, vault);
                    }
                    case "list" -> {
                        List<String> listMsg = pl.getConfig().getStringList("Message." + key + "." + key2);
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
    }

    public Set<String> getAllCategoryList() {
        return allCategoryList;
    }

    public Map<String, String> getAllName() {
        return allName;
    }

    public Map<String, String> getAllChange() {
        return allChange;
    }

    public Map<String, String> getAllMode() {
        return allMode;
    }

    public Map<String, String> getAllSound() {
        return allSound;
    }

    public Map<String, String> getAllSoundMode() {
        return allSoundMode;
    }

    public Map<String, List<String>> getAllListMessage() {
        return allListMessage;
    }
}
