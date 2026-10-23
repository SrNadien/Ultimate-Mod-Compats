package nadiendev.umc.compat.kubejs.witchery.event;

import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import nadiendev.umc.compat.kubejs.witchery.data.AltarKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.MutagensKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.RitesKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.ShapesKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.WitcheryDataEvent;
import nadiendev.umc.compat.kubejs.witchery.startup.CursesKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.startup.FamiliarsKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.startup.RiteFactoriesKubeEvent;

public final class WitcheryDispatcher {

    private WitcheryDispatcher() {
    }

    public static void postStartup() {
        try {
            if (EnchantedStartup.RITE_FACTORIES.hasListeners()) {
                EnchantedStartup.RITE_FACTORIES.post(ScriptType.STARTUP, new RiteFactoriesKubeEvent());
            }
            if (EnchantedStartup.CURSES.hasListeners()) {
                EnchantedStartup.CURSES.post(ScriptType.STARTUP, new CursesKubeEvent());
            }
            if (EnchantedStartup.FAMILIARS.hasListeners()) {
                EnchantedStartup.FAMILIARS.post(ScriptType.STARTUP, new FamiliarsKubeEvent());
            }
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("Fallo al ejecutar los eventos EnchantedStartup", ex);
        }
    }

    public static void postData(KubeDataGenerator generator) {
        postData(EnchantedEvents.RITES, new RitesKubeEvent(generator));
        postData(EnchantedEvents.SHAPES, new ShapesKubeEvent(generator));
        postData(EnchantedEvents.ALTAR, new AltarKubeEvent(generator));
        postData(EnchantedEvents.MUTAGENS, new MutagensKubeEvent(generator));
    }

    private static void postData(EventHandler handler, WitcheryDataEvent event) {
        if (!handler.hasListeners()) {
            return;
        }
        try {
            handler.post(ScriptType.SERVER, event);
            event.flush();
        } catch (RuntimeException ex) {
            ConsoleJS.SERVER.error("Fallo al generar datos de EnchantedEvents." + handler.name, ex);
        }
    }
}
