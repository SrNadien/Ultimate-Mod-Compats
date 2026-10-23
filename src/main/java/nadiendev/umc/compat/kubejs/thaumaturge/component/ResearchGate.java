package nadiendev.umc.compat.kubejs.thaumaturge.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record ResearchGate(ResourceLocation entry, Optional<Integer> stage, boolean negate) {

    public static final Codec<ResearchGate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("entry").forGetter(ResearchGate::entry),
            Codec.INT.optionalFieldOf("stage").forGetter(ResearchGate::stage),
            Codec.BOOL.optionalFieldOf("negate", false).forGetter(ResearchGate::negate)
    ).apply(instance, ResearchGate::new));
}
