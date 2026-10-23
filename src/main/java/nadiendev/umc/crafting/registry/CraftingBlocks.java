package nadiendev.umc.crafting.registry;

import nadiendev.umc.crafting.block.WitchCropBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class CraftingBlocks {

    public static final DeferredBlock<WitchCropBlock> WORMWOOD = CraftingRegistry.BLOCKS.register("wormwood",
            () -> new WitchCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<WitchCropBlock> VERVAIN = CraftingRegistry.BLOCKS.register("vervain",
            () -> new WitchCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> MOONBLOOM = CraftingRegistry.BLOCKS.register("moonbloom",
            () -> new FlowerBlock(MobEffects.NIGHT_VISION, 6.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(state -> 7)));

    public static final DeferredBlock<Block> MUGWORT = CraftingRegistry.BLOCKS.register("mugwort",
            () -> new FlowerBlock(MobEffects.SATURATION, 7.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)));

    private CraftingBlocks() {
    }

    public static void init() {
    }
}
