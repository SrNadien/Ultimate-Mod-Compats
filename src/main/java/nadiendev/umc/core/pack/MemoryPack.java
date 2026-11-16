package nadiendev.umc.core.pack;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.resources.ResourceLocation;

/** A pack that lives in memory: files made when the game starts, for one kind of pack (assets or data). */
public final class MemoryPack extends AbstractPackResources {

    private final PackType type;
    private final Map<String, byte[]> files;

    public MemoryPack(PackLocationInfo location, PackType type, Map<String, byte[]> files) {
        super(location);
        this.type = type;
        this.files = files;
    }

    private static IoSupplier<InputStream> supplier(byte[] bytes) {
        return () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        if (elements.length == 1 && elements[0].equals("pack.mcmeta")) {
            String json = "{\"pack\":{\"pack_format\":" + SharedConstants.getCurrentVersion().getPackVersion(type)
                    + ",\"description\":\"Drawers of the woods of other mods\"}}";
            return supplier(json.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        if (packType != type) {
            return null;
        }
        byte[] bytes = files.get(packType.getDirectory() + "/" + location.getNamespace() + "/" + location.getPath());
        return bytes == null ? null : supplier(bytes);
    }

    @Override
    public void listResources(PackType packType, String namespace, String prefix, ResourceOutput output) {
        if (packType != type) {
            return;
        }
        String start = packType.getDirectory() + "/" + namespace + "/";
        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(start) && key.substring(start.length()).startsWith(prefix)) {
                output.accept(ResourceLocation.fromNamespaceAndPath(namespace, key.substring(start.length())), supplier(entry.getValue()));
            }
        }
    }

    @Override
    public Set<String> getNamespaces(PackType packType) {
        Set<String> namespaces = new HashSet<>();
        if (packType == type) {
            String start = packType.getDirectory() + "/";
            for (String key : files.keySet()) {
                if (key.startsWith(start)) {
                    namespaces.add(key.substring(start.length(), key.indexOf('/', start.length())));
                }
            }
        }
        return namespaces;
    }

    @Override
    public void close() {
    }
}
