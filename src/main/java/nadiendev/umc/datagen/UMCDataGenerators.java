package nadiendev.umc.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.providers.CoreData;
import nadiendev.umc.datagen.providers.CraftingData;
import nadiendev.umc.datagen.providers.CreaturesData;
import nadiendev.umc.datagen.providers.MysticalAgricultureDataGenLangCompat;
import nadiendev.umc.datagen.providers.MysticalAgricultureDataGenModelsCompat;
import nadiendev.umc.datagen.providers.MysticalAgricultureDataGenRecipesCompat;
import nadiendev.umc.datagen.providers.SpiritData;
import nadiendev.umc.datagen.providers.UmcBlockLoot;
import nadiendev.umc.datagen.providers.UmcBlockTagsProvider;
import nadiendev.umc.datagen.providers.UmcEntityLoot;
import nadiendev.umc.datagen.providers.UmcItemTagsProvider;
import nadiendev.umc.datagen.providers.UmcJsonProvider;
import nadiendev.umc.datagen.providers.UmcLangProvider;
import nadiendev.umc.datagen.providers.UmcRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class UMCDataGenerators {

    private static final List<String> LOCALES = List.of("en_us", "es_ar", "es_mx", "es_es");

    private static final List<DataModule> MODULES = List.of(new CoreData(), new CreaturesData(), new SpiritData(), new CraftingData(), new MysticalAgricultureDataGenLangCompat(), new MysticalAgricultureDataGenModelsCompat(), new MysticalAgricultureDataGenRecipesCompat());

    private UMCDataGenerators() {
    }

    @SubscribeEvent
    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        for (String locale : LOCALES) {
            generator.addProvider(client, new UmcLangProvider(output, locale, MODULES));
        }

        generator.addProvider(server, new UmcRecipeProvider(output, lookup, MODULES));

        UmcBlockTagsProvider blockTags = generator.addProvider(server, new UmcBlockTagsProvider(output, lookup, helper, MODULES));
        generator.addProvider(server, new UmcItemTagsProvider(output, lookup, blockTags.contentsGetter(), helper, MODULES));

        generator.addProvider(server, new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(provider -> new UmcBlockLoot(provider, MODULES), LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(provider -> new UmcEntityLoot(MODULES), LootContextParamSets.ENTITY)
        ), lookup));

        RegistrySetBuilder registries = new RegistrySetBuilder();
        MODULES.forEach(module -> module.registries(registries));
        generator.addProvider(server, new DatapackBuiltinEntriesProvider(output, lookup, registries, Set.of(UltimateModCompats.MODID)));

        generator.addProvider(true, new UmcJsonProvider(output, MODULES));

        MODULES.forEach(module -> module.gather(event));
    }
}
