package nadiendev.umc.compat.kubejs.witchery.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import nadiendev.umc.compat.kubejs.witchery.data.AltarKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.MutagensKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.RitesKubeEvent;
import nadiendev.umc.compat.kubejs.witchery.data.ShapesKubeEvent;

public interface EnchantedEvents {

    EventGroup GROUP = EventGroup.of("EnchantedEvents");

    EventHandler RITES = GROUP.server("rites", () -> RitesKubeEvent.class);
    EventHandler SHAPES = GROUP.server("shapes", () -> ShapesKubeEvent.class);
    EventHandler ALTAR = GROUP.server("altar", () -> AltarKubeEvent.class);
    EventHandler MUTAGENS = GROUP.server("mutagens", () -> MutagensKubeEvent.class);
}
