package nadiendev.umc.datagen.providers;

import java.util.function.BiConsumer;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import nadiendev.umc.creatures.registry.CreaturesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class CreaturesLoot {

    private CreaturesLoot() {
    }

    public static void build(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, HolderLookup.Provider registries) {

        output.accept(key(CreaturesEntities.WEREWOLF.get()), LootTable.lootTable()
                .withPool(counted(registries, CreaturesItems.WEREWOLF_FANG.get(), 0, 2, 1))
                .withPool(counted(registries, Items.BONE, 0, 2, 1))
                .withPool(counted(registries, Items.LEATHER, 0, 2, 1))
                .withPool(chance(CreaturesItems.SILVER_INGOT.get(), 0.08F)));

        output.accept(key(CreaturesEntities.VAMPIRE.get()), LootTable.lootTable()
                .withPool(counted(registries, CreaturesItems.VAMPIRE_FANG.get(), 0, 2, 1))
                .withPool(counted(registries, Items.BONE, 0, 2, 1))
                .withPool(counted(registries, Items.REDSTONE, 0, 3, 1))
                .withPool(chance(enchanted("demonic_blood"), 0.06F)));

        output.accept(key(CreaturesEntities.GHOST.get()), LootTable.lootTable()
                .withPool(counted(registries, CreaturesItems.ECTOPLASM.get(), 0, 2, 1))
                .withPool(counted(registries, CreaturesItems.GRAVE_DUST.get(), 0, 1, 1))
                .withPool(chance(enchanted("hint_of_rebirth"), 0.05F)));

        output.accept(key(CreaturesEntities.IMP.get()), LootTable.lootTable()
                .withPool(counted(registries, CreaturesItems.IMP_HORN.get(), 0, 1, 1))
                .withPool(counted(registries, Items.BLAZE_POWDER, 0, 2, 1))
                .withPool(counted(registries, Items.GOLD_NUGGET, 0, 3, 1)));

        output.accept(key(CreaturesEntities.ENT.get()), LootTable.lootTable()
                .withPool(counted(registries, Items.STICK, 2, 6, 1))
                .withPool(counted(registries, Items.OAK_LOG, 1, 3, 1))
                .withPool(counted(registries, Items.OAK_SAPLING, 0, 2, 1))
                .withPool(chance(Items.APPLE, 0.3F))
                .withPool(chance(enchanted("ent_twig"), 0.35F)));
    }

    private static ResourceKey<LootTable> key(EntityType<?> type) {
        return type.getDefaultLootTable();
    }

    private static ItemLike enchanted(String path) {
        return BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath("enchanted", path)).orElseThrow();
    }

    private static LootPool.Builder counted(HolderLookup.Provider registries, ItemLike item, int min, int max, int lootingMax) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, lootingMax))));
    }

    private static LootPool.Builder chance(ItemLike item, float probability) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item))
                .when(LootItemRandomChanceCondition.randomChance(probability));
    }
}
