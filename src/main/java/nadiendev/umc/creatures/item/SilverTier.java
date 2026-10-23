package nadiendev.umc.creatures.item;

import nadiendev.umc.creatures.registry.CreaturesItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public final class SilverTier {

    public static final Tier INSTANCE = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 18, () -> Ingredient.of(CreaturesItems.SILVER_INGOT.get()));

    private SilverTier() {
    }
}
