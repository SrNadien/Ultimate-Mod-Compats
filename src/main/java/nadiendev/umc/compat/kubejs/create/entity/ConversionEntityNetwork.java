package nadiendev.umc.compat.kubejs.create.entity;

import nadiendev.umc.UltimateModCompats;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = UltimateModCompats.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ConversionEntityNetwork {

    private static final String VERSION = "1";

    private ConversionEntityNetwork() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VERSION).optional();
        registrar.playToClient(ConversionEntityPayload.TYPE, ConversionEntityPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> ConversionEntityClientData.set(payload.entries())));
    }
}
