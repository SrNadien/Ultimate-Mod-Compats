package nadiendev.umc.creatures.client.geo;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class CreatureGeoModel<T extends GeoAnimatable> extends GeoModel<T> {

    private final ResourceLocation model;
    private final ResourceLocation animation;
    private final ResourceLocation texture;

    public CreatureGeoModel(String name) {
        this.model = modelPath(name);
        this.animation = animationPath(name);
        this.texture = texturePath(name);
    }

    public static ResourceLocation modelPath(String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "geo/entity/" + name + ".geo.json");
    }

    public static ResourceLocation animationPath(String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "animations/entity/" + name + ".animation.json");
    }

    public static ResourceLocation texturePath(String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/geo/" + name + ".png");
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }
}
