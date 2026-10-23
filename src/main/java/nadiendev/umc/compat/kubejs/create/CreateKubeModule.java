package nadiendev.umc.compat.kubejs.create;

import dev.latvian.mods.kubejs.recipe.schema.RecipeMappingRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.create.schema.CreateSchemas;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class CreateKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "create";
    }

    @Override
    public void registerRecipeMappings(RecipeMappingRegistry registry) {
        registry.register("itemApplication", "create:item_application");
        registry.register("mysteriousConversion", "create:conversion");
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.namespace("create").register("item_application", CreateSchemas.ITEM_APPLICATION);
        if (!isLoaded("kubejs_create")) {
            registry.namespace("create").register("conversion", CreateSchemas.CONVERSION);
            registry.namespace("create").register("haunting", CreateSchemas.HAUNTING);
        }
    }

    private static boolean isLoaded(String modId) {
        if (ModList.get() != null) {
            return ModList.get().isLoaded(modId);
        }
        return LoadingModList.get().getModFileById(modId) != null;
    }
}
