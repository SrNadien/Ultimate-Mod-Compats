package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record AspectAmount(ResourceLocation aspect, int amount) {

    public static final Codec<AspectAmount> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("aspect").forGetter(AspectAmount::aspect),
            Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("amount", 1).forGetter(AspectAmount::amount)
    ).apply(instance, AspectAmount::new));
}
