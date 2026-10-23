package nadiendev.umc.spirit.block.entity;

import java.util.UUID;
import nadiendev.umc.spirit.registry.SpiritBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CovenAltarBlockEntity extends BlockEntity {

    private UUID covenId;

    public CovenAltarBlockEntity(BlockPos pos, BlockState state) {
        super(SpiritBlockEntities.COVEN_ALTAR.get(), pos, state);
    }

    public UUID covenId() {
        return covenId;
    }

    public void bind(UUID covenId) {
        this.covenId = covenId;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (covenId != null) {
            tag.put("coven", NbtUtils.createUUID(covenId));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        covenId = tag.contains("coven") ? NbtUtils.loadUUID(tag.get("coven")) : null;
    }
}
