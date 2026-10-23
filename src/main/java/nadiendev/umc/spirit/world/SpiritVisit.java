package nadiendev.umc.spirit.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record SpiritVisit(ResourceKey<Level> originDimension, BlockPos originPos, float originYaw, long returnAt, long countdownEnd) {

    public static final Codec<SpiritVisit> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(SpiritVisit::originDimension),
            BlockPos.CODEC.fieldOf("pos").forGetter(SpiritVisit::originPos),
            Codec.FLOAT.optionalFieldOf("yaw", 0.0F).forGetter(SpiritVisit::originYaw),
            Codec.LONG.optionalFieldOf("return_at", -1L).forGetter(SpiritVisit::returnAt),
            Codec.LONG.optionalFieldOf("countdown_end", -1L).forGetter(SpiritVisit::countdownEnd)
    ).apply(instance, SpiritVisit::new));

    public static final StreamCodec<ByteBuf, SpiritVisit> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public SpiritVisit withCountdown(long end) {
        return new SpiritVisit(originDimension, originPos, originYaw, returnAt, end);
    }

    public SpiritVisit withReturnAt(long time) {
        return new SpiritVisit(originDimension, originPos, originYaw, time, countdownEnd);
    }
}
