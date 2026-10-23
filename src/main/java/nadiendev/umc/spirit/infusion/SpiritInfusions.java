package nadiendev.umc.spirit.infusion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import io.netty.buffer.ByteBuf;

public record SpiritInfusions(Map<InfusionType, Integer> energy, Optional<InfusionType> active) {

    public static final SpiritInfusions EMPTY = new SpiritInfusions(Map.of(), Optional.empty());

    public static final Codec<SpiritInfusions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(InfusionType.CODEC, Codec.INT).optionalFieldOf("energy", Map.of()).forGetter(SpiritInfusions::energy),
            InfusionType.CODEC.optionalFieldOf("active").forGetter(SpiritInfusions::active)
    ).apply(instance, SpiritInfusions::new));

    public static final StreamCodec<ByteBuf, SpiritInfusions> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public int energy(InfusionType type) {
        return energy.getOrDefault(type, 0);
    }

    public SpiritInfusions withEnergy(InfusionType type, int amount) {
        Map<InfusionType, Integer> copy = new EnumMap<>(InfusionType.class);
        copy.putAll(energy);
        if (amount <= 0) {
            copy.remove(type);
        } else {
            copy.put(type, amount);
        }
        return new SpiritInfusions(copy, active);
    }

    public SpiritInfusions withActive(InfusionType type) {
        return new SpiritInfusions(energy, Optional.ofNullable(type));
    }

    public boolean isActive(InfusionType type) {
        return active.isPresent() && active.get() == type && energy(type) > 0 && type.enabled();
    }
}
