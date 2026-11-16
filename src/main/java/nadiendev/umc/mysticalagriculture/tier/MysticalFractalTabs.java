package nadiendev.umc.mysticalagriculture.tier;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

/**
 * The Mystical Agriculture sub tabs of the compatibility tab. Only touched when Mystical Agriculture is loaded.
 */
public final class MysticalFractalTabs {

    private MysticalFractalTabs() {
    }

    /** The sub tab of a section, or null when the section has nothing to show. */
    public static CreativeSubTab build(CreativeModeTab parent, TierTabOrder.Section section) {
        if (TierTabOrder.items(section).isEmpty()) {
            return null;
        }
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, section.id());
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(section.id())))
                .entries((parameters, output) -> TierTabOrder.items(section).forEach(output::accept))
                .build();
    }
}
