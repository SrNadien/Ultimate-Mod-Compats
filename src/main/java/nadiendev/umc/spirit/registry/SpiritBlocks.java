package nadiendev.umc.spirit.registry;

import nadiendev.umc.spirit.block.CovenAltarBlock;
import nadiendev.umc.spirit.block.DreamCatcherBlock;
import nadiendev.umc.spirit.block.DreamLoomBlock;
import nadiendev.umc.spirit.block.SpiritPlantBlock;
import nadiendev.umc.spirit.block.SpiritPortalBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SpiritBlocks {

    public static final DeferredBlock<Block> SPIRIT_STONE = SpiritRegistry.BLOCKS.register("spirit_stone", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> SPIRIT_SOIL = SpiritRegistry.BLOCKS.register("spirit_soil", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN).strength(0.6F).sound(SoundType.MOSS)));
    public static final DeferredBlock<RotatedPillarBlock> SPIRIT_LOG = SpiritRegistry.BLOCKS.register("spirit_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GRAY).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<LeavesBlock> SPIRIT_LEAVES = SpiritRegistry.BLOCKS.register("spirit_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().lightLevel(state -> 4)
            .isValidSpawn((state, level, pos, type) -> false).isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false)
            .pushReaction(PushReaction.DESTROY).ignitedByLava()));
    public static final DeferredBlock<Block> SPIRIT_PLANKS = SpiritRegistry.BLOCKS.register("spirit_planks", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<SpiritPlantBlock> SPIRIT_BLOOM = SpiritRegistry.BLOCKS.register("spirit_bloom", () -> new SpiritPlantBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.GRASS).lightLevel(state -> 7).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<SpiritPlantBlock> SPIRIT_GRASS = SpiritRegistry.BLOCKS.register("spirit_grass", () -> new SpiritPlantBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().replaceable().sound(SoundType.GRASS).lightLevel(state -> 2).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<SpiritPortalBlock> SPIRIT_PORTAL = SpiritRegistry.BLOCKS.register("spirit_portal", () -> new SpiritPortalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PURPLE).strength(3.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST_CLUSTER).noOcclusion()
            .lightLevel(state -> state.getValue(SpiritPortalBlock.ACTIVE) ? 12 : 3)));
    public static final DeferredBlock<DreamCatcherBlock> DREAM_CATCHER = SpiritRegistry.BLOCKS.register("dream_catcher", () -> new DreamCatcherBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOL).noCollission().instabreak().sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<DreamLoomBlock> DREAM_LOOM = SpiritRegistry.BLOCKS.register("dream_loom", () -> new DreamLoomBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(2.5F).sound(SoundType.WOOD).noOcclusion().ignitedByLava()));
    public static final DeferredBlock<CovenAltarBlock> COVEN_ALTAR = SpiritRegistry.BLOCKS.register("coven_altar", () -> new CovenAltarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PURPLE).strength(3.5F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion().lightLevel(state -> 6)));

    public static final DeferredItem<BlockItem> SPIRIT_STONE_ITEM = item(SPIRIT_STONE);
    public static final DeferredItem<BlockItem> SPIRIT_SOIL_ITEM = item(SPIRIT_SOIL);
    public static final DeferredItem<BlockItem> SPIRIT_LOG_ITEM = item(SPIRIT_LOG);
    public static final DeferredItem<BlockItem> SPIRIT_LEAVES_ITEM = item(SPIRIT_LEAVES);
    public static final DeferredItem<BlockItem> SPIRIT_PLANKS_ITEM = item(SPIRIT_PLANKS);
    public static final DeferredItem<BlockItem> SPIRIT_BLOOM_ITEM = item(SPIRIT_BLOOM);
    public static final DeferredItem<BlockItem> SPIRIT_GRASS_ITEM = item(SPIRIT_GRASS);
    public static final DeferredItem<BlockItem> SPIRIT_PORTAL_ITEM = item(SPIRIT_PORTAL);
    public static final DeferredItem<BlockItem> DREAM_CATCHER_ITEM = item(DREAM_CATCHER);
    public static final DeferredItem<BlockItem> DREAM_LOOM_ITEM = item(DREAM_LOOM);
    public static final DeferredItem<BlockItem> COVEN_ALTAR_ITEM = item(COVEN_ALTAR);

    private SpiritBlocks() {
    }

    private static <B extends Block> DeferredItem<BlockItem> item(DeferredBlock<B> block) {
        return SpiritRegistry.ITEMS.registerSimpleBlockItem(block, new Item.Properties());
    }

    public static void load() {
    }
}
