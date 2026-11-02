package nadiendev.umc.mysticalagriculture.tier;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class TierTooltips {

    public static final String SINGLE_KEY = "tooltip.umc.tier_altar.single";
    public static final String RANGE_KEY = "tooltip.umc.tier_altar.range";

    public static final String PART_KEY = "tooltip.umc.tier_part";

    private TierTooltips() {
    }

    public static final String SPEED_KEY = "tooltip.umc.tier_machine.speed";
    public static final String FUEL_KEY = "tooltip.umc.tier_machine.fuel";

    public static List<Component> machine(int tier, TierMachine stats) {
        return List.of(
                part(tier),
                Component.translatable(SPEED_KEY, format(1.0 / stats.operationMultiplier())).withStyle(ChatFormatting.GRAY),
                Component.translatable(FUEL_KEY, format(stats.fuelUsageMultiplier())).withStyle(ChatFormatting.GRAY));
    }

    private static String format(double value) {
        return value >= 10 ? String.valueOf(Math.round(value)) : String.format(java.util.Locale.ROOT, "%.2f", value);
    }

    public static Component part(int tier) {
        return Component.translatable(PART_KEY, tier).withStyle(ChatFormatting.GRAY);
    }

    public static Component range(int tier) {
        if (tier <= TierRules.MIN_TIER) {
            return Component.translatable(SINGLE_KEY, tier).withStyle(ChatFormatting.GRAY);
        }
        return Component.translatable(RANGE_KEY, tier, tier - 1).withStyle(ChatFormatting.GRAY);
    }
}
