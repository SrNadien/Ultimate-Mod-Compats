package nadiendev.umc.datagen.providers;

import static nadiendev.umc.datagen.providers.guide.GuideJson.array;
import static nadiendev.umc.datagen.providers.guide.GuideJson.object;
import static nadiendev.umc.datagen.providers.guide.GuideJson.with;

import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.registry.CraftingBiomeModifiers;

public final class CraftingWorldgen {

    private static final String NAMESPACE = UltimateModCompats.MODID;

    private CraftingWorldgen() {
    }

    public static void json(UmcJsonSink sink) {
        patch(sink, "moonbloom_patch", "moonbloom", 18, 6);
        patch(sink, "mugwort_patch", "mugwort", 24, 4);

        features(sink, "moonbloom_forest", "#minecraft:is_forest", "moonbloom_patch");
        features(sink, "moonbloom_taiga", "#minecraft:is_taiga", "moonbloom_patch");
        features(sink, "mugwort_plains", "#c:is_plains", "mugwort_patch");
        features(sink, "mugwort_swamp", "#c:is_swamp", "mugwort_patch");

        spawns(sink, "spawn_toad", "#c:is_swamp", "toad");
        spawns(sink, "spawn_owl", "#minecraft:is_forest", "owl");
        spawns(sink, "spawn_raven_taiga", "#minecraft:is_taiga", "raven");
        spawns(sink, "spawn_raven_plains", "#c:is_plains", "raven");
    }

    private static void patch(UmcJsonSink sink, String name, String block, int tries, int rarity) {
        JsonObject state = object("Name", NAMESPACE + ":" + block);
        JsonObject toPlace = object("type", "minecraft:simple_state_provider");
        with(toPlace, "state", state);
        JsonObject simpleConfig = object("to_place", toPlace);
        JsonObject simple = object("type", "minecraft:simple_block");
        with(simple, "config", simpleConfig);

        JsonObject predicate = object("type", "minecraft:matching_blocks");
        with(predicate, "blocks", "minecraft:air");
        JsonObject filter = object("type", "minecraft:block_predicate_filter");
        with(filter, "predicate", predicate);

        JsonObject inner = object("feature", simple);
        with(inner, "placement", array(filter));
        JsonObject config = object("feature", inner);
        with(config, "tries", tries);
        with(config, "xz_spread", 6);
        with(config, "y_spread", 2);
        JsonObject configured = object("type", "minecraft:random_patch");
        with(configured, "config", config);
        sink.data("worldgen/configured_feature/" + name, configured);

        JsonObject rarityFilter = object("type", "minecraft:rarity_filter");
        with(rarityFilter, "chance", rarity);
        JsonObject heightmap = object("type", "minecraft:heightmap");
        with(heightmap, "heightmap", "MOTION_BLOCKING");
        JsonObject placed = object("feature", NAMESPACE + ":" + name);
        with(placed, "placement", array(rarityFilter, object("type", "minecraft:in_square"), heightmap, object("type", "minecraft:biome")));
        sink.data("worldgen/placed_feature/" + name, placed);
    }

    private static void features(UmcJsonSink sink, String name, String biomes, String placedFeature) {
        JsonObject modifier = object("type", CraftingBiomeModifiers.FEATURES.getId().toString());
        with(modifier, "biomes", biomes);
        with(modifier, "features", NAMESPACE + ":" + placedFeature);
        with(modifier, "step", "vegetal_decoration");
        sink.data("neoforge/biome_modifier/" + name, modifier);
    }

    private static void spawns(UmcJsonSink sink, String name, String biomes, String species) {
        JsonObject modifier = object("type", CraftingBiomeModifiers.SPAWNS.getId().toString());
        with(modifier, "biomes", biomes);
        with(modifier, "entity_type", NAMESPACE + ":" + species);
        with(modifier, "species", species);
        sink.data("neoforge/biome_modifier/" + name, modifier);
    }
}
