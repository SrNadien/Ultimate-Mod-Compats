package nadiendev.umc.creatures.network;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureForms;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CreaturesNetwork {

    private static final String VERSION = "1";

    private CreaturesNetwork() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VERSION).optional();
        registrar.playToServer(TransformPayload.TYPE, TransformPayload.STREAM_CODEC, CreaturesNetwork::handleTransform);
        registrar.playToServer(AbilityPayload.TYPE, AbilityPayload.STREAM_CODEC, CreaturesNetwork::handleAbility);
    }

    private static void handleTransform(TransformPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && CreaturesConfig.active()) {
                CreatureForms.toggle(player);
            }
        });
    }

    private static void handleAbility(AbilityPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && CreaturesConfig.active()) {
                CreatureForms.useAbility(player);
            }
        });
    }
}
