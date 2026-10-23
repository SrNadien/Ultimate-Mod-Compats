package nadiendev.umc.datagen.providers;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;
import nadiendev.umc.creatures.registry.CreaturesItems;
import nadiendev.umc.creatures.registry.CreaturesTags;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CreaturesData implements DataModule {

    private CompletableFuture<HolderLookup.Provider> lookup;

    @Override
    public void gather(GatherDataEvent event) {
        this.lookup = event.getLookupProvider();
        event.getGenerator().addProvider(event.includeClient(), new CreaturesItemModels(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
    }

    @Override
    public void addEnglish(LanguageProvider lang) {
        CreaturesLang.english(lang);
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        CreaturesLang.spanish(lang);
    }

    @Override
    public void recipes(RecipeOutput output) {
        CreaturesRecipes.build(output);
    }

    @Override
    public void itemTags(Function<TagKey<Item>, IntrinsicTagAppender<Item>> tags) {
        tags.apply(CreaturesTags.SILVER_WEAPONS).add(CreaturesItems.SILVER_SWORD.get());
        tags.apply(CreaturesTags.STAKES).add(CreaturesItems.WOODEN_STAKE.get());
    }

    @Override
    public void entityLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        CreaturesLoot.build(output, lookup.join());
    }

    @Override
    public void registries(RegistrySetBuilder builder) {
        builder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CreaturesBiomeModifiers::bootstrap);
    }

    @Override
    public void json(UmcJsonSink sink) {
        CreaturesJson.build(sink);
    }
}
