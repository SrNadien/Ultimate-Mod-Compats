package nadiendev.umc.spirit.block;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritPlantBlock extends BushBlock {

    public static final MapCodec<SpiritPlantBlock> CODEC = simpleCodec(SpiritPlantBlock::new);

    private static final VoxelShape SHAPE = box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public SpiritPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(SpiritBlocks.SPIRIT_SOIL.get()) || state.is(BlockTags.DIRT);
    }
}
