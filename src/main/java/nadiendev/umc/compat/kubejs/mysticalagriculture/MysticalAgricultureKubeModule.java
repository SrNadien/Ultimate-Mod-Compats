package nadiendev.umc.compat.kubejs.mysticalagriculture;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.core.RecipeManagerKJS;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.script.ScriptType;
import java.util.Map;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "mysticalagriculture";
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(MysticalEvents.GROUP);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        MysticalRecipeSchemas.register(registry);
    }

    @Override
    public void beforeRecipeLoading(RecipesKubeEvent event, RecipeManagerKJS manager, Map<ResourceLocation, JsonElement> recipeJsons) {
        if (MysticalEvents.RECIPES.hasListeners()) {
            MysticalEvents.RECIPES.post(ScriptType.SERVER, new MysticalRecipesKubeEvent(event, recipeJsons));
        }
    }
}
