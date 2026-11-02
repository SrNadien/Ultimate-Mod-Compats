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
    public static final int DEFAULT_CRYSTAL_DURABILITY = 1000;
    private static final String DEFAULT_NAMESPACE = UltimateModCompats.MODID;
    private static final MysticalAgricultureConfig INSTANCE = new MysticalAgricultureConfig();
    private static final Map<String, ModConfigSpec.BooleanValue> CROPS = new LinkedHashMap<>();

    private static ModConfigSpec.BooleanValue enabled;
    private static ModConfigSpec.ConfigValue<String> cropNamespace;
    private static ModConfigSpec.BooleanValue seedCraftingRemoved;
    private static ModConfigSpec.BooleanValue seedInfusionRecipes;
    private static ModConfigSpec.BooleanValue seedReprocessorRecipes;
    private static ModConfigSpec.BooleanValue essenceCraftingRecipes;
    private static ModConfigSpec.BooleanValue tiersEnabled;
    private static ModConfigSpec.BooleanValue baseAltarsElementalOnly;
    private static ModConfigSpec.BooleanValue previousTierAllowed;
    private static ModConfigSpec.IntValue crystalDurability;
    private static ModConfigSpec.BooleanValue structureTierRequired;
    private static ModConfigSpec.BooleanValue higherTierStructureAllowed;

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
        seedInfusionRecipes = builder.comment("Generate the infusion altar recipe of every bundled seed.").define("seed_infusion", true);
        seedReprocessorRecipes = builder.comment("Generate the reprocessor recipe of every bundled seed.").define("seed_reprocessor", true);
        essenceCraftingRecipes = builder.comment("Generate the recipe that crafts every bundled essence back into the item its crop is made from.").define("essence_crafting", true);
        builder.pop();
        builder.comment("Tiered infusion and awakening altars (levels 1 to 10)").push("tiers");
        seedCraftingRemoved = builder.comment("Removes every crafting table recipe of Mystical Agriculture seeds (the base mod and its addons), so seeds are only made in the altars.")
                .define("remove_seed_crafting", true);
        tiersEnabled = builder.comment("Each tiered altar only makes the seeds of its own tier and of the previous one. When false every altar accepts every recipe.")
                .define("enabled", true);
        baseAltarsElementalOnly = builder.comment("The Mystical Agriculture altars only make elemental seeds, the other seeds move to the tiered altars.")
                .define("base_altars_elemental_only", true);
        previousTierAllowed = builder.comment("A tiered altar also makes the seeds of the tier below it. The level 1 altar only makes tier 1.")
                .define("previous_tier_allowed", true);
        structureTierRequired = builder.comment("A tiered altar only works when the pedestals and essence vessels around it are of its tier or higher.")
                .define("structure_tier_required", true);
        higherTierStructureAllowed = builder.comment("Pedestals and vessels of a higher tier than the altar are accepted. When false they must be exactly the tier of the altar.")
                .define("higher_tier_structure_allowed", true);
        builder.pop();
        builder.comment("Tiered infusion crystals (levels 1 to 10) used to craft the higher tier essences").push("crystals");
        crystalDurability = builder.comment("Uses of every tiered infusion crystal.")
                .defineInRange("durability", DEFAULT_CRYSTAL_DURABILITY, 1, 1000000);
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

    public boolean seedCraftingRemoved() {
        return flag(seedCraftingRemoved, ROOT + ".tiers.remove_seed_crafting");
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

    public boolean tiersEnabled() {
        return flag(tiersEnabled, ROOT + ".tiers.enabled");
    }

    public boolean baseAltarsElementalOnly() {
        return flag(baseAltarsElementalOnly, ROOT + ".tiers.base_altars_elemental_only");
    }

    public boolean previousTierAllowed() {
        return flag(previousTierAllowed, ROOT + ".tiers.previous_tier_allowed");
    }

    public int crystalDurability() {
        return UmcConfig.SPEC.isLoaded() ? crystalDurability.get() : DEFAULT_CRYSTAL_DURABILITY;
    }

    public boolean structureTierRequired() {
        return flag(structureTierRequired, ROOT + ".tiers.structure_tier_required");
    }

    public boolean higherTierStructureAllowed() {
        return flag(higherTierStructureAllowed, ROOT + ".tiers.higher_tier_structure_allowed");
    }

    public boolean cropEnabled(String id) {
        ModConfigSpec.BooleanValue value = CROPS.get(id);
        return value == null || flag(value, ROOT + ".crops." + id);
    }

    private static boolean flag(ModConfigSpec.BooleanValue value, String key) {
        return UmcConfig.SPEC.isLoaded() ? value.get() : EarlyConfig.bool(key, true);
    }
}
