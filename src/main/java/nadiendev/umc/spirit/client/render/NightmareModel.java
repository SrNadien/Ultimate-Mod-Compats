package nadiendev.umc.spirit.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nadiendev.umc.spirit.entity.Nightmare;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class NightmareModel extends EntityModel<Nightmare> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(SpiritWorld.id("nightmare"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart tailUpper;
    private final ModelPart tailLower;

    public NightmareModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.tailUpper = root.getChild("tail_upper");
        this.tailLower = tailUpper.getChild("tail_lower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F)
                        .texOffs(32, 0).addBox(-5.0F, -11.0F, -1.0F, 2.0F, 4.0F, 2.0F)
                        .texOffs(32, 0).mirror().addBox(3.0F, -11.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16)
                        .addBox(-2.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F),
                PartPose.offset(-5.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 16).mirror()
                        .addBox(-1.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F),
                PartPose.offset(5.0F, 3.0F, 0.0F));
        PartDefinition upper = root.addOrReplaceChild("tail_upper", CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-3.0F, 0.0F, -2.0F, 6.0F, 5.0F, 4.0F),
                PartPose.offset(0.0F, 14.0F, 0.0F));
        upper.addOrReplaceChild("tail_lower", CubeListBuilder.create().texOffs(0, 42)
                        .addBox(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, 5.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(Nightmare entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        float sway = Mth.sin(ageInTicks * 0.08F);
        if (entity.isAggressive()) {
            rightArm.xRot = -1.45F + Mth.cos(ageInTicks * 0.3F) * 0.08F;
            leftArm.xRot = -1.45F - Mth.cos(ageInTicks * 0.3F) * 0.08F;
        } else {
            rightArm.xRot = Mth.cos(limbSwing * 0.6F) * 0.6F * limbSwingAmount + sway * 0.06F;
            leftArm.xRot = -Mth.cos(limbSwing * 0.6F) * 0.6F * limbSwingAmount - sway * 0.06F;
        }
        rightArm.zRot = 0.08F + Mth.cos(ageInTicks * 0.09F) * 0.04F;
        leftArm.zRot = -0.08F - Mth.cos(ageInTicks * 0.09F) * 0.04F;
        tailUpper.xRot = 0.12F + sway * 0.1F;
        tailLower.xRot = 0.18F - sway * 0.16F;
        tailUpper.zRot = Mth.cos(ageInTicks * 0.07F) * 0.08F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
