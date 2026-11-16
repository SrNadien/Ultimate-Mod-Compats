package nadiendev.umc.functionalstorage;

import com.buuz135.functionalstorage.block.config.FunctionalStorageConfig;
import com.buuz135.functionalstorage.item.FSAttachments;
import com.buuz135.functionalstorage.item.UpgradeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/** The pieces that need Functional Storage classes. Only loaded when Functional Storage is installed. */
final class StorageUpgradeItems {

    private StorageUpgradeItems() {
    }

    /** Makes the additive factor known to Functional Storage, which reads the factors of the upgrades by name. */
    static void registerCodecs() {
        AdditiveFactor.register();
    }

    /**
     * A storage upgrade like the ones of Functional Storage, but that adds up with the others instead of multiplying: it carries the factor of the items, the one of the fluids
     * and the one of the range of the controller, which are the same factor divided by the divisors of its config.
     */
    static Item upgrade(float factor) {
        Item.Properties properties = new Item.Properties()
                .rarity(Rarity.EPIC)
                .component(FSAttachments.ITEM_STORAGE_MODIFIER, new AdditiveFactor(factor))
                .component(FSAttachments.FLUID_STORAGE_MODIFIER, new AdditiveFactor(factor / FunctionalStorageConfig.FLUID_DIVISOR))
                .component(FSAttachments.CONTROLLER_RANGE_MODIFIER, new AdditiveFactor(factor / FunctionalStorageConfig.RANGE_DIVISOR));
        return new UpgradeItem(properties, UpgradeItem.Type.STORAGE);
    }
}
