package nadiendev.umc.compat.kubejs.thaumaturge.data;

import java.util.List;

public final class ThaumaturgeRegistries {

    public static final String ASPECT = "aspect";
    public static final String BLUEPRINT = "blueprint";
    public static final String RESEARCH_CATEGORY = "research_category";
    public static final String RESEARCH_ENTRY = "research_entry";
    public static final String SCAN = "scan";
    public static final String PECH_TRADE = "pech_trade";
    public static final String LABYRINTH_ENCOUNTER = "labyrinth_encounter";
    public static final String LABYRINTH_ROOM = "labyrinth_room";
    public static final String LABYRINTH = "labyrinth";
    public static final String OBELISK_SITE = "obelisk_site";
    public static final String INSTABILITY_OUTCOME = "instability_outcome";
    public static final String SPELL_PART = "spell_part";
    public static final String SPELL_AFFINITY = "spell_affinity";

    public static final List<String> ALL = List.of(ASPECT, BLUEPRINT, RESEARCH_CATEGORY, RESEARCH_ENTRY, SCAN, PECH_TRADE, LABYRINTH_ENCOUNTER,
            LABYRINTH_ROOM, LABYRINTH, OBELISK_SITE, INSTABILITY_OUTCOME, SPELL_PART, SPELL_AFFINITY);

    private ThaumaturgeRegistries() {
    }

    public static String require(String registry) {
        String normalized = registry.startsWith("thaumaturge:") ? registry.substring("thaumaturge:".length()) : registry;
        if (!ALL.contains(normalized)) {
            throw new IllegalArgumentException("Registro de datos desconocido " + registry + ", usa uno de " + ALL);
        }
        return normalized;
    }
}
