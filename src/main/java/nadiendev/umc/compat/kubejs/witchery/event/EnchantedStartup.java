package nadiendev.umc.compat.kubejs.witchery.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import nadiendev.umc.compat.kubejs.witchery.startup.CursesKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.startup.FamiliarsKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.startup.RiteFactoriesKubeEvent;

public interface EnchantedStartup {

    EventGroup GROUP = EventGroup.of("EnchantedStartup");

    EventHandler RITE_FACTORIES = GROUP.startup("riteFactories", () -> RiteFactoriesKubeEvent.class);
    EventHandler CURSES = GROUP.startup("curses", () -> CursesKubeEvent.class);
    EventHandler FAMILIARS = GROUP.startup("familiars", () -> FamiliarsKubeEvent.class);
}
