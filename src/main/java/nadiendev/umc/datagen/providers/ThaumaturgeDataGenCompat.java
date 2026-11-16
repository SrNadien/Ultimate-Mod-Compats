package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.thaumaturge.ThaumaturgeFractalTabs;
import nadiendev.umc.thaumaturge.ThaumaturgeRegistry;
import nadiendev.umc.thaumaturge.ThaumaturgeStats;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** Lang, item models, tags and recipes of the Thaumaturge wand caps and rods. */
public class ThaumaturgeDataGenCompat implements DataModule {

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ThaumaturgeFractalTabs.ID), "Thaumaturge");
        for (UmcMaterial material : wandMaterials()) {
            lang.add(key(ThaumaturgeRegistry.capId(material)), material.english() + " Wand Cap");
            lang.add(key(ThaumaturgeRegistry.rodId(material)), material.english() + " Wand Rod");
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ThaumaturgeFractalTabs.ID), "Thaumaturge");
        for (UmcMaterial material : wandMaterials()) {
            lang.add(key(ThaumaturgeRegistry.capId(material)), "Punta de varita de " + material.spanish());
            lang.add(key(ThaumaturgeRegistry.rodId(material)), "Palo de varita de " + material.spanish());
        }
    }

    @Override
    public void json(UmcJsonSink sink) {
        JsonArray caps = new JsonArray();
        JsonArray rods = new JsonArray();
        for (UmcMaterial material : wandMaterials()) {
            ThaumaturgeStats stats = ThaumaturgeStats.of(material);
            String cap = ThaumaturgeRegistry.capId(material);
            String rod = ThaumaturgeRegistry.rodId(material);
            sink.assets("models/item/" + cap, itemModel("minecraft:item/generated", cap));
            sink.assets("models/item/" + rod, itemModel("minecraft:item/handheld", rod));
            sink.data("recipe/thaumaturge/" + cap, recipe(material, cap, stats.partVis(), "III", "I I"));
            sink.data("recipe/thaumaturge/" + rod, recipe(material, rod, stats.partVis(), "  I", " I ", "I  "));
            caps.add(optional(cap));
            rods.add(optional(rod));
        }
        sink.data("thaumaturge", "tags/item/wand_caps", tag(caps));
        sink.data("thaumaturge", "tags/item/wand_rods", tag(rods));
    }

    private static List<UmcMaterial> wandMaterials() {
        return Arrays.stream(UmcMaterial.values()).filter(UmcMaterial::hasWands).toList();
    }

    private static String key(String id) {
        return "item." + UltimateModCompats.MODID + "." + id;
    }

    private static JsonObject itemModel(String parent, String texture) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);
        JsonObject layers = new JsonObject();
        layers.addProperty("layer0", UltimateModCompats.MODID + ":item/" + texture);
        model.add("textures", layers);
        return model;
    }

    private static JsonObject tag(JsonArray values) {
        JsonObject tag = new JsonObject();
        tag.addProperty("replace", false);
        tag.add("values", values);
        return tag;
    }

    private static JsonObject optional(String id) {
        JsonObject entry = new JsonObject();
        entry.addProperty("id", UltimateModCompats.MODID + ":" + id);
        entry.addProperty("required", false);
        return entry;
    }

    private static JsonObject recipe(UmcMaterial material, String result, int vis, String... rows) {
        JsonObject recipe = new JsonObject();
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded("thaumaturge"));
        conditions.add(modLoaded(material.mod()));
        JsonObject compat = new JsonObject();
        compat.addProperty("type", UltimateModCompats.MODID + ":compat");
        compat.addProperty("compat", CompatSwitches.THAUMATURGE_WANDS);
        conditions.add(compat);
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "thaumaturge:arcane_workbench_shaped");
        recipe.add("crystals", new JsonArray());
        JsonObject key = new JsonObject();
        JsonObject ingot = new JsonObject();
        ingot.addProperty("item", material.ingot());
        key.add("I", ingot);
        recipe.add("key", key);
        JsonArray pattern = new JsonArray();
        for (String row : rows) {
            pattern.add(row);
        }
        recipe.add("pattern", pattern);
        JsonObject research = new JsonObject();
        research.addProperty("entry", "thaumaturge:base_auromancy");
        recipe.add("research", research);
        JsonObject output = new JsonObject();
        output.addProperty("count", 1);
        output.addProperty("id", UltimateModCompats.MODID + ":" + result);
        recipe.add("result", output);
        recipe.addProperty("vis", vis);
        return recipe;
    }

    private static JsonObject modLoaded(String mod) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", mod);
        return condition;
    }
}
