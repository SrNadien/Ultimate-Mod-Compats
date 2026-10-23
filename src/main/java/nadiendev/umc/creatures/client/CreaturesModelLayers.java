package nadiendev.umc.creatures.client;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class CreaturesModelLayers {

    public static final ModelLayerLocation WEREWOLF = layer("werewolf");
    public static final ModelLayerLocation VAMPIRE = layer("vampire");
    public static final ModelLayerLocation GHOST = layer("ghost");
    public static final ModelLayerLocation IMP = layer("imp");
    public static final ModelLayerLocation ENT = layer("ent");
    public static final ModelLayerLocation ZOMBIE_SERVANT = layer("zombie_servant");
    public static final ModelLayerLocation SKELETON_SERVANT = layer("skeleton_servant");

    private CreaturesModelLayers() {
    }

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name), "main");
    }

    public static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/" + name + ".png");
    }
}
