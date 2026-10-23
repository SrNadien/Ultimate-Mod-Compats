package nadiendev.umc.compat.kubejs.ae2.type;

import net.minecraft.util.StringRepresentable;

public enum EntropyMode implements StringRepresentable {
    HEAT("heat"),
    COOL("cool");

    private final String serializedName;

    EntropyMode(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
