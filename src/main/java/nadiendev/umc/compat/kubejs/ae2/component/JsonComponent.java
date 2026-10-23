package nadiendev.umc.compat.kubejs.ae2.component;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.util.ExtraCodecs;

public record JsonComponent(RecipeComponentType<?> type) implements RecipeComponent<JsonElement> {

    @Override
    public Codec<JsonElement> codec() {
        return ExtraCodecs.JSON;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.RAW_MAP.or(TypeInfo.STRING);
    }

    @Override
    public JsonElement wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof JsonElement json) {
            return json;
        }
        return JsonUtils.of(cx.cx(), from);
    }

    @Override
    public boolean isEmpty(JsonElement value) {
        return value == null || value.isJsonNull();
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
