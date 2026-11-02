package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.mysticalagriculture.block.InfusionPedestalBlock;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class TieredInfusionPedestalBlock extends InfusionPedestalBlock implements TierPart {

    private final int tier;

    public TieredInfusionPedestalBlock(int tier) {
        super();
        this.tier = tier;
    }

    @Override
    public int partTier() {
        return tier;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(TierTooltips.part(tier));
    }
}
