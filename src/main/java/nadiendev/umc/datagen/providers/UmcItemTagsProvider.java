package nadiendev.umc.datagen.providers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class UmcItemTagsProvider extends ItemTagsProvider {

    private final List<DataModule> modules;

    public UmcItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, ExistingFileHelper helper, List<DataModule> modules) {
        super(output, registries, blockTags, UltimateModCompats.MODID, helper);
        this.modules = modules;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        modules.forEach(module -> module.itemTags(this::tag));
    }
}
