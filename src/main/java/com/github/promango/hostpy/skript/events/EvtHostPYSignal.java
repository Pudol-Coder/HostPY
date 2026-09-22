package com.github.promango.hostpy.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import com.github.promango.hostpy.events.HostPYSignalEvent;

/**
 * Skript 문법: on hostpy signal received:
 * 파이썬 -> 서버 신호(HostPYSignalEvent)가 도착하면 실행된다.
 * 스크립트 안에서는 "event-string" 표현식으로 신호 내용을 꺼낼 수 있다.
 *
 * 예시 (.sk):
 * on hostpy signal received:
 *     broadcast "받은 신호: %event-string%"
 */
public class EvtHostPYSignal extends SkriptEvent {

    static {
        Skript.registerEvent("HostPY Signal Received", EvtHostPYSignal.class, HostPYSignalEvent.class,
                "[hostpy] signal received");
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "hostpy signal received";
    }
}
