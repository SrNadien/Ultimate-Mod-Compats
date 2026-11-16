package nadiendev.umc.core.pack;

import java.util.Map;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Adds a pack made in memory to the game, always on and above the other ones. */
public final class MemoryPacks {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryPacks.class);

    private MemoryPacks() {
    }

    public static void add(AddPackFindersEvent event, String id, String title, PackType type, Map<String, byte[]> files) {
        PackLocationInfo info = new PackLocationInfo(id, Component.literal(title), PackSource.BUILT_IN, Optional.empty());
        Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new MemoryPack(location, type, files);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return new MemoryPack(location, type, files);
            }
        };
        Pack pack = Pack.readMetaAndCreate(info, supplier, type, new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack == null) {
            LOGGER.error("Could not make the pack {}", id);
            return;
        }
        event.addRepositorySource(consumer -> consumer.accept(pack));
    }
}
