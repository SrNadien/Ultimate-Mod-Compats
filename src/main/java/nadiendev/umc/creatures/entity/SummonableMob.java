package nadiendev.umc.creatures.entity;

import java.util.UUID;
import javax.annotation.Nullable;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.ai.ServantFollowOwnerGoal;
import nadiendev.umc.creatures.entity.ai.ServantOwnerHurtByTargetGoal;
import nadiendev.umc.creatures.entity.ai.ServantOwnerHurtTargetGoal;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class SummonableMob extends Monster implements OwnableEntity {

    private UUID ownerId;
    private int lifetimeTicks;
    private int ownerMissingTicks;
    private boolean staying;

    protected SummonableMob(EntityType<? extends SummonableMob> type, Level level) {
        super(type, level);
    }

    public boolean isServant() {
        return ownerId != null;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerId;
    }

    public void bindTo(Player owner, int lifetimeSeconds) {
        this.ownerId = owner.getUUID();
        this.lifetimeTicks = lifetimeSeconds * 20;
        this.setPersistenceRequired();
        this.setTarget(null);
    }

    public void release() {
        this.ownerId = null;
        this.lifetimeTicks = 0;
    }

    public boolean isStaying() {
        return staying;
    }

    public void setStaying(boolean staying) {
        this.staying = staying;
        if (staying) {
            this.getNavigation().stop();
        }
    }

    public void moveToward(Entity target, double speed) {
        this.getNavigation().moveTo(target, speed);
    }

    protected void applySettings(CreaturesConfig.MobSettings settings) {
        if (!UmcConfig.SPEC.isLoaded()) {
            return;
        }
        setBase(Attributes.MAX_HEALTH, settings.healthValue());
        setBase(Attributes.ATTACK_DAMAGE, settings.damageValue());
        setBase(Attributes.MOVEMENT_SPEED, settings.speedValue());
        this.setHealth(this.getMaxHealth());
    }

    protected void setBase(Holder<Attribute> attribute, double value) {
        AttributeInstance instance = this.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }

    protected void addServantTargetGoals() {
        this.targetSelector.addGoal(1, new ServantOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ServantOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                target -> this.isServant() && target instanceof Enemy && !(target instanceof Creeper) && !(target instanceof SummonableMob other && other.isServant())));
    }

    protected void addServantFollowGoal(double speed) {
        this.goalSelector.addGoal(6, new ServantFollowOwnerGoal(this, speed));
    }

    protected void addWanderGoal(double speed) {
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, speed) {
            @Override
            public boolean canUse() {
                return !SummonableMob.this.isServant() && super.canUse();
            }
        });
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (isServant() && !CreaturesConfig.SERVANTS_FRIENDLY_FIRE.get()) {
            if (ownerId.equals(target.getUUID())) {
                return false;
            }
            if (target instanceof SummonableMob other && ownerId.equals(other.ownerId)) {
                return false;
            }
        }
        return super.canAttack(target);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        if (isServant()) {
            if (ownerId.equals(other.getUUID())) {
                return true;
            }
            if (other instanceof SummonableMob mob && ownerId.equals(mob.ownerId)) {
                return true;
            }
        }
        return super.isAlliedTo(other);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (isServant() && player.getUUID().equals(ownerId) && hand == InteractionHand.MAIN_HAND && player.getMainHandItem().isEmpty() && player.isShiftKeyDown()) {
            if (!level().isClientSide) {
                setStaying(!isStaying());
                player.displayClientMessage(Component.translatable(isStaying() ? "message.umc.servant.stay" : "message.umc.servant.follow"), true);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide || !isServant()) {
            return;
        }
        if (lifetimeTicks > 0 && --lifetimeTicks == 0) {
            expire();
            return;
        }
        if (this.tickCount % 20 == 0) {
            if (getOwner() == null) {
                ownerMissingTicks += 20;
                if (ownerMissingTicks > 1200) {
                    expire();
                }
            } else {
                ownerMissingTicks = 0;
            }
        }
    }

    public void expire() {
        if (level() instanceof ServerLevel serverLevel) {
            Vec3 pos = position();
            serverLevel.sendParticles(ParticleTypes.SOUL, pos.x, pos.y + getBbHeight() * 0.5, pos.z, 14, 0.3, 0.5, 0.3, 0.03);
            serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y + getBbHeight() * 0.5, pos.z, 8, 0.3, 0.5, 0.3, 0.02);
        }
        this.discard();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isServant() && super.removeWhenFarAway(distanceToClosestPlayer);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return !isServant() && super.shouldDespawnInPeaceful();
    }

    @Override
    protected boolean shouldDropLoot() {
        return !isServant() && super.shouldDropLoot();
    }

    @Override
    protected int getBaseExperienceReward() {
        return isServant() ? 0 : super.getBaseExperienceReward();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (ownerId != null) {
            tag.putUUID("UmcOwner", ownerId);
        }
        tag.putInt("UmcLifetime", lifetimeTicks);
        tag.putBoolean("UmcStaying", staying);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        ownerId = tag.hasUUID("UmcOwner") ? tag.getUUID("UmcOwner") : null;
        lifetimeTicks = tag.getInt("UmcLifetime");
        staying = tag.getBoolean("UmcStaying");
        if (ownerId != null) {
            setPersistenceRequired();
        }
    }
}
