package nadiendev.umc.crafting.registry;

import nadiendev.umc.crafting.entity.OwlEntity;
import nadiendev.umc.crafting.entity.RavenEntity;
import nadiendev.umc.crafting.entity.ToadEntity;
import nadiendev.umc.crafting.entity.familiar.FamiliarHound;
import nadiendev.umc.crafting.entity.familiar.FamiliarOwl;
import nadiendev.umc.crafting.entity.familiar.FamiliarRaven;
import nadiendev.umc.crafting.entity.familiar.FamiliarToad;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class CraftingEntities {

    public static final DeferredHolder<EntityType<?>, EntityType<ToadEntity>> TOAD = CraftingRegistry.ENTITY_TYPES.register("toad",
            () -> EntityType.Builder.of(ToadEntity::new, MobCategory.CREATURE).sized(0.65F, 0.5F).clientTrackingRange(8).build("toad"));

    public static final DeferredHolder<EntityType<?>, EntityType<OwlEntity>> OWL = CraftingRegistry.ENTITY_TYPES.register("owl",
            () -> EntityType.Builder.of(OwlEntity::new, MobCategory.CREATURE).sized(0.5F, 0.8F).clientTrackingRange(8).build("owl"));

    public static final DeferredHolder<EntityType<?>, EntityType<RavenEntity>> RAVEN = CraftingRegistry.ENTITY_TYPES.register("raven",
            () -> EntityType.Builder.of(RavenEntity::new, MobCategory.CREATURE).sized(0.5F, 0.6F).clientTrackingRange(8).build("raven"));

    public static final DeferredHolder<EntityType<?>, EntityType<FamiliarToad>> FAMILIAR_TOAD = CraftingRegistry.ENTITY_TYPES.register("familiar_toad",
            () -> EntityType.Builder.of(FamiliarToad::new, MobCategory.CREATURE).sized(0.65F, 0.5F).clientTrackingRange(8).build("familiar_toad"));

    public static final DeferredHolder<EntityType<?>, EntityType<FamiliarOwl>> FAMILIAR_OWL = CraftingRegistry.ENTITY_TYPES.register("familiar_owl",
            () -> EntityType.Builder.of(FamiliarOwl::new, MobCategory.CREATURE).sized(0.5F, 0.8F).clientTrackingRange(8).build("familiar_owl"));

    public static final DeferredHolder<EntityType<?>, EntityType<FamiliarRaven>> FAMILIAR_RAVEN = CraftingRegistry.ENTITY_TYPES.register("familiar_raven",
            () -> EntityType.Builder.of(FamiliarRaven::new, MobCategory.CREATURE).sized(0.5F, 0.6F).clientTrackingRange(8).build("familiar_raven"));

    public static final DeferredHolder<EntityType<?>, EntityType<FamiliarHound>> FAMILIAR_HOUND = CraftingRegistry.ENTITY_TYPES.register("familiar_hound",
            () -> EntityType.Builder.of(FamiliarHound::new, MobCategory.CREATURE).sized(0.6F, 0.85F).eyeHeight(0.68F).passengerAttachments(0.8375F).clientTrackingRange(10).build("familiar_hound"));

    private CraftingEntities() {
    }

    public static void init() {
    }
}
