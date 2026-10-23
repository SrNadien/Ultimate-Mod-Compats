package nadiendev.umc.datagen;

import java.util.function.BiConsumer;
import java.util.function.Function;
import nadiendev.umc.datagen.providers.UmcBlockLoot;
import nadiendev.umc.datagen.providers.UmcJsonSink;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public interface DataModule {

    default void gather(GatherDataEvent event) {
    }

    default void addEnglish(LanguageProvider lang) {
    }

    default void addSpanish(LanguageProvider lang) {
    }

    default void recipes(RecipeOutput output) {
    }

    default void blockTags(Function<TagKey<Block>, IntrinsicTagAppender<Block>> tags) {
    }

    default void itemTags(Function<TagKey<Item>, IntrinsicTagAppender<Item>> tags) {
    }

    default void blockLoot(UmcBlockLoot loot) {
    }

    default void entityLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    }

    default void registries(RegistrySetBuilder builder) {
    }

    default void json(UmcJsonSink sink) {
    }
}
