package nadiendev.umc.compat.kubejs.excessiveutilities.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedIngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import dev.latvian.mods.kubejs.util.TinyMap;
import java.util.List;
import java.util.Optional;
import nadiendev.umc.compat.kubejs.excessiveutilities.component.ExuComponents;
import nadiendev.umc.compat.kubejs.excessiveutilities.component.NestedComponent;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.DivisionInversion;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.FluidFuelGeneratorType;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.FuelGeneratorType;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.UnstableStability;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public final class ExuSchemas {

    private static final RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    private static final RecipeComponent<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.instance();
    private static final RecipeComponent<Ingredient> OPTIONAL_INGREDIENT = IngredientComponent.OPTIONAL_INGREDIENT.instance();
    private static final RecipeComponent<SizedIngredient> SIZED_INGREDIENT = SizedIngredientComponent.SIZED_INGREDIENT.instance();
    private static final RecipeComponent<SizedFluidIngredient> FLAT_FLUID = SizedFluidIngredientComponent.FLAT.instance();
    private static final RecipeComponent<String> BLOCK_OR_TAG = ExuComponents.of(ExuComponents.BLOCK_OR_TAG);

    public static final RecipeKey<Ingredient> CRUSHER_INGREDIENT = INGREDIENT.inputKey("ingredient");
    public static final RecipeKey<ItemStack> CRUSHER_PRIMARY_OUTPUT = ITEM.outputKey("primary_output");
    public static final RecipeKey<ItemStack> CRUSHER_SECONDARY_OUTPUT = ItemStackComponent.OPTIONAL_ITEM_STACK.instance().outputKey("secondary_output").optional(ItemStack.EMPTY);
    public static final RecipeKey<Float> CRUSHER_SECONDARY_CHANCE = NumberComponent.FLOAT.otherKey("secondary_chance").optional(0.0F);
    public static final RecipeKey<Integer> CRUSHER_TICKS = NumberComponent.INT.otherKey("ticks").optional(200);
    public static final RecipeKey<Integer> CRUSHER_FE_PER_TICK = NumberComponent.INT.otherKey("fe_per_tick").optional(20);

    public static final RecipeSchema CRUSHER = new RecipeSchema(CRUSHER_PRIMARY_OUTPUT, CRUSHER_INGREDIENT, CRUSHER_SECONDARY_OUTPUT, CRUSHER_SECONDARY_CHANCE, CRUSHER_TICKS, CRUSHER_FE_PER_TICK)
            .constructor(CRUSHER_PRIMARY_OUTPUT, CRUSHER_INGREDIENT)
            .constructor(CRUSHER_PRIMARY_OUTPUT, CRUSHER_INGREDIENT, CRUSHER_SECONDARY_OUTPUT, CRUSHER_SECONDARY_CHANCE)
            .uniqueId(CRUSHER_PRIMARY_OUTPUT);

    public static final RecipeKey<SizedIngredient> ENCHANTER_LEFT = SIZED_INGREDIENT.inputKey("left");
    public static final RecipeKey<SizedIngredient> ENCHANTER_RIGHT = SIZED_INGREDIENT.inputKey("right");
    public static final RecipeKey<Integer> ENCHANTER_FE_PER_TICK = NumberComponent.INT.otherKey("fe_per_tick");
    public static final RecipeKey<Integer> ENCHANTER_TICKS = NumberComponent.INT.otherKey("ticks");
    public static final RecipeKey<Float> ENCHANTER_POWER = NumberComponent.FLOAT.otherKey("enchanting_power").optional(15.0F);
    public static final RecipeKey<ItemStack> ENCHANTER_OUTPUT = ITEM.outputKey("output");

    public static final RecipeSchema ENCHANTER = new RecipeSchema(ENCHANTER_OUTPUT, ENCHANTER_LEFT, ENCHANTER_RIGHT, ENCHANTER_FE_PER_TICK, ENCHANTER_TICKS, ENCHANTER_POWER)
            .constructor(ENCHANTER_OUTPUT, ENCHANTER_LEFT, ENCHANTER_RIGHT, ENCHANTER_FE_PER_TICK, ENCHANTER_TICKS)
            .constructor(ENCHANTER_OUTPUT, ENCHANTER_LEFT, ENCHANTER_RIGHT, ENCHANTER_FE_PER_TICK, ENCHANTER_TICKS, ENCHANTER_POWER)
            .uniqueId(ENCHANTER_OUTPUT);

    public static final RecipeKey<ItemStack> QED_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<List<String>> QED_PATTERN = pattern();
    public static final RecipeKey<TinyMap<Character, Ingredient>> QED_KEY = key();
    public static final RecipeKey<Integer> QED_CRYSTAL_TICKS = NumberComponent.INT.otherKey("crystal_ticks");

    public static final RecipeSchema QED = new RecipeSchema(QED_RESULT, QED_PATTERN, QED_KEY, QED_CRYSTAL_TICKS)
            .constructor(QED_RESULT, QED_PATTERN, QED_KEY, QED_CRYSTAL_TICKS)
            .uniqueId(QED_RESULT);

    public static final RecipeKey<Ingredient> RESONATOR_INGREDIENT = INGREDIENT.inputKey("ingredient");
    public static final RecipeKey<ItemStack> RESONATOR_OUTPUT = ITEM.outputKey("output");
    public static final RecipeKey<Double> RESONATOR_GP_COST = NumberComponent.DOUBLE.otherKey("gp_cost");

    public static final RecipeSchema RESONATOR = new RecipeSchema(RESONATOR_OUTPUT, RESONATOR_INGREDIENT, RESONATOR_GP_COST)
            .constructor(RESONATOR_OUTPUT, RESONATOR_INGREDIENT, RESONATOR_GP_COST)
            .uniqueId(RESONATOR_OUTPUT);

    private static final ListRecipeComponent<String> BLOCK_LIST = ListRecipeComponent.create(BLOCK_OR_TAG, false, false, IntBounds.OPTIONAL, Optional.empty());

    public static final RecipeKey<String> WORLD_ITEM_ON = BLOCK_OR_TAG.otherKey("on").defaultOptional();
    public static final RecipeKey<List<String>> WORLD_ITEM_ADJACENT = BLOCK_LIST.otherKey("adjacent").optional(List.of());
    public static final RecipeKey<String> WORLD_ITEM_BEHIND = BLOCK_OR_TAG.otherKey("behind").defaultOptional();
    public static final RecipeKey<ItemStack> WORLD_ITEM_OUTPUT = ITEM.outputKey("output");

    public static final RecipeSchema WORLD_INTERACTION_ITEM = new RecipeSchema(WORLD_ITEM_OUTPUT, WORLD_ITEM_ON, WORLD_ITEM_ADJACENT, WORLD_ITEM_BEHIND)
            .constructor(WORLD_ITEM_OUTPUT)
            .constructor(WORLD_ITEM_OUTPUT, WORLD_ITEM_ON, WORLD_ITEM_ADJACENT)
            .constructor(WORLD_ITEM_OUTPUT, WORLD_ITEM_ON, WORLD_ITEM_ADJACENT, WORLD_ITEM_BEHIND)
            .uniqueId(WORLD_ITEM_OUTPUT);

    public static final RecipeKey<String> WORLD_FLUID_ON = BLOCK_OR_TAG.otherKey("on").defaultOptional();
    public static final RecipeKey<List<String>> WORLD_FLUID_ADJACENT = BLOCK_LIST.otherKey("adjacent").optional(List.of());
    public static final RecipeKey<String> WORLD_FLUID_BEHIND = BLOCK_OR_TAG.otherKey("behind").defaultOptional();
    public static final RecipeKey<FluidStack> WORLD_FLUID_OUTPUT = FluidStackComponent.FLUID_STACK.instance().outputKey("output");

    public static final RecipeSchema WORLD_INTERACTION_FLUID = new RecipeSchema(WORLD_FLUID_OUTPUT, WORLD_FLUID_ON, WORLD_FLUID_ADJACENT, WORLD_FLUID_BEHIND)
            .constructor(WORLD_FLUID_OUTPUT)
            .constructor(WORLD_FLUID_OUTPUT, WORLD_FLUID_ON, WORLD_FLUID_ADJACENT)
            .constructor(WORLD_FLUID_OUTPUT, WORLD_FLUID_ON, WORLD_FLUID_ADJACENT, WORLD_FLUID_BEHIND);

    public static final RecipeKey<FuelGeneratorType> SINGLE_FUEL_GENERATOR = ExuComponents.of(ExuComponents.FUEL_GENERATOR_TYPE).otherKey("generator_type");
    public static final RecipeKey<Ingredient> SINGLE_FUEL_INGREDIENT = INGREDIENT.inputKey("ingredient");
    public static final RecipeKey<Integer> SINGLE_FUEL_FE_PER_TICK = NumberComponent.INT.otherKey("fe_per_tick");
    public static final RecipeKey<Integer> SINGLE_FUEL_DURATION = NumberComponent.INT.otherKey("duration");

    public static final RecipeSchema SINGLE_ITEM_FUEL = new RecipeSchema(SINGLE_FUEL_GENERATOR, SINGLE_FUEL_INGREDIENT, SINGLE_FUEL_FE_PER_TICK, SINGLE_FUEL_DURATION)
            .constructor(SINGLE_FUEL_GENERATOR, SINGLE_FUEL_INGREDIENT, SINGLE_FUEL_FE_PER_TICK, SINGLE_FUEL_DURATION)
            .uniqueId(SINGLE_FUEL_INGREDIENT);

    public static final RecipeKey<FluidFuelGeneratorType> ITEM_FLUID_FUEL_GENERATOR = ExuComponents.of(ExuComponents.FLUID_FUEL_GENERATOR_TYPE).otherKey("generator_type");
    public static final RecipeKey<Ingredient> ITEM_FLUID_FUEL_ITEM = INGREDIENT.inputKey("item_ingredient");
    public static final RecipeKey<SizedFluidIngredient> ITEM_FLUID_FUEL_FLUID = FLAT_FLUID.inputKey("fluid_ingredient");
    public static final RecipeKey<Integer> ITEM_FLUID_FUEL_FE_PER_TICK = NumberComponent.INT.otherKey("fe_per_tick");
    public static final RecipeKey<Integer> ITEM_FLUID_FUEL_DURATION = NumberComponent.INT.otherKey("duration");

    public static final RecipeSchema ITEM_AND_FLUID_FUEL = new RecipeSchema(ITEM_FLUID_FUEL_GENERATOR, ITEM_FLUID_FUEL_ITEM, ITEM_FLUID_FUEL_FLUID, ITEM_FLUID_FUEL_FE_PER_TICK, ITEM_FLUID_FUEL_DURATION)
            .constructor(ITEM_FLUID_FUEL_GENERATOR, ITEM_FLUID_FUEL_ITEM, ITEM_FLUID_FUEL_FLUID, ITEM_FLUID_FUEL_FE_PER_TICK, ITEM_FLUID_FUEL_DURATION)
            .uniqueId(ITEM_FLUID_FUEL_ITEM);

    public static final RecipeKey<SizedFluidIngredient> MAGMATIC_FUEL_INGREDIENT = FLAT_FLUID.inputKey("ingredient");
    public static final RecipeKey<Integer> MAGMATIC_FUEL_FE_PER_TICK = NumberComponent.INT.otherKey("fe_per_tick");
    public static final RecipeKey<Integer> MAGMATIC_FUEL_DURATION = NumberComponent.INT.otherKey("duration");

    public static final RecipeSchema MAGMATIC_FUEL = new RecipeSchema(MAGMATIC_FUEL_INGREDIENT, MAGMATIC_FUEL_FE_PER_TICK, MAGMATIC_FUEL_DURATION)
            .constructor(MAGMATIC_FUEL_INGREDIENT, MAGMATIC_FUEL_FE_PER_TICK, MAGMATIC_FUEL_DURATION)
            .uniqueId(MAGMATIC_FUEL_INGREDIENT);

    public static final RecipeKey<ItemStack> SHAPED_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<List<String>> SHAPED_PATTERN = pattern();
    public static final RecipeKey<TinyMap<Character, Ingredient>> SHAPED_KEY = key();
    public static final RecipeKey<DivisionInversion> SHAPED_INVERSION = ExuComponents.of(ExuComponents.DIVISION_INVERSION).otherKey("inversion").optional(DivisionInversion.EITHER);
    public static final RecipeKey<UnstableStability> SHAPED_STABILITY = ExuComponents.of(ExuComponents.UNSTABLE_STABILITY).otherKey("stability").optional(UnstableStability.EITHER);

    public static final RecipeSchema SHAPED_DIVISION = new RecipeSchema(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY, SHAPED_INVERSION)
            .constructor(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY)
            .constructor(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY, SHAPED_INVERSION)
            .uniqueId(SHAPED_RESULT);

    public static final RecipeSchema SHAPED_UNSTABLE = new RecipeSchema(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY, SHAPED_STABILITY)
            .constructor(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY)
            .constructor(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY, SHAPED_STABILITY)
            .uniqueId(SHAPED_RESULT);

    public static final RecipeSchema KEEP_PAINTBRUSH = shaped();
    public static final RecipeSchema DAMAGE_GLASS_CUTTER = shaped();
    public static final RecipeSchema SNOW_GLOBE = shaped();

    private ExuSchemas() {
    }

    private static RecipeKey<List<String>> pattern() {
        return new NestedComponent<>(StringComponent.STRING.instance().asList(), "pattern").otherKey("pattern");
    }

    private static RecipeKey<TinyMap<Character, Ingredient>> key() {
        return new NestedComponent<>(OPTIONAL_INGREDIENT.asPatternKey(), "pattern").inputKey("key");
    }

    private static RecipeSchema shaped() {
        return new RecipeSchema(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY)
                .constructor(SHAPED_RESULT, SHAPED_PATTERN, SHAPED_KEY)
                .uniqueId(SHAPED_RESULT);
    }
}
