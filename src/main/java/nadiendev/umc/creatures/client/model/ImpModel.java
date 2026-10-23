package nadiendev.umc.creatures.client.model;

import nadiendev.umc.creatures.entity.ImpEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ImpModel extends HierarchicalModel<ImpEntity> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart tail;

    public ImpModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightWing = this.body.getChild("right_wing");
        this.leftWing = this.body.getChild("left_wing");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, -5.5F, -1.0F, -0.25F, 0.0F, -0.35F));
        head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(2.0F, -5.5F, -1.0F, -0.25F, 0.0F, 0.35F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-3.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.3F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(0.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 32).addBox(-9.0F, -4.0F, 0.0F, 9.0F, 10.0F, 1.0F), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.5F, 0.0F, 0.4F, 0.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(0.0F, -4.0F, 0.0F, 9.0F, 10.0F, 1.0F), PartPose.offsetAndRotation(1.0F, 1.0F, 1.5F, 0.0F, -0.4F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 44).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 0.6F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(16, 44).addBox(-1.5F, -1.0F, 6.0F, 3.0F, 3.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F), PartPose.offset(-3.5F, 12.5F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F), PartPose.offset(3.5F, 12.5F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F), PartPose.offset(-1.5F, 18.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F), PartPose.offset(1.5F, 18.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ImpEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.y = Mth.sin(ageInTicks * 0.2F) * 0.8F - 2.0F;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        float flap = Mth.sin(ageInTicks * 1.1F) * 0.55F;
        this.rightWing.yRot = 0.55F + flap;
        this.leftWing.yRot = -0.55F - flap;
        this.rightLeg.xRot = 0.5F + Mth.cos(ageInTicks * 0.15F) * 0.1F;
        this.leftLeg.xRot = 0.5F - Mth.cos(ageInTicks * 0.15F) * 0.1F;
        float swing = this.attackTime > 0.0F ? Mth.sin(this.attackTime * (float) Math.PI) * -1.4F : 0.0F;
        this.rightArm.xRot = -0.4F + swing + Mth.sin(ageInTicks * 0.12F) * 0.1F;
        this.leftArm.xRot = -0.4F + swing - Mth.sin(ageInTicks * 0.12F) * 0.1F;
        this.tail.xRot = 0.6F + Mth.sin(ageInTicks * 0.2F) * 0.15F;
        this.tail.yRot = Mth.cos(ageInTicks * 0.17F) * 0.25F;
        this.body.xRot = 0.25F;
    }
}
