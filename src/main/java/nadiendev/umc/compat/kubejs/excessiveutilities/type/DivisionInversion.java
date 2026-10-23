package nadiendev.umc.compat.kubejs.excessiveutilities.type;

import net.minecraft.util.StringRepresentable;

public enum DivisionInversion implements StringRepresentable {
    INVERTED("inverted"),
    UNINVERTED("uninverted"),
    EITHER("either");

    private final String serializedName;

    DivisionInversion(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
