package nadiendev.umc.compat.kubejs.excessiveutilities.type;

import net.minecraft.util.StringRepresentable;

public enum FuelGeneratorType implements StringRepresentable {
    ENDER("ender"),
    EXPLOSIVE("explosive"),
    PINK("pink"),
    NETHER_STAR("nether_star"),
    FROSTY("frosty"),
    HALITOSIS("halitosis"),
    DEATH("death");

    private final String serializedName;

    FuelGeneratorType(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
