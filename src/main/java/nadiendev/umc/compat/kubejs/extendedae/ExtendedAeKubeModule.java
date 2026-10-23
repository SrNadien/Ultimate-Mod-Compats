package nadiendev.umc.compat.kubejs.extendedae;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;
import nadiendev.umc.compat.kubejs.extendedae.schema.ExtendedAeSchemas;

public class ExtendedAeKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "extendedae";
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        Ae2Components.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace("extendedae");
        namespace.register("crystal_assembler", ExtendedAeSchemas.CRYSTAL_ASSEMBLER);
        namespace.register("circuit_cutter", ExtendedAeSchemas.CIRCUIT_CUTTER);
        namespace.register("crystal_fixer", ExtendedAeSchemas.CRYSTAL_FIXER);
    }
}
