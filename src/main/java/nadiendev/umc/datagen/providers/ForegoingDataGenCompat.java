package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.foregoing.ForegoingFractalTabs;
import nadiendev.umc.foregoing.ForegoingRegistry;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** Lang, item models and recipes of the Industrial Foregoing addons. */
public class ForegoingDataGenCompat implements DataModule {

    private static final String[] KINDS_EN = {"Range", "Speed", "Efficiency", "Processing"};
    private static final String[] KINDS_ES = {"rango", "velocidad", "eficiencia", "procesamiento"};
    /** The best addon of Industrial Foregoing of each kind, which the first material is made from. */
    private static final String[] TOP_ADDONS = {"range_addon_tier_11", "speed_addon_tier_2", "efficiency_addon_tier_2", "processing_addon_tier_2"};

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ForegoingFractalTabs.ID), "Ind For");
        for (UmcMaterial material : UmcMaterial.values()) {
            for (int kind = 0; kind < ForegoingRegistry.KINDS.length; kind++) {
                lang.add(key(material, kind), material.english() + " " + KINDS_EN[kind] + " Addon");
            }
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ForegoingFractalTabs.ID), "Ind For");
        for (UmcMaterial material : UmcMaterial.values()) {
            for (int kind = 0; kind < ForegoingRegistry.KINDS.length; kind++) {
                lang.add(key(material, kind), "Mejora de " + KINDS_ES[kind] + " de " + material.spanish());
            }
        }
    }

    @Override
    public void json(UmcJsonSink sink) {
        for (UmcMaterial material : UmcMaterial.values()) {
            for (int kind = 0; kind < ForegoingRegistry.KINDS.length; kind++) {
                String id = ForegoingRegistry.addonId(material, ForegoingRegistry.KINDS[kind]);
                sink.assets("models/item/" + id, itemModel(id));
                sink.data("recipe/industrialforegoing/" + id, recipe(material, kind, id, false));
                if (material.previous() != null && !material.previous().mod().equals(material.mod())) {
                    sink.data("recipe/industrialforegoing/" + id + "_without_" + material.previous().mod(), recipe(material, kind, id, true));
                }
            }
        }
    }

    private static String key(UmcMaterial material, int kind) {
        return "item." + UltimateModCompats.MODID + "." + ForegoingRegistry.addonId(material, ForegoingRegistry.KINDS[kind]);
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
     * The addons are made in the dissolution chamber, like the ones of the Endless World pack: the first material of
     * each mod starts from the best addon of Industrial Foregoing, and the other ones from the addon of the material
     * before them, around its plates and block. Allthemodium goes on from nadienite.
     */
    private static JsonObject recipe(UmcMaterial material, int kind, String id, boolean fallback) {
        UmcMaterial previous = fallback ? null : material.previous();
        JsonObject recipe = new JsonObject();
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded("industrialforegoing"));
        conditions.add(modLoaded(material.mod()));
        if (fallback) {
            conditions.add(notLoaded(material.previous().mod()));
        } else if (previous != null && !previous.mod().equals(material.mod())) {
            conditions.add(modLoaded(previous.mod()));
        }
        JsonObject compat = new JsonObject();
        compat.addProperty("type", UltimateModCompats.MODID + ":compat");
        compat.addProperty("compat", CompatSwitches.INDUSTRIAL_FOREGOING);
        conditions.add(compat);
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "industrialforegoing:dissolution_chamber");
        JsonArray input = new JsonArray();
        boolean fromPrevious = previous != null && material.mod().equals(UmcMaterial.ATM_MOD);
        if (fromPrevious) {
            input.add(item(UltimateModCompats.MODID + ":" + ForegoingRegistry.addonId(previous, ForegoingRegistry.KINDS[kind])));
            for (int plate = 0; plate < 4; plate++) {
                input.add(item(material.plate()));
            }
            input.add(item(material.block()));
        } else {
            input.add(item("industrialforegoing:" + TOP_ADDONS[kind]));
            input.add(item("industrialforegoing:" + TOP_ADDONS[kind]));
            for (int ingot = 0; ingot < 4; ingot++) {
                input.add(item(material.mod().equals(UmcMaterial.ATM_MOD) ? material.plate() : material.ingot()));
            }
            if (material.mod().equals(UmcMaterial.ATM_MOD)) {
                input.set(2, item(material.block()));
            }
        }
        input.add(item("industrialforegoing:plastic"));
        input.add(item("industrialforegoing:plastic"));
        recipe.add("input", input);
        JsonObject fluid = new JsonObject();
        fluid.addProperty("amount", 1000);
        fluid.addProperty("fluid", "industrialforegoing:latex");
        recipe.add("inputFluid", fluid);
        JsonObject output = new JsonObject();
        output.addProperty("count", 1);
        output.addProperty("id", UltimateModCompats.MODID + ":" + id);
        recipe.add("output", output);
        recipe.addProperty("processingTime", processingTime(material));
        return recipe;
    }

    private static int processingTime(UmcMaterial material) {
        return switch (material) {
            case ALLTHEMODIUM -> 600;
            case VIBRANIUM -> 800;
            case UNOBTAINIUM -> 1200;
            case PIGLICH -> 1500;
            default -> 400;
        };
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
