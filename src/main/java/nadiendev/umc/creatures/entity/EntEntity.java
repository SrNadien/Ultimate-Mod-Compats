package nadiendev.umc.creatures.entity;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntEntity extends SummonableMob {

    public EntEntity(EntityType<? extends EntEntity> type, Level level) {
        super(type, level);
        this.xpReward = 20;
        applySettings(CreaturesConfig.ENT);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.ENT.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.ENT.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.ENT.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static boolean checkSpawn(EntityType<EntEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return CreaturesConfig.ENT.isEnabled() && Mob.checkMobSpawnRules(type, level, reason, pos, random) && level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        addServantFollowGoal(1.0);
        addWanderGoal(0.6);
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                target -> !isServant() && CreaturesConfig.ENT_PROVOKED_BY_AXES.get() && target.getMainHandItem().getItem() instanceof AxeItem && this.distanceToSqr(target) < 144.0));
        addServantTargetGoals();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide && this.tickCount % 40 == 0 && this.getHealth() < this.getMaxHealth() && level().isDay() && level().canSeeSky(blockPosition().above())) {
            this.heal(1.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide) {
            if (source.is(DamageTypeTags.IS_FIRE)) {
                amount *= CreaturesConfig.ENT_FIRE_MULTIPLIER.get().floatValue();
            } else if (source.getEntity() instanceof LivingEntity attacker && attacker.getMainHandItem().getItem() instanceof AxeItem) {
                amount *= CreaturesConfig.ENT_AXE_MULTIPLIER.get().floatValue();
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            Vec3 motion = target.getDeltaMovement();
            target.setDeltaMovement(motion.x, motion.y + 0.45, motion.z);
            target.hurtMarked = true;
        }
        return hit;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(CreaturesSounds.ENT_STEP.get(), 0.8F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return CreaturesSounds.ENT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CreaturesSounds.ENT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CreaturesSounds.ENT_DEATH.get();
    }
}
