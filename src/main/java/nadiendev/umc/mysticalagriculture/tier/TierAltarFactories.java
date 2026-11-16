package nadiendev.umc.mysticalagriculture.tier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class TierAltarFactories {

    private TierAltarFactories() {
    }

    /** Made here and not in the registry, so that the registry does not load the Mystical Agriculture classes when the mod is not installed. */
    public static Item crystal(int tier) {
        return new TierCrystalItem(tier);
    }

    public static Block infusion(int tier) {
        return new TieredInfusionAltarBlock(tier);
    }

    public static Block awakening(int tier) {
        return new TieredAwakeningAltarBlock(tier);
    }

    public static Block infusionPedestal(int tier) {
        return new TieredInfusionPedestalBlock(tier);
    }

    public static Block awakeningPedestal(int tier) {
        return new TieredAwakeningPedestalBlock(tier);
    }

    public static Block vessel(int tier) {
        return new TieredEssenceVesselBlock(tier);
    }

    public static Block reprocessor(int tier) {
        return new TieredReprocessorBlock(tier);
    }

    public static Block furnace(int tier) {
        return new TieredFurnaceBlock(tier);
    }
}
