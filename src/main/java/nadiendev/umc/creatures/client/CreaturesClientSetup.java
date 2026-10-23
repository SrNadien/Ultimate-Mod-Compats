package nadiendev.umc.creatures.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.client.layer.FormEyesLayer;
import nadiendev.umc.creatures.client.model.EntModel;
import nadiendev.umc.creatures.client.model.GhostModel;
import nadiendev.umc.creatures.client.model.ImpModel;
import nadiendev.umc.creatures.client.model.ServantModel;
import nadiendev.umc.creatures.client.model.VampireModel;
import nadiendev.umc.creatures.client.model.WerewolfModel;
import nadiendev.umc.creatures.client.renderer.CreatureRenderer;
import nadiendev.umc.creatures.client.renderer.EntRenderer;
import nadiendev.umc.creatures.client.renderer.GhostRenderer;
import nadiendev.umc.creatures.client.renderer.ImpRenderer;
import nadiendev.umc.creatures.client.renderer.ServantRenderer;
import nadiendev.umc.creatures.client.renderer.VampireRenderer;
import nadiendev.umc.creatures.client.renderer.WerewolfRenderer;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class CreaturesClientSetup {

    private CreaturesClientSetup() {
    }

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CreaturesModelLayers.WEREWOLF, WerewolfModel::createLayer);
        event.registerLayerDefinition(CreaturesModelLayers.VAMPIRE, VampireModel::createLayer);
        event.registerLayerDefinition(CreaturesModelLayers.GHOST, GhostModel::createLayer);
        event.registerLayerDefinition(CreaturesModelLayers.IMP, ImpModel::createLayer);
        event.registerLayerDefinition(CreaturesModelLayers.ENT, EntModel::createLayer);
        event.registerLayerDefinition(CreaturesModelLayers.ZOMBIE_SERVANT, ServantModel::createZombieLayer);
        event.registerLayerDefinition(CreaturesModelLayers.SKELETON_SERVANT, ServantModel::createSkeletonLayer);
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CreaturesEntities.WEREWOLF.get(), context -> new CreatureRenderer<>(context, LocalModelAvailability.WEREWOLF, WerewolfRenderer::new));
        event.registerEntityRenderer(CreaturesEntities.VAMPIRE.get(), context -> new CreatureRenderer<>(context, LocalModelAvailability.VAMPIRE, VampireRenderer::new));
        event.registerEntityRenderer(CreaturesEntities.GHOST.get(), GhostRenderer::new);
        event.registerEntityRenderer(CreaturesEntities.IMP.get(), ImpRenderer::new);
        event.registerEntityRenderer(CreaturesEntities.ENT.get(), EntRenderer::new);
        event.registerEntityRenderer(CreaturesEntities.ZOMBIE_SERVANT.get(), context -> new ServantRenderer(context, CreaturesModelLayers.ZOMBIE_SERVANT, "zombie_servant"));
        event.registerEntityRenderer(CreaturesEntities.SKELETON_SERVANT.get(), context -> new ServantRenderer(context, CreaturesModelLayers.SKELETON_SERVANT, "skeleton_servant"));
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        CreaturesPlayerForms.create(event.getContext());
        for (PlayerSkin.Model skin : PlayerSkin.Model.values()) {
            if (event.getSkin(skin) instanceof PlayerRenderer renderer) {
                renderer.addLayer(new FormEyesLayer(renderer));
            }
        }
    }

    @SubscribeEvent
    public static void reloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) LocalModelAvailability::reload);
    }

    @SubscribeEvent
    public static void keys(RegisterKeyMappingsEvent event) {
        event.register(CreaturesKeys.TRANSFORM);
        event.register(CreaturesKeys.ABILITY);
    }
}
