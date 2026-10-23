package nadiendev.umc.compat.kubejs.thaumaturge.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import nadiendev.umc.compat.kubejs.thaumaturge.data.AspectsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.BlueprintsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.RawDataKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.ResearchKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.ScansKubeEvent;

public interface ThaumaturgeEvents {

    EventGroup GROUP = EventGroup.of("ThaumaturgeEvents");

    EventHandler RESEARCH = GROUP.server("research", () -> ResearchKubeEvent.class);
    EventHandler ASPECTS = GROUP.server("aspects", () -> AspectsKubeEvent.class);
    EventHandler SCANS = GROUP.server("scans", () -> ScansKubeEvent.class);
    EventHandler BLUEPRINTS = GROUP.server("blueprints", () -> BlueprintsKubeEvent.class);
    EventHandler DATA = GROUP.server("data", () -> RawDataKubeEvent.class);
}
