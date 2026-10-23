package nadiendev.umc.crafting.client;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.client.armor.WitchArmorModel;
import nadiendev.umc.crafting.client.model.BirdModel;
import nadiendev.umc.crafting.client.model.ToadModel;
import nadiendev.umc.crafting.client.render.BirdRenderer;
import nadiendev.umc.crafting.client.render.HoundRenderer;
import nadiendev.umc.crafting.client.render.ToadRenderer;
import nadiendev.umc.crafting.entity.BirdEntity;
import nadiendev.umc.crafting.registry.CraftingEntities;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class CraftingClientEvents {

    private CraftingClientEvents() {
    }

    @SubscribeEvent
    public static void onLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ToadModel.LAYER, ToadModel::createLayer);
        event.registerLayerDefinition(BirdModel.OWL_LAYER, BirdModel::createOwlLayer);
        event.registerLayerDefinition(BirdModel.RAVEN_LAYER, BirdModel::createRavenLayer);
        event.registerLayerDefinition(WitchArmorModel.HAT_LAYER, WitchArmorModel::createHatLayer);
        event.registerLayerDefinition(WitchArmorModel.ROBE_LAYER, WitchArmorModel::createRobeLayer);
        event.registerLayerDefinition(WitchArmorModel.CLOAK_LAYER, WitchArmorModel::createCloakLayer);
        event.registerLayerDefinition(WitchArmorModel.BOOTS_LAYER, WitchArmorModel::createBootsLayer);
    }

    @SubscribeEvent
    public static void onRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CraftingEntities.TOAD.get(), context -> new ToadRenderer(context, false));
        event.registerEntityRenderer(CraftingEntities.FAMILIAR_TOAD.get(), context -> new ToadRenderer(context, true));
        event.<BirdEntity>registerEntityRenderer(CraftingEntities.OWL.get(), context -> new BirdRenderer(context, BirdModel.OWL_LAYER, "owl", 0.3F, false));
        event.<BirdEntity>registerEntityRenderer(CraftingEntities.FAMILIAR_OWL.get(), context -> new BirdRenderer(context, BirdModel.OWL_LAYER, "owl", 0.3F, true));
        event.<BirdEntity>registerEntityRenderer(CraftingEntities.RAVEN.get(), context -> new BirdRenderer(context, BirdModel.RAVEN_LAYER, "raven", 0.25F, false));
        event.<BirdEntity>registerEntityRenderer(CraftingEntities.FAMILIAR_RAVEN.get(), context -> new BirdRenderer(context, BirdModel.RAVEN_LAYER, "raven", 0.25F, true));
        event.registerEntityRenderer(CraftingEntities.FAMILIAR_HOUND.get(), HoundRenderer::new);
    }

    @SubscribeEvent
    public static void onClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new ArmorExtension(WitchArmorModel.HAT_LAYER), CraftingItems.WITCH_HAT.get());
        event.registerItem(new ArmorExtension(WitchArmorModel.ROBE_LAYER), CraftingItems.WITCH_ROBE.get());
        event.registerItem(new ArmorExtension(WitchArmorModel.CLOAK_LAYER), CraftingItems.WITCH_CLOAK.get());
        event.registerItem(new ArmorExtension(WitchArmorModel.BOOTS_LAYER), CraftingItems.WITCH_BOOTS.get());
    }

    private static final class ArmorExtension implements IClientItemExtensions {

        private final ModelLayerLocation layer;
        private WitchArmorModel cached;

        private ArmorExtension(ModelLayerLocation layer) {
            this.layer = layer;
        }

        @Override
        public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
            if (cached == null) {
                cached = new WitchArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
            }
            return cached;
        }
    }
}
