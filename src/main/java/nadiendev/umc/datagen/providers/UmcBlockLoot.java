package nadiendev.umc.datagen.providers;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

public class UmcBlockLoot extends BlockLootSubProvider {

    private final List<DataModule> modules;

    public UmcBlockLoot(HolderLookup.Provider registries, List<DataModule> modules) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        this.modules = modules;
    }

    @Override
    protected void generate() {
        modules.forEach(module -> module.blockLoot(this));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return StreamSupport.stream(BuiltInRegistries.BLOCK.spliterator(), false)
                .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(UltimateModCompats.MODID))
                .toList();
    }

    public void self(Block block) {
        dropSelf(block);
    }

    public void custom(Block block, LootTable.Builder table) {
        add(block, table);
    }

    public void nothing(Block block) {
        add(block, noDrop());
    }
}
