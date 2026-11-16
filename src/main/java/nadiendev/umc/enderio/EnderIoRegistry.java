package nadiendev.umc.enderio;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The EnderIO items of the Ultimate Armoury materials, registered in the {@code umc} namespace. */
public final class EnderIoRegistry {

    public static final String GRINDING_BALL = "_grinding_ball";
    public static final String CAPACITOR = "_capacitor";
    public static final String[] CONDUIT_KINDS = {"item", "energy", "fluid"};

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final List<DeferredItem<Item>> REGISTERED = new ArrayList<>();
    private static final List<DeferredItem<Item>> BALLS = new ArrayList<>();
    private static final List<DeferredItem<Item>> CAPACITORS = new ArrayList<>();

    private EnderIoRegistry() {
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.ENDERIO)) {
            return;
        }
        for (UmcMaterial material : UmcMaterial.available()) {
            float base = EnderIoStats.of(material).capacitorBase();
            DeferredItem<Item> ball = ITEMS.registerSimpleItem(material.id() + GRINDING_BALL);
            DeferredItem<Item> capacitor = ITEMS.register(material.id() + CAPACITOR, () -> EnderIoItems.capacitor(base));
            BALLS.add(ball);
            CAPACITORS.add(capacitor);
            REGISTERED.add(ball);
            REGISTERED.add(capacitor);
        }
        ITEMS.register(modBus);
    }

    public static List<Item> balls() {
        return BALLS.stream().<Item>map(DeferredItem::get).toList();
    }

    public static List<Item> capacitors() {
        return CAPACITORS.stream().<Item>map(DeferredItem::get).toList();
    }

    public static List<Item> items() {
        return REGISTERED.stream().<Item>map(DeferredItem::get).toList();
    }

    public static String conduitId(UmcMaterial material, String kind) {
        return material.id() + "_" + kind;
    }
}
