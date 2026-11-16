package nadiendev.umc.thaumaturge;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The Thaumaturge wand caps and rods of the Ultimate Armoury materials, registered in the {@code umc} namespace. */
public final class ThaumaturgeRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final List<DeferredItem<Item>> REGISTERED = new ArrayList<>();

    private ThaumaturgeRegistry() {
    }

    public static String capId(UmcMaterial material) {
        return "wand_cap_" + material.id();
    }

    public static String rodId(UmcMaterial material) {
        return "wand_rod_" + material.id();
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.THAUMATURGE_WANDS)) {
            return;
        }
        ThaumaturgeParts.register(modBus, ITEMS, REGISTERED);
        ITEMS.register(modBus);
    }

    public static List<Item> items() {
        return REGISTERED.stream().<Item>map(DeferredItem::get).toList();
    }
}
