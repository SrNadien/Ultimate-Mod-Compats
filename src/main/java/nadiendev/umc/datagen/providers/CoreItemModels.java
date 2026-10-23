package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CoreItemModels extends ItemModelProvider {

    public CoreItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(UmcItems.TAB_ICON.get());
    }
}
