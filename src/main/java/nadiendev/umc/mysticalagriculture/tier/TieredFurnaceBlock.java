package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.mysticalagriculture.block.EssenceFurnaceBlock;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class TieredFurnaceBlock extends EssenceFurnaceBlock implements TierPart, TierMachine {

    private final int tier;
    private final TierMachine stats;

    public TieredFurnaceBlock(int tier) {
        super();
        this.tier = tier;
        this.stats = MachineStats.furnace(tier);
    }

    @Override
    public int partTier() {
        return tier;
    }

    @Override
    public double operationMultiplier() {
        return stats.operationMultiplier();
    }

    @Override
    public double fuelUsageMultiplier() {
        return stats.fuelUsageMultiplier();
    }

    @Override
    public double capacityMultiplier() {
        return stats.capacityMultiplier();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.addAll(TierTooltips.machine(tier, stats));
    }
}
