package nadiendev.umc.creatures.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class WerewolfModel<T extends LivingEntity> extends HumanoidModel<T> {

    private final ModelPart tail;

    public WerewolfModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("body").getChild("tail");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        CubeDeformation fur = new CubeDeformation(0.3F);

        PartDefinition head = root.getChild("head");
        head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(24, 48).addBox(-2.0F, -3.0F, -8.0F, 4.0F, 3.0F, 4.0F), PartPose.ZERO);
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(44, 48).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 1.0F, 3.0F), PartPose.ZERO);
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 56).addBox(-4.0F, -11.0F, -1.0F, 2.0F, 3.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 56).mirror().addBox(2.0F, -11.0F, -1.0F, 2.0F, 3.0F, 1.0F), PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("fur", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, fur), PartPose.ZERO);
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 48).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 9.0F, 2.0F, 0.9F, 0.0F, 0.0F));

        root.getChild("right_arm").addOrReplaceChild("fur", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, fur), PartPose.ZERO);
        root.getChild("left_arm").addOrReplaceChild("fur", CubeListBuilder.create().texOffs(40, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, fur), PartPose.ZERO);
        root.getChild("right_leg").addOrReplaceChild("fur", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, fur), PartPose.ZERO);
        root.getChild("left_leg").addOrReplaceChild("fur", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, fur), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity instanceof Mob mob && mob.isAggressive()) {
            AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
        }
        this.tail.xRot = 0.9F + Mth.cos(limbSwing * 0.6662F) * 0.15F * limbSwingAmount;
        this.tail.zRot = Mth.cos(ageInTicks * 0.1F) * 0.06F;
    }
}
