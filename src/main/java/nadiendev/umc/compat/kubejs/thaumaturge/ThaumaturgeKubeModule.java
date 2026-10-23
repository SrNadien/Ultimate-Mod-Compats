package nadiendev.umc.compat.kubejs.thaumaturge;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeMappingRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.thaumaturge.component.ThaumaturgeComponents;
import nadiendev.umc.compat.kubejs.thaumaturge.event.ThaumaturgeDispatcher;
import nadiendev.umc.compat.kubejs.thaumaturge.event.ThaumaturgeEvents;
import nadiendev.umc.compat.kubejs.thaumaturge.event.ThaumaturgeStartup;
import nadiendev.umc.compat.kubejs.thaumaturge.schema.ThaumaturgeSchemas;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;

public class ThaumaturgeKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return ThaumaturgeIds.THAUMATURGE;
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(ThaumaturgeEvents.GROUP);
        registry.register(ThaumaturgeStartup.GROUP);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        ThaumaturgeComponents.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace(ThaumaturgeIds.THAUMATURGE);
        namespace.register("arcane_workbench_shaped", ThaumaturgeSchemas.ARCANE_SHAPED);
        namespace.register("arcane_workbench_shapeless", ThaumaturgeSchemas.ARCANE_SHAPELESS);
        namespace.register("crucible", ThaumaturgeSchemas.CRUCIBLE);
        namespace.register("infusion", ThaumaturgeSchemas.INFUSION);
        namespace.register("infusion_enchantment", ThaumaturgeSchemas.INFUSION_ENCHANTMENT);
        namespace.register("runic_augment", ThaumaturgeSchemas.RUNIC_AUGMENT);
        namespace.register("dust_trigger_simple", ThaumaturgeSchemas.DUST_TRIGGER_SIMPLE);
        namespace.register("dust_trigger_tag", ThaumaturgeSchemas.DUST_TRIGGER_TAG);
        namespace.register("dust_trigger_multiblock", ThaumaturgeSchemas.DUST_TRIGGER_MULTIBLOCK);
        namespace.register("salis_mundus", ThaumaturgeSchemas.SPECIAL);
        namespace.register("label_filter", ThaumaturgeSchemas.SPECIAL);
    }

    @Override
    public void registerRecipeMappings(RecipeMappingRegistry registry) {
        registry.register("thaumaturgeArcaneShaped", ThaumaturgeIds.thaumaturge("arcane_workbench_shaped"));
        registry.register("thaumaturgeArcaneShapeless", ThaumaturgeIds.thaumaturge("arcane_workbench_shapeless"));
        registry.register("thaumaturgeCrucible", ThaumaturgeIds.thaumaturge("crucible"));
        registry.register("thaumaturgeInfusion", ThaumaturgeIds.thaumaturge("infusion"));
        registry.register("thaumaturgeInfusionEnchantment", ThaumaturgeIds.thaumaturge("infusion_enchantment"));
        registry.register("thaumaturgeRunicAugment", ThaumaturgeIds.thaumaturge("runic_augment"));
        registry.register("thaumaturgeDustTrigger", ThaumaturgeIds.thaumaturge("dust_trigger_simple"));
        registry.register("thaumaturgeDustTriggerTag", ThaumaturgeIds.thaumaturge("dust_trigger_tag"));
        registry.register("thaumaturgeDustTriggerMultiblock", ThaumaturgeIds.thaumaturge("dust_trigger_multiblock"));
    }

    @Override
    public void initStartup() {
        ThaumaturgeDispatcher.installStartupBridge();
    }

    @Override
    public void generateData(KubeDataGenerator generator) {
        ThaumaturgeDispatcher.postData(generator);
    }
}
