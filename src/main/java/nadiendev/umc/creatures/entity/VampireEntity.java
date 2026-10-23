package nadiendev.umc.creatures.entity;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.geo.CreatureGeoAnimations;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.form.CreatureKind;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VampireEntity extends SummonableMob implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public VampireEntity(EntityType<? extends VampireEntity> type, Level level) {
        super(type, level);
        this.xpReward = 10;
        applySettings(CreaturesConfig.VAMPIRE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.VAMPIRE.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.VAMPIRE.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.VAMPIRE.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public static boolean checkSpawn(EntityType<VampireEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return CreaturesConfig.VAMPIRE.isEnabled() && Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, false));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, entity -> entity instanceof Player player && CreatureForms.holdsItem(player, CreaturesConfig.VAMPIRE_REPELLENT_ITEM.get()), 6.0F, 1.0, 1.3, entity -> true));
        addServantFollowGoal(1.15);
        addWanderGoal(1.0);
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target -> !isServant() && !CreatureForms.isKind(target, CreatureKind.VAMPIRE)));
        addServantTargetGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide || isServant() || this.tickCount % 20 != 0 || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (CreaturesConfig.VAMPIRE_MOB_SUNLIGHT_BURNS.get() && CreatureForms.isBurningInSun(this, serverLevel, CreaturesConfig.VAMPIRE_HELMET_PROTECTS.get(), CreaturesConfig.VAMPIRE_RAIN_PROTECTS.get())) {
            this.igniteForSeconds(CreaturesConfig.VAMPIRE_SUNLIGHT_FIRE_SECONDS.get());
        }
        CreatureForms.applyRepellent(this, serverLevel, CreaturesConfig.VAMPIRE_REPELLENT_ITEM.get(), CreaturesConfig.VAMPIRE_REPELLENT_RADIUS.get(), CreaturesConfig.VAMPIRE_REPELLENT_WEAKNESS_SECONDS.get());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide) {
            this.triggerAnim(CreatureGeoAnimations.ATTACK, CreatureGeoAnimations.ATTACK);
        }
        if (hit && CreaturesConfig.VAMPIRE_MOB_LIFESTEAL.get()) {
            this.heal((float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5));
            this.playSound(CreaturesSounds.VAMPIRE_BITE.get(), 0.8F, 1.0F);
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        CreatureGeoAnimations.register(controllers, "vampire", this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return CreaturesSounds.VAMPIRE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CreaturesSounds.VAMPIRE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CreaturesSounds.VAMPIRE_DEATH.get();
    }
}
