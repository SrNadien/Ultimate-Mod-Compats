package nadiendev.umc.crafting.entity.familiar;

import nadiendev.umc.crafting.entity.RavenEntity;
import nadiendev.umc.crafting.familiar.FamiliarBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FamiliarRaven extends RavenEntity {

    public FamiliarRaven(EntityType<? extends FamiliarRaven> type, Level level) {
        super(type, level);
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
