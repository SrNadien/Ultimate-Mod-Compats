package nadiendev.umc.creatures.client.model;

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

public class VampireModel<T extends LivingEntity> extends HumanoidModel<T> {

    private final ModelPart cape;

    public VampireModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 16.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 2.2F, 0.1F, 0.0F, 0.0F));
        body.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.25F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity instanceof Mob mob && mob.isAggressive()) {
            this.rightArm.xRot = -1.3F + Mth.sin(this.attackTime * (float) Math.PI) * -0.9F;
            this.leftArm.xRot = -1.3F;
        }
        float sway = Math.min(1.0F, limbSwingAmount);
        this.cape.xRot = 0.1F + sway * 0.7F + (this.crouching ? 0.3F : 0.0F) + Mth.sin(ageInTicks * 0.08F) * 0.03F;
    }
}
