package nadiendev.umc.creatures.entity;

import java.util.EnumSet;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ImpEntity extends SummonableMob {

    public ImpEntity(EntityType<? extends ImpEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.xpReward = 6;
        applySettings(CreaturesConfig.IMP);
        if (UmcConfig.SPEC.isLoaded()) {
            setBase(Attributes.FLYING_SPEED, CreaturesConfig.IMP.speedValue() * 2.0);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.IMP.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.IMP.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.IMP.defaultSpeed)
                .add(Attributes.FLYING_SPEED, CreaturesConfig.IMP.defaultSpeed * 2.0)
                .add(Attributes.FOLLOW_RANGE, 28.0);
    }

    public static boolean checkSpawn(EntityType<ImpEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return CreaturesConfig.IMP.isEnabled() && Monster.checkAnyLightMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new ImpFireballGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        addServantFollowGoal(1.2);
        this.goalSelector.addGoal(8, new WaterAvoidingRandomFlyingGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return !ImpEntity.this.isServant() && super.canUse();
            }
        });
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target -> !isServant()));
        addServantTargetGoals();
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide && this.random.nextInt(4) == 0) {
            level().addParticle(ParticleTypes.SMALL_FLAME, getRandomX(0.4), getY() + 0.3, getRandomZ(0.4), 0.0, 0.01, 0.0);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && CreaturesConfig.IMP_SETS_FIRE.get()) {
            target.igniteForSeconds(3.0F);
        }
        return hit;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return CreaturesSounds.IMP_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CreaturesSounds.IMP_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CreaturesSounds.IMP_DEATH.get();
    }

    private class ImpFireballGoal extends Goal {

        private int cooldown;

        ImpFireballGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = ImpEntity.this.getTarget();
            if (!CreaturesConfig.IMP_FIREBALL_ENABLED.get() || target == null || !target.isAlive()) {
                return false;
            }
            double distance = ImpEntity.this.distanceToSqr(target);
            return distance > 25.0 && distance < 400.0 && ImpEntity.this.hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = ImpEntity.this.getTarget();
            if (!CreaturesConfig.IMP_FIREBALL_ENABLED.get() || target == null || !target.isAlive()) {
                return false;
            }
            double distance = ImpEntity.this.distanceToSqr(target);
            return distance > 16.0 && distance < 484.0 && ImpEntity.this.hasLineOfSight(target);
        }

        @Override
        public void start() {
            cooldown = 10;
        }

        @Override
        public void stop() {
            ImpEntity.this.getNavigation().stop();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = ImpEntity.this.getTarget();
            if (target == null) {
                return;
            }
            ImpEntity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            ImpEntity.this.getNavigation().stop();
            if (--cooldown > 0) {
                return;
            }
            cooldown = CreaturesConfig.IMP_FIREBALL_COOLDOWN_TICKS.get();
            Vec3 direction = target.getEyePosition().subtract(ImpEntity.this.getEyePosition()).normalize().scale(0.8);
            SmallFireball fireball = new SmallFireball(ImpEntity.this.level(), ImpEntity.this, direction);
            fireball.setPos(ImpEntity.this.getX() + direction.x, ImpEntity.this.getEyeY() - 0.1, ImpEntity.this.getZ() + direction.z);
            ImpEntity.this.level().addFreshEntity(fireball);
            ImpEntity.this.playSound(CreaturesSounds.IMP_FIREBALL.get(), 1.0F, 1.0F);
        }
    }
}
