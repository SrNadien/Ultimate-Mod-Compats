package nadiendev.umc.compat.kubejs.ae2.kube;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.rhino.Context;
import nadiendev.umc.compat.kubejs.ae2.schema.Ae2Schemas;
import nadiendev.umc.compat.kubejs.ae2.type.EntropyMode;

public class EntropyKubeRecipe extends Ae2KubeRecipe {

    public KubeRecipe heat() {
        return setValue(Ae2Schemas.ENTROPY_MODE, EntropyMode.HEAT);
    }

    public KubeRecipe cool() {
        return setValue(Ae2Schemas.ENTROPY_MODE, EntropyMode.COOL);
    }

    public KubeRecipe inputBlock(String id) {
        return put(Ae2Schemas.ENTROPY_INPUT, "block", stateJson(null, id, null));
    }

    public KubeRecipe inputBlock(Context cx, String id, Object properties) {
        return put(Ae2Schemas.ENTROPY_INPUT, "block", stateJson(cx, id, properties));
    }

    public KubeRecipe inputFluid(String id) {
        return put(Ae2Schemas.ENTROPY_INPUT, "fluid", stateJson(null, id, null));
    }

    public KubeRecipe inputFluid(Context cx, String id, Object properties) {
        return put(Ae2Schemas.ENTROPY_INPUT, "fluid", stateJson(cx, id, properties));
    }

    public KubeRecipe outputBlock(String id) {
        return put(Ae2Schemas.ENTROPY_OUTPUT, "block", stateJson(null, id, null));
    }

    public KubeRecipe outputBlock(Context cx, String id, Object properties) {
        return put(Ae2Schemas.ENTROPY_OUTPUT, "block", stateJson(cx, id, properties));
    }

    public KubeRecipe outputFluid(String id) {
        return put(Ae2Schemas.ENTROPY_OUTPUT, "fluid", stateJson(null, id, null));
    }

    public KubeRecipe outputFluid(Context cx, String id, Object properties) {
        return put(Ae2Schemas.ENTROPY_OUTPUT, "fluid", stateJson(cx, id, properties));
    }

    public KubeRecipe drop(Context cx, Object item) {
        JsonObject output = objectCopy(Ae2Schemas.ENTROPY_OUTPUT);
        JsonArray drops = output.has("drops") && output.get("drops").isJsonArray() ? output.getAsJsonArray("drops").deepCopy() : new JsonArray();
        drops.add(itemJson(cx, item));
        output.add("drops", drops);
        return setValue(Ae2Schemas.ENTROPY_OUTPUT, output);
    }

    private KubeRecipe put(RecipeKey<JsonElement> key, String field, JsonObject state) {
        JsonObject object = objectCopy(key);
        object.add(field, state);
        return setValue(key, object);
    }
}
