package nadiendev.umc.functionalstorage;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.functionalstorage.wood.WoodDrawers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/** The Functional Storage sub tab of the creative tab. */
public final class FunctionalStorageFractalTabs {

    public static final String ID = "functionalstorage";

    private FunctionalStorageFractalTabs() {
    }

    public static CreativeSubTab build(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, ID);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(ID)))
                .entries((parameters, output) -> {
                    for (Item item : FunctionalStorageRegistry.items()) {
                        output.accept(item);
                    }
                    for (Item item : WoodDrawers.items()) {
                        output.accept(item);
                    }
                })
                .build();
    }
}
