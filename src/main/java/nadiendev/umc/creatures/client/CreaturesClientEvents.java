package nadiendev.umc.creatures.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.creatures.client.geo.FormAnimatable;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureCurse;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.network.AbilityPayload;
import nadiendev.umc.creatures.network.TransformPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class CreaturesClientEvents {

    private CreaturesClientEvents() {
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.screen != null) {
            while (CreaturesKeys.TRANSFORM.consumeClick()) {
            }
            while (CreaturesKeys.ABILITY.consumeClick()) {
            }
            return;
        }
        CreatureCurse curse = CreatureForms.get(player);
        while (CreaturesKeys.TRANSFORM.consumeClick()) {
            if (curse.isCursed()) {
                PacketDistributor.sendToServer(new TransformPayload());
            }
        }
        while (CreaturesKeys.ABILITY.consumeClick()) {
            if (curse.transformed()) {
                PacketDistributor.sendToServer(new AbilityPayload());
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayer(RenderPlayerEvent.Pre event) {
        if (!UmcConfig.SPEC.isLoaded() || !(event.getEntity() instanceof AbstractClientPlayer player)) {
            return;
        }
        CreatureCurse curse = CreatureForms.get(player);
        if (!curse.transformed()) {
            return;
        }
        GeoReplacedEntityRenderer<AbstractClientPlayer, FormAnimatable> geoRenderer = CreaturesPlayerForms.getGeo(curse.kind());
        if (geoRenderer != null) {
            event.setCanceled(true);
            float geoYaw = Mth.lerp(event.getPartialTick(), player.yRotO, player.getYRot());
            geoRenderer.render(player, geoYaw, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
        }
    }
}
