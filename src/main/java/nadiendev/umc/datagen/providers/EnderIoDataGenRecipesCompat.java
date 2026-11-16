package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.enderio.EnderIoRegistry;
import nadiendev.umc.enderio.EnderIoStats;

public class EnderIoDataGenRecipesCompat implements DataModule {

    @Override
    public void json(UmcJsonSink sink) {
        JsonObject balls = new JsonObject();
        for (UmcMaterial material : UmcMaterial.values()) {
            String ball = UltimateModCompats.MODID + ":" + material.id() + EnderIoRegistry.GRINDING_BALL;
            EnderIoStats stats = EnderIoStats.of(material);
            sink.data("recipe/enderio/" + material.id() + "_grinding_ball", grindingBallRecipe(material));
            sink.data("recipe/enderio/" + material.id() + "_capacitor", capacitorRecipe(material, false));
            if (material.previous() != null && !material.previous().mod().equals(material.mod())) {
                sink.data("recipe/enderio/" + material.id() + "_capacitor_without_" + material.previous().mod(), capacitorRecipe(material, true));
            }
            JsonObject entry = new JsonObject();
            entry.add("neoforge:conditions", conditions(material));
            entry.addProperty("BonusMultiplier", stats.bonusMultiplier());
            entry.addProperty("Durability", stats.ballDurability());
            entry.addProperty("OutputMultiplier", stats.outputMultiplier());
            entry.addProperty("PowerUse", stats.ballPowerUse());
            balls.add(ball, entry);
            for (String kind : EnderIoRegistry.CONDUIT_KINDS) {
                conduit(sink, material, kind, stats);
            }
        }
        JsonObject map = new JsonObject();
        map.add("values", balls);
        sink.data("enderio", "data_maps/item/grinding_ball", map);
    }

    private static void conduit(UmcJsonSink sink, UmcMaterial material, String kind, EnderIoStats stats) {
        String id = EnderIoRegistry.conduitId(material, kind);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(material));
        json.addProperty("type", "enderio:" + kind);
        JsonObject description = new JsonObject();
        description.addProperty("translate", "conduit." + UltimateModCompats.MODID + "." + id);
        json.add("description", description);
        json.addProperty("texture", UltimateModCompats.MODID + ":block/conduit/" + id);
        switch (kind) {
            case "item" -> {
                json.addProperty("ticks_per_cycle", stats.itemConduitTicksPerCycle());
                json.addProperty("transfer_rate", stats.itemConduitRate());
            }
            case "energy" -> json.addProperty("transfer_rate", stats.energyConduitRate());
            default -> json.addProperty("transfer_rate", stats.fluidConduitRate());
        }
        sink.data("enderio/conduit/" + id, json);

        // Like the conduits of EnderIO, every conduit has two recipes: one from the ingots of its material and
        // one that upgrades the conduit of the same kind of the material before it (the Ender conduit of EnderIO for
        // the first one).
        sink.data("recipe/enderio/" + id + "_conduit_from_ingots", conduitRecipe(material, kind, id, false));
        sink.data("recipe/enderio/" + id + "_conduit", conduitRecipe(material, kind, id, true));
    }

    private static JsonObject conduitRecipe(UmcMaterial material, String kind, String id, boolean upgrade) {
        UmcMaterial previous = material.previous();
        JsonArray conditions = conditions(material);
        if (upgrade && previous != null && !previous.mod().equals(material.mod())) {
            conditions.add(modLoaded(previous.mod()));
        }
        JsonObject recipe = new JsonObject();
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "building");
        JsonObject key = new JsonObject();
        key.add("B", item("enderio:conduit_binder"));
        key.add("I", item(material.ingot()));
        if (upgrade) {
            JsonObject conduit = new JsonObject();
            conduit.addProperty("type", "enderio:conduit");
            conduit.addProperty("conduit_type", previous == null
                    ? "enderio:ender_" + kind
                    : UltimateModCompats.MODID + ":" + EnderIoRegistry.conduitId(previous, kind));
            key.add("C", conduit);
        }
        recipe.add("key", key);
        recipe.add("pattern", upgrade ? array("BBB", "ICI", "BBB") : array("BBB", "III", "BBB"));
        JsonObject result = new JsonObject();
        JsonObject components = new JsonObject();
        components.addProperty("enderio:conduit", UltimateModCompats.MODID + ":" + id);
        result.add("components", components);
        result.addProperty("count", 8);
        result.addProperty("id", "enderio:conduit");
        recipe.add("result", result);
        return recipe;
    }

    private static JsonObject grindingBallRecipe(UmcMaterial material) {
        JsonObject recipe = new JsonObject();
        recipe.add("neoforge:conditions", conditions(material));
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        JsonObject key = new JsonObject();
        key.add("I", item(material.ingot()));
        recipe.add("key", key);
        recipe.add("pattern", array(" I ", "III", " I "));
        recipe.add("result", result(UltimateModCompats.MODID + ":" + material.id() + EnderIoRegistry.GRINDING_BALL, 24));
        return recipe;
    }

    /** Every capacitor is made from the capacitor of the material before it, so the three of them are a progression. */
    private static JsonObject capacitorRecipe(UmcMaterial material, boolean fallback) {
        JsonObject recipe = new JsonObject();
        UmcMaterial previous = fallback ? null : material.previous();
        JsonArray conditions = conditions(material);
        if (fallback) {
            conditions.add(notLoaded(material.previous().mod()));
        } else if (previous != null && !previous.mod().equals(material.mod())) {
            conditions.add(modLoaded(previous.mod()));
        }
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        JsonObject key = new JsonObject();
        key.add("I", item(material.ingot()));
        key.add("P", item(previous == null ? "enderio:octadic_capacitor" : UltimateModCompats.MODID + ":" + previous.id() + EnderIoRegistry.CAPACITOR));
        if (previous == null) {
            JsonObject alloy = new JsonObject();
            alloy.addProperty("tag", "c:ingots/vibrant_alloy");
            key.add("C", alloy);
        } else {
            if (previous.mod().equals(UmcMaterial.ATM_MOD)) {
                JsonObject gear = new JsonObject();
                gear.addProperty("tag", "c:gears/" + previous.id());
                key.add("C", gear);
            } else {
                key.add("C", item(previous.block()));
            }
        }
        recipe.add("key", key);
        recipe.add("pattern", array(" I ", "PCP", " I "));
        recipe.add("result", result(UltimateModCompats.MODID + ":" + material.id() + EnderIoRegistry.CAPACITOR, 1));
        return recipe;
    }

    private static JsonArray conditions(UmcMaterial material) {
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded("enderio"));
        conditions.add(modLoaded(material.mod()));
        JsonObject compat = new JsonObject();
        compat.addProperty("type", UltimateModCompats.MODID + ":compat");
        compat.addProperty("compat", CompatSwitches.ENDERIO);
        conditions.add(compat);
        return conditions;
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

    private static JsonObject result(String id, int count) {
        JsonObject result = new JsonObject();
        result.addProperty("count", count);
        result.addProperty("id", id);
        return result;
    }

    private static JsonArray array(String... rows) {
        JsonArray array = new JsonArray();
        for (String row : rows) {
            array.add(row);
        }
        return array;
    }
}
