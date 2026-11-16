package nadiendev.umc.solarflux;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The mirror and the coating of each material, which the solar panel of that material is made from. The panels
 * themselves are registered by Solar Flux Reborn, see {@link ArmourySolarFluxCompat} and {@link AtmSolarFluxCompat}.
 */
public final class SolarFluxRegistry {

    /** The name of the solar panel of a material in Solar Flux Reborn: the block is {@code solarflux:sp_umc.<id>}. */
    public static String panelName(UmcMaterial material) {
        return "umc." + material.id();
    }

    public static String mirrorId(UmcMaterial material) {
        return material.id() + "_mirror";
    }

    public static String coatingId(UmcMaterial material) {
        return material.id() + "_coating";
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final Map<UmcMaterial, DeferredItem<Item>> MIRRORS = new EnumMap<>(UmcMaterial.class);
    private static final Map<UmcMaterial, DeferredItem<Item>> COATINGS = new EnumMap<>(UmcMaterial.class);

    private SolarFluxRegistry() {
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.SOLAR_FLUX)) {
            return;
        }
        for (UmcMaterial material : UmcMaterial.available()) {
            MIRRORS.put(material, ITEMS.registerSimpleItem(mirrorId(material)));
            COATINGS.put(material, ITEMS.registerSimpleItem(coatingId(material)));
        }
        ITEMS.register(modBus);
    }

    public static Item mirror(UmcMaterial material) {
        return MIRRORS.get(material).get();
    }

    public static Item coating(UmcMaterial material) {
        return COATINGS.get(material).get();
    }

    public static boolean has(UmcMaterial material) {
        return MIRRORS.containsKey(material);
    }

    public static List<Item> mirrors() {
        return MIRRORS.values().stream().<Item>map(DeferredItem::get).toList();
    }

    public static List<Item> coatings() {
        return COATINGS.values().stream().<Item>map(DeferredItem::get).toList();
    }

    public static List<Item> items() {
        List<Item> all = new ArrayList<>(mirrors());
        all.addAll(coatings());
        return all;
    }
}
