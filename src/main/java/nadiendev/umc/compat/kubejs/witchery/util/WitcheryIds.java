package nadiendev.umc.compat.kubejs.witchery.util;

import net.minecraft.resources.ResourceLocation;

public final class WitcheryIds {

    public static final String ENCHANTED = "enchanted";
    public static final String KUBEJS = "kubejs";

    private WitcheryIds() {
    }

    public static ResourceLocation owned(String raw) {
        return withDefault(raw, KUBEJS);
    }

    public static ResourceLocation enchanted(String raw) {
        return withDefault(raw, ENCHANTED);
    }

    public static String enchantedString(String raw) {
        return enchanted(raw).toString();
    }

    public static ResourceLocation withDefault(String raw, String namespace) {
        String trimmed = raw.trim();
        return trimmed.indexOf(':') >= 0 ? ResourceLocation.parse(trimmed) : ResourceLocation.fromNamespaceAndPath(namespace, trimmed);
    }

    public static ResourceLocation registryEntry(ResourceLocation id, String registryPath) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), ENCHANTED + "/" + registryPath + "/" + id.getPath());
    }
}
