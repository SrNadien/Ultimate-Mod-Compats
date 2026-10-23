package nadiendev.umc.crafting.familiar;

import net.favouriteless.enchanted.api.familiars.FamiliarEntry;
import net.favouriteless.enchanted.api.familiars.FamiliarHelper;
import net.favouriteless.enchanted.common.enchanted.familiars.FamiliarSavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public final class FamiliarBehavior {

    private FamiliarBehavior() {
    }

    public static boolean tryDismiss(TamableAnimal familiar, Player player, InteractionHand hand) {
        if (player.isCrouching() && hand == InteractionHand.MAIN_HAND && player.getUUID().equals(familiar.getOwnerUUID())) {
            if (!familiar.level().isClientSide) {
                dismiss(familiar);
            }
            return true;
        }
        return false;
    }

    public static void tick(TamableAnimal familiar) {
        if (familiar.level().isClientSide || familiar.getOwnerUUID() == null) {
            return;
        }
        FamiliarEntry entry = FamiliarSavedData.get(familiar.level()).getEntry(familiar.getOwnerUUID());
        if (entry == null || !familiar.getUUID().equals(entry.getUUID())) {
            familiar.discard();
        }
    }

    public static void save(TamableAnimal familiar, CompoundTag tag) {
        if (familiar.level().isClientSide || familiar.getOwnerUUID() == null) {
            return;
        }
        FamiliarSavedData data = FamiliarSavedData.get(familiar.level());
        FamiliarEntry entry = data.getEntry(familiar.getOwnerUUID());
        if (entry != null) {
            entry.setNbt(tag);
            data.setDirty();
        }
    }

    public static void die(TamableAnimal familiar) {
        if (familiar.level().isClientSide) {
            return;
        }
        familiar.setHealth(familiar.getMaxHealth());
        dismiss(familiar);
    }

    private static void dismiss(TamableAnimal familiar) {
        FamiliarEntry entry = familiar.getOwnerUUID() == null ? null : FamiliarSavedData.get(familiar.level()).getEntry(familiar.getOwnerUUID());
        if (entry == null) {
            familiar.discard();
            return;
        }
        FamiliarHelper.get().dismiss(familiar);
    }

    public static boolean isImmune(DamageSource source) {
        return source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypeTags.IS_DROWNING);
    }
}
