package nadiendev.umc.spirit.registry;

import nadiendev.umc.spirit.infusion.InfusionType;
import nadiendev.umc.spirit.item.InfusionEssenceItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SpiritItems {

    public static final DeferredItem<Item> SPIRIT_DUST = SpiritRegistry.ITEMS.registerSimpleItem("spirit_dust");
    public static final DeferredItem<Item> DREAM_THREAD = SpiritRegistry.ITEMS.registerSimpleItem("dream_thread");
    public static final DeferredItem<InfusionEssenceItem> LEAP_ESSENCE = SpiritRegistry.ITEMS.register("leap_essence",
            () -> new InfusionEssenceItem(InfusionType.LEAP, new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<InfusionEssenceItem> CLIMB_ESSENCE = SpiritRegistry.ITEMS.register("climb_essence",
            () -> new InfusionEssenceItem(InfusionType.CLIMB, new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<InfusionEssenceItem> SIGHT_ESSENCE = SpiritRegistry.ITEMS.register("sight_essence",
            () -> new InfusionEssenceItem(InfusionType.SIGHT, new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<DeferredSpawnEggItem> NIGHTMARE_SPAWN_EGG = SpiritRegistry.ITEMS.register("nightmare_spawn_egg",
            () -> new DeferredSpawnEggItem(SpiritEntities.NIGHTMARE, 0x2A1A4A, 0x9D7CFF, new Item.Properties()));

    private SpiritItems() {
    }

    public static void load() {
    }
}
