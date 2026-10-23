package nadiendev.umc.spirit.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.network.CycleInfusionPayload;
import nadiendev.umc.spirit.network.ReturnPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class SpiritClientEvents {

    private SpiritClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        while (SpiritKeys.CYCLE_INFUSION.consumeClick()) {
            PacketDistributor.sendToServer(new CycleInfusionPayload());
        }
        while (SpiritKeys.SPIRIT_RETURN.consumeClick()) {
            PacketDistributor.sendToServer(new ReturnPayload());
        }
    }
}
