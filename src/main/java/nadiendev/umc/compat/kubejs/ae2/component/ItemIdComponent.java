package nadiendev.umc.compat.kubejs.ae2.component;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.UniqueIdBuilder;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public record ItemIdComponent(RecipeComponentType<?> type) implements RecipeComponent<Item> {

    @Override
    public Codec<Item> codec() {
        return BuiltInRegistries.ITEM.byNameCodec();
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.STRING.or(TypeInfo.of(Item.class)).or(TypeInfo.of(ItemStack.class));
    }

    @Override
    public Item wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof ItemStack stack) {
            return stack.getItem();
        }
        if (from instanceof ItemLike item) {
            return item.asItem();
        }
        ResourceLocation id = ResourceLocation.parse(String.valueOf(from).trim());
        return BuiltInRegistries.ITEM.getOptional(id).orElseThrow(() -> new IllegalArgumentException("Objeto desconocido '" + id + "'"));
    }

    @Override
    public void buildUniqueId(UniqueIdBuilder builder, Item value) {
        builder.append(BuiltInRegistries.ITEM.getKey(value));
    }

    @Override
    public boolean isEmpty(Item value) {
        return value == null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
