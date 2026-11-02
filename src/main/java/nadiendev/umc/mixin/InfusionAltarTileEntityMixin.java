package nadiendev.umc.mixin;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.tileentity.InfusionAltarTileEntity;
import nadiendev.umc.mysticalagriculture.tier.TierAltar;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = InfusionAltarTileEntity.class, remap = false)
public abstract class InfusionAltarTileEntityMixin {

    @Inject(method = "getActiveRecipe", at = @At("RETURN"), cancellable = true)
    private void umc$restrictByTier(CallbackInfoReturnable<IInfusionRecipe> cir) {
        IInfusionRecipe recipe = cir.getReturnValue();
        if (recipe == null) {
            return;
        }
        InfusionAltarTileEntity altar = (InfusionAltarTileEntity) (Object) this;
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
