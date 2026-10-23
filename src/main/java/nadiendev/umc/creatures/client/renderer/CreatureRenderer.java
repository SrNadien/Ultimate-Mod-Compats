package nadiendev.umc.creatures.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.umc.creatures.client.LocalModelAvailability;
import nadiendev.umc.creatures.client.geo.CreatureGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CreatureRenderer<T extends Mob & GeoEntity> extends EntityRenderer<T> {

    private final String name;
    private final EntityRenderer<T> legacy;
    private final GeoEntityRenderer<T> geo;

    public CreatureRenderer(EntityRendererProvider.Context context, String name, EntityRendererProvider<T> legacyProvider) {
        super(context);
        this.name = name;
        this.legacy = legacyProvider.create(context);
        this.geo = new GeoEntityRenderer<>(context, new CreatureGeoModel<T>(name));
    }

    private EntityRenderer<T> active() {
        return LocalModelAvailability.isAvailable(name) ? geo : legacy;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        active().render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double camX, double camY, double camZ) {
        return active().shouldRender(entity, frustum, camX, camY, camZ);
    }

    @Override
    public Vec3 getRenderOffset(T entity, float partialTick) {
        return active().getRenderOffset(entity, partialTick);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return active().getTextureLocation(entity);
    }
}
