package nadiendev.umc.creatures.rite.factory;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.creatures.rite.CureCurseRite;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.resources.ResourceLocation;

public record CureCurseFactory() implements RiteFactory {

    public static final ResourceLocation ID = EnchantedIds.id("cure_curse");

    public static final MapCodec<CureCurseFactory> CODEC = MapCodec.unit(new CureCurseFactory());

    @Override
    public Rite create(Rite.BaseRiteParams baseParams, Rite.RiteParams params) {
        return new CureCurseRite(baseParams, params);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
