package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.cucumber.energy.DynamicEnergyStorage;
import com.blakebr0.mysticalagriculture.api.machine.IUpgradeableMachine;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeTier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class TierMachines {

    private TierMachines() {
    }

    public static int operationTime(BlockEntity machine, int value) {
        return machine.getBlockState().getBlock() instanceof TierMachine tier ? MachineStats.scale(value, tier.operationMultiplier()) : value;
    }

    public static int fuelUsage(BlockEntity machine, int value) {
        return machine.getBlockState().getBlock() instanceof TierMachine tier ? MachineStats.scale(value, tier.fuelUsageMultiplier()) : value;
    }

    public static void applyCapacity(BlockEntity machine, DynamicEnergyStorage energy) {
        Block block = machine.getBlockState().getBlock();
        if (!(block instanceof TierMachine tier) || !(machine instanceof IUpgradeableMachine upgradeable)) {
            return;
        }
        MachineUpgradeTier upgrade = upgradeable.getMachineTier();
        double upgradeMultiplier = upgrade == null ? 1.0 : upgrade.getFuelCapacityMultiplier();
        int capacity = (int) Math.min(Integer.MAX_VALUE, MachineStats.BASE_FUEL_CAPACITY * upgradeMultiplier * tier.capacityMultiplier());
        if (energy.getMaxEnergyStored() != capacity) {
            energy.setMaxEnergyStorage(capacity);
        }
    }
}
