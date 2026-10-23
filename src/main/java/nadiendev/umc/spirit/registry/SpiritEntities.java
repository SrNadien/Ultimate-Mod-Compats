package nadiendev.umc.spirit.registry;

import nadiendev.umc.spirit.entity.Nightmare;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class SpiritEntities {

    public static final DeferredHolder<EntityType<?>, EntityType<Nightmare>> NIGHTMARE = SpiritRegistry.ENTITY_TYPES.register("nightmare",
            () -> EntityType.Builder.of(Nightmare::new, MobCategory.MONSTER).sized(0.7F, 1.9F).clientTrackingRange(8).build("nightmare"));

    private SpiritEntities() {
    }

    public static void load() {
    }
}
