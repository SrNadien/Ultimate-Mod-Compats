package nadiendev.umc.creatures.network;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TransformPayload() implements CustomPacketPayload {

    public static final Type<TransformPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "transform"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TransformPayload> STREAM_CODEC = StreamCodec.unit(new TransformPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
