package nadiendev.umc.crafting.registry;

import nadiendev.umc.crafting.item.WitchArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

public final class CraftingItems {

    public static final DeferredItem<Item> WORMWOOD_SEEDS = CraftingRegistry.ITEMS.register("wormwood_seeds",
            () -> new ItemNameBlockItem(CraftingBlocks.WORMWOOD.get(), new Item.Properties()));
    public static final DeferredItem<Item> VERVAIN_SEEDS = CraftingRegistry.ITEMS.register("vervain_seeds",
            () -> new ItemNameBlockItem(CraftingBlocks.VERVAIN.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MOONBLOOM = CraftingRegistry.ITEMS.registerSimpleBlockItem("moonbloom", CraftingBlocks.MOONBLOOM);
    public static final DeferredItem<BlockItem> MUGWORT = CraftingRegistry.ITEMS.registerSimpleBlockItem("mugwort", CraftingBlocks.MUGWORT);

    public static final DeferredItem<Item> WORMWOOD_SPRIG = CraftingRegistry.ITEMS.registerSimpleItem("wormwood_sprig");
    public static final DeferredItem<Item> VERVAIN_FLOWER = CraftingRegistry.ITEMS.registerSimpleItem("vervain_flower");
    public static final DeferredItem<Item> MOONTHREAD = CraftingRegistry.ITEMS.registerSimpleItem("moonthread");
    public static final DeferredItem<Item> WITCH_CLOTH = CraftingRegistry.ITEMS.registerSimpleItem("witch_cloth");
    public static final DeferredItem<Item> WORMWOOD_TINCTURE = CraftingRegistry.ITEMS.registerSimpleItem("wormwood_tincture");
    public static final DeferredItem<Item> VERVAIN_TONIC = CraftingRegistry.ITEMS.registerSimpleItem("vervain_tonic");
    public static final DeferredItem<Item> MOONDEW = CraftingRegistry.ITEMS.registerSimpleItem("moondew");

    public static final DeferredItem<WitchArmorItem> WITCH_HAT = CraftingRegistry.ITEMS.register("witch_hat",
            () -> new WitchArmorItem(CraftingArmorMaterials.WITCH_HAT, ArmorItem.Type.HELMET, WitchArmorItem.Piece.HAT,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final DeferredItem<WitchArmorItem> WITCH_ROBE = CraftingRegistry.ITEMS.register("witch_robe",
            () -> new WitchArmorItem(CraftingArmorMaterials.WITCH_ROBE, ArmorItem.Type.CHESTPLATE, WitchArmorItem.Piece.ROBE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final DeferredItem<WitchArmorItem> WITCH_CLOAK = CraftingRegistry.ITEMS.register("witch_cloak",
            () -> new WitchArmorItem(CraftingArmorMaterials.WITCH_CLOAK, ArmorItem.Type.CHESTPLATE, WitchArmorItem.Piece.CLOAK,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final DeferredItem<WitchArmorItem> WITCH_BOOTS = CraftingRegistry.ITEMS.register("witch_boots",
            () -> new WitchArmorItem(CraftingArmorMaterials.WITCH_BOOTS, ArmorItem.Type.BOOTS, WitchArmorItem.Piece.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final DeferredItem<DeferredSpawnEggItem> TOAD_SPAWN_EGG = CraftingRegistry.ITEMS.register("toad_spawn_egg",
            () -> new DeferredSpawnEggItem(CraftingEntities.TOAD, 0x6B5B2E, 0xC9B25A, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> OWL_SPAWN_EGG = CraftingRegistry.ITEMS.register("owl_spawn_egg",
            () -> new DeferredSpawnEggItem(CraftingEntities.OWL, 0x8A6A45, 0xE8D9B0, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> RAVEN_SPAWN_EGG = CraftingRegistry.ITEMS.register("raven_spawn_egg",
            () -> new DeferredSpawnEggItem(CraftingEntities.RAVEN, 0x1B1A24, 0x5A4C8C, new Item.Properties()));

    private CraftingItems() {
    }

    public static void init() {
    }
}
