package nadiendev.umc.creatures.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class CreaturesRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateModCompats.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, UltimateModCompats.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, UltimateModCompats.MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, UltimateModCompats.MODID);

    private CreaturesRegistry() {
    }

    public static void register(IEventBus modBus) {
        CreaturesItems.init();
        CreaturesEntities.init();
        CreaturesSounds.init();
        CreaturesAttachments.init();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        ENTITY_TYPES.register(modBus);
        SOUND_EVENTS.register(modBus);
        ATTACHMENT_TYPES.register(modBus);
    }
}
