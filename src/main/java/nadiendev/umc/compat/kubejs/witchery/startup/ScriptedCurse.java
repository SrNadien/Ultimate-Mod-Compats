package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import java.util.function.Consumer;
import net.favouriteless.enchanted.api.curses.Curse;
import net.minecraft.server.level.ServerPlayer;

public class ScriptedCurse implements Curse {

    private final CurseDefinition definition;

    public ScriptedCurse(CurseDefinition definition) {
        this.definition = definition;
    }

    @Override
    public void onInitialise(ServerPlayer target, int strength, long age) {
        run(definition.initialiseCallback(), target, strength, age, "onInitialise");
    }

    @Override
    public void onRemove(ServerPlayer target, int strength, long age) {
        run(definition.removeCallback(), target, strength, age, "onRemove");
    }

    @Override
    public void tick(ServerPlayer target, int strength, long age) {
        Consumer<CurseContext> callback = definition.tickCallback();
        if (callback == null) {
            return;
        }
        try {
            callback.accept(new CurseContext(target, strength, age));
        } catch (Exception ex) {
            definition.disableTick();
            ConsoleJS.STARTUP.error("Error en el tick de la maldicion " + definition.id() + ", se desactiva su tick", ex);
        }
    }

    @Override
    public int getMaxStrength() {
        return definition.strengthLimit();
    }

    @Override
    public Type<?> type() {
        return definition.type();
    }

    private void run(Consumer<CurseContext> callback, ServerPlayer target, int strength, long age, String name) {
        if (callback == null) {
            return;
        }
        try {
            callback.accept(new CurseContext(target, strength, age));
        } catch (Exception ex) {
            ConsoleJS.STARTUP.error("Error en " + name + " de la maldicion " + definition.id(), ex);
        }
    }
}
