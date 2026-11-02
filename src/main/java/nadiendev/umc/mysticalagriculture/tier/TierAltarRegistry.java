package nadiendev.umc.mysticalagriculture.tier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TierAltarRegistry {

    public static final String MYSTICAL_AGRICULTURE = "mysticalagriculture";
    public static final String INFUSION_PREFIX = "infusion_altar_tier_";
    public static final String AWAKENING_PREFIX = "awakening_altar_tier_";
    public static final String INFUSION_PEDESTAL_PREFIX = "infusion_pedestal_tier_";
    public static final String AWAKENING_PEDESTAL_PREFIX = "awakening_pedestal_tier_";
    public static final String VESSEL_PREFIX = "essence_vessel_tier_";
    public static final String REPROCESSOR_PREFIX = "seed_reprocessor_tier_";
    public static final String FURNACE_PREFIX = "furnace_tier_";
    public static final String CRYSTAL_PREFIX = "infusion_crystal_tier_";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateModCompats.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);

    private static final List<DeferredBlock<? extends Block>> INFUSION = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> AWAKENING = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> INFUSION_PEDESTALS = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> AWAKENING_PEDESTALS = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> VESSELS = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> REPROCESSORS = new ArrayList<>();
    private static final List<DeferredBlock<? extends Block>> FURNACES = new ArrayList<>();
    private static final List<DeferredItem<BlockItem>> BLOCK_ITEMS = new ArrayList<>();
    private static final List<DeferredItem<Item>> CRYSTALS = new ArrayList<>();

    private TierAltarRegistry() {
    }

    public static void register(IEventBus modBus) {
        if (!ModList.get().isLoaded(MYSTICAL_AGRICULTURE)) {
            return;
        }
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            add(INFUSION, INFUSION_PREFIX, tier, TierAltarFactories::infusion);
            add(AWAKENING, AWAKENING_PREFIX, tier, TierAltarFactories::awakening);
            add(INFUSION_PEDESTALS, INFUSION_PEDESTAL_PREFIX, tier, TierAltarFactories::infusionPedestal);
            add(AWAKENING_PEDESTALS, AWAKENING_PEDESTAL_PREFIX, tier, TierAltarFactories::awakeningPedestal);
            add(VESSELS, VESSEL_PREFIX, tier, TierAltarFactories::vessel);
            add(REPROCESSORS, REPROCESSOR_PREFIX, tier, TierAltarFactories::reprocessor);
            add(FURNACES, FURNACE_PREFIX, tier, TierAltarFactories::furnace);
            int level = tier;
            CRYSTALS.add(ITEMS.register(CRYSTAL_PREFIX + tier, () -> new TierCrystalItem(level)));
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }

    private static void add(List<DeferredBlock<? extends Block>> target, String prefix, int tier, IntFunction<Block> factory) {
        DeferredBlock<Block> block = BLOCKS.register(prefix + tier, () -> factory.apply(tier));
        target.add(block);
        BLOCK_ITEMS.add(ITEMS.registerSimpleBlockItem(prefix + tier, block));
    }

    public static Block[] infusionBlocks() {
        return blocks(INFUSION);
    }

    public static Block[] awakeningBlocks() {
        return blocks(AWAKENING);
    }

    public static Block[] infusionPedestalBlocks() {
        return blocks(INFUSION_PEDESTALS);
    }

    public static Block[] awakeningPedestalBlocks() {
        return blocks(AWAKENING_PEDESTALS);
    }

    public static Block[] vesselBlocks() {
        return blocks(VESSELS);
    }

    public static Block[] reprocessorBlocks() {
        return blocks(REPROCESSORS);
    }

    public static Block[] furnaceBlocks() {
        return blocks(FURNACES);
    }

    public static List<Block> allBlocks() {
        List<Block> all = new ArrayList<>();
        for (Block[] group : new Block[][]{infusionBlocks(), awakeningBlocks(), infusionPedestalBlocks(), awakeningPedestalBlocks(), vesselBlocks(), reprocessorBlocks(), furnaceBlocks()}) {
            all.addAll(List.of(group));
        }
        return all;
    }

    public static List<Item> crystals() {
        return CRYSTALS.stream().<Item>map(DeferredItem::get).toList();
    }

    public static List<Item> items() {
        List<Item> all = new ArrayList<>(BLOCK_ITEMS.stream().<Item>map(DeferredItem::get).toList());
        all.addAll(crystals());
        return all;
    }

    private static Block[] blocks(List<DeferredBlock<? extends Block>> list) {
        return list.stream().map(DeferredBlock::get).toArray(Block[]::new);
    }
}
