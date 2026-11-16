package nadiendev.umc.enderio;

import com.enderio.enderio.api.capacitor.CapacitorData;
import com.enderio.enderio.content.capacitors.CapacitorItem;
import com.enderio.enderio.init.EIODataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/** Builds the items that need EnderIO classes. Only loaded when EnderIO is installed. */
final class EnderIoItems {

    private EnderIoItems() {
    }

    static Item capacitor(float base) {
        return new CapacitorItem(new Item.Properties()
                .rarity(Rarity.EPIC)
                .component(EIODataComponents.CAPACITOR_DATA, CapacitorData.simple(base)));
    }
}
