package nadiendev.umc.crafting.client.model;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.entity.ToadEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ToadModel<T extends ToadEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "toad"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart frontLeft;
    private final ModelPart frontRight;
    private final ModelPart backLeft;
    private final ModelPart backRight;

    public ToadModel(ModelPart part) {
        this.root = part.getChild("root");
        this.head = root.getChild("head");
        this.frontLeft = root.getChild("front_left_leg");
        this.frontRight = root.getChild("front_right_leg");
        this.backLeft = root.getChild("back_left_leg");
        this.backRight = root.getChild("back_right_leg");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 9.0F), PartPose.ZERO);
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 4.0F, 4.0F), PartPose.offset(0.0F, -4.0F, -4.0F));
        head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(24, 16).addBox(1.5F, -4.5F, -2.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(32, 16).addBox(-3.5F, -4.5F, -2.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(3.5F, -3.0F, -3.0F));
        root.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(8, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-3.5F, -3.0F, -3.0F));
        root.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(16, 32).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 4.0F), PartPose.offset(4.0F, -3.0F, 3.0F));
        root.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(32, 32).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 4.0F), PartPose.offset(-4.0F, -3.0F, 3.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;

        float swing = limbSwing * 0.8F;
        frontLeft.xRot = Mth.cos(swing) * 1.2F * limbSwingAmount;
        frontRight.xRot = -Mth.cos(swing) * 1.2F * limbSwingAmount;
        backLeft.xRot = -Mth.cos(swing) * 1.4F * limbSwingAmount;
        backRight.xRot = Mth.cos(swing) * 1.4F * limbSwingAmount;
        root.y = 24.0F - Math.abs(Mth.sin(swing)) * 2.0F * limbSwingAmount;

        if (entity.isInSittingPose()) {
            backLeft.xRot = -1.0F;
            backRight.xRot = -1.0F;
            head.xRot -= 0.25F;
        }
    }
}
