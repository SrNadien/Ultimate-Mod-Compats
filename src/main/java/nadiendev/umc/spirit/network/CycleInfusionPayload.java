package nadiendev.umc.spirit.network;

import io.netty.buffer.ByteBuf;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CycleInfusionPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CycleInfusionPayload> TYPE = new CustomPacketPayload.Type<>(SpiritWorld.id("cycle_infusion"));
    public static final StreamCodec<ByteBuf, CycleInfusionPayload> CODEC = StreamCodec.unit(new CycleInfusionPayload());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
