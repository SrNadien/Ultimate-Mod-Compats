package nadiendev.umc.creatures.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.EntModel;
import nadiendev.umc.creatures.entity.EntEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EntRenderer extends MobRenderer<EntEntity, EntModel> {

    private static final ResourceLocation TEXTURE = CreaturesModelLayers.texture("ent");
    private static final ResourceLocation GLOW = CreaturesModelLayers.texture("ent_glow");

    public EntRenderer(EntityRendererProvider.Context context) {
        super(context, new EntModel(context.bakeLayer(CreaturesModelLayers.ENT)), 1.0F);
        this.addLayer(new GlowLayer<>(this, GLOW));
    }

    @Override
    protected void scale(EntEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntEntity entity) {
        return TEXTURE;
    }
}
