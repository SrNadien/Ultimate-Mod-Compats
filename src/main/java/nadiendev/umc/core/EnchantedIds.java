package nadiendev.umc.core;

import net.minecraft.resources.ResourceLocation;

public final class EnchantedIds {

    public static final String NAMESPACE = "enchanted";

    private EnchantedIds() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, path);
    }
}
