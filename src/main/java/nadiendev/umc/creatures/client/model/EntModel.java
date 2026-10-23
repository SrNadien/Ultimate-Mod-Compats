package nadiendev.umc.creatures.client.model;

import nadiendev.umc.creatures.entity.EntEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class EntModel extends HierarchicalModel<EntEntity> {

    private final ModelPart root;
    private final ModelPart canopy;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public EntModel(ModelPart root) {
        this.root = root;
        this.canopy = root.getChild("canopy");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("trunk", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 18.0F, 10.0F), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition canopy = root.addOrReplaceChild("canopy", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -10.0F, -6.0F, 12.0F, 10.0F, 12.0F), PartPose.offset(0.0F, -4.0F, 0.0F));
        canopy.addOrReplaceChild("leaves", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -10.0F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(1.6F)), PartPose.ZERO);
        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 0).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.offset(-5.0F, -1.0F, 0.0F));
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(88, 0).addBox(-5.0F, 14.0F, -3.0F, 6.0F, 4.0F, 6.0F), PartPose.ZERO);
        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(0.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.offset(5.0F, -1.0F, 0.0F));
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(88, 0).mirror().addBox(-1.0F, 14.0F, -3.0F, 6.0F, 4.0F, 6.0F), PartPose.ZERO);
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(64, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 10.0F, 5.0F), PartPose.offset(-3.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 10.0F, 5.0F), PartPose.offset(3.0F, 14.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(EntEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.canopy.yRot = netHeadYaw * ((float) Math.PI / 180.0F) * 0.5F;
        this.canopy.xRot = headPitch * ((float) Math.PI / 180.0F) * 0.3F;
        this.canopy.zRot = Mth.sin(ageInTicks * 0.05F) * 0.03F;
        float walk = Mth.cos(limbSwing * 0.5F) * 0.9F * limbSwingAmount;
        this.rightLeg.xRot = walk;
        this.leftLeg.xRot = -walk;
        float idle = Mth.sin(ageInTicks * 0.06F) * 0.05F;
        this.rightArm.xRot = -walk * 0.6F + idle;
        this.leftArm.xRot = walk * 0.6F - idle;
        this.rightArm.zRot = 0.05F;
        this.leftArm.zRot = -0.05F;
        if (this.attackTime > 0.0F) {
            float raise = Mth.sin(this.attackTime * (float) Math.PI);
            this.rightArm.xRot = -2.4F * raise;
            this.leftArm.xRot = -2.4F * raise;
        }
    }
}
