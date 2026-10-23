package nadiendev.umc.creatures.client.renderer;

import javax.annotation.Nullable;
import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.GhostModel;
import nadiendev.umc.creatures.entity.GhostEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GhostRenderer extends MobRenderer<GhostEntity, GhostModel> {

    private static final ResourceLocation TEXTURE = CreaturesModelLayers.texture("ghost");
    private static final ResourceLocation GLOW = CreaturesModelLayers.texture("ghost_glow");

    public GhostRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModel(context.bakeLayer(CreaturesModelLayers.GHOST)), 0.0F);
        this.addLayer(new GlowLayer<>(this, GLOW));
    }

    @Nullable
    @Override
    protected RenderType getRenderType(GhostEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(TEXTURE);
    }

    @Override
    public ResourceLocation getTextureLocation(GhostEntity entity) {
        return TEXTURE;
    }
}
