package nadiendev.umc.core.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class UmcItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    public static final DeferredItem<Item> TAB_ICON = ITEMS.registerSimpleItem("tab_icon");

    private UmcItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
