package nadiendev.umc.compat.kubejs.excessiveutilities.type;

import net.minecraft.util.StringRepresentable;

public enum UnstableStability implements StringRepresentable {
    STABLE("stable"),
    UNSTABLE("unstable"),
    EITHER("either");

    private final String serializedName;

    UnstableStability(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
