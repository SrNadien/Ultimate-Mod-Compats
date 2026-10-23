package nadiendev.umc.datagen.providers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class UmcBlockTagsProvider extends BlockTagsProvider {

    private final List<DataModule> modules;

    public UmcBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper, List<DataModule> modules) {
        super(output, registries, UltimateModCompats.MODID, helper);
        this.modules = modules;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        modules.forEach(module -> module.blockTags(this::tag));
    }
}
