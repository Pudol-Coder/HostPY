package com.github.promango.hostpy.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import com.github.promango.hostpy.HostPY;

/**
 * Skript 문법: send hostpy signal "내용"
 * 파이썬 스크립트(to-python 폴더 감시 중인 쪽)로 신호를 보낸다.
 */
@Name("Send HostPY Signal")
@Description("파이썬 스크립트로 신호를 보낸다. HostPY 플러그인이 필요하다.")
@Examples({"send hostpy signal \"broadcast:안녕!\"", "send hostpy signal \"chat:%player%:%message%\""})
@Since("1.2")
public class EffSendHostPYSignal extends Effect {

    static {
        Skript.registerEffect(EffSendHostPYSignal.class, "send hostpy signal %string%");
    }

    private Expression<String> signal;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        signal = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String value = signal.getSingle(event);
        if (value != null) {
            HostPY.getInstance().getSignalManager().sendSignal(value);
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "send hostpy signal " + signal.toString(event, debug);
    }
}
