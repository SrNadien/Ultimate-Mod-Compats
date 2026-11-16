package nadiendev.umc.core.witchcraft;

import nadiendev.umc.crafting.client.CraftingClientEvents;
import nadiendev.umc.crafting.event.ClothingEvents;
import nadiendev.umc.crafting.event.CraftingModEvents;
import nadiendev.umc.crafting.event.FamiliarBonusEvents;
import nadiendev.umc.crafting.event.PlantEvents;
import nadiendev.umc.crafting.registry.CraftingRegistry;
import nadiendev.umc.creatures.client.CreaturesClientEvents;
import nadiendev.umc.creatures.client.CreaturesClientSetup;
import nadiendev.umc.creatures.command.CreaturesCommands;
import nadiendev.umc.creatures.event.CreaturesEntityEvents;
import nadiendev.umc.creatures.event.CreaturesEvents;
import nadiendev.umc.creatures.network.CreaturesNetwork;
import nadiendev.umc.creatures.registry.CreaturesRegistry;
import nadiendev.umc.creatures.rite.CreaturesRites;
import nadiendev.umc.spirit.client.SpiritClientEvents;
import nadiendev.umc.spirit.client.SpiritClientModEvents;
import nadiendev.umc.spirit.coven.CovenCommands;
import nadiendev.umc.spirit.coven.CovenEvents;
import nadiendev.umc.spirit.dream.DreamEvents;
import nadiendev.umc.spirit.entity.SpiritEntityEvents;
import nadiendev.umc.spirit.infusion.InfusionEvents;
import nadiendev.umc.spirit.network.SpiritNetwork;
import nadiendev.umc.spirit.registry.SpiritRegistry;
import nadiendev.umc.spirit.rite.SpiritRites;
import nadiendev.umc.spirit.world.SpiritScheduler;
import nadiendev.umc.spirit.world.SpiritWorldEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * The witchcraft of this mod (the creatures, the spirit world and the crafting): everything that is built over Enchanted
 * and GeckoLib. None of it is loaded when Enchanted is not installed, so the mod only needs NeoForge and Fractal.
 */
public final class WitchcraftModule {

    public static final String ENCHANTED = "enchanted";

    private WitchcraftModule() {
    }

    public static boolean enabled() {
        return ModList.get().isLoaded(ENCHANTED);
    }

    public static void register(IEventBus modBus) {
        CreaturesRegistry.register(modBus);
        SpiritRegistry.register(modBus);
        CraftingRegistry.register(modBus);
        for (Class<?> type : new Class<?>[]{ClothingEvents.class, CraftingModEvents.class, FamiliarBonusEvents.class, PlantEvents.class,
                CreaturesCommands.class, CreaturesEntityEvents.class, CreaturesEvents.class, CreaturesNetwork.class, CreaturesRites.class,
                CovenCommands.class, CovenEvents.class, DreamEvents.class, SpiritEntityEvents.class, InfusionEvents.class,
                SpiritNetwork.class, SpiritRites.class, SpiritScheduler.class, SpiritWorldEvents.class}) {
            ManualSubscribers.register(modBus, type);
        }
        if (FMLEnvironment.dist.isClient()) {
            for (Class<?> type : new Class<?>[]{CraftingClientEvents.class, CreaturesClientEvents.class, CreaturesClientSetup.class,
                    SpiritClientEvents.class, SpiritClientModEvents.class}) {
                ManualSubscribers.register(modBus, type);
            }
        }
    }
}
