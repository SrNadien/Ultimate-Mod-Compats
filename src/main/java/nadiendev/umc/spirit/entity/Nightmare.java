package nadiendev.umc.spirit.entity;

import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.registry.SpiritEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class Nightmare extends Monster {

    private int lifetime = -1;

    public Nightmare(EntityType<? extends Nightmare> type, Level level) {
        super(type, level);
        this.xpReward = 8;
        applyConfig();
    }

    private void applyConfig() {
        try {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(SpiritConfig.NIGHTMARE_HEALTH.get());
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(SpiritConfig.NIGHTMARE_DAMAGE.get());
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(SpiritConfig.NIGHTMARE_SPEED.get());
            setHealth(getMaxHealth());
        } catch (IllegalStateException ignored) {
            setHealth(getMaxHealth());
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    public static boolean checkSpawn(EntityType<Nightmare> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL && !SpiritConfig.NIGHTMARE_SPAWNS_ENABLED.get()) {
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public void setLifetime(int ticks) {
        this.lifetime = ticks;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12.0F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        int seconds = SpiritConfig.NIGHTMARE_CURSE_SECONDS.get();
        if (hit && seconds > 0 && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(SpiritEffects.NIGHTMARE, seconds * 20, 0), this);
        }
        return hit;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            if (random.nextInt(3) == 0) {
                level().addParticle(ParticleTypes.SQUID_INK, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0.0D, 0.02D, 0.0D);
            }
            return;
        }
        if (lifetime > 0 && --lifetime == 0) {
            ((ServerLevel) level()).sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY(0.5D), getZ(), 20, 0.3D, 0.5D, 0.3D, 0.02D);
            discard();
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return lifetime > 0 || super.removeWhenFarAway(distance);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Lifetime", lifetime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        lifetime = tag.contains("Lifetime") ? tag.getInt("Lifetime") : -1;
    }
}
