package nadiendev.umc.spirit.rite;

import nadiendev.umc.UltimateModCompats;
import net.favouriteless.enchanted.api.circle_magic.RiteFactoryRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class SpiritRites {

    private SpiritRites() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> RiteFactoryRegistry.get().register(SpiritJourneyFactory.ID, SpiritJourneyFactory.CODEC));
    }
}
