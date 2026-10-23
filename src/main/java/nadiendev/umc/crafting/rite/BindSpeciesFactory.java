package nadiendev.umc.crafting.rite;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.core.EnchantedIds;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.api.circle_magic.RiteFactoryRegistry;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class BindSpeciesFactory implements RiteFactory {

    public static final ResourceLocation ID = EnchantedIds.id("bind_species_familiar");

    public static final MapCodec<BindSpeciesFactory> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("species").forGetter(factory -> factory.species)
    ).apply(instance, BindSpeciesFactory::new));

    private final EntityType<?> species;

    public BindSpeciesFactory(EntityType<?> species) {
        this.species = species;
    }

    public static void register() {
        RiteFactoryRegistry.get().register(ID, CODEC);
    }

    @Override
    public Rite create(BaseRiteParams baseParams, RiteParams params) {
        return new BindSpeciesRite(baseParams, params, species);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
