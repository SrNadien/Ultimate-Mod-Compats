package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.registry.CreaturesItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class CreaturesItemModels extends ItemModelProvider {

    public CreaturesItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    public String getName() {
        return "Creatures Item Models";
    }

    @Override
    protected void registerModels() {
        basicItem(CreaturesItems.SILVER_INGOT.get());
        basicItem(CreaturesItems.GRAVE_DUST.get());
        basicItem(CreaturesItems.ECTOPLASM.get());
        basicItem(CreaturesItems.IMP_HORN.get());
        basicItem(CreaturesItems.WEREWOLF_FANG.get());
        basicItem(CreaturesItems.VAMPIRE_FANG.get());
        basicItem(CreaturesItems.PURIFYING_TONIC.get());
        basicItem(CreaturesItems.LUNAR_TALISMAN.get());
        basicItem(CreaturesItems.BLOOD_SIGIL.get());
        basicItem(CreaturesItems.SUMMONING_HORN.get());
        basicItem(CreaturesItems.DEMONOMICON.get());
        handheld(CreaturesItems.SILVER_SWORD);
        handheld(CreaturesItems.WOODEN_STAKE);
        handheld(CreaturesItems.NECROMANCER_SCEPTER);
        spawnEgg(CreaturesItems.WEREWOLF_SPAWN_EGG);
        spawnEgg(CreaturesItems.VAMPIRE_SPAWN_EGG);
        spawnEgg(CreaturesItems.GHOST_SPAWN_EGG);
        spawnEgg(CreaturesItems.IMP_SPAWN_EGG);
        spawnEgg(CreaturesItems.ENT_SPAWN_EGG);
    }

    private void handheld(DeferredItem<? extends Item> item) {
        String name = item.getId().getPath();
        withExistingParent(name, mcLoc("item/handheld")).texture("layer0", modLoc("item/" + name));
    }

    private void spawnEgg(DeferredItem<? extends Item> item) {
        withExistingParent(item.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
