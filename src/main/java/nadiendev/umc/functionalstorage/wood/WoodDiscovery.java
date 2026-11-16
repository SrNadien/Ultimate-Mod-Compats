package nadiendev.umc.functionalstorage.wood;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds the woods of the other mods by looking at the files of the installed mods, before any block exists: a wood is a
 * {@code <name>_planks} block that has a {@code <name>_log} (or {@code _stem}) next to it and textures of its own.
 */
public final class WoodDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(WoodDiscovery.class);
    private static final Set<String> SKIPPED_NAMESPACES = Set.of("minecraft", "neoforge", "c", "forge", "functionalstorage", "umc");
    private static final String[] LOG_SUFFIXES = {"_log", "_stem", "_stalk"};
    private static final String[] PLANKS_KEYS = {"all", "texture", "side", "top"};
    private static final String[] LOG_SIDE_KEYS = {"side", "all", "texture", "bark"};
    private static final String[] LOG_END_KEYS = {"end", "top", "up"};

    /** A texture of a mod: its id for the models and its file to read the color from. */
    private record Texture(String id, Path path) {
    }

    /**
     * One wood of another mod.
     *
     * @param name          unique name used for the drawers, namespace and wood joined ("biomesoplenty_fir")
     * @param planksId      id of the planks block
     * @param logId         id of the log block
     * @param pretty        display name ("Fir")
     * @param planks        file of the texture of the planks
     * @param log           file of the texture of the side of the log
     * @param planksTexture id of the texture of the planks, which the front of the drawer uses
     * @param logSideTexture id of the texture of the side of the log, which the box and the dividers of the drawer use
     * @param logEndTexture id of the texture of the end of the log, which the top and the bottom of the drawer use
     */
    public record Wood(String name, String planksId, String logId, String pretty, Path planks, Path log,
                       String planksTexture, String logSideTexture, String logEndTexture) {
    }

    private WoodDiscovery() {
    }

    /** The woods of vanilla: the name, the planks and the log. */
    private static final String[][] VANILLA = {
            {"oak", "oak_planks", "oak_log"}, {"spruce", "spruce_planks", "spruce_log"}, {"birch", "birch_planks", "birch_log"},
            {"jungle", "jungle_planks", "jungle_log"}, {"acacia", "acacia_planks", "acacia_log"}, {"dark_oak", "dark_oak_planks", "dark_oak_log"},
            {"mangrove", "mangrove_planks", "mangrove_log"}, {"cherry", "cherry_planks", "cherry_log"},
            {"crimson", "crimson_planks", "crimson_stem"}, {"warped", "warped_planks", "warped_stem"}};

    /**
     * The woods of vanilla, which get drawers of their own with the same look as the ones of the other mods. They are named
     * {@code minecraft_oak} and so on, so they do not clash with the drawers of Functional Storage.
     */
    public static List<Wood> vanilla() {
        IModFileInfo info = ModList.get().getModFileById("minecraft");
        if (info == null) {
            LOGGER.warn("The files of Minecraft are not there, the drawers of the woods of vanilla are not made");
            return List.of();
        }
        Path textures = info.getFile().findResource("assets", "minecraft", "textures", "block");
        List<Wood> woods = new ArrayList<>();
        for (String[] wood : VANILLA) {
            Path planks = textures.resolve(wood[1] + ".png");
            Path log = textures.resolve(wood[2] + ".png");
            if (!Files.isRegularFile(planks) || !Files.isRegularFile(log)) {
                LOGGER.warn("Missing textures for the wood {} of vanilla", wood[0]);
                continue;
            }
            woods.add(new Wood("minecraft_" + wood[0], "minecraft:" + wood[1], "minecraft:" + wood[2], pretty(wood[0]), planks, log,
                    "minecraft:block/" + wood[1], "minecraft:block/" + wood[2], "minecraft:block/" + wood[2] + "_top"));
        }
        return woods;
    }

    public static List<Wood> discover() {
        Map<String, Wood> woods = new LinkedHashMap<>();
        for (IModInfo mod : ModList.get().getMods()) {
            IModFileInfo fileInfo = mod.getOwningFile();
            if (fileInfo == null) {
                continue;
            }
            Path assets = fileInfo.getFile().findResource("assets");
            if (!Files.isDirectory(assets)) {
                continue;
            }
            try (Stream<Path> namespaces = Files.list(assets)) {
                for (Path namespacePath : namespaces.toList()) {
                    String namespace = namespacePath.getFileName().toString().replace("/", "");
                    if (SKIPPED_NAMESPACES.contains(namespace)) {
                        continue;
                    }
                    scan(namespace, namespacePath, woods);
                }
            } catch (IOException | RuntimeException exception) {
                LOGGER.warn("Could not look for woods in {}", mod.getModId(), exception);
            }
        }
        LOGGER.info("Found {} woods of other mods for the Functional Storage drawers: {}", woods.size(), woods.keySet());
        return new ArrayList<>(woods.values());
    }

    private static void scan(String namespace, Path namespacePath, Map<String, Wood> woods) throws IOException {
        Path blockstates = namespacePath.resolve("blockstates");
        if (!Files.isDirectory(blockstates)) {
            return;
        }
        try (Stream<Path> files = Files.list(blockstates)) {
            for (Path file : files.toList()) {
                String fileName = file.getFileName().toString().replace("/", "");
                if (!fileName.endsWith("_planks.json")) {
                    continue;
                }
                String stem = fileName.substring(0, fileName.length() - "_planks.json".length());
                String logName = null;
                for (String suffix : LOG_SUFFIXES) {
                    if (Files.exists(blockstates.resolve(stem + suffix + ".json"))) {
                        logName = stem + suffix;
                        break;
                    }
                }
                String name = (namespace + "_" + stem).toLowerCase(Locale.ROOT);
                if (logName == null || woods.containsKey(name) || !name.matches("[a-z0-9_]+")) {
                    continue;
                }
                Texture planks = texture(namespace, namespacePath, stem + "_planks", PLANKS_KEYS);
                Texture log = texture(namespace, namespacePath, logName, LOG_SIDE_KEYS);
                if (planks == null || log == null) {
                    continue;
                }
                Texture end = texture(namespace, namespacePath, logName, LOG_END_KEYS);
                woods.put(name, new Wood(name, namespace + ":" + stem + "_planks", namespace + ":" + logName, pretty(stem),
                        planks.path(), log.path(), planks.id(), log.id(), (end != null ? end : log).id()));
            }
        }
    }

    /** The texture a block uses for one of the keys of its model; falls back to the texture named like the block. */
    private static Texture texture(String namespace, Path namespacePath, String block, String[] keys) {
        Path model = namespacePath.resolve("models").resolve("block").resolve(block + ".json");
        if (Files.isRegularFile(model)) {
            try (Reader reader = Files.newBufferedReader(model)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (json.has("textures") && json.get("textures").isJsonObject()) {
                    Texture texture = resolve(namespace, namespacePath, pick(json.getAsJsonObject("textures"), keys));
                    if (texture != null) {
                        return texture;
                    }
                }
            } catch (IOException | RuntimeException ignored) {
                // falls back to the name of the block
            }
        }
        if (keys == LOG_END_KEYS) {
            return null;
        }
        Path guess = namespacePath.resolve("textures").resolve("block").resolve(block + ".png");
        return Files.isRegularFile(guess) ? new Texture(namespace + ":block/" + block, guess) : null;
    }

    private static String pick(JsonObject textures, String[] keys) {
        for (String key : keys) {
            if (textures.has(key) && textures.get(key).isJsonPrimitive()) {
                return textures.get(key).getAsString();
            }
        }
        if (keys == LOG_END_KEYS) {
            return null;
        }
        for (Map.Entry<String, JsonElement> entry : textures.entrySet()) {
            if (!entry.getKey().equals("particle") && !entry.getKey().equals("end") && entry.getValue().isJsonPrimitive()) {
                return entry.getValue().getAsString();
            }
        }
        return null;
    }

    /** A reference like {@code namespace:block/name}, only when its texture is in the same mod. */
    private static Texture resolve(String own, Path namespacePath, String reference) {
        if (reference == null || reference.startsWith("#")) {
            return null;
        }
        String namespace = reference.contains(":") ? reference.substring(0, reference.indexOf(':')) : "minecraft";
        String path = reference.contains(":") ? reference.substring(reference.indexOf(':') + 1) : reference;
        if (!namespace.equals(own)) {
            return null;
        }
        Path texture = namespacePath.resolve("textures").resolve(path + ".png");
        return Files.isRegularFile(texture) ? new Texture(namespace + ":" + path, texture) : null;
    }

    private static String pretty(String stem) {
        StringBuilder builder = new StringBuilder();
        for (String word : stem.split("_")) {
            if (word.isEmpty()) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return builder.toString();
    }
}
