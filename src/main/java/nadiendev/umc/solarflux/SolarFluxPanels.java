package nadiendev.umc.solarflux;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.ItemStack;
import org.zeith.solarflux.init.SolarPanelsSF;
import org.zeith.solarflux.panels.SolarPanel;

/** The panels, which Solar Flux Reborn registers. Only loaded when Solar Flux Reborn is installed. */
final class SolarFluxPanels {

    private SolarFluxPanels() {
    }

    static SolarPanel panel(UmcMaterial material) {
        return SolarPanelsSF.PANELS.get(SolarFluxRegistry.panelName(material));
    }

    static SolarPanel tierEight() {
        return SolarPanelsSF.CORE_PANELS[7];
    }

    static List<ItemStack> stacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (UmcMaterial material : UmcMaterial.available()) {
            SolarPanel panel = panel(material);
            if (panel != null) {
                stacks.add(new ItemStack(panel));
            }
        }
        return stacks;
    }
}
