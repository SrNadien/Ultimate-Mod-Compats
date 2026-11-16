package nadiendev.umc.core.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.core.config.CompatSwitches;
import net.neoforged.neoforge.common.conditions.ICondition;

/** True when the switch of one compatibility is on in {@code umc.toml}. */
public record CompatCondition(String compat) implements ICondition {

    public static final MapCodec<CompatCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("compat").forGetter(CompatCondition::compat)
    ).apply(instance, CompatCondition::new));

    @Override
    public boolean test(IContext context) {
        return CompatSwitches.switchedOn(compat);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
