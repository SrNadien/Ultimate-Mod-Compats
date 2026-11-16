package nadiendev.umc.arsnouveau;

import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

/** The block entity of the jars of the materials. The capacity is the one of the block it belongs to. */
public class ArsJarTile extends SourceJarTile {

    /** The fill levels of the jar model: empty, a sliver, and ten more up to full. */
    private static final int MAX_FILL_LEVEL = 11;

    public ArsJarTile(BlockPos pos, BlockState state) {
        super(ArsJars.TILE.get(), pos, state);
    }

    private int capacity() {
        return getBlockState().getBlock() instanceof ArsJarBlock jar ? jar.capacity() : 10_000;
    }

    @Override
    protected SourceStorage createDefaultStorage() {
        int capacity = capacity();
        return new SourceStorage(capacity, capacity) {
            @Override
            public void onContentsChanged() {
                updateBlock();
            }
        };
    }

    @Override
    public boolean updateBlock() {
        if (level == null) {
            return false;
        }
        BlockState state = level.getBlockState(worldPosition);
        if (!state.hasProperty(SourceJar.fill)) {
            return false;
        }
        int perLevel = Math.max(1, capacity() / 10);
        int fill = getSource() <= 0 ? 0 : Math.min(getSource() / perLevel + 1, MAX_FILL_LEVEL);
        if (state.setValue(SourceJar.fill, fill) != state) {
            level.setBlock(worldPosition, state.setValue(SourceJar.fill, fill), 3);
        }
        level.sendBlockUpdated(worldPosition, state, state, 3);
        setChanged();
        return true;
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
        tooltip.add(Component.translatable("ars_nouveau.source_jar.fullness", (int) ((long) getSource() * 100 / getMaxSource())));
    }
}
