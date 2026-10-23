package nadiendev.umc.compat.kubejs.ae2;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.ae2.component.Ae2Components;
import nadiendev.umc.compat.kubejs.ae2.schema.Ae2Schemas;

public class Ae2KubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "ae2";
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        Ae2Components.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace("ae2");
        namespace.register("inscriber", Ae2Schemas.INSCRIBER);
        namespace.register("transform", Ae2Schemas.TRANSFORM);
        namespace.register("charger", Ae2Schemas.CHARGER);
        namespace.register("matter_cannon", Ae2Schemas.MATTER_CANNON);
        namespace.register("entropy", Ae2Schemas.ENTROPY);
        namespace.register("storage_cell_upgrade", Ae2Schemas.STORAGE_CELL_UPGRADE);
        namespace.register("storage_cell_disassembly", Ae2Schemas.STORAGE_CELL_DISASSEMBLY);
        namespace.register("crafting_unit_transform", Ae2Schemas.CRAFTING_UNIT_TRANSFORM);
        namespace.register("quartz_cutting", Ae2Schemas.QUARTZ_CUTTING);
        namespace.register("add_item_upgrade", Ae2Schemas.SPECIAL);
        namespace.register("remove_item_upgrade", Ae2Schemas.SPECIAL);
        namespace.register("facade", Ae2Schemas.SPECIAL);
    }
}
