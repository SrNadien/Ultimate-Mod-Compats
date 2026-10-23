package nadiendev.umc.mysticalagriculture.config;

import java.util.LinkedHashMap;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.EarlyConfig;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class MysticalAgricultureConfig {

    private static final String ROOT = "compat.mysticalagriculture";
    private static final String DEFAULT_NAMESPACE = UltimateModCompats.MODID;
    private static final MysticalAgricultureConfig INSTANCE = new MysticalAgricultureConfig();
    private static final Map<String, ModConfigSpec.BooleanValue> CROPS = new LinkedHashMap<>();

    private static ModConfigSpec.BooleanValue enabled;
    private static ModConfigSpec.ConfigValue<String> cropNamespace;
    private static ModConfigSpec.BooleanValue seedCraftingRecipes;
    private static ModConfigSpec.BooleanValue seedInfusionRecipes;
    private static ModConfigSpec.BooleanValue seedReprocessorRecipes;
    private static ModConfigSpec.BooleanValue essenceCraftingRecipes;

    private MysticalAgricultureConfig() {
    }

    public static MysticalAgricultureConfig get() {
        return INSTANCE;
    }

    public static void define(ModConfigSpec.Builder builder) {
        enabled = builder.comment("Master switch. When false none of the bundled Mystical Agriculture crops is registered.")
                .define("enabled", true);
        cropNamespace = builder.comment("Namespace of the crop ids (the items themselves always live in mysticalagriculture).")
                .define("crop_namespace", DEFAULT_NAMESPACE, value -> value instanceof String text && ResourceLocation.tryBuild(text, "test") != null);
        builder.push("recipes");
        seedCraftingRecipes = builder.comment("Generate the shaped crafting recipe of every bundled seed.").define("seed_crafting", true);
        seedInfusionRecipes = builder.comment("Generate the infusion altar recipe of every bundled seed.").define("seed_infusion", true);
        seedReprocessorRecipes = builder.comment("Generate the reprocessor recipe of every bundled seed.").define("seed_reprocessor", true);
        essenceCraftingRecipes = builder.comment("Generate the recipe that crafts every bundled essence back into the item its crop is made from.").define("essence_crafting", true);
        builder.pop();
        builder.comment("Register each bundled crop when the mods it needs are loaded.").push("crops");
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            CROPS.put(definition.id(), builder.comment("Register the " + definition.names().get("en_us") + " crop.").define(definition.id(), true));
        }
        builder.pop();
    }

    public boolean enabled() {
        return flag(enabled, ROOT + ".enabled");
    }

    public String cropNamespace() {
        return UmcConfig.SPEC.isLoaded() ? cropNamespace.get() : EarlyConfig.text(ROOT + ".crop_namespace", DEFAULT_NAMESPACE);
    }

    public boolean seedCraftingRecipes() {
        return flag(seedCraftingRecipes, ROOT + ".recipes.seed_crafting");
    }

    public boolean seedInfusionRecipes() {
        return flag(seedInfusionRecipes, ROOT + ".recipes.seed_infusion");
    }

    public boolean seedReprocessorRecipes() {
        return flag(seedReprocessorRecipes, ROOT + ".recipes.seed_reprocessor");
    }

    public boolean essenceCraftingRecipes() {
        return flag(essenceCraftingRecipes, ROOT + ".recipes.essence_crafting");
    }

    public boolean cropEnabled(String id) {
        ModConfigSpec.BooleanValue value = CROPS.get(id);
        return value == null || flag(value, ROOT + ".crops." + id);
    }

    private static boolean flag(ModConfigSpec.BooleanValue value, String key) {
        return UmcConfig.SPEC.isLoaded() ? value.get() : EarlyConfig.bool(key, true);
    }
}
