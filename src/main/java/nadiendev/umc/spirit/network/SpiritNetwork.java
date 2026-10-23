package nadiendev.umc.spirit.network;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.infusion.InfusionEvents;
import nadiendev.umc.spirit.world.SpiritWorldEvents;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class SpiritNetwork {

    private static final String VERSION = "1";

    private SpiritNetwork() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VERSION).optional();
        registrar.playToServer(CycleInfusionPayload.TYPE, CycleInfusionPayload.CODEC, (payload, context) -> {
            if (context.player() instanceof ServerPlayer player) {
                InfusionEvents.cycle(player);
            }
        });
        registrar.playToServer(ReturnPayload.TYPE, ReturnPayload.CODEC, (payload, context) -> {
            if (context.player() instanceof ServerPlayer player) {
                SpiritWorldEvents.startReturn(player);
            }
        });
    }
}
