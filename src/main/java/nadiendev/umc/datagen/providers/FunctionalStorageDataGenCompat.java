package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.functionalstorage.FunctionalStorageFractalTabs;
import nadiendev.umc.functionalstorage.FunctionalStorageRegistry;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** Lang, item models and recipes of the storage upgrades of the materials. */
public class FunctionalStorageDataGenCompat implements DataModule {

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(FunctionalStorageFractalTabs.ID), "Func Storage");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(key(material), material.english() + " Storage Upgrade");
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(FunctionalStorageFractalTabs.ID), "Func Storage");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(key(material), "Mejora de almacenamiento de " + material.spanish());
        }
    }

    @Override
    public void json(UmcJsonSink sink) {
        for (UmcMaterial material : UmcMaterial.values()) {
            String id = FunctionalStorageRegistry.upgradeId(material);
            sink.assets("models/item/" + id, itemModel(id));
            sink.data("recipe/functionalstorage/" + id, recipe(material, id, false));
            if (material.previous() != null && !material.previous().mod().equals(material.mod())) {
                sink.data("recipe/functionalstorage/" + id + "_without_" + material.previous().mod(), recipe(material, id, true));
            }
        }
    }

    private static String key(UmcMaterial material) {
        return "item." + UltimateModCompats.MODID + "." + FunctionalStorageRegistry.upgradeId(material);
    }

    private static JsonObject itemModel(String texture) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        JsonObject layers = new JsonObject();
        layers.addProperty("layer0", UltimateModCompats.MODID + ":item/" + texture);
        model.add("textures", layers);
        return model;
    }

    /**
     * Made like the diamond upgrade of Functional Storage: the upgrade of the tier before in the middle, between two
     * wooden chests, and ingots and blocks of the material in the corners and the top and bottom. The first upgrade of
     * each chain starts from the netherite one, the best of Functional Storage; allthemodium goes on from nadienite.
     */
    private static JsonObject recipe(UmcMaterial material, String id, boolean fallback) {
        UmcMaterial previous = fallback ? null : material.previous();
        JsonObject recipe = new JsonObject();
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded("functionalstorage"));
        conditions.add(modLoaded(material.mod()));
        if (fallback) {
            conditions.add(notLoaded(material.previous().mod()));
        } else if (previous != null && !previous.mod().equals(material.mod())) {
            conditions.add(modLoaded(previous.mod()));
        }
        JsonObject compat = new JsonObject();
        compat.addProperty("type", UltimateModCompats.MODID + ":compat");
        compat.addProperty("compat", CompatSwitches.FUNCTIONAL_STORAGE);
        conditions.add(compat);
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        JsonObject key = new JsonObject();
        key.add("B", item(material.block()));
        key.add("I", item(material.ingot()));
        JsonObject chests = new JsonObject();
        chests.addProperty("tag", "c:chests/wooden");
        key.add("C", chests);
        key.add("D", item(previous == null ? "functionalstorage:netherite_upgrade"
                : UltimateModCompats.MODID + ":" + FunctionalStorageRegistry.upgradeId(previous)));
        recipe.add("key", key);
        JsonArray pattern = new JsonArray();
        pattern.add("IBI");
        pattern.add("CDC");
        pattern.add("IBI");
        recipe.add("pattern", pattern);
        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", UltimateModCompats.MODID + ":" + id);
        recipe.add("result", result);
        return recipe;
    }

    private static JsonObject notLoaded(String mod) {
        JsonObject not = new JsonObject();
        not.addProperty("type", "neoforge:not");
        not.add("value", modLoaded(mod));
        return not;
    }

    private static JsonObject modLoaded(String mod) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", mod);
        return condition;
    }

    private static JsonObject item(String id) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", id);
        return ingredient;
    }
}
