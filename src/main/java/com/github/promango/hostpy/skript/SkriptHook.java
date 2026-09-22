package com.github.promango.hostpy.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.registrations.EventValues;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.github.promango.hostpy.HostPY;
import com.github.promango.hostpy.events.HostPYSignalEvent;

import java.io.IOException;

/**
 * Skript가 서버에 설치되어 있을 때만 Skript 문법(effect/event)을 등록한다.
 * Skript가 없어도 HostPY 자체는 정상 동작해야 하므로 반드시 존재 여부를 먼저 확인한다.
 */
public class SkriptHook {

    public static void hook(HostPY plugin) {
        Plugin skript = Bukkit.getPluginManager().getPlugin("Skript");
        if (skript == null || !skript.isEnabled()) {
            plugin.getLogger().info("[HostPY] Skript가 설치되어 있지 않아 Skript 연동은 건너뜁니다.");
            return;
        }

        try {
            SkriptAddon addon = Skript.registerAddon(plugin);
            // com.github.promango.hostpy.skript.effects, com.github.promango.hostpy.skript.events 패키지 안의
            // 모든 클래스를 로드해서 각 클래스의 static 등록 블록이 실행되게 한다.
            addon.loadClasses("com.github.promango.hostpy.skript", "effects", "events");

            // HostPYSignalEvent 발생 시 Skript 스크립트에서 event-string 으로 신호 내용을 꺼낼 수 있게 등록
            EventValues.registerEventValue(HostPYSignalEvent.class, String.class,
                    HostPYSignalEvent::getSignal, 0);

            plugin.getLogger().info("[HostPY] Skript 연동이 활성화되었습니다.");
        } catch (IOException e) {
            plugin.getLogger().warning("[HostPY] Skript 문법 등록 실패: " + e.getMessage());
        }
    }
}
