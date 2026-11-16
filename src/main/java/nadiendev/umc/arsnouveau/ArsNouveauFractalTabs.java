package nadiendev.umc.arsnouveau;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/** The Ars Nouveau sub tab of the creative tab. */
public final class ArsNouveauFractalTabs {

    public static final String ID = "arsnouveau";

    private ArsNouveauFractalTabs() {
    }

    public static CreativeSubTab build(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, ID);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(ID)))
                .entries((parameters, output) -> {
                    for (Item item : ArsNouveauRegistry.items()) {
                        output.accept(item);
                    }
                })
                .build();
    }
}
