package nadiendev.umc.spirit.network;

import io.netty.buffer.ByteBuf;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ReturnPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ReturnPayload> TYPE = new CustomPacketPayload.Type<>(SpiritWorld.id("spirit_return"));
    public static final StreamCodec<ByteBuf, ReturnPayload> CODEC = StreamCodec.unit(new ReturnPayload());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
