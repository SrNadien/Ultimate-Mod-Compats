package nadiendev.umc.compat.kubejs.functionalstorage;

import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.functionalstorage.schema.FunctionalStorageSchemas;

public class FunctionalStorageKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "functionalstorage";
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace("functionalstorage");
        namespace.register("custom_compacting", FunctionalStorageSchemas.CUSTOM_COMPACTING);
        namespace.register("copy_components", FunctionalStorageSchemas.COPY_COMPONENTS_RECIPE);
        namespace.register("framed_recipe", FunctionalStorageSchemas.FRAMED_RECIPE);
    }
}
