package nadiendev.umc.compat.kubejs.thaumaturge.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.GolemTraitsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.MobTraitsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.WandCapsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.WandRodsKubeEvent;

public interface ThaumaturgeStartup {

    EventGroup GROUP = EventGroup.of("ThaumaturgeStartup");

    EventHandler WAND_CAPS = GROUP.startup("wandCaps", () -> WandCapsKubeEvent.class);
    EventHandler WAND_RODS = GROUP.startup("wandRods", () -> WandRodsKubeEvent.class);
    EventHandler GOLEM_TRAITS = GROUP.startup("golemTraits", () -> GolemTraitsKubeEvent.class);
    EventHandler MOB_TRAITS = GROUP.startup("mobTraits", () -> MobTraitsKubeEvent.class);
}
