package nadiendev.umc.creatures.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.client.FormVisuals;
import nadiendev.umc.creatures.form.CreatureCurse;
import nadiendev.umc.creatures.form.CreatureForms;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FormEyesLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/form_eyes.png");
    private static final int FULL_BRIGHT = 0xF000F0;

    public FormEyesLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player.isInvisible()) {
            return;
        }
        CreatureCurse curse = CreatureForms.get(player);
        if (!curse.transformed()) {
            return;
        }
        Integer color = FormVisuals.eyeColor(curse.kind());
        if (color == null) {
            return;
        }
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(TEXTURE));
        getParentModel().renderToBuffer(poseStack, consumer, FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color);
    }
}
