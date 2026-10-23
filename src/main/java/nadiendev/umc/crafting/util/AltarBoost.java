package nadiendev.umc.crafting.util;

import java.lang.reflect.Field;
import net.favouriteless.enchanted.common.blocks.entity.AltarBlockEntity;
import net.favouriteless.enchanted.common.enchanted.altar.AltarPowerData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public final class AltarBoost {

    private static Field powerField;
    private static Field dataField;
    private static boolean failed;

    private AltarBoost() {
    }

    private static boolean prepare() {
        if (failed) {
            return false;
        }
        if (powerField != null && dataField != null) {
            return true;
        }
        try {
            powerField = AltarBlockEntity.class.getDeclaredField("power");
            powerField.setAccessible(true);
            dataField = AltarBlockEntity.class.getDeclaredField("powerData");
            dataField.setAccessible(true);
            return true;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            failed = true;
            return false;
        }
    }

    public static int boost(ServerLevel level, BlockPos center, int radius, double amount) {
        if (amount <= 0.0D || !prepare()) {
            return 0;
        }
        int boosted = 0;
        int minX = (center.getX() - radius) >> 4;
        int maxX = (center.getX() + radius) >> 4;
        int minZ = (center.getZ() - radius) >> 4;
        int maxZ = (center.getZ() + radius) >> 4;
        double radiusSqr = (double) radius * radius;
        for (int cx = minX; cx <= maxX; cx++) {
            for (int cz = minZ; cz <= maxZ; cz++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(cx, cz);
                if (chunk == null) {
                    continue;
                }
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof AltarBlockEntity altar && altar.getBlockPos().distSqr(center) <= radiusSqr && charge(altar, amount)) {
                        boosted++;
                    }
                }
            }
        }
        return boosted;
    }

    private static boolean charge(AltarBlockEntity altar, double amount) {
        try {
            AltarPowerData data = (AltarPowerData) dataField.get(altar);
            double capacity = data.getCapacity();
            double power = powerField.getDouble(altar);
            if (power >= capacity) {
                return false;
            }
            powerField.setDouble(altar, Math.min(capacity, power + amount));
            altar.setChanged();
            return true;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            failed = true;
            return false;
        }
    }
}
