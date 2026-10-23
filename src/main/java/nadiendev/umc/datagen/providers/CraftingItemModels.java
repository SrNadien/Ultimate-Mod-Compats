package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class CraftingItemModels extends ItemModelProvider {

    public CraftingItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    public String getName() {
        return "Crafting Item Models";
    }

    @Override
    protected void registerModels() {
        flat(CraftingItems.WORMWOOD_SEEDS);
        flat(CraftingItems.VERVAIN_SEEDS);
        flat(CraftingItems.WORMWOOD_SPRIG);
        flat(CraftingItems.VERVAIN_FLOWER);
        flat(CraftingItems.MOONTHREAD);
        flat(CraftingItems.WITCH_CLOTH);
        flat(CraftingItems.WORMWOOD_TINCTURE);
        flat(CraftingItems.VERVAIN_TONIC);
        flat(CraftingItems.MOONDEW);
        flat(CraftingItems.WITCH_HAT);
        flat(CraftingItems.WITCH_ROBE);
        flat(CraftingItems.WITCH_CLOAK);
        flat(CraftingItems.WITCH_BOOTS);
        blockFlat("moonbloom");
        blockFlat("mugwort");
        spawnEgg(CraftingItems.TOAD_SPAWN_EGG);
        spawnEgg(CraftingItems.OWL_SPAWN_EGG);
        spawnEgg(CraftingItems.RAVEN_SPAWN_EGG);
    }

    private void flat(DeferredItem<? extends Item> item) {
        basicItem(item.get());
    }

    private void blockFlat(String name) {
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    private void spawnEgg(DeferredItem<? extends Item> item) {
        withExistingParent(item.getId().getPath(), "item/template_spawn_egg");
    }
}
