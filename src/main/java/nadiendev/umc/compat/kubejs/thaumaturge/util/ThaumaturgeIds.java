package nadiendev.umc.compat.kubejs.thaumaturge.util;

import net.minecraft.resources.ResourceLocation;

public final class ThaumaturgeIds {

    public static final String THAUMATURGE = "thaumaturge";
    public static final String KUBEJS = "kubejs";

    private ThaumaturgeIds() {
    }

    public static ResourceLocation owned(String raw) {
        return withDefault(raw, KUBEJS);
    }

    public static ResourceLocation thaumaturge(String raw) {
        return withDefault(raw, THAUMATURGE);
    }

    public static String thaumaturgeString(String raw) {
        return thaumaturge(raw).toString();
    }

    public static ResourceLocation withDefault(String raw, String namespace) {
        String trimmed = raw.trim();
        return trimmed.indexOf(':') >= 0 ? ResourceLocation.parse(trimmed) : ResourceLocation.fromNamespaceAndPath(namespace, trimmed);
    }

    public static ResourceLocation registryEntry(ResourceLocation id, String registryPath) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), THAUMATURGE + "/" + registryPath + "/" + id.getPath());
    }
}
