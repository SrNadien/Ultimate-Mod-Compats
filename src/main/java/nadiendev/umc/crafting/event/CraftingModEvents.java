package nadiendev.umc.crafting.event;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.config.SpeciesConfig;
import nadiendev.umc.crafting.entity.ToadEntity;
import nadiendev.umc.crafting.entity.BirdEntity;
import nadiendev.umc.crafting.entity.familiar.FamiliarHound;
import nadiendev.umc.crafting.familiar.FamiliarTypes;
import nadiendev.umc.crafting.registry.CraftingEntities;
import nadiendev.umc.crafting.rite.BindSpeciesFactory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CraftingModEvents {

    private CraftingModEvents() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FamiliarTypes.register();
            BindSpeciesFactory.register();
        });
    }

    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(CraftingEntities.TOAD.get(), ToadEntity.createAttributes().build());
        event.put(CraftingEntities.FAMILIAR_TOAD.get(), ToadEntity.createAttributes().build());
        event.put(CraftingEntities.OWL.get(), BirdEntity.createAttributes().build());
        event.put(CraftingEntities.FAMILIAR_OWL.get(), BirdEntity.createAttributes().build());
        event.put(CraftingEntities.RAVEN.get(), BirdEntity.createAttributes().build());
        event.put(CraftingEntities.FAMILIAR_RAVEN.get(), BirdEntity.createAttributes().build());
        event.put(CraftingEntities.FAMILIAR_HOUND.get(), FamiliarHound.createHoundAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        register(event, CraftingEntities.TOAD.get(), CraftingConfig.TOAD);
        register(event, CraftingEntities.OWL.get(), CraftingConfig.OWL);
        register(event, CraftingEntities.RAVEN.get(), CraftingConfig.RAVEN);
    }

    private static <T extends Animal> void register(RegisterSpawnPlacementsEvent event, EntityType<T> type, SpeciesConfig config) {
        event.register(type, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, spawnType, pos, random) -> CraftingConfig.ENABLED.get()
                        && CraftingConfig.FAMILIARS_ENABLED.get()
                        && config.enabled.get()
                        && config.spawnEnabled.get()
                        && random.nextDouble() < config.spawnChance.get()
                        && Animal.checkAnimalSpawnRules(entityType, level, spawnType, pos, random),
                RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
