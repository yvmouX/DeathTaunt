package cn.yvmou.deathtaunt;

import cn.yvmou.deathtaunt.commands.HelpCommand;
import cn.yvmou.deathtaunt.commands.ListCommand;
import cn.yvmou.deathtaunt.commands.ReloadCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final DeathTaunt pl;

    public CommandManager(DeathTaunt deathTaunt) {
        this.pl = deathTaunt;

        registerCommands();
    }

    private final Map<String, CommandExecutor> commands = new HashMap<>();
    private final Map<String, String> commandsPerms = new HashMap<>();
    private final Map<String, Boolean> commandsDefault = new HashMap<>();
    private final Map<String, Boolean> multilevelCommands = new HashMap<>();

    private void registerCommands() {
        commands.put("list", new ListCommand(pl));
        commandsPerms.put("list", "deathtaunt.list");
        commandsDefault.put("list", false);
        multilevelCommands.put("list", true); // 多级命令

        commands.put("reload", new ReloadCommand(pl));
        commandsPerms.put("reload", "deathtaunt.reload");
        commandsDefault.put("reload", false);
        multilevelCommands.put("reload", false);

        commands.put("help", new HelpCommand());
        commandsPerms.put("help", "deathtaunt.help");
        commandsDefault.put("help", true);
        multilevelCommands.put("help", false);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 处理一个参数的命令
        if (args.length >= 1) {
            String perm = commandsPerms.get(args[0].toLowerCase());
            Boolean defaultPerm = commandsDefault.get(args[0].toLowerCase());
            if (perm != null && defaultPerm != null && !defaultPerm && !sender.hasPermission(perm)) {
                sender.sendMessage(PluginInfo.getPluginPrefix() + "§c你没有权限执行此命令." + " §7" + perm);
                return false;
            }
            CommandExecutor executor = commands.get(args[0].toLowerCase());
            Boolean multilevelCommand = multilevelCommands.get(args[0].toLowerCase());
            if (multilevelCommand == null || executor == null) {
                sender.sendMessage(PluginInfo.getPluginPrefix() + "§c未知子命令.");
                return false;
            }
            if (!multilevelCommand) {
                if (args.length == 1) {
                    executor.onCommand(sender, command, label, args);
                } else sender.sendMessage(PluginInfo.getPluginPrefix() + "§c未知子命令.");
            } else executor.onCommand(sender, command, label, args);
        } else {
            if (sender.hasPermission(commandsPerms.get("help")) || commandsDefault.get("help")) {
                return new HelpCommand().onCommand(sender, command, label, args);
            } else
                sender.sendMessage(PluginInfo.getPluginPrefix() + "§c你没有权限执行此命令." + " §7" + commandsPerms.get("help"));

        }
        return false;
    }


    // Tab补全
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(commands.keySet());
        }
        return List.of();
    }
}
