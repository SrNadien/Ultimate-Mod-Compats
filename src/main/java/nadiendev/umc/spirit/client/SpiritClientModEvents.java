package nadiendev.umc.spirit.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.client.render.NightmareModel;
import nadiendev.umc.spirit.client.render.NightmareRenderer;
import nadiendev.umc.spirit.registry.SpiritEntities;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class SpiritClientModEvents {

    private SpiritClientModEvents() {
    }

    @SubscribeEvent
    public static void onKeys(RegisterKeyMappingsEvent event) {
        event.register(SpiritKeys.CYCLE_INFUSION);
        event.register(SpiritKeys.SPIRIT_RETURN);
    }

    @SubscribeEvent
    public static void onLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(SpiritWorld.id("infusion_hud"), new SpiritHud());
    }

    @SubscribeEvent
    public static void onEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(SpiritWorld.ID, new SpiritSpecialEffects());
    }

    @SubscribeEvent
    public static void onRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SpiritEntities.NIGHTMARE.get(), NightmareRenderer::new);
    }

    @SubscribeEvent
    public static void onLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NightmareModel.LAYER, NightmareModel::createBodyLayer);
    }
}
