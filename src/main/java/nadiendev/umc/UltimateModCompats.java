package nadiendev.umc;

import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.core.registry.UmcConditions;
import nadiendev.umc.core.registry.UmcItems;
import nadiendev.umc.core.registry.UmcTabs;
import nadiendev.umc.crafting.registry.CraftingRegistry;
import nadiendev.umc.creatures.registry.CreaturesRegistry;
import nadiendev.umc.spirit.registry.SpiritRegistry;
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
        CreaturesRegistry.register(modBus);
        SpiritRegistry.register(modBus);
        CraftingRegistry.register(modBus);

        container.registerConfig(ModConfig.Type.COMMON, UmcConfig.SPEC, UmcConfig.FILE);
    }
}
