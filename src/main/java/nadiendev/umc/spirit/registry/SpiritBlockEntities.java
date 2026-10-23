package nadiendev.umc.spirit.registry;

import nadiendev.umc.spirit.block.entity.CovenAltarBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class SpiritBlockEntities {

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CovenAltarBlockEntity>> COVEN_ALTAR = SpiritRegistry.BLOCK_ENTITY_TYPES.register("coven_altar",
            () -> BlockEntityType.Builder.of(CovenAltarBlockEntity::new, SpiritBlocks.COVEN_ALTAR.get()).build(null));

    private SpiritBlockEntities() {
    }

    public static void load() {
    }
}
