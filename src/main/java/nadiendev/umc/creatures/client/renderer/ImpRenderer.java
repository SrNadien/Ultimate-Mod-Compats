package nadiendev.umc.creatures.client.renderer;

import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.ImpModel;
import nadiendev.umc.creatures.entity.ImpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ImpRenderer extends MobRenderer<ImpEntity, ImpModel> {

    private static final ResourceLocation TEXTURE = CreaturesModelLayers.texture("imp");
    private static final ResourceLocation GLOW = CreaturesModelLayers.texture("imp_glow");

    public ImpRenderer(EntityRendererProvider.Context context) {
        super(context, new ImpModel(context.bakeLayer(CreaturesModelLayers.IMP)), 0.25F);
        this.addLayer(new GlowLayer<>(this, GLOW));
    }

    @Override
    public ResourceLocation getTextureLocation(ImpEntity entity) {
        return TEXTURE;
    }
}
