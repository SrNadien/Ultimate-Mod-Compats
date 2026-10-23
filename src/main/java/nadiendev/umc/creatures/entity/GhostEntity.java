package nadiendev.umc.creatures.entity;

import java.util.EnumSet;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import nadiendev.umc.creatures.registry.CreaturesTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GhostEntity extends SummonableMob {

    public GhostEntity(EntityType<? extends GhostEntity> type, Level level) {
        super(type, level);
        this.moveControl = new GhostMoveControl(this);
        this.xpReward = 8;
        applySettings(CreaturesConfig.GHOST);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.GHOST.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.GHOST.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.GHOST.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    public static boolean checkSpawn(EntityType<GhostEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return CreaturesConfig.GHOST.isEnabled() && Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    public void move(MoverType type, Vec3 movement) {
        super.move(type, movement);
        this.checkInsideBlocks();
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new GhostChargeGoal());
        this.goalSelector.addGoal(5, new GhostFollowOwnerGoal());
        this.goalSelector.addGoal(8, new GhostWanderGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target -> !isServant()));
        addServantTargetGoals();
    }

    @Override
    public void moveToward(Entity target, double speed) {
        this.moveControl.setWantedPosition(target.getX(), target.getY() + 1.0, target.getZ(), speed);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide && isPhysical(source) && !isSilver(source)) {
            amount *= CreaturesConfig.GHOST_PHYSICAL_MULTIPLIER.get().floatValue();
        }
        return super.hurt(source, amount);
    }

    private static boolean isPhysical(DamageSource source) {
        return source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO)
                || source.is(DamageTypes.ARROW) || source.is(DamageTypes.MOB_PROJECTILE) || source.is(DamageTypes.THROWN) || source.is(DamageTypes.TRIDENT)
                || source.is(DamageTypeTags.IS_PROJECTILE);
    }

    private static boolean isSilver(DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker && attacker.getMainHandItem().is(CreaturesTags.SILVER_WEAPONS);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            int seconds = CreaturesConfig.GHOST_BLINDNESS_SECONDS.get();
            if (seconds > 0) {
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, seconds * 20, 0), this);
            }
        }
        return hit;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return CreaturesSounds.GHOST_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CreaturesSounds.GHOST_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CreaturesSounds.GHOST_DEATH.get();
    }

    private static class GhostMoveControl extends MoveControl {

        private final GhostEntity ghost;

        GhostMoveControl(GhostEntity ghost) {
            super(ghost);
            this.ghost = ghost;
        }

        @Override
        public void tick() {
            if (this.operation != Operation.MOVE_TO) {
                return;
            }
            Vec3 delta = new Vec3(this.wantedX - ghost.getX(), this.wantedY - ghost.getY(), this.wantedZ - ghost.getZ());
            double length = delta.length();
            if (length < ghost.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                ghost.setDeltaMovement(ghost.getDeltaMovement().scale(0.5));
                return;
            }
            ghost.setDeltaMovement(ghost.getDeltaMovement().add(delta.scale(this.speedModifier * 0.05 / length)));
            if (ghost.getTarget() == null) {
                Vec3 motion = ghost.getDeltaMovement();
                ghost.setYRot(-((float) Mth.atan2(motion.x, motion.z)) * (180.0F / (float) Math.PI));
            } else {
                double dx = ghost.getTarget().getX() - ghost.getX();
                double dz = ghost.getTarget().getZ() - ghost.getZ();
                ghost.setYRot(-((float) Mth.atan2(dx, dz)) * (180.0F / (float) Math.PI));
            }
            ghost.yBodyRot = ghost.getYRot();
        }
    }

    private class GhostChargeGoal extends Goal {

        GhostChargeGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = GhostEntity.this.getTarget();
            return target != null && target.isAlive() && !GhostEntity.this.getMoveControl().hasWanted() && GhostEntity.this.random.nextInt(reducedTickDelay(5)) == 0
                    && GhostEntity.this.distanceToSqr(target) > 4.0;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = GhostEntity.this.getTarget();
            return GhostEntity.this.getMoveControl().hasWanted() && target != null && target.isAlive();
        }

        @Override
        public void start() {
            LivingEntity target = GhostEntity.this.getTarget();
            if (target != null) {
                Vec3 eye = target.getEyePosition();
                GhostEntity.this.moveControl.setWantedPosition(eye.x, eye.y, eye.z, 1.0);
            }
            GhostEntity.this.playSound(CreaturesSounds.GHOST_AMBIENT.get(), 1.0F, 1.2F);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = GhostEntity.this.getTarget();
            if (target == null) {
                return;
            }
            if (GhostEntity.this.getBoundingBox().intersects(target.getBoundingBox())) {
                GhostEntity.this.doHurtTarget(target);
                GhostEntity.this.getMoveControl().setWantedPosition(target.getX(), target.getEyeY() + 1.5, target.getZ(), 1.0);
            } else if (GhostEntity.this.distanceToSqr(target) < 9.0) {
                Vec3 eye = target.getEyePosition();
                GhostEntity.this.moveControl.setWantedPosition(eye.x, eye.y, eye.z, 1.0);
            }
        }
    }

    private class GhostFollowOwnerGoal extends Goal {

        GhostFollowOwnerGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity owner = GhostEntity.this.getOwner();
            if (!GhostEntity.this.isServant() || GhostEntity.this.isStaying() || owner == null || GhostEntity.this.getTarget() != null || GhostEntity.this.getMoveControl().hasWanted()) {
                return false;
            }
            return GhostEntity.this.distanceToSqr(owner) > 16.0;
        }

        @Override
        public void start() {
            LivingEntity owner = GhostEntity.this.getOwner();
            if (owner != null) {
                GhostEntity.this.moveControl.setWantedPosition(owner.getX(), owner.getY() + 1.5, owner.getZ(), 1.2);
            }
        }
    }

    private class GhostWanderGoal extends Goal {

        GhostWanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (GhostEntity.this.isStaying() || GhostEntity.this.getMoveControl().hasWanted() || GhostEntity.this.getTarget() != null) {
                return false;
            }
            return GhostEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos center = GhostEntity.this.blockPosition();
            LivingEntity owner = GhostEntity.this.getOwner();
            if (owner != null) {
                center = owner.blockPosition();
            }
            for (int i = 0; i < 3; i++) {
                BlockPos target = center.offset(GhostEntity.this.random.nextInt(15) - 7, GhostEntity.this.random.nextInt(11) - 5, GhostEntity.this.random.nextInt(15) - 7);
                if (GhostEntity.this.level().isEmptyBlock(target)) {
                    GhostEntity.this.moveControl.setWantedPosition(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 0.25);
                    if (GhostEntity.this.getTarget() == null) {
                        GhostEntity.this.getLookControl().setLookAt(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
