package nadiendev.umc.mysticalagriculture.tier;

import com.blakebr0.mysticalagriculture.block.AwakeningPedestalBlock;
import com.blakebr0.mysticalagriculture.block.EssenceVesselBlock;
import com.blakebr0.mysticalagriculture.block.InfusionPedestalBlock;
import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import java.util.List;
import java.util.Set;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class TierRules {

    public static final int BASE_ALTAR = 0;
    public static final int MIN_TIER = 1;
    public static final int MAX_TIER = 10;
    private static final String MYSTICAL_AGRICULTURE = "mysticalagriculture";
    private static final Set<String> ELEMENTAL_CROPS = Set.of("air", "earth", "fire", "water");
    private static final int AWAKENED_TIER = 6;
    private static final String AWAKENED_PREFIX = "awakened_";
    private static final int NOT_A_SEED = -1;
    private static final int NOT_A_TIER_BLOCK = -1;

    private TierRules() {
    }

    public static boolean allows(int altarTier, ItemStack result) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.tiersEnabled()) {
            return true;
        }
        int upgradeTier = upgradeTier(result);
        if (upgradeTier != NOT_A_TIER_BLOCK) {
            return upgradeTier == altarTier + 1;
        }
        int seedTier = seedTier(result);
        if (altarTier == BASE_ALTAR) {
            return !config.baseAltarsElementalOnly() || seedTier < MIN_TIER;
        }
        if (seedTier < MIN_TIER) {
            return false;
        }
        if (seedTier == altarTier) {
            return true;
        }
        return config.previousTierAllowed() && altarTier > MIN_TIER && seedTier == altarTier - 1;
    }

    public static boolean structureAllows(Level level, List<BlockPos> positions, int altarTier) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.tiersEnabled() || !config.structureTierRequired() || altarTier == BASE_ALTAR) {
            return true;
        }
        for (BlockPos position : positions) {
            Block block = level.getBlockState(position).getBlock();
            if (!isStructurePart(block)) {
                continue;
            }
            int partTier = block instanceof TierPart part ? part.partTier() : BASE_ALTAR;
            boolean fits = config.higherTierStructureAllowed() ? partTier >= altarTier : partTier == altarTier;
            if (!fits) {
                return false;
            }
        }
        return true;
    }

    public static int seedTier(ItemStack stack) {
        if (stack.getItem() instanceof ICropProvider provider) {
            ResourceLocation crop = provider.getCrop().getId();
            if (crop.getNamespace().equals(MYSTICAL_AGRICULTURE) && ELEMENTAL_CROPS.contains(crop.getPath())) {
                return BASE_ALTAR;
            }
            return provider.getCrop().getTier().getValue();
        }
        if (stack.getItem() instanceof IAugmentProvider augment) {
            return augment.getAugment().getTier();
        }
        if (stack.getItem() instanceof ITinkerable tinkerable) {
            return tinkerable.getTinkerableTier();
        }
        if (isAwakened(stack)) {
            return AWAKENED_TIER;
        }
        return NOT_A_SEED;
    }

    private static boolean isAwakened(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().startsWith(AWAKENED_PREFIX);
    }

    private static int upgradeTier(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem item) {
            Block block = item.getBlock();
            if (block instanceof TierAltar altar) {
                return altar.altarTier();
            }
            if (block instanceof TierPart part) {
                return part.partTier();
            }
        }
        return NOT_A_TIER_BLOCK;
    }

    private static boolean isStructurePart(Block block) {
        return block instanceof InfusionPedestalBlock || block instanceof AwakeningPedestalBlock || block instanceof EssenceVesselBlock;
    }
}
