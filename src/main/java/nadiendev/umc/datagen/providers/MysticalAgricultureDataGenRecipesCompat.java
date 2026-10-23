package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.LinkedHashSet;
import java.util.Set;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureDataGenRecipesCompat implements DataModule {

    private static final String TARGET = "mysticalagriculture";
    private static final Set<String> ALWAYS_LOADED = Set.of("minecraft", "neoforge", "mysticalagriculture");
    private static final String[] ESSENCE_PATTERN = {"EEE", "E E", "EEE"};

    @Override
    public void json(UmcJsonSink sink) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            if (definition.hasEssenceRecipe()) {
                sink.data("recipe/mystical/essence/" + definition.id(), essenceRecipe(definition));
            }
        }
    }

    private static JsonArray conditions(MysticalCropDefinition definition) {
        Set<String> mods = new LinkedHashSet<>(definition.requires());
        String resultMod = ResourceLocation.parse(definition.essenceResult()).getNamespace();
        if (!ALWAYS_LOADED.contains(resultMod)) {
            mods.add(resultMod);
        }
        JsonArray conditions = new JsonArray();
        mods.forEach(mod -> conditions.add(modLoaded(mod)));
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
        JsonArray conditions = conditions(definition);
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
