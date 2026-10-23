package nadiendev.umc.creatures.network;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AbilityPayload() implements CustomPacketPayload {

    public static final Type<AbilityPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "ability"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityPayload> STREAM_CODEC = StreamCodec.unit(new AbilityPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
