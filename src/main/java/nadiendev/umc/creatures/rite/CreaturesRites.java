package nadiendev.umc.creatures.rite;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.rite.factory.CureCurseFactory;
import nadiendev.umc.creatures.rite.factory.InflictCurseFactory;
import nadiendev.umc.creatures.rite.factory.SummonMinionsFactory;
import net.favouriteless.enchanted.api.circle_magic.RiteFactoryRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CreaturesRites {

    private CreaturesRites() {
    }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RiteFactoryRegistry registry = RiteFactoryRegistry.get();
            registry.register(SummonMinionsFactory.ID, SummonMinionsFactory.CODEC);
            registry.register(InflictCurseFactory.ID, InflictCurseFactory.CODEC);
            registry.register(CureCurseFactory.ID, CureCurseFactory.CODEC);
        });
    }
}
