package nadiendev.umc.datagen.providers;

import static nadiendev.umc.datagen.providers.guide.GuideJson.array;
import static nadiendev.umc.datagen.providers.guide.GuideJson.object;
import static nadiendev.umc.datagen.providers.guide.GuideJson.with;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.function.BiConsumer;
import java.util.function.Function;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.crafting.registry.CraftingBlocks;
import nadiendev.umc.crafting.registry.CraftingItems;
import nadiendev.umc.crafting.rite.BindSpeciesFactory;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.datagen.providers.guide.CraftingGuide;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CraftingData implements DataModule {

    private static final TagKey<Item> ENCHANTED_ARMORS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("enchanted", "armors"));
    private static final TagKey<Block> ENCHANTED_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("enchanted", "crops"));

    @Override
    public void gather(GatherDataEvent event) {
        CraftingLoot.setLookup(event.getLookupProvider());
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new CraftingItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new CraftingBlockStates(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new CraftingPowerProviders(output, event.getLookupProvider()));
    }

    @Override
    public void addEnglish(LanguageProvider lang) {
        CraftingLang.add(lang, false);
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        CraftingLang.add(lang, true);
    }

    @Override
    public void recipes(RecipeOutput output) {
        CraftingRecipes.build(output);
    }

    @Override
    public void blockTags(Function<TagKey<Block>, IntrinsicTagAppender<Block>> tags) {
        tags.apply(BlockTags.CROPS).add(CraftingBlocks.WORMWOOD.get(), CraftingBlocks.VERVAIN.get());
        tags.apply(BlockTags.MAINTAINS_FARMLAND).add(CraftingBlocks.WORMWOOD.get(), CraftingBlocks.VERVAIN.get());
        tags.apply(BlockTags.SMALL_FLOWERS).add(CraftingBlocks.MOONBLOOM.get(), CraftingBlocks.MUGWORT.get());
        tags.apply(ENCHANTED_CROPS).add(CraftingBlocks.WORMWOOD.get(), CraftingBlocks.VERVAIN.get());
    }

    @Override
    public void itemTags(Function<TagKey<Item>, IntrinsicTagAppender<Item>> tags) {
        tags.apply(ItemTags.HEAD_ARMOR).add(CraftingItems.WITCH_HAT.get());
        tags.apply(ItemTags.CHEST_ARMOR).add(CraftingItems.WITCH_ROBE.get(), CraftingItems.WITCH_CLOAK.get());
        tags.apply(ItemTags.FOOT_ARMOR).add(CraftingItems.WITCH_BOOTS.get());
        tags.apply(ENCHANTED_ARMORS).add(CraftingItems.WITCH_HAT.get(), CraftingItems.WITCH_ROBE.get(), CraftingItems.WITCH_CLOAK.get(), CraftingItems.WITCH_BOOTS.get());
        tags.apply(ItemTags.SMALL_FLOWERS).add(CraftingItems.MOONBLOOM.get(), CraftingItems.MUGWORT.get());
        tags.apply(Tags.Items.SEEDS).add(CraftingItems.WORMWOOD_SEEDS.get(), CraftingItems.VERVAIN_SEEDS.get());
        tags.apply(Tags.Items.CROPS).add(CraftingItems.WORMWOOD_SPRIG.get(), CraftingItems.VERVAIN_FLOWER.get());
    }

    @Override
    public void blockLoot(UmcBlockLoot loot) {
        CraftingLoot.blocks(loot);
    }

    @Override
    public void entityLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        CraftingLoot.entities(output);
    }

    @Override
    public void json(UmcJsonSink sink) {
        rite(sink, "bind_toad_familiar", "umc:toad", 5000, "umc:wormwood_tincture", "enchanted:whiff_of_magic", "minecraft:slime_ball");
        rite(sink, "bind_owl_familiar", "umc:owl", 5000, "umc:moondew", "enchanted:odour_of_purity", "minecraft:feather");
        rite(sink, "bind_raven_familiar", "umc:raven", 5000, "umc:vervain_tonic", "enchanted:reek_of_misfortune", "minecraft:ink_sac");
        rite(sink, "bind_hound_familiar", "minecraft:wolf", 6000, "umc:vervain_tonic", "umc:moondew", "enchanted:tongue_of_dog");
        CraftingWorldgen.json(sink);
        CraftingGuide.generate(sink);
    }

    private static void rite(UmcJsonSink sink, String name, String species, int power, String... items) {
        JsonObject rite = object();
        JsonArray list = new JsonArray();
        for (String item : items) {
            JsonObject entry = object();
            with(entry, "id", item);
            with(entry, "count", 1);
            list.add(entry);
        }
        with(rite, "items", list);
        with(rite, "shapes", object("enchanted:medium_circle", "enchanted:ritual_chalk"));
        with(rite, "power", power);
        JsonObject factory = object();
        with(factory, "type", BindSpeciesFactory.ID.toString());
        with(factory, "species", species);
        with(rite, "factory", factory);
        sink.data(EnchantedIds.NAMESPACE, "enchanted/circle_magic/rite/" + name, rite);
    }
}
