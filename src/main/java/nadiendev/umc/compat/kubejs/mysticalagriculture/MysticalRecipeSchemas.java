package nadiendev.umc.compat.kubejs.mysticalagriculture;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.CustomObjectRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedIngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.util.IntBounds;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class MysticalRecipeSchemas {

    public static final String NAMESPACE = "mysticalagriculture";

    private static final RecipeKey<ItemStack> RESULT = ItemStackComponent.ITEM_STACK.outputKey("result");
    private static final RecipeKey<Ingredient> INPUT = IngredientComponent.INGREDIENT.inputKey("input");
    private static final RecipeKey<Boolean> TRANSFER_COMPONENTS = BooleanComponent.BOOLEAN.otherKey("transfer_components").optional(false);

    private static final RecipeKey<List<Ingredient>> INFUSION_INGREDIENTS = IngredientComponent.INGREDIENT.instance()
            .asList()
            .withBounds(IntBounds.of(1, 8))
            .inputKey("ingredients");

    private static final RecipeKey<List<Ingredient>> AWAKENING_INGREDIENTS = IngredientComponent.INGREDIENT.instance()
            .asList()
            .withBounds(IntBounds.of(1, 4))
            .inputKey("ingredients");

    private static final RecipeKey<List<ItemStack>> AWAKENING_ESSENCES = ItemStackComponent.ITEM_STACK.instance()
            .asList()
            .withBounds(IntBounds.of(1, 4))
            .inputKey("essences");

    private static final RecipeKey<String> ENCHANTMENT = StringComponent.ID.otherKey("enchantment");

    private static final RecipeKey<List<SizedIngredient>> ENCHANTER_INGREDIENTS = SizedIngredientComponent.FLAT.instance()
            .asList()
            .withBounds(IntBounds.of(1, 2))
            .inputKey("ingredients");

    private static final RecipeKey<List<CustomObjectRecipeComponent.Value>> SOUL_RESULT = RecipeComponent.builder(
            new CustomObjectRecipeComponent.Key("type", StringComponent.ID.instance()),
            new CustomObjectRecipeComponent.Key("souls", NumberComponent.POSITIVE_DOUBLE.instance()))
            .outputKey("result");

    private static final RecipeKey<String> GROUP = StringComponent.STRING.otherKey("group").defaultOptional();

    private static final RecipeKey<List<Ingredient>> FARMLAND_INGREDIENTS = IngredientComponent.INGREDIENT.instance()
            .asList()
            .withBounds(IntBounds.of(1, 9))
            .inputKey("ingredients");

    private static final RecipeKey<SizedIngredient> SPAWNER_INPUT = SizedIngredientComponent.FLAT.inputKey("input");

    private static final RecipeKey<List<List<CustomObjectRecipeComponent.Value>>> SPAWNER_ENTITIES = RecipeComponent.builder(
            new CustomObjectRecipeComponent.Key("entity", StringComponent.ID.instance()),
            new CustomObjectRecipeComponent.Key("weight", NumberComponent.POSITIVE_INT.instance()))
            .asList()
            .withBounds(IntBounds.of(1, Integer.MAX_VALUE))
            .otherKey("entities");

    private MysticalRecipeSchemas() {
    }

    public static void register(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace(NAMESPACE);
        namespace.register("infusion", new RecipeSchema(RESULT, INPUT, INFUSION_INGREDIENTS, TRANSFER_COMPONENTS));
        namespace.register("awakening", new RecipeSchema(RESULT, INPUT, AWAKENING_INGREDIENTS, AWAKENING_ESSENCES, TRANSFER_COMPONENTS));
        namespace.register("enchanter", new RecipeSchema(ENCHANTMENT, ENCHANTER_INGREDIENTS));
        namespace.register("reprocessor", new RecipeSchema(RESULT, INPUT));
        namespace.register("soul_extraction", new RecipeSchema(SOUL_RESULT, INPUT));
        namespace.register("farmland_till", new RecipeSchema(RESULT, FARMLAND_INGREDIENTS, GROUP));
        namespace.register("soulium_spawner", new RecipeSchema(SPAWNER_INPUT, SPAWNER_ENTITIES));
        namespace.special("soul_jar_empty");
    }
}
