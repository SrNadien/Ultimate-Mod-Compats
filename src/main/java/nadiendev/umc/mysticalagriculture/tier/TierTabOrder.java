package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.item.MysticalSeedsItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class TierTabOrder {

    public enum Section {
        SEEDS("seeds"),
        ALTARS("altars"),
        PEDESTALS("pedestals"),
        ESSENCES("essences"),
        CRYSTALS("crystals"),
        MACHINES("machines"),
        TABLES("tables");

        private final String id;

        Section(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }

    private static final List<String> KINDS = List.of(
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

    public static List<Item> items(Section section) {
        List<Item> all = new ArrayList<>(TierAltarRegistry.items());
        String cropNamespace = MysticalAgricultureConfig.get().cropNamespace();
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof ICropProvider provider && provider.getCrop().getId().getNamespace().equals(cropNamespace))
                .forEach(all::add);
        return all.stream()
                .filter(item -> sectionOf(item) == section)
                .sorted(Comparator.comparingInt(TierTabOrder::tier)
                        .thenComparingInt(TierTabOrder::kind)
                        .thenComparing(item -> BuiltInRegistries.ITEM.getKey(item).toString()))
                .toList();
    }

    private static Section sectionOf(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        if (path.startsWith(TierAltarRegistry.INFUSION_PREFIX) || path.startsWith(TierAltarRegistry.AWAKENING_PREFIX)) {
            return Section.ALTARS;
        }
        if (path.startsWith(TierAltarRegistry.REPROCESSOR_PREFIX)) {
            return Section.MACHINES;
        }
        if (path.startsWith(TierAltarRegistry.FURNACE_PREFIX)) {
            return Section.TABLES;
        }
        if (path.startsWith(TierAltarRegistry.INFUSION_PEDESTAL_PREFIX) || path.startsWith(TierAltarRegistry.AWAKENING_PEDESTAL_PREFIX)
                || path.startsWith(TierAltarRegistry.VESSEL_PREFIX)) {
            return Section.PEDESTALS;
        }
        if (path.startsWith(TierAltarRegistry.CRYSTAL_PREFIX)) {
            return Section.CRYSTALS;
        }
        return item instanceof MysticalSeedsItem ? Section.SEEDS : Section.ESSENCES;
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
        for (int index = 0; index < KINDS.size(); index++) {
            if (id.getPath().startsWith(KINDS.get(index))) {
                return index;
            }
        }
        return KINDS.size();
    }
}
