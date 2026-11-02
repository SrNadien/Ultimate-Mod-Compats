package nadiendev.umc.mixin;

import com.blakebr0.mysticalagriculture.tileentity.EssenceFurnaceTileEntity;
import nadiendev.umc.mysticalagriculture.tier.TierMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EssenceFurnaceTileEntity.class, remap = false)
public abstract class EssenceFurnaceTileEntityMixin {

    @Inject(method = "getOperationTime", at = @At("RETURN"), cancellable = true)
    private void umc$operationTime(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(TierMachines.operationTime((EssenceFurnaceTileEntity) (Object) this, cir.getReturnValue()));
    }

    @Inject(method = "getFuelUsage", at = @At("RETURN"), cancellable = true)
    private void umc$fuelUsage(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(TierMachines.fuelUsage((EssenceFurnaceTileEntity) (Object) this, cir.getReturnValue()));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private static void umc$capacity(Level level, BlockPos pos, BlockState state, EssenceFurnaceTileEntity machine, CallbackInfo ci) {
        TierMachines.applyCapacity(machine, machine.getEnergy());
    }
}
