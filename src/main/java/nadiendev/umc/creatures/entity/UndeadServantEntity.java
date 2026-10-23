package nadiendev.umc.creatures.entity;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class UndeadServantEntity extends SummonableMob {

    public UndeadServantEntity(EntityType<? extends UndeadServantEntity> type, Level level) {
        super(type, level);
        applySettings(isSkeleton() ? CreaturesConfig.SKELETON_SERVANT : CreaturesConfig.ZOMBIE_SERVANT);
    }

    public static AttributeSupplier.Builder createZombieAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.ZOMBIE_SERVANT.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.ZOMBIE_SERVANT.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.ZOMBIE_SERVANT.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public static AttributeSupplier.Builder createSkeletonAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, CreaturesConfig.SKELETON_SERVANT.defaultHealth)
                .add(Attributes.ATTACK_DAMAGE, CreaturesConfig.SKELETON_SERVANT.defaultDamage)
                .add(Attributes.MOVEMENT_SPEED, CreaturesConfig.SKELETON_SERVANT.defaultSpeed)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public boolean isSkeleton() {
        return this.getType() == CreaturesEntities.SKELETON_SERVANT.get();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1, false));
        addServantFollowGoal(1.15);
        addWanderGoal(0.9);
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target -> !isServant()));
        addServantTargetGoals();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return isSkeleton() ? SoundEvents.SKELETON_AMBIENT : SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return isSkeleton() ? SoundEvents.SKELETON_HURT : SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return isSkeleton() ? SoundEvents.SKELETON_DEATH : SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(isSkeleton() ? SoundEvents.SKELETON_STEP : SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }
}
