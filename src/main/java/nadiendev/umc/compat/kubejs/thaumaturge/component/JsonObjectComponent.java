package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.type.TypeInfo;

public record JsonObjectComponent(RecipeComponentType<?> type) implements RecipeComponent<JsonObject> {

    private static final Codec<JsonObject> CODEC = Codec.PASSTHROUGH.xmap(
            dynamic -> dynamic.convert(JsonOps.INSTANCE).getValue().getAsJsonObject(),
            json -> new Dynamic<JsonElement>(JsonOps.INSTANCE, json)
    );

    @Override
    public Codec<JsonObject> codec() {
        return CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(JsonObject.class).or(TypeInfo.RAW_MAP);
    }

    @Override
    public JsonObject wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof JsonObject json) {
            return json;
        }
        return JsonUtils.objectOf(cx.cx(), from);
    }

    @Override
    public boolean isEmpty(JsonObject value) {
        return value == null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
