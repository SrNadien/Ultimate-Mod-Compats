package nadiendev.umc.compat.kubejs.advancedae;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.advancedae.schema.AdvancedAeSchemas;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;

public class AdvancedAeKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "advanced_ae";
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        Ae2Components.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace("advanced_ae");
        namespace.register("reaction", AdvancedAeSchemas.REACTION);
    }
}
