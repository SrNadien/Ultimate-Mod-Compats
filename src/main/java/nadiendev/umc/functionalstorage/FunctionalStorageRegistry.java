package nadiendev.umc.functionalstorage;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.functionalstorage.wood.WoodDrawers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The storage upgrades of the materials for Functional Storage, registered in the {@code umc} namespace. */
public final class FunctionalStorageRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final Map<UmcMaterial, DeferredItem<Item>> UPGRADES = new EnumMap<>(UmcMaterial.class);

    private FunctionalStorageRegistry() {
    }

    public static String upgradeId(UmcMaterial material) {
        return material.id() + "_storage_upgrade";
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.FUNCTIONAL_STORAGE)) {
            return;
        }
        StorageUpgradeItems.registerCodecs();
        for (UmcMaterial material : UmcMaterial.available()) {
            float factor = FunctionalStorageStats.multiplier(material);
            UPGRADES.put(material, ITEMS.register(upgradeId(material), () -> StorageUpgradeItems.upgrade(factor)));
        }
        ITEMS.register(modBus);
        // the woods go to Functional Storage before it registers its drawers, which happens after the mods are built
        WoodDrawers.init(modBus);
    }

    public static List<Item> items() {
        return UPGRADES.values().stream().<Item>map(DeferredItem::get).toList();
    }
}
