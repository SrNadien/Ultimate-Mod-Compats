package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.LinkedHashSet;
import java.util.Set;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import nadiendev.umc.mysticalagriculture.condition.MysticalCropCondition;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierEssences;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureDataGenRecipesCompat implements DataModule {

    private static final String TARGET = "mysticalagriculture";
    private static final Set<String> ALWAYS_LOADED = Set.of("minecraft", "neoforge", "mysticalagriculture");
    private static final String[] ESSENCE_PATTERN = {"EEE", "E E", "EEE"};

    private static final String KUBEJS = "kubejs";
    private static final String MASTER_CRYSTAL = TARGET + ":master_infusion_crystal";
    private static final int MASTER_CRYSTAL_TIERS = 6;
    private static final String[] ELEMENTS = {"air", "earth", "water", "fire"};

    private static void tierUpgrades(UmcJsonSink sink) {
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            infusionUpgrade(sink, tier, TierAltarRegistry.INFUSION_PREFIX, "infusion_altar", item(TARGET + ":soulstone"));
            infusionUpgrade(sink, tier, TierAltarRegistry.INFUSION_PEDESTAL_PREFIX, "infusion_pedestal", null);
            infusionUpgrade(sink, tier, TierAltarRegistry.VESSEL_PREFIX, "essence_vessel", null);
            infusionUpgrade(sink, tier, TierAltarRegistry.REPROCESSOR_PREFIX, "seed_reprocessor", tag("c:storage_blocks/iron"));
            infusionUpgrade(sink, tier, TierAltarRegistry.FURNACE_PREFIX, "furnace", item("minecraft:coal_block"));
            awakeningUpgrade(sink, tier, TierAltarRegistry.AWAKENING_PREFIX, "awakening_altar", 5);
            awakeningUpgrade(sink, tier, TierAltarRegistry.AWAKENING_PEDESTAL_PREFIX, "awakening_pedestal", 3);
        }
    }

    private static void infusionUpgrade(UmcJsonSink sink, int tier, String prefix, String base, JsonObject block) {
        JsonArray ingredients = new JsonArray();
        for (int index = 0; index < 4; index++) {
            ingredients.add(tag(TierEssences.tag(tier).location().toString()));
            if (block != null && index < 3) {
                ingredients.add(block.deepCopy());
            }
        }
        ingredients.add(crystal(tier));
        JsonObject recipe = upgradeRecipe("mysticalagriculture:infusion", tier, prefix, base);
        recipe.add("ingredients", ingredients);
        sink.data("recipe/mystical/tier/" + prefix + tier, recipe);
    }

    private static void awakeningUpgrade(UmcJsonSink sink, int tier, String prefix, String base, int essencesPerTier) {
        JsonArray essences = new JsonArray();
        for (String element : ELEMENTS) {
            JsonObject essence = new JsonObject();
            essence.addProperty("id", TARGET + ":" + element + "_essence");
            essence.addProperty("count", Math.min(64, essencesPerTier * tier));
            essences.add(essence);
        }
        JsonArray ingredients = new JsonArray();
        ingredients.add(tag(TierEssences.tag(tier).location().toString()));
        ingredients.add(crystal(tier));
        for (int index = 0; index < 2; index++) {
            ingredients.add(item(TARGET + ":cognizant_dust"));
        }
        JsonObject recipe = upgradeRecipe("mysticalagriculture:awakening", tier, prefix, base);
        recipe.add("essences", essences);
        recipe.add("ingredients", ingredients);
        sink.data("recipe/mystical/tier/" + prefix + tier, recipe);
    }

    private static JsonObject upgradeRecipe(String type, int tier, String prefix, String base) {
        String previous = tier == TierRules.MIN_TIER ? TARGET + ":" + base : UltimateModCompats.MODID + ":" + prefix + (tier - 1);
        JsonObject result = new JsonObject();
        result.addProperty("id", UltimateModCompats.MODID + ":" + prefix + tier);
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", type);
        String essenceMod = TierEssences.essence(tier).getNamespace();
        if (!ALWAYS_LOADED.contains(essenceMod)) {
            JsonArray conditions = new JsonArray();
            conditions.add(modLoaded(essenceMod));
            recipe.add("neoforge:conditions", conditions);
        }
        recipe.add("input", item(previous));
        recipe.add("result", result);
        return recipe;
    }

    private static JsonArray crystal(int tier) {
        JsonArray choices = new JsonArray();
        choices.add(item(UltimateModCompats.MODID + ":" + TierAltarRegistry.CRYSTAL_PREFIX + tier));
        choices.add(item(MASTER_CRYSTAL));
        return choices;
    }

    private static JsonObject item(String id) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", id);
        return ingredient;
    }

    private static JsonObject tag(String id) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("tag", id);
        return ingredient;
    }

    private static final String[] REPLACED_RECIPES = {
            "mysticalagriculture/recipe/infusion_crystal",
            "mysticalagriculture/recipe/master_infusion_crystal",
            "mysticalagriculture/recipe/prudentium_essence",
            "mysticalagriculture/recipe/tertium_essence",
            "mysticalagriculture/recipe/imperium_essence",
            "mysticalagriculture/recipe/supremium_essence",
            "mysticalagradditions/recipe/insanium_essence",
            "mysticalextendedtier/recipe/ornium_essence",
            "mysticalextendedtier/recipe/lightium_essence",
            "mysticalextendedtier/recipe/tornium_essence",
            "mysticalextendedtier/recipe/ouranium_essence",
            "mysticalextendedtier/recipe/compat/agradditions/insanium_to_ornium_essence"
    };
    private static final String PLUS_TOP_BOTTOM = " # ";
    private static final String PLUS_MIDDLE = "#X#";

    private static void crystals(UmcJsonSink sink) {
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            String crystal = UltimateModCompats.MODID + ":" + TierAltarRegistry.CRYSTAL_PREFIX + tier;
            if (tier == TierRules.MIN_TIER) {
                JsonObject recipe = shaped(new String[]{"%#%", "#X#", "%#%"}, crystal, new JsonArray());
                JsonObject key = recipe.getAsJsonObject("key");
                key.add("#", item(TARGET + ":prosperity_shard"));
                key.add("%", item(TierEssences.essence(tier).toString()));
                key.add("X", tag("c:gems/diamond"));
                sink.data("recipe/mystical/crystal/crystal_" + tier, recipe);
                continue;
            }
            String previousEssence = TierEssences.essence(tier - 1).toString();
            String previousCrystal = UltimateModCompats.MODID + ":" + TierAltarRegistry.CRYSTAL_PREFIX + (tier - 1);
            JsonObject crystalRecipe = shaped(new String[]{PLUS_TOP_BOTTOM, PLUS_MIDDLE, PLUS_TOP_BOTTOM}, crystal, essenceConditions(tier - 1));
            crystalRecipe.getAsJsonObject("key").add("#", item(previousEssence));
            crystalRecipe.getAsJsonObject("key").add("X", item(previousCrystal));
            sink.data("recipe/mystical/crystal/crystal_" + tier, crystalRecipe);

            JsonObject essenceRecipe = shaped(new String[]{PLUS_TOP_BOTTOM, PLUS_MIDDLE, PLUS_TOP_BOTTOM}, TierEssences.essence(tier).toString(), essenceConditions(tier));
            essenceRecipe.getAsJsonObject("key").add("#", item(previousEssence));
            essenceRecipe.getAsJsonObject("key").add("X", crystal(tier));
            sink.data("recipe/mystical/crystal/essence_" + tier, essenceRecipe);
        }
        for (String replaced : REPLACED_RECIPES) {
            sink.raw("data/" + replaced + ".json", disabled());
        }
        masterCrystal(sink);
    }

    private static void masterCrystal(UmcJsonSink sink) {
        JsonArray ingredients = new JsonArray();
        for (int tier = 1; tier <= MASTER_CRYSTAL_TIERS; tier++) {
            ingredients.add(item(UltimateModCompats.MODID + ":" + TierAltarRegistry.CRYSTAL_PREFIX + tier));
        }
        ingredients.add(item("mysticalagradditions:insanium_gemstone_block"));
        ingredients.add(item("mysticalagriculture:nether_star_seeds"));
        JsonObject result = new JsonObject();
        result.addProperty("id", TARGET + ":master_infusion_crystal");
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "mysticalagriculture:infusion");
        recipe.add("input", item(TARGET + ":supremium_gemstone_block"));
        recipe.add("ingredients", ingredients);
        recipe.add("result", result);
        sink.data("recipe/mystical/crystal/master_infusion_crystal", recipe);
    }

    private static JsonArray essenceConditions(int tier) {
        Set<String> mods = new LinkedHashSet<>();
        mods.add(TierEssences.essence(tier).getNamespace());
        if (tier > TierRules.MIN_TIER) {
            mods.add(TierEssences.essence(tier - 1).getNamespace());
        }
        JsonArray conditions = new JsonArray();
        mods.stream().filter(mod -> !ALWAYS_LOADED.contains(mod)).forEach(mod -> conditions.add(modLoaded(mod)));
        return conditions;
    }

    private static JsonObject shaped(String[] rows, String result, JsonArray conditions) {
        JsonArray pattern = new JsonArray();
        for (String row : rows) {
            pattern.add(row);
        }
        JsonObject output = new JsonObject();
        output.addProperty("id", result);
        JsonObject recipe = new JsonObject();
        if (conditions.size() > 0) {
            recipe.add("neoforge:conditions", conditions);
        }
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        recipe.add("pattern", pattern);
        recipe.add("key", new JsonObject());
        recipe.add("result", output);
        return recipe;
    }

    private static JsonObject disabled() {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:false");
        JsonArray conditions = new JsonArray();
        conditions.add(condition);
        JsonObject recipe = new JsonObject();
        recipe.add("neoforge:conditions", conditions);
        return recipe;
    }

    @Override
    public void json(UmcJsonSink sink) {
        tierUpgrades(sink);
        crystals(sink);
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            if (definition.hasEssenceRecipe()) {
                sink.data("recipe/mystical/essence/" + definition.id(), essenceRecipe(definition));
            }
            seedRecipes(sink, definition);
        }
    }

    private static void seedRecipes(UmcJsonSink sink, MysticalCropDefinition definition) {
        String id = definition.id();
        String seeds = TARGET + ":" + id + "_seeds";

        JsonObject infusion = seedRecipe(definition, MysticalCropCondition.SEED_INFUSION, "mysticalagriculture:infusion", seeds);
        JsonArray ingredients = new JsonArray();
        for (int index = 0; index < 4; index++) {
            ingredients.add(component("material", definition));
            ingredients.add(component("essence", definition));
        }
        infusion.add("ingredients", ingredients);
        infusion.add("input", component("seed", definition));
        sink.data("recipe/mystical/seed/infusion/" + id, infusion);

        JsonObject reprocessor = new JsonObject();
        reprocessor.add("neoforge:conditions", conditions(definition, MysticalCropCondition.SEED_REPROCESSOR));
        reprocessor.addProperty("type", "mysticalagriculture:reprocessor");
        reprocessor.add("input", item(seeds));
        JsonObject output = new JsonObject();
        output.addProperty("count", 2);
        output.addProperty("id", TARGET + ":" + id + "_essence");
        reprocessor.add("result", output);
        sink.data("recipe/mystical/seed/reprocessor/" + id, reprocessor);
    }

    private static JsonObject seedRecipe(MysticalCropDefinition definition, String recipe, String type, String seeds) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(definition, recipe));
        json.addProperty("type", type);
        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", seeds);
        json.add("result", result);
        return json;
    }

    private static JsonObject component(String component, MysticalCropDefinition definition) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "mysticalagriculture:crop_component");
        json.addProperty("component", component);
        json.addProperty("crop", UltimateModCompats.MODID + ":" + definition.id());
        return json;
    }

    static JsonArray conditions(MysticalCropDefinition definition, String recipe) {
        Set<String> mods = new LinkedHashSet<>(definition.requires());
        if (definition.hasEssenceRecipe()) {
            String resultMod = ResourceLocation.parse(definition.essenceResult()).getNamespace();
            if (!ALWAYS_LOADED.contains(resultMod)) {
                mods.add(resultMod);
            }
        }
        JsonArray conditions = new JsonArray();
        mods.forEach(mod -> conditions.add(modLoaded(mod)));
        if (definition.requires().contains(KUBEJS) && !definition.tagIngredient()) {
            JsonObject exists = new JsonObject();
            exists.addProperty("type", "neoforge:item_exists");
            exists.addProperty("item", definition.ingredient());
            conditions.add(exists);
        }
        if (!definition.requiresAny().isEmpty()) {
            JsonArray values = new JsonArray();
            definition.requiresAny().forEach(mod -> values.add(modLoaded(mod)));
            JsonObject any = new JsonObject();
            any.addProperty("type", "neoforge:or");
            any.add("values", values);
            conditions.add(any);
        }
        JsonObject crop = new JsonObject();
        crop.addProperty("type", UltimateModCompats.MODID + ":mystical_crop");
        crop.addProperty("crop", definition.id());
        crop.addProperty("recipe", recipe);
        conditions.add(crop);
        return conditions;
    }

    private static JsonObject modLoaded(String mod) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", mod);
        return condition;
    }

    private static JsonObject essenceRecipe(MysticalCropDefinition definition) {
        JsonArray conditions = conditions(definition, MysticalCropCondition.ESSENCE_CRAFTING);
        JsonArray pattern = new JsonArray();
        for (String row : ESSENCE_PATTERN) {
            pattern.add(row);
        }
        JsonObject essence = new JsonObject();
        essence.addProperty("item", TARGET + ":" + definition.id() + "_essence");
        JsonObject key = new JsonObject();
        key.add("E", essence);
        JsonObject result = new JsonObject();
        result.addProperty("id", definition.essenceResult());
        result.addProperty("count", definition.essenceCraftCount());
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        recipe.add("neoforge:conditions", conditions);
        recipe.add("pattern", pattern);
        recipe.add("key", key);
        recipe.add("result", result);
        return recipe;
    }
}
