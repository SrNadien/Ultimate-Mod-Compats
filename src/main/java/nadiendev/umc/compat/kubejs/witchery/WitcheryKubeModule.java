package nadiendev.umc.compat.kubejs.witchery;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeMappingRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.witchery.component.WitcheryComponents;
import nadiendev.umc.compat.kubejs.witchery.event.WitcheryDispatcher;
import nadiendev.umc.compat.kubejs.witchery.event.EnchantedEvents;
import nadiendev.umc.compat.kubejs.witchery.event.EnchantedStartup;
import nadiendev.umc.compat.kubejs.witchery.item.BrewItemBuilder;
import nadiendev.umc.compat.kubejs.witchery.item.ItemPoppetItemBuilder;
import nadiendev.umc.compat.kubejs.witchery.item.PlayerPoppetItemBuilder;
import nadiendev.umc.compat.kubejs.witchery.item.ThrowableBrewItemBuilder;
import nadiendev.umc.compat.kubejs.witchery.schema.WitcherySchemas;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.core.registries.Registries;

public class WitcheryKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return WitcheryIds.ENCHANTED;
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(EnchantedEvents.GROUP);
        registry.register(EnchantedStartup.GROUP);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        WitcheryComponents.register(registry);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        RecipeNamespace namespace = registry.namespace(WitcheryIds.ENCHANTED);
        namespace.register("kettle", WitcherySchemas.KETTLE);
        namespace.register("wheel", WitcherySchemas.SPINNING);
        namespace.register("distilling", WitcherySchemas.DISTILLING);
        namespace.register("byproduct", WitcherySchemas.BYPRODUCT);
    }

    @Override
    public void registerRecipeMappings(RecipeMappingRegistry registry) {
        registry.register("enchantedKettle", WitcheryIds.enchanted("kettle"));
        registry.register("enchantedSpinning", WitcheryIds.enchanted("wheel"));
        registry.register("enchantedWheel", WitcheryIds.enchanted("wheel"));
        registry.register("enchantedDistilling", WitcheryIds.enchanted("distilling"));
        registry.register("enchantedByproduct", WitcheryIds.enchanted("byproduct"));
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, items -> {
            items.add(WitcheryIds.enchanted("player_poppet"), PlayerPoppetItemBuilder.class, PlayerPoppetItemBuilder::new);
            items.add(WitcheryIds.enchanted("item_poppet"), ItemPoppetItemBuilder.class, ItemPoppetItemBuilder::new);
            items.add(WitcheryIds.enchanted("brew"), BrewItemBuilder.class, BrewItemBuilder::new);
            items.add(WitcheryIds.enchanted("throwable_brew"), ThrowableBrewItemBuilder.class, ThrowableBrewItemBuilder::new);
        });
    }

    @Override
    public void initStartup() {
        WitcheryDispatcher.postStartup();
    }

    @Override
    public void generateData(KubeDataGenerator generator) {
        WitcheryDispatcher.postData(generator);
    }
}
