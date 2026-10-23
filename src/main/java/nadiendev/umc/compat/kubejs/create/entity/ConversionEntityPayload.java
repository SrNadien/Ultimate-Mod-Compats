package nadiendev.umc.compat.kubejs.create.entity;

import java.util.List;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ConversionEntityPayload(List<ConversionEntityRegistry.Entry> entries) implements CustomPacketPayload {

    public static final Type<ConversionEntityPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "conversion_entities"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConversionEntityPayload> STREAM_CODEC = StreamCodec.composite(
            ConversionEntityRegistry.Entry.STREAM_CODEC.apply(ByteBufCodecs.list()), ConversionEntityPayload::entries,
            ConversionEntityPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
