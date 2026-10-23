package nadiendev.umc.compat.kubejs.create.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.create.kube.ConversionKubeRecipe;
import nadiendev.umc.compat.kubejs.create.kube.ItemApplicationKubeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class CreateSchemas {

    private static final RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    private static final RecipeComponent<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.instance();

    public static final RecipeKey<List<ItemStack>> ITEM_APPLICATION_RESULTS = ITEM.asList().outputKey("results");
    public static final RecipeKey<List<Ingredient>> ITEM_APPLICATION_INGREDIENTS = INGREDIENT.asList().withBounds(IntBounds.of(2, 2)).inputKey("ingredients");
    public static final RecipeKey<Boolean> KEEP_HELD_ITEM = BooleanComponent.BOOLEAN.otherKey("keep_held_item").optional(false);

    public static final RecipeSchema ITEM_APPLICATION = new RecipeSchema(ITEM_APPLICATION_RESULTS, ITEM_APPLICATION_INGREDIENTS, KEEP_HELD_ITEM)
            .factory(new KubeRecipeFactory(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "create_item_application"), ItemApplicationKubeRecipe.class, ItemApplicationKubeRecipe::new))
            .constructor(ITEM_APPLICATION_RESULTS, ITEM_APPLICATION_INGREDIENTS)
            .uniqueId(ITEM_APPLICATION_RESULTS);

    private static final int MAX_PROCESSING_RESULTS = 12;

    public static final RecipeKey<List<ItemStack>> CONVERSION_RESULTS = ITEM.asList().withBounds(IntBounds.of(1, 1)).outputKey("results");
    public static final RecipeKey<List<Ingredient>> CONVERSION_INGREDIENTS = INGREDIENT.asList().withBounds(IntBounds.of(1, 1)).inputKey("ingredients");

    public static final RecipeSchema CONVERSION = new RecipeSchema(CONVERSION_RESULTS, CONVERSION_INGREDIENTS)
            .factory(new KubeRecipeFactory(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "create_conversion"), ConversionKubeRecipe.class, ConversionKubeRecipe::new))
            .uniqueId(CONVERSION_RESULTS);

    public static final RecipeKey<List<ItemStack>> HAUNTING_RESULTS = ITEM.asList().withBounds(IntBounds.of(1, MAX_PROCESSING_RESULTS)).outputKey("results");

    public static final RecipeSchema HAUNTING = new RecipeSchema(HAUNTING_RESULTS, CONVERSION_INGREDIENTS)
            .uniqueId(HAUNTING_RESULTS);

    private CreateSchemas() {
    }
}
