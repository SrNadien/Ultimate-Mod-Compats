package nadiendev.umc.creatures.client.geo;

import nadiendev.umc.creatures.entity.geo.CreatureGeoAnimations;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FormAnimatable implements GeoReplacedEntity {

    private final String name;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FormAnimatable(String name) {
        this.name = name;
    }

    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.PLAYER;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(CreatureGeoAnimations.movement(this, name, FormAnimatable::moving));
        controllers.add(CreatureGeoAnimations.attackSwing(this, name, FormAnimatable::swinging));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private static boolean moving(AnimationState<FormAnimatable> state) {
        Entity entity = state.getData(DataTickets.ENTITY);
        if (entity == null) {
            return false;
        }
        Vec3 velocity = entity.getDeltaMovement();
        if (velocity.x * velocity.x + velocity.z * velocity.z > 0.01 * 0.01) {
            return true;
        }
        return entity instanceof LivingEntity living && living.walkAnimation.speed() > 0.02F;
    }

    private static boolean swinging(AnimationState<FormAnimatable> state) {
        return state.getData(DataTickets.ENTITY) instanceof LivingEntity living && living.swinging;
    }
}
