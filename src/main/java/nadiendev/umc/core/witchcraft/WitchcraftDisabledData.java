package nadiendev.umc.core.witchcraft;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.pack.MemoryPacks;
import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Turns off the data of the witchcraft that is loaded by the game itself, when Enchanted is not installed and so the
 * blocks, entities and codecs those files talk about do not exist: the dimension, the biomes, the features and the biome
 * modifiers of the spirit world and the creatures. A world could not be loaded with them. Each one is replaced by a file
 * with a condition that is never true, which makes the game skip it.
 */
public final class WitchcraftDisabledData {

    private static final Logger LOGGER = LoggerFactory.getLogger(WitchcraftDisabledData.class);
    /** The folders of the data of this mod that are entries of the registries of the game. */
    private static final String[] REGISTRY_FOLDERS = {"dimension", "dimension_type", "worldgen", "neoforge"};
    private static final byte[] OFF = "{\"neoforge:conditions\":[{\"type\":\"neoforge:false\"}]}".getBytes(StandardCharsets.UTF_8);

    private WitchcraftDisabledData() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(WitchcraftDisabledData::addPack);
    }

    private static void addPack(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        Map<String, byte[]> files = new HashMap<>();
        Path root = ModList.get().getModFileById(UltimateModCompats.MODID).getFile().findResource("data", UltimateModCompats.MODID);
        for (String folder : REGISTRY_FOLDERS) {
            Path base = root.resolve(folder);
            if (!Files.isDirectory(base)) {
                continue;
            }
            try (Stream<Path> walk = Files.walk(base)) {
                walk.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json"))
                        .forEach(path -> files.put("data/" + UltimateModCompats.MODID + "/" + folder + "/" + base.relativize(path).toString().replace('\\', '/'), OFF));
            } catch (IOException exception) {
                LOGGER.error("Could not list the data of the witchcraft in {}", folder, exception);
            }
        }
        LOGGER.info("Enchanted is not installed: {} files of the witchcraft are turned off", files.size());
        MemoryPacks.add(event, "umc_witchcraft_off", "Ultimate Mod Compats: witchcraft off", PackType.SERVER_DATA, files);
    }
}
