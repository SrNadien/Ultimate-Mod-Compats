package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.registry.SpiritItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SpiritItemModels extends ItemModelProvider {

    public SpiritItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    public String getName() {
        return "Spirit Item Models";
    }

    @Override
    protected void registerModels() {
        basicItem(SpiritItems.SPIRIT_DUST.get());
        basicItem(SpiritItems.DREAM_THREAD.get());
        basicItem(SpiritItems.LEAP_ESSENCE.get());
        basicItem(SpiritItems.CLIMB_ESSENCE.get());
        basicItem(SpiritItems.SIGHT_ESSENCE.get());
        spawnEggItem(SpiritItems.NIGHTMARE_SPAWN_EGG.get());
    }
}
