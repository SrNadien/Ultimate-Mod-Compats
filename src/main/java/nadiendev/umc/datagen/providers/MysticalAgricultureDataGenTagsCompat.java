package nadiendev.umc.datagen.providers;

import java.util.function.Function;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierEssences;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MysticalAgricultureDataGenTagsCompat implements DataModule {

    @Override
    public void blockTags(Function<TagKey<Block>, IntrinsicTagAppender<Block>> tags) {
        IntrinsicTagAppender<Block> pickaxe = tags.apply(BlockTags.MINEABLE_WITH_PICKAXE);
        for (Block block : TierAltarRegistry.allBlocks()) {
            pickaxe.add(block);
        }
    }

    @Override
    public void itemTags(Function<TagKey<Item>, IntrinsicTagAppender<Item>> tags) {
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            tags.apply(TierEssences.tag(tier)).addOptional(TierEssences.essence(tier));
        }
    }

    @Override
    public void blockLoot(UmcBlockLoot loot) {
        for (Block block : TierAltarRegistry.allBlocks()) {
            loot.self(block);
        }
    }
}
