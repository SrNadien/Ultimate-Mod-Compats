package nadiendev.umc.compat.kubejs.witchery.component;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.resources.ResourceLocation;

public final class WitcheryComponents {

    public static final RecipeComponentType<Integer> HEX_COLOUR = RecipeComponentType.unit(
            ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "enchanted_hex_colour"), HexColourComponent::new);

    private WitcheryComponents() {
    }

    public static void register(RecipeComponentTypeRegistry registry) {
        registry.register(HEX_COLOUR);
    }

    @SuppressWarnings("unchecked")
    public static <T> RecipeComponent<T> of(RecipeComponentType<T> type) {
        return (RecipeComponent<T>) type.instance();
    }
}
