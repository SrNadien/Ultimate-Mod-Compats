package nadiendev.umc.compat.kubejs.excessiveutilities;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.excessiveutilities.component.ExuComponents;
import nadiendev.umc.compat.kubejs.excessiveutilities.schema.ExuSchemas;

public class ExcessiveUtilitiesKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "excessive_utilities";
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        ExuComponents.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace("excessive_utilities");
        namespace.register("crusher", ExuSchemas.CRUSHER);
        namespace.register("enchanter", ExuSchemas.ENCHANTER);
        namespace.register("qed", ExuSchemas.QED);
        namespace.register("resonator", ExuSchemas.RESONATOR);
        namespace.register("world_interaction_item", ExuSchemas.WORLD_INTERACTION_ITEM);
        namespace.register("world_interaction_fluid", ExuSchemas.WORLD_INTERACTION_FLUID);
        namespace.register("single_item_fuel", ExuSchemas.SINGLE_ITEM_FUEL);
        namespace.register("item_and_fluid_fuel", ExuSchemas.ITEM_AND_FLUID_FUEL);
        namespace.register("magmatic_fuel", ExuSchemas.MAGMATIC_FUEL);
        namespace.register("shaped_division", ExuSchemas.SHAPED_DIVISION);
        namespace.register("shaped_unstable", ExuSchemas.SHAPED_UNSTABLE);
        namespace.register("keep_paintbrush", ExuSchemas.KEEP_PAINTBRUSH);
        namespace.register("damage_glass_cutter", ExuSchemas.DAMAGE_GLASS_CUTTER);
        namespace.register("snow_globe", ExuSchemas.SNOW_GLOBE);
    }
}
