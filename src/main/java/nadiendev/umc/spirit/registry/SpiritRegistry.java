package nadiendev.umc.spirit.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class SpiritRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateModCompats.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, UltimateModCompats.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, UltimateModCompats.MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, UltimateModCompats.MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, UltimateModCompats.MODID);

    private SpiritRegistry() {
    }

    public static void register(IEventBus modBus) {
        SpiritBlocks.load();
        SpiritItems.load();
        SpiritEntities.load();
        SpiritEffects.load();
        SpiritBlockEntities.load();
        SpiritAttachments.load();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        ENTITY_TYPES.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        MOB_EFFECTS.register(modBus);
        ATTACHMENT_TYPES.register(modBus);
    }
}
