package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import nadiendev.umc.mysticalagriculture.condition.MysticalCropCondition;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class AgriCraftDataGenPlantsCompat implements DataModule {

    private static final String AGRICRAFT = "agricraft";
    private static final String MYSTICAL_AGRICULTURE = "mysticalagriculture";
    private static final String MODEL_PREFIX = MYSTICAL_AGRICULTURE + ":block/mystical_resource_crop_";
    private static final int[] STAGES = {2, 4, 6, 8, 10, 12, 14, 16};
    private static final int HARVEST_STAGE = 3;
    private static final double GROWTH_CHANCE = 0.65;
    private static final double GROWTH_BONUS = 0.025;
    private static final double SPREAD_CHANCE = 0.1;
    private static final double ESSENCE_CHANCE = 0.75;
    private static final double SOIL_TOLERANCE = 0.2;
    private static final double LIGHT_TOLERANCE = 0.5;
    private static final int MIN_LIGHT = 10;
    private static final int MAX_LIGHT = 16;

    @Override
    public void json(UmcJsonSink sink) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            String id = definition.id();
            sink.data("agricraft/plants/" + id, plant(definition));
            for (int stage = 0; stage < STAGES.length; stage++) {
                sink.raw("assets/" + UltimateModCompats.MODID + "/models/crop/" + id + "_stage" + stage + ".json", stageModel(definition, stage));
            }
            JsonObject seed = new JsonObject();
            seed.addProperty("parent", MYSTICAL_AGRICULTURE + ":item/" + id + "_seeds");
            sink.raw("assets/" + UltimateModCompats.MODID + "/models/seed/" + id + ".json", seed);
        }
    }

    @Override
    public void addEnglish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(plantKey(definition), definition.names().get("en_us"));
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(plantKey(definition), definition.names().get("es_es"));
        }
    }

    private static String plantKey(MysticalCropDefinition definition) {
        return "plant." + AGRICRAFT + "." + UltimateModCompats.MODID + "." + definition.id();
    }

    private static JsonObject plant(MysticalCropDefinition definition) {
        JsonArray conditions = MysticalAgricultureDataGenRecipesCompat.conditions(definition, MysticalCropCondition.CROP);
        JsonObject agricraft = new JsonObject();
        agricraft.addProperty("type", "neoforge:mod_loaded");
        agricraft.addProperty("modid", AGRICRAFT);
        conditions.add(agricraft);

        JsonObject seed = new JsonObject();
        seed.addProperty("item", MYSTICAL_AGRICULTURE + ":" + definition.id() + "_seeds");
        seed.addProperty("override_planting", true);
        seed.addProperty("grass_drop_chance", 0.0);
        seed.addProperty("seed_drop_bonus", 0.0);
        seed.addProperty("seed_drop_chance", 1.0);
        JsonArray seeds = new JsonArray();
        seeds.add(seed);

        JsonArray stages = new JsonArray();
        for (int stage : STAGES) {
            stages.add(stage);
        }

        JsonObject product = new JsonObject();
        product.addProperty("item", MYSTICAL_AGRICULTURE + ":" + definition.id() + "_essence");
        product.addProperty("min", 1);
        product.addProperty("max", 1);
        product.addProperty("chance", ESSENCE_CHANCE);
        product.addProperty("required", false);
        JsonArray products = new JsonArray();
        products.add(product);

        JsonArray mods = new JsonArray();
        mods.add(MYSTICAL_AGRICULTURE);

        JsonObject plant = new JsonObject();
        plant.add("neoforge:conditions", conditions);
        plant.add("mods", mods);
        plant.add("seeds", seeds);
        plant.add("stages", stages);
        plant.addProperty("harvest_stage", HARVEST_STAGE);
        plant.addProperty("growth_chance", GROWTH_CHANCE);
        plant.addProperty("growth_bonus", GROWTH_BONUS);
        plant.addProperty("cloneable", false);
        plant.addProperty("spread_chance", SPREAD_CHANCE);
        plant.add("products", products);
        plant.add("requirement", requirement());
        return plant;
    }

    private static JsonObject requirement() {
        JsonObject requirement = new JsonObject();
        requirement.add("soil_humidity", soil("wet", "equal"));
        requirement.add("soil_acidity", soil("slightly_acidic", "equal"));
        requirement.add("soil_nutrients", soil("high", "equal_or_higher"));
        requirement.addProperty("min_light", MIN_LIGHT);
        requirement.addProperty("max_light", MAX_LIGHT);
        requirement.addProperty("light_tolerance_factor", LIGHT_TOLERANCE);
        return requirement;
    }

    private static JsonObject soil(String value, String type) {
        JsonObject condition = new JsonObject();
        condition.addProperty("value", value);
        condition.addProperty("type", type);
        condition.addProperty("tolerance_factor", SOIL_TOLERANCE);
        return condition;
    }

    private static JsonObject stageModel(MysticalCropDefinition definition, int stage) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", MODEL_PREFIX + stage);
        if (stage == STAGES.length - 1) {
            JsonObject textures = new JsonObject();
            textures.addProperty("flower", definition.flowerTexture().toString());
            model.add("textures", textures);
        }
        return model;
    }
}
