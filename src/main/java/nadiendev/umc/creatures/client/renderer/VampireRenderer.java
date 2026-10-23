package nadiendev.umc.creatures.client.renderer;

import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.VampireModel;
import nadiendev.umc.creatures.entity.VampireEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class VampireRenderer extends HumanoidMobRenderer<VampireEntity, VampireModel<VampireEntity>> {

    private static final ResourceLocation TEXTURE = CreaturesModelLayers.texture("vampire");
    private static final ResourceLocation GLOW = CreaturesModelLayers.texture("vampire_glow");

    public VampireRenderer(EntityRendererProvider.Context context) {
        super(context, new VampireModel<>(context.bakeLayer(CreaturesModelLayers.VAMPIRE)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new GlowLayer<>(this, GLOW));
    }

    @Override
    public ResourceLocation getTextureLocation(VampireEntity entity) {
        return TEXTURE;
    }
}
