package nadiendev.umc.functionalstorage.wood;

import com.buuz135.functionalstorage.util.IWoodType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * A wood of another mod for the drawers of Functional Storage. The blocks of the other mod may not exist yet when the
 * drawers are registered, so they are looked up when asked and oak stands in for the ones that are not there.
 */
public final class ModdedWoodType implements IWoodType {

    private final WoodDiscovery.Wood wood;

    public ModdedWoodType(WoodDiscovery.Wood wood) {
        this.wood = wood;
    }

    @Override
    public Block getWood() {
        return BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(wood.logId())).orElse(Blocks.OAK_LOG);
    }

    @Override
    public Block getPlanks() {
        return BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(wood.planksId())).orElse(Blocks.OAK_PLANKS);
    }

    @Override
    public String getName() {
        return wood.name();
    }
}
