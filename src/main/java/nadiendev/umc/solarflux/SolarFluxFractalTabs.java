package nadiendev.umc.solarflux;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** The Solar Flux sub tab of the creative tab: first the panels, then the mirrors and then the coatings. */
public final class SolarFluxFractalTabs {

    public static final String ID = "solarflux";

    private SolarFluxFractalTabs() {
    }

    public static CreativeSubTab build(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, ID);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(ID)))
                .entries((parameters, output) -> {
                    for (ItemStack stack : SolarFluxPanels.stacks()) {
                        output.accept(stack);
                    }
                    for (Item item : SolarFluxRegistry.mirrors()) {
                        output.accept(item);
                    }
                    for (Item item : SolarFluxRegistry.coatings()) {
                        output.accept(item);
                    }
                })
                .build();
    }
}
