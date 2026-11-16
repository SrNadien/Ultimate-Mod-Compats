package nadiendev.umc.arsnouveau;

import com.hollingsworth.arsnouveau.common.items.ModBlockItem;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The jars themselves. Only loaded when Ars Nouveau is installed. */
final class ArsJars {

    static final String TILE_ID = "source_jar";
    static DeferredHolder<BlockEntityType<?>, BlockEntityType<ArsJarTile>> TILE;

    private static final List<DeferredBlock<Block>> BLOCKS = new ArrayList<>();

    private ArsJars() {
    }

    static void register(DeferredRegister.Blocks blocks, DeferredRegister.Items items, DeferredRegister<BlockEntityType<?>> tiles, List<DeferredItem<Item>> out) {
        for (UmcMaterial material : UmcMaterial.available()) {
            String id = ArsNouveauRegistry.jarId(material);
            int capacity = ArsNouveauStats.capacity(material);
            DeferredBlock<Block> block = blocks.register(id, () -> new ArsJarBlock(id, capacity));
            BLOCKS.add(block);
            out.add(items.register(id, () -> new ModBlockItem(block.get(), new Item.Properties().rarity(Rarity.EPIC))));
        }
        TILE = tiles.register(TILE_ID, () -> BlockEntityType.Builder.of(ArsJarTile::new,
                BLOCKS.stream().map(DeferredBlock::get).toArray(Block[]::new)).build(null));
    }

    static void capabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(CapabilityRegistry.SOURCE_CAPABILITY, TILE.get(), (tile, direction) -> tile.getSourceStorage());
    }

    static List<Block> blocks() {
        return BLOCKS.stream().<Block>map(DeferredBlock::get).toList();
    }
}
