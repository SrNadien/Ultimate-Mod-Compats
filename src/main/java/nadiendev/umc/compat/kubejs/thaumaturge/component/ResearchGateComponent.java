package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.Optional;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;

public record ResearchGateComponent(RecipeComponentType<?> type) implements RecipeComponent<ResearchGate> {

    @Override
    public Codec<ResearchGate> codec() {
        return ResearchGate.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(ResearchGate.class).or(TypeInfo.STRING).or(TypeInfo.RAW_MAP);
    }

    @Override
    public ResearchGate wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof ResearchGate gate) {
            return gate;
        }
        if (from instanceof CharSequence text) {
            return parse(text.toString());
        }
        JsonElement json = JsonUtils.of(cx.cx(), from);
        return ResearchGate.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(IllegalArgumentException::new);
    }

    public static ResearchGate parse(String raw) {
        String text = raw.trim();
        boolean negate = text.startsWith("!");
        if (negate) {
            text = text.substring(1);
        }
        Optional<Integer> stage = Optional.empty();
        int at = text.lastIndexOf('@');
        if (at >= 0) {
            stage = Optional.of(Integer.parseInt(text.substring(at + 1)));
            text = text.substring(0, at);
        }
        return new ResearchGate(ThaumaturgeIds.thaumaturge(text), stage, negate);
    }

    @Override
    public boolean isEmpty(ResearchGate value) {
        return value == null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
