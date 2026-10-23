package nadiendev.umc.compat.kubejs.ae2.component;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.component.EnumComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.component.SizedIngredientComponent;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.ae2.type.AeStack;
import nadiendev.umc.compat.kubejs.ae2.type.EntropyMode;
import nadiendev.umc.compat.kubejs.ae2.type.InscriberMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class Ae2Components {

    public static final RecipeComponentType<JsonElement> JSON = RecipeComponentType.unit(id("json"), JsonComponent::new);

    public static final RecipeComponentType<Item> ITEM_ID = RecipeComponentType.unit(id("item_id"), ItemIdComponent::new);

    public static final RecipeComponentType<SizedIngredient> AMOUNT_INGREDIENT = RecipeComponentType.unit(id("amount_ingredient"),
            type -> new AmountIngredientComponent(type, SizedIngredientComponent.SIZED_INGREDIENT.instance()));

    public static final RecipeComponentType<AeStack> AE_STACK = RecipeComponentType.unit(id("generic_stack"), AeStackComponent::new);

    public static final RecipeComponentType<InscriberMode> INSCRIBER_MODE = EnumComponent.of(id("inscriber_mode"), InscriberMode.class);

    public static final RecipeComponentType<EntropyMode> ENTROPY_MODE = EnumComponent.of(id("entropy_mode"), EntropyMode.class);

    private static RecipeComponentTypeRegistry lastRegistry;

    private Ae2Components() {
    }

    public static synchronized void register(RecipeComponentTypeRegistry registry) {
        if (lastRegistry == registry) {
            return;
        }
        lastRegistry = registry;
        registry.register(JSON);
        registry.register(ITEM_ID);
        registry.register(AMOUNT_INGREDIENT);
        registry.register(AE_STACK);
        registry.register(INSCRIBER_MODE);
        registry.register(ENTROPY_MODE);
    }

    public static <T> RecipeComponent<T> of(RecipeComponentType<T> type) {
        return type.instance();
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }
}
