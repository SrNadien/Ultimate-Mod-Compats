package nadiendev.umc.spirit.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.network.CycleInfusionPayload;
import nadiendev.umc.spirit.network.ReturnPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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
        while (SpiritKeys.TOGGLE_HUD.consumeClick()) {
            SpiritHud.visible = !SpiritHud.visible;
            minecraft.player.displayClientMessage(Component.translatable(SpiritHud.visible ? "hud.umc.spirit_hud_on" : "hud.umc.spirit_hud_off"), true);
        }
        while (SpiritKeys.SPIRIT_RETURN.consumeClick()) {
            PacketDistributor.sendToServer(new ReturnPayload());
        }
    }
}
