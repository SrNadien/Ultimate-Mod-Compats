package nadiendev.umc.thaumaturge;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/** The Thaumaturge sub tab of the creative tab. */
public final class ThaumaturgeFractalTabs {

    public static final String ID = "thaumaturge";

    private ThaumaturgeFractalTabs() {
    }

    public static CreativeSubTab build(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, ID);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(ID)))
                .entries((parameters, output) -> {
                    for (Item item : ThaumaturgeRegistry.items()) {
                        output.accept(item);
                    }
                })
                .build();
    }
}
