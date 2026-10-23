package nadiendev.umc.compat.kubejs.excessiveutilities.component;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record BlockOrTagComponent(RecipeComponentType<?> type) implements RecipeComponent<String> {

    private static final Codec<String> CODEC = ResourceLocation.CODEC.xmap(ResourceLocation::toString, ResourceLocation::parse);

    @Override
    public Codec<String> codec() {
        return CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.STRING.or(TypeInfo.of(Block.class));
    }

    @Override
    public String wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof Block block) {
            return BuiltInRegistries.BLOCK.getKey(block).toString();
        }
        String text = String.valueOf(from).trim();
        if (text.startsWith("#")) {
            text = text.substring(1);
        }
        return ResourceLocation.parse(text).toString();
    }

    @Override
    public boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
