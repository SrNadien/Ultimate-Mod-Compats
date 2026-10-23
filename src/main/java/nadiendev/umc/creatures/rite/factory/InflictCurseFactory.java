package nadiendev.umc.creatures.rite.factory;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.creatures.form.CreatureKind;
import nadiendev.umc.creatures.rite.InflictCurseRite;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.resources.ResourceLocation;

public record InflictCurseFactory(CreatureKind kind) implements RiteFactory {

    public static final ResourceLocation ID = EnchantedIds.id("inflict_curse");

    public static final MapCodec<InflictCurseFactory> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CreatureKind.CODEC.fieldOf("curse").forGetter(InflictCurseFactory::kind)
    ).apply(instance, InflictCurseFactory::new));

    @Override
    public Rite create(Rite.BaseRiteParams baseParams, Rite.RiteParams params) {
        return new InflictCurseRite(baseParams, params, kind);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
