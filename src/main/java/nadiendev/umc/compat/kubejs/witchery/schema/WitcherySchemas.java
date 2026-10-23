package nadiendev.umc.compat.kubejs.witchery.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import java.util.List;
import nadiendev.umc.compat.kubejs.witchery.component.WitcheryComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface WitcherySchemas {

    int DEFAULT_COOK_COLOUR = 0x2D155E;
    int DEFAULT_FINAL_COLOUR = 0x4A1AAD;

    RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    RecipeComponent<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.instance();
    RecipeComponent<Integer> HEX_COLOUR = WitcheryComponents.of(WitcheryComponents.HEX_COLOUR);

    RecipeKey<ItemStack> KETTLE_RESULT = ITEM.outputKey("result");
    RecipeKey<List<ItemStack>> KETTLE_INGREDIENTS = ITEM.asList().inputKey("ingredients");
    RecipeKey<Integer> KETTLE_POWER = NumberComponent.INT.otherKey("power").optional(0);
    RecipeKey<Boolean> KETTLE_BOTTLE = BooleanComponent.BOOLEAN.instance().otherKey("requires_bottle").optional(false);
    RecipeKey<Integer> KETTLE_COOK_COLOUR = HEX_COLOUR.otherKey("cook_colour").optional(DEFAULT_COOK_COLOUR).functionNames("cookColour", "cookColor");
    RecipeKey<Integer> KETTLE_FINAL_COLOUR = HEX_COLOUR.otherKey("final_colour").optional(DEFAULT_FINAL_COLOUR).functionNames("finalColour", "finalColor");

    RecipeSchema KETTLE = new RecipeSchema(KETTLE_RESULT, KETTLE_INGREDIENTS, KETTLE_POWER, KETTLE_BOTTLE, KETTLE_COOK_COLOUR, KETTLE_FINAL_COLOUR)
            .constructor(KETTLE_RESULT, KETTLE_INGREDIENTS);

    RecipeKey<ItemStack> SPINNING_RESULT = ITEM.outputKey("result");
    RecipeKey<List<ItemStack>> SPINNING_INGREDIENTS = ITEM.asList().withBounds(IntBounds.of(3, 3)).inputKey("ingredients");
    RecipeKey<Integer> SPINNING_POWER = NumberComponent.INT.otherKey("power").optional(0);
    RecipeKey<Integer> SPINNING_DURATION = NumberComponent.INT.otherKey("duration").optional(300);

    RecipeSchema SPINNING = new RecipeSchema(SPINNING_RESULT, SPINNING_INGREDIENTS, SPINNING_POWER, SPINNING_DURATION)
            .constructor(SPINNING_RESULT, SPINNING_INGREDIENTS);

    RecipeKey<List<ItemStack>> DISTILLING_INGREDIENTS = ITEM.asList().withBounds(IntBounds.of(1, 3)).inputKey("ingredients");
    RecipeKey<List<ItemStack>> DISTILLING_RESULTS = ITEM.asList().withBounds(IntBounds.of(1, 4)).outputKey("results");
    RecipeKey<Integer> DISTILLING_DURATION = NumberComponent.INT.otherKey("duration").optional(300);
    RecipeKey<Integer> DISTILLING_POWER = NumberComponent.INT.otherKey("power").optional(750);

    RecipeSchema DISTILLING = new RecipeSchema(DISTILLING_RESULTS, DISTILLING_INGREDIENTS, DISTILLING_DURATION, DISTILLING_POWER)
            .constructor(DISTILLING_RESULTS, DISTILLING_INGREDIENTS);

    RecipeKey<Ingredient> BYPRODUCT_INGREDIENT = INGREDIENT.inputKey("ingredient");
    RecipeKey<ItemStack> BYPRODUCT_RESULT = ITEM.outputKey("result");

    RecipeSchema BYPRODUCT = new RecipeSchema(BYPRODUCT_RESULT, BYPRODUCT_INGREDIENT)
            .constructor(BYPRODUCT_RESULT, BYPRODUCT_INGREDIENT);
}
