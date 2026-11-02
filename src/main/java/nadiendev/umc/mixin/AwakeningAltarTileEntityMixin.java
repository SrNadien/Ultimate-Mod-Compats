package nadiendev.umc.mixin;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.tileentity.AwakeningAltarTileEntity;
import nadiendev.umc.mysticalagriculture.tier.TierAltar;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AwakeningAltarTileEntity.class, remap = false)
public abstract class AwakeningAltarTileEntityMixin {

    @Inject(method = "getActiveRecipe", at = @At("RETURN"), cancellable = true)
    private void umc$restrictByTier(CallbackInfoReturnable<IAwakeningRecipe> cir) {
        IAwakeningRecipe recipe = cir.getReturnValue();
        if (recipe == null) {
            return;
        }
        AwakeningAltarTileEntity altar = (AwakeningAltarTileEntity) (Object) this;
        Level level = altar.getLevel();
        if (level == null) {
            return;
        }
        int tier = altar.getBlockState().getBlock() instanceof TierAltar tiered ? tiered.altarTier() : TierRules.BASE_ALTAR;
        if (!TierRules.allows(tier, recipe.getResultItem(level.registryAccess())) || !TierRules.structureAllows(level, altar.getPedestalPositions(), tier)) {
            cir.setReturnValue(null);
        }
    }
}
