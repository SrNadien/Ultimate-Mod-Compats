package nadiendev.umc.creatures.registry;

import nadiendev.umc.creatures.entity.EntEntity;
import nadiendev.umc.creatures.entity.GhostEntity;
import nadiendev.umc.creatures.entity.ImpEntity;
import nadiendev.umc.creatures.entity.UndeadServantEntity;
import nadiendev.umc.creatures.entity.VampireEntity;
import nadiendev.umc.creatures.entity.WerewolfEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class CreaturesEntities {

    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfEntity>> WEREWOLF = CreaturesRegistry.ENTITY_TYPES.register("werewolf",
            () -> EntityType.Builder.of(WerewolfEntity::new, MobCategory.MONSTER).sized(0.7F, 2.1F).eyeHeight(1.85F).clientTrackingRange(10).build("werewolf"));

    public static final DeferredHolder<EntityType<?>, EntityType<VampireEntity>> VAMPIRE = CreaturesRegistry.ENTITY_TYPES.register("vampire",
            () -> EntityType.Builder.of(VampireEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(10).build("vampire"));

    public static final DeferredHolder<EntityType<?>, EntityType<GhostEntity>> GHOST = CreaturesRegistry.ENTITY_TYPES.register("ghost",
            () -> EntityType.Builder.of(GhostEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(10).build("ghost"));

    public static final DeferredHolder<EntityType<?>, EntityType<ImpEntity>> IMP = CreaturesRegistry.ENTITY_TYPES.register("imp",
            () -> EntityType.Builder.of(ImpEntity::new, MobCategory.MONSTER).sized(0.5F, 0.9F).fireImmune().clientTrackingRange(10).build("imp"));

    public static final DeferredHolder<EntityType<?>, EntityType<EntEntity>> ENT = CreaturesRegistry.ENTITY_TYPES.register("ent",
            () -> EntityType.Builder.of(EntEntity::new, MobCategory.CREATURE).sized(1.4F, 3.6F).eyeHeight(3.1F).clientTrackingRange(10).build("ent"));

    public static final DeferredHolder<EntityType<?>, EntityType<UndeadServantEntity>> ZOMBIE_SERVANT = CreaturesRegistry.ENTITY_TYPES.register("zombie_servant",
            () -> EntityType.Builder.of(UndeadServantEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build("zombie_servant"));

    public static final DeferredHolder<EntityType<?>, EntityType<UndeadServantEntity>> SKELETON_SERVANT = CreaturesRegistry.ENTITY_TYPES.register("skeleton_servant",
            () -> EntityType.Builder.of(UndeadServantEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build("skeleton_servant"));

    private CreaturesEntities() {
    }

    static void init() {
    }
}
