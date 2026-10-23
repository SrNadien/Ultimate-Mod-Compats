package nadiendev.umc.creatures.rite.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.creatures.rite.SummonMinionsRite;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public record SummonMinionsFactory(EntityType<?> entity, int count) implements RiteFactory {

    public static final ResourceLocation ID = EnchantedIds.id("summon_minions");

    public static final MapCodec<SummonMinionsFactory> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(SummonMinionsFactory::entity),
            Codec.INT.optionalFieldOf("count", 1).forGetter(SummonMinionsFactory::count)
    ).apply(instance, SummonMinionsFactory::new));

    @Override
    public Rite create(Rite.BaseRiteParams baseParams, Rite.RiteParams params) {
        return new SummonMinionsRite(baseParams, params, entity, count);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
