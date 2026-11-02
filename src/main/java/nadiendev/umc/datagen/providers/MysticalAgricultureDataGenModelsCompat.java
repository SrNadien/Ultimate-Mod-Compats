package nadiendev.umc.datagen.providers;

import com.google.gson.JsonObject;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureDataGenModelsCompat implements DataModule {

    private static final String TARGET = "mysticalagriculture";
    private static final String ASSETS = "assets/" + TARGET + "/";
    private static final int MAX_AGE = 7;

    @Override
    public void json(UmcJsonSink sink) {
        tierAltars(sink);
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            String id = definition.id();
            sink.raw(ASSETS + "models/item/" + id + "_seeds.json", itemModel(definition.seedTexture()));
            sink.raw(ASSETS + "models/item/" + id + "_essence.json", itemModel(definition.essenceTexture()));
            sink.raw(ASSETS + "models/block/" + id + "_crop.json", cropModel(definition.flowerTexture()));
            sink.raw(ASSETS + "blockstates/" + id + "_crop.json", cropBlockState(id));
        }
    }

    private static void tierAltars(UmcJsonSink sink) {
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            String infusionAltar = TierAltarRegistry.INFUSION_PREFIX + tier;
            String awakeningAltar = TierAltarRegistry.AWAKENING_PREFIX + tier;
            tierBlock(sink, infusionAltar, "infusion_altar", Map.of(
                    "1", texture(infusionAltar + "_side"),
                    "2", texture(infusionAltar + "_bottom"),
                    "3", texture(infusionAltar + "_top")));
            tierBlock(sink, awakeningAltar, "awakening_altar", Map.of(
                    "1", texture(awakeningAltar + "_side"),
                    "2", texture(awakeningAltar + "_bottom"),
                    "3", texture(awakeningAltar + "_top")));
            String infusionPedestal = TierAltarRegistry.INFUSION_PEDESTAL_PREFIX + tier;
            tierBlock(sink, infusionPedestal, "infusion_pedestal", Map.of(
                    "1", texture(infusionAltar + "_top"),
                    "particle", texture(infusionPedestal + "_base")));
            String awakeningPedestal = TierAltarRegistry.AWAKENING_PEDESTAL_PREFIX + tier;
            tierBlock(sink, awakeningPedestal, "awakening_pedestal", Map.of(
                    "1", texture(awakeningAltar + "_top"),
                    "particle", texture(awakeningPedestal + "_base")));
            String vessel = TierAltarRegistry.VESSEL_PREFIX + tier;
            tierBlock(sink, vessel, "essence_vessel", Map.of(
                    "0", texture(vessel + "_base"),
                    "1", texture(vessel + "_top"),
                    "2", texture(vessel + "_side"),
                    "particle", texture(vessel + "_base")));
            tierMachines(sink, tier);
            String crystal = TierAltarRegistry.CRYSTAL_PREFIX + tier;
            sink.raw("assets/" + UltimateModCompats.MODID + "/models/item/" + crystal + ".json",
                    itemModel(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "item/" + crystal)));
        }
    }

    private static void tierMachines(UmcJsonSink sink, int tier) {
        machine(sink, TierAltarRegistry.REPROCESSOR_PREFIX + tier, tier);
        machine(sink, TierAltarRegistry.FURNACE_PREFIX + tier, tier);
    }

    private static void machine(UmcJsonSink sink, String name, int tier) {
        String namespace = UltimateModCompats.MODID;
        for (boolean running : new boolean[]{false, true}) {
            String model = name + (running ? "_on" : "");
            Map<String, String> textures = Map.of(
                    "top", texture("machine_tier_" + tier + "_side"),
                    "side", texture("machine_tier_" + tier + "_side"),
                    "front", texture(name + (running ? "_front_on" : "_front")));
            sink.raw("assets/" + namespace + "/models/block/" + model + ".json", tierModel("minecraft:block/orientable", textures));
        }
        JsonObject variants = new JsonObject();
        String[] facings = {"north", "east", "south", "west"};
        int[] rotations = {0, 90, 180, 270};
        for (int index = 0; index < facings.length; index++) {
            for (boolean running : new boolean[]{false, true}) {
                JsonObject variant = new JsonObject();
                variant.addProperty("model", namespace + ":block/" + name + (running ? "_on" : ""));
                if (rotations[index] != 0) {
                    variant.addProperty("y", rotations[index]);
                }
                variants.add("facing=" + facings[index] + ",running=" + running, variant);
            }
        }
        JsonObject state = new JsonObject();
        state.add("variants", variants);
        sink.raw("assets/" + namespace + "/blockstates/" + name + ".json", state);
        JsonObject item = new JsonObject();
        item.addProperty("parent", namespace + ":block/" + name);
        sink.raw("assets/" + namespace + "/models/item/" + name + ".json", item);
    }

    private static String texture(String name) {
        return UltimateModCompats.MODID + ":block/mystical/" + name;
    }

    private static void tierBlock(UmcJsonSink sink, String name, String base, Map<String, String> textures) {
        String prefix = UltimateModCompats.MODID + ":";
        JsonObject model = new JsonObject();
        model.addProperty("model", prefix + "block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", model);
        JsonObject state = new JsonObject();
        state.add("variants", variants);
        sink.raw("assets/" + UltimateModCompats.MODID + "/blockstates/" + name + ".json", state);
        sink.raw("assets/" + UltimateModCompats.MODID + "/models/block/" + name + ".json", tierModel(TARGET + ":block/" + base, textures));
        sink.raw("assets/" + UltimateModCompats.MODID + "/models/item/" + name + ".json", tierModel(TARGET + ":item/" + base, textures));
    }

    private static JsonObject tierModel(String parent, Map<String, String> textures) {
        JsonObject json = new JsonObject();
        textures.forEach(json::addProperty);
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);
        model.add("textures", json);
        return model;
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
