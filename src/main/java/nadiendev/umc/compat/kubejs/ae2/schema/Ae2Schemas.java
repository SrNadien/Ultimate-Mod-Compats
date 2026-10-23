package nadiendev.umc.compat.kubejs.ae2.schema;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BlockComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import java.util.List;
import java.util.function.Supplier;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;
import nadiendev.umc.compat.kubejs.ae2.component.NestedComponent;
import nadiendev.umc.compat.kubejs.ae2.kube.EntropyKubeRecipe;
import nadiendev.umc.compat.kubejs.ae2.kube.InscriberKubeRecipe;
import nadiendev.umc.compat.kubejs.ae2.kube.TransformKubeRecipe;
import nadiendev.umc.compat.kubejs.ae2.type.EntropyMode;
import nadiendev.umc.compat.kubejs.ae2.type.InscriberMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public final class Ae2Schemas {

    private static final RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    private static final RecipeComponent<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.instance();
    private static final RecipeComponent<Ingredient> OPTIONAL_INGREDIENT = IngredientComponent.OPTIONAL_INGREDIENT.instance();
    private static final RecipeComponent<JsonElement> JSON = Ae2Components.of(Ae2Components.JSON);
    private static final RecipeComponent<Item> ITEM_ID = Ae2Components.of(Ae2Components.ITEM_ID);

    public static final RecipeKey<ItemStack> INSCRIBER_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<Ingredient> INSCRIBER_MIDDLE = new NestedComponent<>(INGREDIENT, "ingredients").inputKey("middle");
    public static final RecipeKey<Ingredient> INSCRIBER_TOP = new NestedComponent<>(OPTIONAL_INGREDIENT, "ingredients").inputKey("top").optional(Ingredient.EMPTY);
    public static final RecipeKey<Ingredient> INSCRIBER_BOTTOM = new NestedComponent<>(OPTIONAL_INGREDIENT, "ingredients").inputKey("bottom").optional(Ingredient.EMPTY);
    public static final RecipeKey<InscriberMode> INSCRIBER_MODE = Ae2Components.of(Ae2Components.INSCRIBER_MODE).otherKey("mode").optional(InscriberMode.INSCRIBE).alwaysWrite();

    public static final RecipeSchema INSCRIBER = new RecipeSchema(INSCRIBER_RESULT, INSCRIBER_MIDDLE, INSCRIBER_TOP, INSCRIBER_BOTTOM, INSCRIBER_MODE)
            .factory(factory("ae2_inscriber", InscriberKubeRecipe.class, InscriberKubeRecipe::new))
            .constructor(INSCRIBER_RESULT, INSCRIBER_MIDDLE)
            .constructor(INSCRIBER_RESULT, INSCRIBER_MIDDLE, INSCRIBER_TOP)
            .constructor(INSCRIBER_RESULT, INSCRIBER_MIDDLE, INSCRIBER_TOP, INSCRIBER_BOTTOM)
            .constructor(INSCRIBER_RESULT, INSCRIBER_MIDDLE, INSCRIBER_TOP, INSCRIBER_BOTTOM, INSCRIBER_MODE)
            .uniqueId(INSCRIBER_RESULT);

    public static final RecipeKey<ItemStack> TRANSFORM_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<List<Ingredient>> TRANSFORM_INGREDIENTS = INGREDIENT.asList().inputKey("ingredients");
    public static final RecipeKey<JsonElement> TRANSFORM_CIRCUMSTANCE = JSON.otherKey("circumstance").defaultOptional();

    public static final RecipeSchema TRANSFORM = new RecipeSchema(TRANSFORM_RESULT, TRANSFORM_INGREDIENTS, TRANSFORM_CIRCUMSTANCE)
            .factory(factory("ae2_transform", TransformKubeRecipe.class, TransformKubeRecipe::new))
            .constructor(TRANSFORM_RESULT, TRANSFORM_INGREDIENTS)
            .uniqueId(TRANSFORM_RESULT);

    public static final RecipeKey<ItemStack> CHARGER_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<Ingredient> CHARGER_INGREDIENT = INGREDIENT.inputKey("ingredient");

    public static final RecipeSchema CHARGER = new RecipeSchema(CHARGER_RESULT, CHARGER_INGREDIENT)
            .constructor(CHARGER_RESULT, CHARGER_INGREDIENT)
            .uniqueId(CHARGER_RESULT);

    public static final RecipeKey<Ingredient> MATTER_CANNON_AMMO = INGREDIENT.inputKey("ammo");
    public static final RecipeKey<Float> MATTER_CANNON_WEIGHT = NumberComponent.FLOAT.otherKey("weight");

    public static final RecipeSchema MATTER_CANNON = new RecipeSchema(MATTER_CANNON_AMMO, MATTER_CANNON_WEIGHT)
            .constructor(MATTER_CANNON_AMMO, MATTER_CANNON_WEIGHT);

    public static final RecipeKey<EntropyMode> ENTROPY_MODE = Ae2Components.of(Ae2Components.ENTROPY_MODE).otherKey("mode");
    public static final RecipeKey<JsonElement> ENTROPY_INPUT = JSON.otherKey("input").optional(new JsonObject()).alwaysWrite();
    public static final RecipeKey<JsonElement> ENTROPY_OUTPUT = JSON.otherKey("output").optional(new JsonObject()).alwaysWrite();

    public static final RecipeSchema ENTROPY = new RecipeSchema(ENTROPY_MODE, ENTROPY_INPUT, ENTROPY_OUTPUT)
            .factory(factory("ae2_entropy", EntropyKubeRecipe.class, EntropyKubeRecipe::new))
            .constructor(ENTROPY_MODE)
            .constructor(ENTROPY_MODE, ENTROPY_INPUT, ENTROPY_OUTPUT);

    public static final RecipeKey<Item> CELL_UPGRADE_INPUT_CELL = ITEM_ID.otherKey("input_cell");
    public static final RecipeKey<Item> CELL_UPGRADE_INPUT_COMPONENT = ITEM_ID.otherKey("input_component");
    public static final RecipeKey<Item> CELL_UPGRADE_RESULT_CELL = ITEM_ID.otherKey("result_cell");
    public static final RecipeKey<Item> CELL_UPGRADE_RESULT_COMPONENT = ITEM_ID.otherKey("result_component");

    public static final RecipeSchema STORAGE_CELL_UPGRADE = new RecipeSchema(CELL_UPGRADE_INPUT_CELL, CELL_UPGRADE_INPUT_COMPONENT, CELL_UPGRADE_RESULT_CELL, CELL_UPGRADE_RESULT_COMPONENT)
            .constructor(CELL_UPGRADE_INPUT_CELL, CELL_UPGRADE_INPUT_COMPONENT, CELL_UPGRADE_RESULT_CELL, CELL_UPGRADE_RESULT_COMPONENT);

    public static final RecipeKey<Item> DISASSEMBLY_CELL = ITEM_ID.otherKey("cell");
    public static final RecipeKey<List<ItemStack>> DISASSEMBLY_ITEMS = ITEM.asList().outputKey("cell_disassembly_items");

    public static final RecipeSchema STORAGE_CELL_DISASSEMBLY = new RecipeSchema(DISASSEMBLY_CELL, DISASSEMBLY_ITEMS)
            .constructor(DISASSEMBLY_CELL, DISASSEMBLY_ITEMS);

    public static final RecipeKey<Item> CRAFTING_UNIT_UPGRADE_ITEM = ITEM_ID.otherKey("upgrade_item");
    public static final RecipeKey<Block> CRAFTING_UNIT_UPGRADED_BLOCK = BlockComponent.BLOCK.instance().otherKey("upgraded_block");

    public static final RecipeSchema CRAFTING_UNIT_TRANSFORM = new RecipeSchema(CRAFTING_UNIT_UPGRADE_ITEM, CRAFTING_UNIT_UPGRADED_BLOCK)
            .constructor(CRAFTING_UNIT_UPGRADE_ITEM, CRAFTING_UNIT_UPGRADED_BLOCK);

    public static final RecipeKey<ItemStack> QUARTZ_CUTTING_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<List<Ingredient>> QUARTZ_CUTTING_INGREDIENTS = INGREDIENT.asList().withBounds(IntBounds.of(1, 9)).inputKey("ingredients");

    public static final RecipeSchema QUARTZ_CUTTING = new RecipeSchema(QUARTZ_CUTTING_RESULT, QUARTZ_CUTTING_INGREDIENTS)
            .constructor(QUARTZ_CUTTING_RESULT, QUARTZ_CUTTING_INGREDIENTS)
            .uniqueId(QUARTZ_CUTTING_RESULT);

    public static final RecipeSchema SPECIAL = new RecipeSchema();

    private Ae2Schemas() {
    }

    private static KubeRecipeFactory factory(String name, Class<?> type, Supplier<? extends KubeRecipe> supplier) {
        return new KubeRecipeFactory(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name), type, supplier);
    }
}
