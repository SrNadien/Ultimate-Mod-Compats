package nadiendev.umc.creatures.form;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CreatureCurse(CreatureKind kind, boolean transformed, boolean forced, long forcedDay, long transformReadyAt, long abilityReadyAt) {

    public static final CreatureCurse NONE = new CreatureCurse(CreatureKind.NONE, false, false, -1L, 0L, 0L);

    public static final Codec<CreatureCurse> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CreatureKind.CODEC.optionalFieldOf("kind", CreatureKind.NONE).forGetter(CreatureCurse::kind),
            Codec.BOOL.optionalFieldOf("transformed", false).forGetter(CreatureCurse::transformed),
            Codec.BOOL.optionalFieldOf("forced", false).forGetter(CreatureCurse::forced),
            Codec.LONG.optionalFieldOf("forced_day", -1L).forGetter(CreatureCurse::forcedDay),
            Codec.LONG.optionalFieldOf("transform_ready_at", 0L).forGetter(CreatureCurse::transformReadyAt),
            Codec.LONG.optionalFieldOf("ability_ready_at", 0L).forGetter(CreatureCurse::abilityReadyAt)
    ).apply(instance, CreatureCurse::new));

    public static final StreamCodec<ByteBuf, CreatureCurse> STREAM_CODEC = StreamCodec.composite(
            CreatureKind.STREAM_CODEC, CreatureCurse::kind,
            ByteBufCodecs.BOOL, CreatureCurse::transformed,
            ByteBufCodecs.BOOL, CreatureCurse::forced,
            ByteBufCodecs.VAR_LONG, CreatureCurse::forcedDay,
            ByteBufCodecs.VAR_LONG, CreatureCurse::transformReadyAt,
            ByteBufCodecs.VAR_LONG, CreatureCurse::abilityReadyAt,
            CreatureCurse::new
    );

    public boolean isCursed() {
        return kind != CreatureKind.NONE;
    }

    public boolean isTransformed(CreatureKind expected) {
        return transformed && kind == expected;
    }

    public CreatureCurse withKind(CreatureKind newKind) {
        return new CreatureCurse(newKind, false, false, -1L, transformReadyAt, abilityReadyAt);
    }

    public CreatureCurse withTransformed(boolean value, boolean wasForced) {
        return new CreatureCurse(kind, value, value && wasForced, forcedDay, transformReadyAt, abilityReadyAt);
    }

    public CreatureCurse withForcedDay(long day) {
        return new CreatureCurse(kind, transformed, forced, day, transformReadyAt, abilityReadyAt);
    }

    public CreatureCurse withTransformReadyAt(long time) {
        return new CreatureCurse(kind, transformed, forced, forcedDay, time, abilityReadyAt);
    }

    public CreatureCurse withAbilityReadyAt(long time) {
        return new CreatureCurse(kind, transformed, forced, forcedDay, transformReadyAt, time);
    }
}
