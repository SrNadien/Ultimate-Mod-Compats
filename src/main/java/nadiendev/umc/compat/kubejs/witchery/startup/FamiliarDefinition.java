package nadiendev.umc.compat.kubejs.witchery.startup;

import java.util.function.BiConsumer;
import net.favouriteless.enchanted.api.familiars.FamiliarType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;

public class FamiliarDefinition extends FamiliarType<TamableAnimal, TamableAnimal> {

    private BiConsumer<TamableAnimal, TamableAnimal> copier = (from, familiar) -> { };

    FamiliarDefinition(ResourceLocation id, ResourceLocation typeIn, ResourceLocation typeOut) {
        super(id, () -> resolve(typeIn), () -> resolve(typeOut));
    }

    public FamiliarDefinition copy(BiConsumer<TamableAnimal, TamableAnimal> callback) {
        this.copier = callback;
        return this;
    }

    @Override
    public TamableAnimal create(TamableAnimal from) {
        TamableAnimal familiar = getTypeOut().create(from.level());
        copier.accept(from, familiar);
        return familiar;
    }

    @SuppressWarnings("unchecked")
    private static EntityType<TamableAnimal> resolve(ResourceLocation id) {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(id)
                .orElseThrow(() -> new IllegalStateException("La entidad " + id + " del familiar no existe"));
        return (EntityType<TamableAnimal>) type;
    }
}
