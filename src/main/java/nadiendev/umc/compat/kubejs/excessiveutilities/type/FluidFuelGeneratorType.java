package nadiendev.umc.compat.kubejs.excessiveutilities.type;

import net.minecraft.util.StringRepresentable;

public enum FluidFuelGeneratorType implements StringRepresentable {
    SLIMY("slimy"),
    HEATED_REDSTONE("heated_redstone");

    private final String serializedName;

    FluidFuelGeneratorType(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
