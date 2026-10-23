package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.type.TypeInfo;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;

public record AspectAmountComponent(RecipeComponentType<?> type) implements RecipeComponent<AspectAmount> {

    @Override
    public Codec<AspectAmount> codec() {
        return AspectAmount.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(AspectAmount.class).or(TypeInfo.STRING).or(TypeInfo.RAW_MAP);
    }

    @Override
    public AspectAmount wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof AspectAmount amount) {
            return amount;
        }
        JsonObject json = from instanceof CharSequence text ? ThaumaturgeJson.aspect(text.toString()) : JsonUtils.objectOf(cx.cx(), from);
        if (json.has("aspect") && json.get("aspect").isJsonPrimitive() && json.get("aspect").getAsString().indexOf(':') < 0) {
            json.addProperty("aspect", "thaumaturge:" + json.get("aspect").getAsString());
        }
        return AspectAmount.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean isEmpty(AspectAmount value) {
        return value == null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
