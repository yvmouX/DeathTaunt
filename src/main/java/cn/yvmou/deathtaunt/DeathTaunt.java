package cn.yvmou.deathtaunt;

import cn.yvmou.deathtaunt.listener.PlayerDeathListener;
import cn.yvmou.deathtaunt.tools.CheckConfigTools;
import cn.yvmou.deathtaunt.ui.MainUi;
import cn.yvmou.deathtaunt.utils.ConfigUtils;
import cn.yvmou.deathtaunt.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DeathTaunt extends JavaPlugin {

    public String getVersion() {
        return "1.0.1";
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        ConfigUtils.init(this);
        MessageUtils.init(this);
        CheckConfigTools.init(this);

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new MainUi(this), this);

        // 注册命令执行器和补全器
        CommandManager commandManager = new CommandManager(this);
        Objects.requireNonNull(getCommand("deathtaunt")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("deathtaunt")).setTabCompleter(commandManager);

        CheckConfigTools.checkConfig();

        //System.out.println(ConfigUtils.superGetAllStrings("Message.*.name"));

    }

    @Override
    public void onDisable() {
        MessageUtils.sendAnsiColorMessage(ChatColor.RED, "插件已成功卸载！");
    }
}
