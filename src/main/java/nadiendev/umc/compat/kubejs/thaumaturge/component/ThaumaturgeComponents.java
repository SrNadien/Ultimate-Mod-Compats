package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.resources.ResourceLocation;

public final class ThaumaturgeComponents {

    public static final RecipeComponentType<AspectAmount> ASPECT = RecipeComponentType.unit(id("thaumaturge_aspect"), AspectAmountComponent::new);
    public static final RecipeComponentType<ResearchGate> RESEARCH = RecipeComponentType.unit(id("thaumaturge_research"), ResearchGateComponent::new);
    public static final RecipeComponentType<JsonObject> JSON_OBJECT = RecipeComponentType.unit(id("thaumaturge_json_object"), JsonObjectComponent::new);

    private ThaumaturgeComponents() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }

    public static void register(RecipeComponentTypeRegistry registry) {
        registry.register(ASPECT);
        registry.register(RESEARCH);
        registry.register(JSON_OBJECT);
    }

    @SuppressWarnings("unchecked")
    public static <T> RecipeComponent<T> of(RecipeComponentType<T> type) {
        return (RecipeComponent<T>) type.instance();
    }
}
