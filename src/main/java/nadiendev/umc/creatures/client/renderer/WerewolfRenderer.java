package nadiendev.umc.creatures.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.umc.creatures.client.CreaturesModelLayers;
import nadiendev.umc.creatures.client.layer.GlowLayer;
import nadiendev.umc.creatures.client.model.WerewolfModel;
import nadiendev.umc.creatures.entity.WerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WerewolfRenderer extends HumanoidMobRenderer<WerewolfEntity, WerewolfModel<WerewolfEntity>> {

    public static final float SCALE = 1.12F;
    private static final ResourceLocation TEXTURE = CreaturesModelLayers.texture("werewolf");
    private static final ResourceLocation GLOW = CreaturesModelLayers.texture("werewolf_glow");

    public WerewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfModel<>(context.bakeLayer(CreaturesModelLayers.WEREWOLF)), 0.6F);
        this.addLayer(new GlowLayer<>(this, GLOW));
    }

    @Override
    protected void scale(WerewolfEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(SCALE, SCALE, SCALE);
    }

    @Override
    public ResourceLocation getTextureLocation(WerewolfEntity entity) {
        return TEXTURE;
    }
}
