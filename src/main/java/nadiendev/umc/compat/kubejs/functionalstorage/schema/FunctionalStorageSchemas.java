package nadiendev.umc.compat.kubejs.functionalstorage.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BookCategoryComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.postprocessing.KeyPatternCleanupPostProcessor;
import dev.latvian.mods.kubejs.util.TinyMap;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

public final class FunctionalStorageSchemas {

    private static final RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    private static final RecipeComponent<Ingredient> OPTIONAL_INGREDIENT = IngredientComponent.OPTIONAL_INGREDIENT.instance();

    public static final RecipeKey<ItemStack> COMPACTING_LOWER = ITEM.inputKey("lower_input");
    public static final RecipeKey<ItemStack> COMPACTING_HIGHER = ITEM.inputKey("higher_input");

    public static final RecipeSchema CUSTOM_COMPACTING = new RecipeSchema(COMPACTING_LOWER, COMPACTING_HIGHER)
            .constructor(COMPACTING_LOWER, COMPACTING_HIGHER);

    public static final RecipeKey<ItemStack> COPY_RESULT = ITEM.outputKey("result");
    public static final RecipeKey<List<String>> COPY_PATTERN = StringComponent.STRING.instance().asList().otherKey("pattern");
    public static final RecipeKey<TinyMap<Character, Ingredient>> COPY_KEY = OPTIONAL_INGREDIENT.asPatternKey().inputKey("key");
    public static final RecipeKey<List<String>> COPY_COMPONENTS = StringComponent.ID.instance().asList().otherKey("components");
    public static final RecipeKey<Integer> COPY_INDEX = NumberComponent.INT.otherKey("copyIndex");
    public static final RecipeKey<CraftingBookCategory> COPY_CATEGORY = BookCategoryComponent.CRAFTING_BOOK_CATEGORY.instance().otherKey("category").optional(CraftingBookCategory.MISC);

    public static final RecipeSchema COPY_COMPONENTS_RECIPE = new RecipeSchema(COPY_RESULT, COPY_PATTERN, COPY_KEY, COPY_COMPONENTS, COPY_INDEX, COPY_CATEGORY)
            .constructor(COPY_RESULT, COPY_PATTERN, COPY_KEY, COPY_COMPONENTS, COPY_INDEX)
            .postProcessor(new KeyPatternCleanupPostProcessor("pattern", "key", OPTIONAL_INGREDIENT))
            .uniqueId(COPY_RESULT);

    public static final RecipeKey<CraftingBookCategory> FRAMED_CATEGORY = BookCategoryComponent.CRAFTING_BOOK_CATEGORY.instance().otherKey("category").optional(CraftingBookCategory.MISC).alwaysWrite();

    public static final RecipeSchema FRAMED_RECIPE = new RecipeSchema(FRAMED_CATEGORY);

    private FunctionalStorageSchemas() {
    }
}
