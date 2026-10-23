package nadiendev.umc.compat.kubejs.advancedae.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import java.util.List;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;
import nadiendev.umc.compat.kubejs.ae2.type.AeStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public final class AdvancedAeSchemas {

    public static final RecipeKey<AeStack> REACTION_OUTPUT = Ae2Components.of(Ae2Components.AE_STACK).outputKey("output");
    public static final RecipeKey<List<SizedIngredient>> REACTION_INPUT_ITEMS = Ae2Components.of(Ae2Components.AMOUNT_INGREDIENT).asList().inputKey("input_items").functionNames("inputItems", "items");
    public static final RecipeKey<SizedFluidIngredient> REACTION_INPUT_FLUID = SizedFluidIngredientComponent.NESTED.instance().inputKey("input_fluid").functionNames("inputFluid", "fluid");
    public static final RecipeKey<Integer> REACTION_INPUT_ENERGY = NumberComponent.INT.otherKey("input_energy").functionNames("inputEnergy", "energy");

    public static final RecipeSchema REACTION = new RecipeSchema(REACTION_OUTPUT, REACTION_INPUT_ITEMS, REACTION_INPUT_FLUID, REACTION_INPUT_ENERGY)
            .constructor(REACTION_OUTPUT, REACTION_INPUT_ITEMS, REACTION_INPUT_FLUID, REACTION_INPUT_ENERGY);

    private AdvancedAeSchemas() {
    }
}
