package nadiendev.umc.foregoing;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The Industrial Foregoing addons of the Ultimate Armoury materials, registered in the {@code umc} namespace. */
public final class ForegoingRegistry {

    public static final String[] KINDS = {"range", "speed", "efficiency", "processing"};

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final Map<UmcMaterial, List<DeferredItem<Item>>> ADDONS = new EnumMap<>(UmcMaterial.class);

    private ForegoingRegistry() {
    }

    public static String addonId(UmcMaterial material, String kind) {
        return material.id() + "_" + kind + "_addon";
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.INDUSTRIAL_FOREGOING)) {
            return;
        }
        for (UmcMaterial material : UmcMaterial.available()) {
            List<DeferredItem<Item>> addons = new ArrayList<>();
            for (String kind : KINDS) {
                addons.add(ITEMS.register(addonId(material, kind), ForegoingItems::addon));
            }
            ADDONS.put(material, addons);
        }
        ITEMS.register(modBus);
        modBus.addListener(ForegoingRegistry::components);
    }

    private static void components(ModifyDefaultComponentsEvent event) {
        ADDONS.forEach((material, addons) -> ForegoingItems.components(event, material,
                addons.get(0).get(), addons.get(1).get(), addons.get(2).get(), addons.get(3).get()));
    }

    public static List<Item> items() {
        return ADDONS.values().stream().flatMap(List::stream).<Item>map(DeferredItem::get).toList();
    }
}
