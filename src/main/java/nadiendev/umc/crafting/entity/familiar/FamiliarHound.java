package nadiendev.umc.crafting.entity.familiar;

import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.familiar.FamiliarBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FamiliarHound extends Wolf {

    public FamiliarHound(EntityType<? extends Wolf> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createHoundAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public void setTame(boolean tame, boolean applyTamingSideEffects) {
        super.setTame(tame, applyTamingSideEffects);
        if (tame) {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(CraftingConfig.HOUND.maxHealth.get());
            if (applyTamingSideEffects) {
                setHealth(getMaxHealth());
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (FamiliarBehavior.tryDismiss(this, player, hand)) {
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        FamiliarBehavior.tick(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        FamiliarBehavior.save(this, tag);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        FamiliarBehavior.die(this);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return FamiliarBehavior.isImmune(source) || super.isInvulnerableTo(source);
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }
}
