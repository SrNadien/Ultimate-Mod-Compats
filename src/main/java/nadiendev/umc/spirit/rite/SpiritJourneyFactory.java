package nadiendev.umc.spirit.rite;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.core.EnchantedIds;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.resources.ResourceLocation;

public record SpiritJourneyFactory() implements RiteFactory {

    public static final ResourceLocation ID = EnchantedIds.id("spirit_journey");
    public static final MapCodec<SpiritJourneyFactory> CODEC = MapCodec.unit(new SpiritJourneyFactory());

    @Override
    public Rite create(BaseRiteParams baseParams, RiteParams params) {
        return new SpiritJourneyRite(baseParams, params);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
