package nadiendev.umc.datagen.providers;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.block.WitchCropBlock;
import nadiendev.umc.crafting.registry.CraftingBlocks;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class CraftingLoot {

    private static CompletableFuture<HolderLookup.Provider> lookup;

    public static void setLookup(CompletableFuture<HolderLookup.Provider> future) {
        lookup = future;
    }

    private CraftingLoot() {
    }

    public static void blocks(UmcBlockLoot loot) {
        loot.custom(CraftingBlocks.WORMWOOD.get(), crop(CraftingBlocks.WORMWOOD.get(), CraftingItems.WORMWOOD_SPRIG.get(), CraftingItems.WORMWOOD_SEEDS.get()));
        loot.custom(CraftingBlocks.VERVAIN.get(), crop(CraftingBlocks.VERVAIN.get(), CraftingItems.VERVAIN_FLOWER.get(), CraftingItems.VERVAIN_SEEDS.get()));
        loot.self(CraftingBlocks.MOONBLOOM.get());
        loot.self(CraftingBlocks.MUGWORT.get());
    }

    private static LootTable.Builder crop(Block block, Item product, Item seeds) {
        Holder<Enchantment> fortune = lookup.join().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        LootItemCondition.Builder mature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WitchCropBlock.AGE, WitchCropBlock.MAX_AGE));
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(product)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .when(mature)
                                .otherwise(LootItem.lootTableItem(seeds))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(mature)
                        .add(LootItem.lootTableItem(seeds)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.5714286F, 3))));
    }

    public static void entities(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(key("entities/toad"), drops(Items.SLIME_BALL, 0.0F, 1.0F, Items.SPIDER_EYE, 0.0F, 1.0F));
        output.accept(key("entities/owl"), drops(Items.FEATHER, 0.0F, 2.0F, Items.BONE, 0.0F, 1.0F));
        output.accept(key("entities/raven"), drops(Items.FEATHER, 0.0F, 2.0F, Items.ROTTEN_FLESH, 0.0F, 1.0F));
    }

    private static ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path));
    }

    private static LootTable.Builder drops(Item first, float firstMin, float firstMax, Item second, float secondMin, float secondMax) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(first).apply(SetItemCountFunction.setCount(UniformGenerator.between(firstMin, firstMax)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(second).apply(SetItemCountFunction.setCount(UniformGenerator.between(secondMin, secondMax)))));
    }
}
