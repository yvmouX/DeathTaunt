package cn.yvmou.deathtaunt;

import cn.yvmou.deathtaunt.commands.HelpCommand;
import cn.yvmou.deathtaunt.commands.OpenMainUiCommand;
import cn.yvmou.deathtaunt.commands.ReloadCommand;
import cn.yvmou.deathtaunt.ui.MainUi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter {
    private DeathTaunt pl;
    private final Map<String, CommandExecutor> commands = new HashMap<>();

    public CommandManager() {
    }

    public CommandManager(DeathTaunt deathTaunt) {
        pl = deathTaunt;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", new HelpCommand());
        commands.put("reload", new ReloadCommand(pl));
        commands.put("open", new OpenMainUiCommand(new MainUi(pl)));
    }

    // 执行命令
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            // 子命令处理
            CommandExecutor executor = commands.get(args[0].toLowerCase());
            // 执行子命令
            if (executor != null) {
                return executor.onCommand(sender, command, label, args);
            } else {
                sender.sendMessage("§f[§bDeathTaunt§f] §c未知子命令.");
                return false;
            }
        } else {
            // 发送帮助信息
            new HelpCommand().onCommand(sender, command, label, args);
        }
        return false;
    }

    // Tab补全
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("help", "reload", "open");
        }
        return List.of();
    }
}
