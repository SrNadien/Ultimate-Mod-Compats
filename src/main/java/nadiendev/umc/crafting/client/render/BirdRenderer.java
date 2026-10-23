package nadiendev.umc.crafting.client.render;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.client.model.BirdModel;
import nadiendev.umc.crafting.entity.BirdEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BirdRenderer extends MobRenderer<BirdEntity, BirdModel<BirdEntity>> {

    private final ResourceLocation texture;

    public BirdRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, String name, float shadow, boolean familiar) {
        super(context, new BirdModel<>(context.bakeLayer(layer)), shadow);
        this.texture = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/" + name + ".png");
        if (familiar) {
            addLayer(new FamiliarEyesLayer<>(this, ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/" + name + "_eyes.png")));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BirdEntity entity) {
        return texture;
    }
}
