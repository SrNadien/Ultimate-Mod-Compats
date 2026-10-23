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

public class WerewolfEntity extends SummonableMob implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public WerewolfEntity(EntityType<? extends WerewolfEntity> type, Level level) {
        super(type, level);
        this.xpReward = 12;
        applySettings(CreaturesConfig.WEREWOLF);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.WEREWOLF.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.WEREWOLF.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.WEREWOLF.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
    }

    public static boolean checkSpawn(EntityType<WerewolfEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (!CreaturesConfig.WEREWOLF.isEnabled()) {
            return false;
        }
        if (reason == MobSpawnType.NATURAL && CreaturesConfig.WEREWOLF_SPAWN_FULL_MOON_ONLY.get() && level.getMoonPhase() != 0) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, entity -> entity instanceof Player player && CreatureForms.holdsItem(player, CreaturesConfig.WEREWOLF_REPELLENT_ITEM.get()), 6.0F, 1.0, 1.3, entity -> true));
        addServantFollowGoal(1.2);
        addWanderGoal(1.0);
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target -> !isServant() && !CreatureForms.isKind(target, CreatureKind.WEREWOLF)));
        addServantTargetGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide && !isServant() && this.tickCount % 20 == 0 && level() instanceof ServerLevel serverLevel) {
            CreatureForms.applyRepellent(this, serverLevel, CreaturesConfig.WEREWOLF_REPELLENT_ITEM.get(), CreaturesConfig.WEREWOLF_REPELLENT_RADIUS.get(), CreaturesConfig.WEREWOLF_REPELLENT_WEAKNESS_SECONDS.get());
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide) {
            this.triggerAnim(CreatureGeoAnimations.ATTACK, CreatureGeoAnimations.ATTACK);
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        CreatureGeoAnimations.register(controllers, "werewolf", this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return CreaturesSounds.WEREWOLF_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CreaturesSounds.WEREWOLF_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CreaturesSounds.WEREWOLF_DEATH.get();
    }

    @Override
    public float getVoicePitch() {
        return 0.8F + this.random.nextFloat() * 0.2F;
    }
}
