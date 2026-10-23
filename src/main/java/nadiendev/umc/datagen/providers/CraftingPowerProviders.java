package nadiendev.umc.datagen.providers;

import java.util.concurrent.CompletableFuture;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.registry.CraftingBlocks;
import net.favouriteless.enchanted.api.datagen.providers.PowerProvidersProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.util.TriConsumer;

public class CraftingPowerProviders extends PowerProvidersProvider {

    public CraftingPowerProviders(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(UltimateModCompats.MODID, output, registries);
    }

    @Override
    public String getName() {
        return "Crafting Altar Power";
    }

    @Override
    protected void buildBlocks(HolderLookup.Provider registries, TriConsumer<Block, Integer, Integer> output) {
        output.accept(CraftingBlocks.WORMWOOD.get(), 3, 20);
        output.accept(CraftingBlocks.VERVAIN.get(), 3, 20);
        output.accept(CraftingBlocks.MOONBLOOM.get(), 8, 20);
        output.accept(CraftingBlocks.MUGWORT.get(), 6, 20);
    }

    @Override
    protected void buildTags(HolderLookup.Provider registries, TriConsumer<TagKey<Block>, Integer, Integer> output) {
    }
}
