package nadiendev.umc.spirit.coven;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.favouriteless.enchanted.common.blocks.entity.AltarBlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AltarPower {

    private static final Logger LOGGER = LoggerFactory.getLogger(AltarPower.class);

    private static Field powerField;
    private static Field powerDataField;
    private static Method capacityMethod;
    private static boolean failed;

    private AltarPower() {
    }

    private static boolean prepare() {
        if (failed) {
            return false;
        }
        if (powerField != null) {
            return true;
        }
        try {
            Field power = AltarBlockEntity.class.getDeclaredField("power");
            power.setAccessible(true);
            Field data = AltarBlockEntity.class.getDeclaredField("powerData");
            data.setAccessible(true);
            Method capacity = data.getType().getMethod("getCapacity");
            powerField = power;
            powerDataField = data;
            capacityMethod = capacity;
            return true;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            failed = true;
            LOGGER.warn("Enchanted altar power access is unavailable, the coven altar power bonus is disabled", exception);
            return false;
        }
    }

    public static void add(AltarBlockEntity altar, double amount) {
        if (amount <= 0.0D || !prepare()) {
            return;
        }
        try {
            double current = powerField.getDouble(altar);
            double capacity = ((Number) capacityMethod.invoke(powerDataField.get(altar))).doubleValue();
            if (current >= capacity) {
                return;
            }
            powerField.setDouble(altar, Math.min(capacity, current + amount));
            altar.setChanged();
        } catch (ReflectiveOperationException | RuntimeException exception) {
            failed = true;
            LOGGER.warn("Failed to add power to an Enchanted altar, the coven altar power bonus is disabled", exception);
        }
    }
}
