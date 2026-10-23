package nadiendev.umc.datagen.providers;

import com.google.gson.JsonObject;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureDataGenModelsCompat implements DataModule {

    private static final String TARGET = "mysticalagriculture";
    private static final String ASSETS = "assets/" + TARGET + "/";
    private static final int MAX_AGE = 7;

    @Override
    public void json(UmcJsonSink sink) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            String id = definition.id();
            sink.raw(ASSETS + "models/item/" + id + "_seeds.json", itemModel(definition.seedTexture()));
            sink.raw(ASSETS + "models/item/" + id + "_essence.json", itemModel(definition.essenceTexture()));
            sink.raw(ASSETS + "models/block/" + id + "_crop.json", cropModel(definition.flowerTexture()));
            sink.raw(ASSETS + "blockstates/" + id + "_crop.json", cropBlockState(id));
        }
    }

    private static JsonObject itemModel(ResourceLocation texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", texture.toString());
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        model.add("textures", textures);
        return model;
    }

    private static JsonObject cropModel(ResourceLocation flower) {
        JsonObject textures = new JsonObject();
        textures.addProperty("flower", flower.toString());
        JsonObject model = new JsonObject();
        model.addProperty("parent", TARGET + ":block/mystical_resource_crop_" + MAX_AGE);
        model.add("textures", textures);
        return model;
    }

    private static JsonObject cropBlockState(String id) {
        JsonObject variants = new JsonObject();
        for (int age = 0; age <= MAX_AGE; age++) {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", age == MAX_AGE
                    ? TARGET + ":block/" + id + "_crop"
                    : TARGET + ":block/mystical_resource_crop_" + age);
            variants.add("age=" + age, variant);
        }
        JsonObject state = new JsonObject();
        state.add("variants", variants);
        return state;
    }
}
