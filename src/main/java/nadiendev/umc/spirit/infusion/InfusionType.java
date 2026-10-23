package nadiendev.umc.spirit.infusion;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import nadiendev.umc.spirit.config.SpiritConfig;
import net.minecraft.util.StringRepresentable;

public enum InfusionType implements StringRepresentable {

    LEAP("leap", 0x7FE8FF),
    CLIMB("climb", 0xB98CFF),
    SIGHT("sight", 0xFFE680);

    public static final Codec<InfusionType> CODEC = StringRepresentable.fromEnum(InfusionType::values);

    private final String id;
    private final int color;

    InfusionType(String id, int color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public String getSerializedName() {
        return id;
    }

    public int color() {
        return color;
    }

    public String translationKey() {
        return "infusion.umc." + id;
    }

    public boolean enabled() {
        return switch (this) {
            case LEAP -> SpiritConfig.LEAP_ENABLED.get();
            case CLIMB -> SpiritConfig.CLIMB_ENABLED.get();
            case SIGHT -> SpiritConfig.SIGHT_ENABLED.get();
        };
    }

    public static List<InfusionType> all() {
        return Arrays.asList(values());
    }
}
