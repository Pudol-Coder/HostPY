package com.github.promango.hostpy.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import com.github.promango.hostpy.HostPY;

import java.util.ArrayList;
import java.util.List;

/** /hostpy 명령어의 탭 자동완성. */
public class HostPYTabCompleter implements TabCompleter {
    private static final List<String> SUBCOMMANDS = List.of("start", "stop", "list");
    private final HostPY plugin;

    public HostPYTabCompleter(HostPY plugin) { this.plugin = plugin; }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("hostpy")) return List.of();
        if (args.length == 1) return filter(SUBCOMMANDS, args[0]);
        if (args.length == 2) {
            return switch (args[0].toLowerCase()) {
                case "start" -> filter(plugin.getScriptManager().getAvailableScriptNames(), args[1]);
                case "stop" -> filter(plugin.getScriptManager().getRunningScriptNames(), args[1]);
                default -> List.of();
            };
        }
        return List.of();
    }

    private List<String> filter(List<String> options, String typed) {
        String lower = typed.toLowerCase();
        List<String> result = new ArrayList<>();
        for (String option : options) if (option.toLowerCase().startsWith(lower)) result.add(option);
        return result;
    }
}
