package nadiendev.umc.compat.kubejs.ae2.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public record AmountIngredientComponent(RecipeComponentType<?> type, RecipeComponent<SizedIngredient> parentComponent) implements RecipeComponentWithParent<SizedIngredient> {

    public static final Codec<SizedIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(SizedIngredient::ingredient),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("amount", 1).forGetter(SizedIngredient::count)
    ).apply(instance, SizedIngredient::new));

    @Override
    public Codec<SizedIngredient> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
