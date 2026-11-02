package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.cucumber.item.BaseReusableItem;
import java.util.List;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class TierCrystalItem extends BaseReusableItem {

    public static final String TOOLTIP_KEY = "tooltip.umc.tier_crystal";

    private final int tier;

    public TierCrystalItem(int tier) {
        super(MysticalAgricultureConfig.DEFAULT_CRYSTAL_DURABILITY);
        this.tier = tier;
    }

    public int crystalTier() {
        return tier;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return MysticalAgricultureConfig.get().crystalDurability() - 1;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable(TOOLTIP_KEY, tier).withStyle(ChatFormatting.GRAY));
    }
}
