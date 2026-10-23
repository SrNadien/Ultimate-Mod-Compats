package nadiendev.umc.crafting.client.model;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.entity.BirdEntity;
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

public class BirdModel<T extends BirdEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation OWL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "owl"), "main");
    public static final ModelLayerLocation RAVEN_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "raven"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BirdModel(ModelPart part) {
        this.root = part.getChild("root");
        this.head = root.getChild("head");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createOwlLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -2.5F, 6.0F, 6.0F, 5.0F), PartPose.ZERO);
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F), PartPose.offset(0.0F, -8.0F, 0.0F));
        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(44, 0).addBox(-0.5F, -3.0F, -3.5F, 1.0F, 2.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_tuft", CubeListBuilder.create().texOffs(48, 0).addBox(1.5F, -7.0F, -1.5F, 1.0F, 2.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("right_tuft", CubeListBuilder.create().texOffs(52, 0).addBox(-2.5F, -7.0F, -1.5F, 1.0F, 2.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F), PartPose.offset(3.0F, -8.0F, 0.0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(10, 12).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F), PartPose.offset(-3.0F, -8.0F, 0.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 2.5F, 0.35F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(30, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(1.5F, -2.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(34, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(-1.5F, -2.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    public static LayerDefinition createRavenLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, -4.0F, 5.0F, 5.0F, 8.0F), PartPose.ZERO);
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(26, 0).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.0F, -6.0F, -3.5F));
        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(42, 0).addBox(-0.5F, -1.5F, -6.0F, 1.0F, 1.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F), PartPose.offset(2.5F, -7.0F, 0.0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F), PartPose.offset(-2.5F, -7.0F, 0.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 14).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, 0.2F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(1.0F, -2.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(4, 26).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(-1.0F, -2.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;

        if (entity.isFlying()) {
            float flap = Mth.sin(ageInTicks * 1.3F) * 0.8F + 0.7F;
            leftWing.zRot = -flap;
            rightWing.zRot = flap;
            leftLeg.xRot = 0.8F;
            rightLeg.xRot = 0.8F;
            tail.xRot = 0.5F;
        } else {
            leftWing.zRot = -0.05F;
            rightWing.zRot = 0.05F;
            float step = Mth.cos(limbSwing * 0.9F) * 0.9F * limbSwingAmount;
            leftLeg.xRot = step;
            rightLeg.xRot = -step;
            tail.xRot = 0.3F;
        }
    }
}
