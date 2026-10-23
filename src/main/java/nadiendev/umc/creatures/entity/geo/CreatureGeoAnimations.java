package nadiendev.umc.creatures.entity.geo;

import java.util.function.Predicate;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public final class CreatureGeoAnimations {

    public static final String MOVEMENT = "movement";
    public static final String ATTACK = "attack";
    public static final int TRANSITION_TICKS = 4;

    private CreatureGeoAnimations() {
    }

    public static String animation(String name, String action) {
        return "animation." + name + "." + action;
    }

    public static <T extends GeoAnimatable> void register(AnimatableManager.ControllerRegistrar controllers, String name, T animatable) {
        controllers.add(movement(animatable, name, AnimationState::isMoving));
        controllers.add(attackTriggered(animatable, name));
    }

    public static <T extends GeoAnimatable> AnimationController<T> movement(T animatable, String name, Predicate<AnimationState<T>> moving) {
        RawAnimation walk = RawAnimation.begin().thenLoop(animation(name, "walk"));
        RawAnimation idle = RawAnimation.begin().thenLoop(animation(name, "idle"));
        return new AnimationController<>(animatable, MOVEMENT, TRANSITION_TICKS, state -> state.setAndContinue(moving.test(state) ? walk : idle));
    }

    public static <T extends GeoAnimatable> AnimationController<T> attackTriggered(T animatable, String name) {
        return new AnimationController<T>(animatable, ATTACK, 0, state -> PlayState.STOP)
                .triggerableAnim(ATTACK, RawAnimation.begin().thenPlay(animation(name, "attack")));
    }

    public static <T extends GeoAnimatable> AnimationController<T> attackSwing(T animatable, String name, Predicate<AnimationState<T>> swinging) {
        RawAnimation attack = RawAnimation.begin().thenPlay(animation(name, "attack"));
        return new AnimationController<>(animatable, ATTACK, 0, state -> {
            if (swinging.test(state)) {
                return state.setAndContinue(attack);
            }
            return state.getController().getAnimationState() == AnimationController.State.STOPPED ? PlayState.STOP : PlayState.CONTINUE;
        });
    }
}
