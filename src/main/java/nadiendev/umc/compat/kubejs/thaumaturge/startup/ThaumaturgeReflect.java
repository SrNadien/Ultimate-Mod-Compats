package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class ThaumaturgeReflect {

    public static final String API_PACKAGE = "com.leclowndu93150.thaumaturge.api.";

    private ThaumaturgeReflect() {
    }

    public static Class<?> type(String simpleName) {
        try {
            return Class.forName(API_PACKAGE + simpleName);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Thaumaturge no expone la clase " + API_PACKAGE + simpleName, ex);
        }
    }

    public static ResourceKey<Registry<Object>> registryKey(String path) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("thaumaturge", path));
    }

    public static ResourceKey<Object> elementKey(String registryPath, ResourceLocation id) {
        return ResourceKey.create(registryKey(registryPath), id);
    }

    public static Object newInstance(Class<?> type, List<Class<?>> parameterTypes, Object... arguments) {
        try {
            return type.getConstructor(parameterTypes.toArray(new Class<?>[0])).newInstance(arguments);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("No se pudo construir " + type.getName(), ex);
        }
    }
}
