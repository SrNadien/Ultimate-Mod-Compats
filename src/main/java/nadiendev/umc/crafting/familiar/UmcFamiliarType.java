package nadiendev.umc.crafting.familiar;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.favouriteless.enchanted.api.familiars.FamiliarType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;

public class UmcFamiliarType<T extends TamableAnimal, C extends TamableAnimal> extends FamiliarType<T, C> {

    private final BiConsumer<T, C> copier;

    public UmcFamiliarType(ResourceLocation id, Supplier<EntityType<T>> typeIn, Supplier<EntityType<C>> typeOut, BiConsumer<T, C> copier) {
        super(id, typeIn, typeOut);
        this.copier = copier;
    }

    @Override
    public C create(T from) {
        C familiar = getTypeOut().create(from.level());
        copier.accept(from, familiar);
        return familiar;
    }
}
