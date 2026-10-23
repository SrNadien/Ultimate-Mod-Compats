package nadiendev.umc.creatures.client.model;

import nadiendev.umc.creatures.entity.GhostEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class GhostModel extends HierarchicalModel<GhostEntity> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart waist;
    private final ModelPart skirt;
    private final ModelPart wisp;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public GhostModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.torso = root.getChild("torso");
        this.waist = this.torso.getChild("waist");
        this.skirt = this.waist.getChild("skirt");
        this.wisp = this.skirt.getChild("wisp");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F), PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition waist = torso.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(16, 28).addBox(-3.0F, 0.0F, -1.5F, 6.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition skirt = waist.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(16, 38).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 6.0F, 0.0F));
        skirt.addOrReplaceChild("wisp", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 5.0F, 1.0F), PartPose.offset(0.0F, 6.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F), PartPose.offset(-4.0F, 4.5F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(0.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F), PartPose.offset(4.0F, 4.5F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(GhostEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.y = Mth.sin(ageInTicks * 0.1F) * 1.2F;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.torso.xRot = 0.15F + Mth.sin(ageInTicks * 0.07F) * 0.05F;
        this.waist.xRot = 0.15F + Mth.sin(ageInTicks * 0.07F + 0.6F) * 0.08F;
        this.skirt.xRot = 0.15F + Mth.sin(ageInTicks * 0.07F + 1.2F) * 0.12F;
        this.wisp.xRot = 0.1F + Mth.sin(ageInTicks * 0.07F + 1.8F) * 0.18F;
        float reach = entity.isAggressive() ? -1.5F : -0.9F;
        float swing = this.attackTime > 0.0F ? Mth.sin(this.attackTime * (float) Math.PI) * -0.8F : 0.0F;
        this.rightArm.xRot = reach + swing + Mth.sin(ageInTicks * 0.1F) * 0.08F;
        this.leftArm.xRot = reach + swing - Mth.sin(ageInTicks * 0.1F) * 0.08F;
        this.rightArm.zRot = 0.15F + Mth.cos(ageInTicks * 0.09F) * 0.05F;
        this.leftArm.zRot = -0.15F - Mth.cos(ageInTicks * 0.09F) * 0.05F;
    }
}
