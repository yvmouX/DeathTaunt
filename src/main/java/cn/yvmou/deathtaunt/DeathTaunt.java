package cn.yvmou.deathtaunt;

import cn.yvmou.deathtaunt.commands.GetConfig;
import cn.yvmou.deathtaunt.listener.PlayerDeathListener;
import cn.yvmou.deathtaunt.utils.ConfigUtils;
import cn.yvmou.deathtaunt.utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class DeathTaunt extends JavaPlugin {


    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        ConfigUtils.init(getConfig());
        MessageUtils.init(getLogger());
        //CheckConfig.init(this);

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        // 注册命令执行器和补全器
        CommandManager commandManager = new CommandManager(this);
        Objects.requireNonNull(getCommand("deathtaunt")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("deathtaunt")).setTabCompleter(commandManager);

        CheckConfig.checkConfig(this);


    }



//    @Override
//    public void onDisable() {
//        MessageUtils.sendAnsiColorMessage(ChatColor.RED, "插件已成功卸载！");
//    }
}
