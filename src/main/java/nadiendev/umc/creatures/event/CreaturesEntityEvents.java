package nadiendev.umc.creatures.event;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.entity.EntEntity;
import nadiendev.umc.creatures.entity.GhostEntity;
import nadiendev.umc.creatures.entity.ImpEntity;
import nadiendev.umc.creatures.entity.UndeadServantEntity;
import nadiendev.umc.creatures.entity.VampireEntity;
import nadiendev.umc.creatures.entity.WerewolfEntity;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CreaturesEntityEvents {

    private CreaturesEntityEvents() {
    }

    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event) {
        event.put(CreaturesEntities.WEREWOLF.get(), WerewolfEntity.createAttributes().build());
        event.put(CreaturesEntities.VAMPIRE.get(), VampireEntity.createAttributes().build());
        event.put(CreaturesEntities.GHOST.get(), GhostEntity.createAttributes().build());
        event.put(CreaturesEntities.IMP.get(), ImpEntity.createAttributes().build());
        event.put(CreaturesEntities.ENT.get(), EntEntity.createAttributes().build());
        event.put(CreaturesEntities.ZOMBIE_SERVANT.get(), UndeadServantEntity.createZombieAttributes().build());
        event.put(CreaturesEntities.SKELETON_SERVANT.get(), UndeadServantEntity.createSkeletonAttributes().build());
    }

    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(CreaturesEntities.WEREWOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfEntity::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CreaturesEntities.VAMPIRE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VampireEntity::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CreaturesEntities.GHOST.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostEntity::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CreaturesEntities.IMP.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ImpEntity::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CreaturesEntities.ENT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntEntity::checkSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
