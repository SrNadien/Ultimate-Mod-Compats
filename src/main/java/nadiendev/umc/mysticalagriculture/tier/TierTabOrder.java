package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.item.MysticalSeedsItem;
import java.util.Comparator;
import java.util.List;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class TierTabOrder {

    private static final List<String> TIER_KINDS = List.of(
            TierAltarRegistry.CRYSTAL_PREFIX,
            TierAltarRegistry.INFUSION_PREFIX,
            TierAltarRegistry.AWAKENING_PREFIX,
            TierAltarRegistry.INFUSION_PEDESTAL_PREFIX,
            TierAltarRegistry.AWAKENING_PEDESTAL_PREFIX,
            TierAltarRegistry.VESSEL_PREFIX,
            TierAltarRegistry.REPROCESSOR_PREFIX,
            TierAltarRegistry.FURNACE_PREFIX);
    private static final int NO_TIER = 99;

    private TierTabOrder() {
    }

    public static List<Item> items() {
        List<Item> all = new java.util.ArrayList<>(TierAltarRegistry.items());
        String cropNamespace = MysticalAgricultureConfig.get().cropNamespace();
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof ICropProvider provider && provider.getCrop().getId().getNamespace().equals(cropNamespace))
                .forEach(all::add);
        all.sort(Comparator.comparingInt(TierTabOrder::kind)
                .thenComparingInt(TierTabOrder::tier)
                .thenComparing(item -> BuiltInRegistries.ITEM.getKey(item).toString()));
        return all;
    }

    private static int tier(Item item) {
        if (item instanceof TierCrystalItem crystal) {
            return crystal.crystalTier();
        }
        if (item instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof TierAltar altar) {
                return altar.altarTier();
            }
            if (blockItem.getBlock() instanceof TierPart part) {
                return part.partTier();
            }
        }
        if (item instanceof ICropProvider provider) {
            return provider.getCrop().getTier().getValue();
        }
        return NO_TIER;
    }

    private static int kind(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        for (int index = 0; index < TIER_KINDS.size(); index++) {
            if (id.getPath().startsWith(TIER_KINDS.get(index))) {
                return index;
            }
        }
        return item instanceof MysticalSeedsItem ? TIER_KINDS.size() : TIER_KINDS.size() + 1;
    }
}
