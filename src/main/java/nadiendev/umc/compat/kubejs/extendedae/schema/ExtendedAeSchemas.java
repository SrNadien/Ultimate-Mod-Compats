package nadiendev.umc.compat.kubejs.extendedae.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import java.util.List;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public final class ExtendedAeSchemas {

    private static final RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    private static final RecipeComponent<SizedIngredient> AMOUNT_INGREDIENT = Ae2Components.of(Ae2Components.AMOUNT_INGREDIENT);

    public static final RecipeKey<ItemStack> ASSEMBLER_OUTPUT = ITEM.outputKey("output");
    public static final RecipeKey<List<SizedIngredient>> ASSEMBLER_INPUT_ITEMS = AMOUNT_INGREDIENT.asList().inputKey("input_items").functionNames("inputItems", "items");
    public static final RecipeKey<SizedFluidIngredient> ASSEMBLER_INPUT_FLUID = SizedFluidIngredientComponent.NESTED.instance().inputKey("input_fluid").defaultOptional().functionNames("inputFluid", "fluid");

    public static final RecipeSchema CRYSTAL_ASSEMBLER = new RecipeSchema(ASSEMBLER_OUTPUT, ASSEMBLER_INPUT_ITEMS, ASSEMBLER_INPUT_FLUID)
            .constructor(ASSEMBLER_OUTPUT, ASSEMBLER_INPUT_ITEMS)
            .constructor(ASSEMBLER_OUTPUT, ASSEMBLER_INPUT_ITEMS, ASSEMBLER_INPUT_FLUID)
            .uniqueId(ASSEMBLER_OUTPUT);

    public static final RecipeKey<ItemStack> CUTTER_OUTPUT = ITEM.outputKey("output");
    public static final RecipeKey<SizedIngredient> CUTTER_INPUT = AMOUNT_INGREDIENT.inputKey("input");

    public static final RecipeSchema CIRCUIT_CUTTER = new RecipeSchema(CUTTER_OUTPUT, CUTTER_INPUT)
            .constructor(CUTTER_OUTPUT, CUTTER_INPUT)
            .uniqueId(CUTTER_OUTPUT);

    public static final RecipeKey<ItemStack> FIXER_INPUT = ITEM.inputKey("input");
    public static final RecipeKey<ItemStack> FIXER_OUTPUT = ITEM.outputKey("output");
    public static final RecipeKey<SizedIngredient> FIXER_FUEL = AMOUNT_INGREDIENT.inputKey("fuel");
    public static final RecipeKey<Integer> FIXER_CHANCE = NumberComponent.intRange(1, 10000).otherKey("chance");

    public static final RecipeSchema CRYSTAL_FIXER = new RecipeSchema(FIXER_OUTPUT, FIXER_INPUT, FIXER_FUEL, FIXER_CHANCE)
            .constructor(FIXER_OUTPUT, FIXER_INPUT, FIXER_FUEL, FIXER_CHANCE)
            .uniqueId(FIXER_OUTPUT);

    private ExtendedAeSchemas() {
    }
}
