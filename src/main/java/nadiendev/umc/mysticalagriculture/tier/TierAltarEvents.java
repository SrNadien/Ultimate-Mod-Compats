package nadiendev.umc.mysticalagriculture.tier;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class TierAltarEvents {

    private static final ResourceLocation INFUSION_ALTAR = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "infusion_altar");
    private static final ResourceLocation AWAKENING_ALTAR = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "awakening_altar");

    private static final ResourceLocation INFUSION_PEDESTAL = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "infusion_pedestal");
    private static final ResourceLocation AWAKENING_PEDESTAL = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "awakening_pedestal");
    private static final ResourceLocation ESSENCE_VESSEL = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "essence_vessel");

    private static final ResourceLocation SEED_REPROCESSOR = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "seed_reprocessor");
    private static final ResourceLocation ESSENCE_FURNACE = ResourceLocation.fromNamespaceAndPath(TierAltarRegistry.MYSTICAL_AGRICULTURE, "furnace");

    private TierAltarEvents() {
    }

    @SubscribeEvent
    public static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
        if (!ModList.get().isLoaded(TierAltarRegistry.MYSTICAL_AGRICULTURE)) {
            return;
        }
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(INFUSION_ALTAR)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.infusionBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(AWAKENING_ALTAR)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.awakeningBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(INFUSION_PEDESTAL)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.infusionPedestalBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(AWAKENING_PEDESTAL)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.awakeningPedestalBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(ESSENCE_VESSEL)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.vesselBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(SEED_REPROCESSOR)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.reprocessorBlocks()));
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(ESSENCE_FURNACE)
                .ifPresent(type -> event.modify(type, TierAltarRegistry.furnaceBlocks()));
    }
}
