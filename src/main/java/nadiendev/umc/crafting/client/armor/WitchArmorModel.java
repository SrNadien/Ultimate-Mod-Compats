package nadiendev.umc.crafting.client.armor;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WitchArmorModel extends HumanoidModel<LivingEntity> {

    public static final ModelLayerLocation HAT_LAYER = layer("witch_hat");
    public static final ModelLayerLocation ROBE_LAYER = layer("witch_robe");
    public static final ModelLayerLocation CLOAK_LAYER = layer("witch_cloak");
    public static final ModelLayerLocation BOOTS_LAYER = layer("witch_boots");

    public WitchArmorModel(ModelPart root) {
        super(root);
    }

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name), "main");
    }

    private static PartDefinition blank(MeshDefinition mesh, String name) {
        return mesh.getRoot().addOrReplaceChild(name, CubeListBuilder.create(), PartPose.ZERO);
    }

    public static LayerDefinition createHatLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        blank(mesh, "hat");
        PartDefinition head = blank(mesh, "head");
        head.addOrReplaceChild("brim", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 1.0F, 14.0F), PartPose.rotation(-0.06F, 0.0F, 0.0F));
        head.addOrReplaceChild("band", CubeListBuilder.create().texOffs(0, 40).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 1.0F, 10.0F), PartPose.ZERO);
        PartDefinition cone1 = head.addOrReplaceChild("cone_1", CubeListBuilder.create().texOffs(0, 15).addBox(-4.5F, -3.0F, -4.5F, 9.0F, 3.0F, 9.0F), PartPose.offset(0.0F, -10.0F, 0.0F));
        PartDefinition cone2 = cone1.addOrReplaceChild("cone_2", CubeListBuilder.create().texOffs(0, 28).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.12F, 0.0F, 0.0F));
        PartDefinition cone3 = cone2.addOrReplaceChild("cone_3", CubeListBuilder.create().texOffs(24, 28).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.16F, 0.0F, 0.0F));
        PartDefinition cone4 = cone3.addOrReplaceChild("cone_4", CubeListBuilder.create().texOffs(40, 28).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.25F, 0.0F, 0.0F));
        cone4.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(48, 28).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.45F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createRobeLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        CubeDeformation fat = new CubeDeformation(0.4F);
        PartDefinition body = mesh.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, fat), PartPose.ZERO);
        PartDefinition skirt = body.addOrReplaceChild("skirt_1", CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 5.0F, 5.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        skirt.addOrReplaceChild("skirt_2", CubeListBuilder.create().texOffs(0, 28).addBox(-5.5F, 0.0F, -3.0F, 11.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 5.0F, 0.0F));
        body.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(0, 42).addBox(-5.0F, -1.0F, -3.0F, 10.0F, 2.0F, 6.0F), PartPose.ZERO);
        PartDefinition rightArm = mesh.getRoot().addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, fat), PartPose.offset(-5.0F, 2.0F, 0.0F));
        rightArm.addOrReplaceChild("sleeve", CubeListBuilder.create().texOffs(36, 32).addBox(-3.5F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.ZERO);
        PartDefinition leftArm = mesh.getRoot().addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, fat), PartPose.offset(5.0F, 2.0F, 0.0F));
        leftArm.addOrReplaceChild("sleeve", CubeListBuilder.create().texOffs(36, 32).mirror().addBox(-1.5F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createCloakLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition body = mesh.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
        body.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(24, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 22.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.5F, 2.4F, 0.12F, 0.0F, 0.0F));
        body.addOrReplaceChild("mantle", CubeListBuilder.create().texOffs(0, 24).addBox(-6.0F, -1.0F, -3.0F, 12.0F, 3.0F, 6.0F), PartPose.ZERO);
        body.addOrReplaceChild("clasp", CubeListBuilder.create().texOffs(36, 24).addBox(-1.0F, 1.0F, -3.4F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
        mesh.getRoot().addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 36).addBox(-3.5F, -3.0F, -2.5F, 5.0F, 3.0F, 5.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        mesh.getRoot().addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-1.5F, -3.0F, -2.5F, 5.0F, 3.0F, 5.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createBootsLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        CubeDeformation fat = new CubeDeformation(0.35F);
        PartDefinition right = mesh.getRoot().addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, fat), PartPose.offset(-1.9F, 12.0F, 0.0F));
        right.addOrReplaceChild("cuff", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, 6.0F, -2.5F, 5.0F, 2.0F, 5.0F), PartPose.ZERO);
        right.addOrReplaceChild("toe", CubeListBuilder.create().texOffs(20, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 11.0F, -2.3F, -0.4F, 0.0F, 0.0F));
        PartDefinition left = mesh.getRoot().addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, fat), PartPose.offset(1.9F, 12.0F, 0.0F));
        left.addOrReplaceChild("cuff", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-2.5F, 6.0F, -2.5F, 5.0F, 2.0F, 5.0F), PartPose.ZERO);
        left.addOrReplaceChild("toe", CubeListBuilder.create().texOffs(20, 10).mirror().addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 11.0F, -2.3F, -0.4F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}
