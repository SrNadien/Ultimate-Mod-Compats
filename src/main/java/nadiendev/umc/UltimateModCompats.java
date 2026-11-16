package nadiendev.umc;

import nadiendev.umc.arsnouveau.ArsNouveauRegistry;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.enderio.EnderIoRegistry;
import nadiendev.umc.foregoing.ForegoingRegistry;
import nadiendev.umc.functionalstorage.FunctionalStorageRegistry;
import nadiendev.umc.core.registry.UmcConditions;
import nadiendev.umc.core.registry.UmcItems;
import nadiendev.umc.core.registry.UmcTabs;
import nadiendev.umc.core.witchcraft.WitchcraftDisabledData;
import nadiendev.umc.core.witchcraft.WitchcraftModule;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.solarflux.SolarFluxRegistry;
import nadiendev.umc.thaumaturge.ThaumaturgeRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(UltimateModCompats.MODID)
public class UltimateModCompats {

    public static final String MODID = "umc";

    public UltimateModCompats(IEventBus modBus, ModContainer container) {
        UmcItems.register(modBus);
        UmcConditions.register(modBus);
        UmcTabs.register(modBus);
        if (WitchcraftModule.enabled()) {
            WitchcraftModule.register(modBus);
        } else {
            WitchcraftDisabledData.register(modBus);
        }
        TierAltarRegistry.register(modBus);
        if (CompatSwitches.enabled(CompatSwitches.ENDERIO)) {
            EnderIoRegistry.register(modBus);
        }

        if (CompatSwitches.enabled(CompatSwitches.INDUSTRIAL_FOREGOING)) {
            ForegoingRegistry.register(modBus);
        }

        if (CompatSwitches.enabled(CompatSwitches.THAUMATURGE_WANDS)) {
            ThaumaturgeRegistry.register(modBus);
        }

        if (CompatSwitches.enabled(CompatSwitches.ARS_NOUVEAU)) {
            ArsNouveauRegistry.register(modBus);
        }

        if (CompatSwitches.enabled(CompatSwitches.SOLAR_FLUX)) {
            SolarFluxRegistry.register(modBus);
        }

        if (CompatSwitches.enabled(CompatSwitches.FUNCTIONAL_STORAGE)) {
            FunctionalStorageRegistry.register(modBus);
        }

        container.registerConfig(ModConfig.Type.COMMON, UmcConfig.SPEC, UmcConfig.FILE);
    }
}
