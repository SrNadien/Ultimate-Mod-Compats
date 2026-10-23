package nadiendev.umc.spirit.entity;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.registry.SpiritEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class SpiritEntityEvents {

    private SpiritEntityEvents() {
    }

    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(SpiritEntities.NIGHTMARE.get(), Nightmare.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(SpiritEntities.NIGHTMARE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Nightmare::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
