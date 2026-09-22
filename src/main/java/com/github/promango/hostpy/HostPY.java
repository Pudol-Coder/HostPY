package com.github.promango.hostpy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.promango.hostpy.listeners.HostPYTabCompleter;
import com.github.promango.hostpy.managers.ScriptManager;
import com.github.promango.hostpy.managers.SignalManager;
import com.github.promango.hostpy.skript.SkriptHook;

public final class HostPY extends JavaPlugin {

    private static HostPY instance;
    private ScriptManager scriptManager;
    private SignalManager signalManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.scriptManager = new ScriptManager(this);
        this.signalManager = new SignalManager(this);

        signalManager.start();

        if (getCommand("hostpy") != null) {
            getCommand("hostpy").setTabCompleter(new HostPYTabCompleter(this));
        }

        SkriptHook.hook(this);
        getLogger().info("HostPY가 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        if (scriptManager != null) scriptManager.stopAll();
        if (signalManager != null) signalManager.stop();
        getLogger().info("HostPY가 비활성화되었습니다.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("hostpy")) return false;

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> {
                if (args.length < 2) {
                    sender.sendMessage("§c[HostPY] 사용법: /hostpy start <파일명>");
                    return true;
                }
                scriptManager.startScript(args[1], sender);
            }
            case "stop" -> {
                if (args.length < 2) {
                    sender.sendMessage("§c[HostPY] 사용법: /hostpy stop <파일명>");
                    return true;
                }
                scriptManager.stopScript(args[1], sender);
            }
            case "list" -> scriptManager.listScripts(sender);
            default -> sendHelp(sender);
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§b[ HostPY 도움말 ]");
        sender.sendMessage("§7/hostpy start <파일명> §f- Python 스크립트 실행");
        sender.sendMessage("§7/hostpy stop <파일명> §f- 실행 중인 스크립트 종료");
        sender.sendMessage("§7/hostpy list §f- 실행 중인 스크립트 목록");
    }

    public ScriptManager getScriptManager() { return scriptManager; }
    public SignalManager getSignalManager() { return signalManager; }
    public static HostPY getInstance() { return instance; }
}
