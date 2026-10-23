package nadiendev.umc.creatures.client.renderer;

import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.ServantModel;
import nadiendev.umc.creatures.entity.UndeadServantEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ServantRenderer extends HumanoidMobRenderer<UndeadServantEntity, ServantModel> {

    private final ResourceLocation texture;

    public ServantRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, String textureName) {
        super(context, new ServantModel(context.bakeLayer(layer)), 0.5F);
        this.texture = CreaturesModelLayers.texture(textureName);
        this.addLayer(new GlowLayer<>(this, CreaturesModelLayers.texture(textureName + "_glow")));
    }

    @Override
    public ResourceLocation getTextureLocation(UndeadServantEntity entity) {
        return texture;
    }
}
