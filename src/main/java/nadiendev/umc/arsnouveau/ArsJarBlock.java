package nadiendev.umc.arsnouveau;

import com.hollingsworth.arsnouveau.common.block.ModBlock;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.items.data.BlockFillContents;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** A source jar that holds more source than the one of Ars Nouveau. */
public class ArsJarBlock extends SourceJar {

    private final int capacity;

    public ArsJarBlock(String id, int capacity) {
        super(ModBlock.defaultProperties().noOcclusion(), id);
        this.capacity = capacity;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArsJarTile(pos, state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SourceJarTile tile) {
            tile.updateBlock();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int percent = (int) ((long) BlockFillContents.get(stack) * 100 / capacity);
        tooltip.add(Component.translatable("ars_nouveau.source_jar.fullness", percent));
    }
}
