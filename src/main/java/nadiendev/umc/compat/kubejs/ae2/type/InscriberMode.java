package nadiendev.umc.compat.kubejs.ae2.type;

import net.minecraft.util.StringRepresentable;

public enum InscriberMode implements StringRepresentable {
    INSCRIBE("inscribe"),
    PRESS("press");

    private final String serializedName;

    InscriberMode(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
