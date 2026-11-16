package nadiendev.umc.functionalstorage.wood;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.server.packs.PackType;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The files of the drawers of the woods. Each drawer is the cherry drawer of Functional Storage with its textures
 * recolored to the planks and the log of the wood: the blockstate and the models are the ones of the cherry drawer with
 * the new names.
 */
final class WoodDrawerContent {

    private static final Logger LOGGER = LoggerFactory.getLogger(WoodDrawerContent.class);
    private static final String FS = "functionalstorage";
    static final int[] SLOTS = {1, 2, 4};
    private static final Map<String, String> LANGUAGES = Map.of(
            "en_us", "%s Drawer (%s)",
            "es_es", "Cajón de %s (%s)",
            "es_ar", "Cajón de %s (%s)",
            "es_mx", "Cajón de %s (%s)",
            "es_cl", "Cajón de %s (%s)",
            "es_uy", "Cajón de %s (%s)",
            "es_ve", "Cajón de %s (%s)");
    private static final String[] SIZES = {"1x1", "1x2", "-", "2x2"};

    private WoodDrawerContent() {
    }

    static Map<String, byte[]> build(List<WoodDiscovery.Wood> woods, PackType type) {
        Map<String, byte[]> files = new HashMap<>();
        if (type == PackType.CLIENT_RESOURCES) {
            assets(woods, files);
        } else {
            data(woods, files);
        }
        return files;
    }

    private static void assets(List<WoodDiscovery.Wood> woods, Map<String, byte[]> files) {
        Map<Integer, String> blockstates = new HashMap<>();
        Map<Integer, byte[]> fronts = new HashMap<>();
        Map<Integer, JsonObject> dividers = new HashMap<>();
        JsonObject frontModel;
        JsonObject sideModel;
        try {
            for (int slots : SLOTS) {
                blockstates.put(slots, new String(readFs("assets", FS, "blockstates", "cherry_" + slots + ".json"), StandardCharsets.UTF_8));
                fronts.put(slots, readFs("assets", FS, "textures", "block", "cherry_front_" + slots + ".png"));
                if (slots > 1) {
                    dividers.put(slots, model("divider_" + slots));
                }
            }
            frontModel = model("front");
            sideModel = model("side");
        } catch (IOException | RuntimeException exception) {
            LOGGER.error("Could not read the cherry drawer of Functional Storage, the drawers of the woods have no assets", exception);
            return;
        }
        Map<String, JsonObject> lang = new HashMap<>();
        LANGUAGES.keySet().forEach(code -> lang.put(code, new JsonObject()));
        for (WoodDiscovery.Wood wood : woods) {
            int plank;
            int log;
            try {
                plank = average(wood.planks());
                log = average(wood.log());
            } catch (IOException | RuntimeException exception) {
                LOGGER.warn("Could not read the textures of the wood {}", wood.name(), exception);
                continue;
            }
            String base = "assets/" + FS + "/";
            for (int slots : SLOTS) {
                String name = wood.name() + "_" + slots;
                put(files, base + "blockstates/" + name + ".json", blockstates.get(slots).replace("block/cherry_" + slots, "block/" + name));
                put(files, base + "textures/block/" + wood.name() + "_front_" + slots + ".png", recolor(fronts.get(slots), plank, log));
                put(files, base + "models/block/" + name + ".json", blockModel(frontModel, sideModel, dividers.get(slots), wood));
                put(files, base + "models/item/" + name + ".json", "{\"parent\":\"minecraft:builtin/entity\"}");
                for (Map.Entry<String, String> entry : LANGUAGES.entrySet()) {
                    lang.get(entry.getKey()).addProperty("block." + FS + "." + name, entry.getValue().formatted(entry.getKey().startsWith("es_") ? spanish(wood) : wood.pretty(), SIZES[slots - 1]));
                }
            }
        }
        lang.forEach((code, json) -> put(files, base() + "lang/" + code + ".json", json.toString()));
    }

    /** The names of the woods of vanilla in Spanish; the other ones keep the name of their files. */
    private static String spanish(WoodDiscovery.Wood wood) {
        return switch (wood.name()) {
            case "minecraft_oak" -> "roble";
            case "minecraft_spruce" -> "abeto";
            case "minecraft_birch" -> "abedul";
            case "minecraft_jungle" -> "jungla";
            case "minecraft_acacia" -> "acacia";
            case "minecraft_dark_oak" -> "roble oscuro";
            case "minecraft_mangrove" -> "manglar";
            case "minecraft_cherry" -> "cerezo";
            case "minecraft_crimson" -> "carmesí";
            case "minecraft_warped" -> "deformado";
            default -> wood.pretty();
        };
    }

    private static String base() {
        return "assets/" + FS + "/";
    }

    private static JsonObject model(String name) throws IOException {
        return JsonParser.parseString(new String(readFs("assets", FS, "models", "block", name + ".json"), StandardCharsets.UTF_8)).getAsJsonObject();
    }

    /**
     * The model of the framed drawers of Functional Storage with the textures of the wood, which is what a framed drawer
     * looks like with the planks as its front and the log as its dividers and its sides: the front is one plate of
     * planks, and the box and the dividers are the log, with the end of the log on the top and the bottom and the bark
     * on the other faces. The pieces are the ones of the front, the side and the dividers of the framed drawer.
     */
    private static String blockModel(JsonObject front, JsonObject side, JsonObject divider, WoodDiscovery.Wood wood) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:block/block");
        JsonArray elements = new JsonArray();
        addElements(elements, front, "#planks", "#planks");
        addElements(elements, side, "#bark", "#end");
        if (divider != null) {
            addElements(elements, divider, "#bark", "#end");
        }
        model.add("elements", elements);
        JsonObject textures = new JsonObject();
        textures.addProperty("particle", wood.logSideTexture());
        textures.addProperty("planks", wood.planksTexture());
        textures.addProperty("bark", wood.logSideTexture());
        textures.addProperty("end", wood.logEndTexture());
        model.add("textures", textures);
        return model.toString();
    }

    /** Copies the pieces of a model with the texture of each face replaced: the one of the top and the bottom, and the one of the others. */
    private static void addElements(JsonArray into, JsonObject from, String sides, String ends) {
        for (var element : from.getAsJsonArray("elements")) {
            JsonObject copy = element.getAsJsonObject().deepCopy();
            JsonObject faces = copy.getAsJsonObject("faces");
            for (String direction : faces.keySet()) {
                boolean end = direction.equals("up") || direction.equals("down");
                faces.getAsJsonObject(direction).addProperty("texture", end ? ends : sides);
            }
            into.add(copy);
        }
    }

    private static void data(List<WoodDiscovery.Wood> woods, Map<String, byte[]> files) {
        JsonArray all = new JsonArray();
        Map<Integer, JsonArray> bySize = new HashMap<>();
        JsonArray blocks = new JsonArray();
        for (int slots : SLOTS) {
            bySize.put(slots, new JsonArray());
        }
        for (WoodDiscovery.Wood wood : woods) {
            for (int slots : SLOTS) {
                String name = wood.name() + "_" + slots;
                String id = FS + ":" + name;
                all.add(id);
                bySize.get(slots).add(id);
                blocks.add(id);
                put(files, "data/" + FS + "/recipe/" + name + ".json", recipe(wood, slots, id));
                put(files, "data/" + FS + "/loot_table/blocks/" + name + ".json",
                        "{\"type\":\"minecraft:block\",\"random_sequence\":\"" + FS + ":blocks/" + name + "\"}");
            }
        }
        put(files, "data/" + FS + "/tags/item/drawer.json", tag(all));
        put(files, "data/" + FS + "/tags/item/drawer_1x1.json", tag(bySize.get(1)));
        put(files, "data/" + FS + "/tags/item/drawer_1x2.json", tag(bySize.get(2)));
        put(files, "data/" + FS + "/tags/item/drawer_2x2.json", tag(bySize.get(4)));
        put(files, "data/minecraft/tags/block/mineable/axe.json", tag(blocks));
    }

    private static String tag(JsonArray values) {
        JsonObject tag = new JsonObject();
        tag.addProperty("replace", false);
        tag.add("values", values);
        return tag.toString();
    }

    /** The same recipes as the drawers of the woods of Functional Storage: planks around a wooden chest. */
    private static String recipe(WoodDiscovery.Wood wood, int slots, String result) {
        JsonObject recipe = new JsonObject();
        JsonArray conditions = new JsonArray();
        JsonObject exists = new JsonObject();
        exists.addProperty("type", "neoforge:item_exists");
        exists.addProperty("item", wood.planksId());
        conditions.add(exists);
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        JsonObject key = new JsonObject();
        JsonObject planks = new JsonObject();
        planks.addProperty("item", wood.planksId());
        key.add("P", planks);
        JsonObject chests = new JsonObject();
        chests.addProperty("tag", "c:chests/wooden");
        key.add("C", chests);
        recipe.add("key", key);
        JsonArray pattern = new JsonArray();
        String[] rows = switch (slots) {
            case 1 -> new String[]{"PPP", "PCP", "PPP"};
            case 2 -> new String[]{"PCP", "PPP", "PCP"};
            default -> new String[]{"CPC", "PPP", "CPC"};
        };
        for (String row : rows) {
            pattern.add(row);
        }
        recipe.add("pattern", pattern);
        JsonObject output = new JsonObject();
        output.addProperty("count", slots);
        output.addProperty("id", result);
        recipe.add("result", output);
        return recipe.toString();
    }

    private static void put(Map<String, byte[]> files, String path, String text) {
        files.put(path, text.getBytes(StandardCharsets.UTF_8));
    }

    private static void put(Map<String, byte[]> files, String path, byte[] bytes) {
        files.put(path, bytes);
    }

    /** A file of Functional Storage, from the jar of the mod. */
    private static byte[] readFs(String... parts) throws IOException {
        Path root = ModList.get().getModFileById(FS).getFile().findResource(parts);
        return Files.readAllBytes(root);
    }

    /** The average color of the visible pixels of a texture. */
    private static int average(Path texture) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(texture)));
        long red = 0;
        long green = 0;
        long blue = 0;
        long count = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                if ((argb >>> 24) > 0) {
                    red += argb >> 16 & 255;
                    green += argb >> 8 & 255;
                    blue += argb & 255;
                    count++;
                }
            }
        }
        if (count == 0) {
            return 0x808080;
        }
        return (int) (red / count) << 16 | (int) (green / count) << 8 | (int) (blue / count);
    }

    /**
     * Recolors a texture of the cherry drawer in two tones: the light parts (the planks of the panel) get the color of
     * the planks and the dark parts (the border of log) the color of the log, each keeping its own light and dark. The
     * grey nails of the corners stay as they are. The two parts are told apart at the threshold that best splits the
     * light from the dark.
     */
    private static byte[] recolor(byte[] png, int plankColor, int logColor) {
        try {
            BufferedImage source = ImageIO.read(new ByteArrayInputStream(png));
            int width = source.getWidth();
            int height = source.getHeight();
            int[] histogram = new int[256];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = source.getRGB(x, y);
                    if ((argb >>> 24) > 0 && !isNail(argb)) {
                        histogram[(int) Math.round(luminance(argb))]++;
                    }
                }
            }
            int threshold = otsu(histogram);
            double plankSum = 0;
            int plankCount = 0;
            double logSum = 0;
            int logCount = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = source.getRGB(x, y);
                    if ((argb >>> 24) == 0 || isNail(argb)) {
                        continue;
                    }
                    double light = luminance(argb);
                    if (light > threshold) {
                        plankSum += light;
                        plankCount++;
                    } else {
                        logSum += light;
                        logCount++;
                    }
                }
            }
            double plankMean = Math.max(1, plankSum / Math.max(1, plankCount));
            double logMean = Math.max(1, logSum / Math.max(1, logCount));
            BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = source.getRGB(x, y);
                    int alpha = argb >>> 24;
                    if (alpha == 0) {
                        continue;
                    }
                    if (isNail(argb)) {
                        out.setRGB(x, y, argb);
                        continue;
                    }
                    double light = luminance(argb);
                    boolean plank = light > threshold;
                    double factor = light / (plank ? plankMean : logMean);
                    int color = plank ? plankColor : logColor;
                    int red = clamp((color >> 16 & 255) * factor);
                    int green = clamp((color >> 8 & 255) * factor);
                    int blue = clamp((color & 255) * factor);
                    out.setRGB(x, y, alpha << 24 | red << 16 | green << 8 | blue);
                }
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(out, "png", bytes);
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /** The nails of the corners of the drawers: grey, with no color of their own. */
    private static boolean isNail(int argb) {
        int red = argb >> 16 & 255;
        int green = argb >> 8 & 255;
        int blue = argb & 255;
        double light = luminance(argb);
        return Math.max(red, Math.max(green, blue)) - Math.min(red, Math.min(green, blue)) < 18 && light > 90 && light < 240;
    }

    /** The threshold that splits a histogram of lights into two groups with the least spread inside each (Otsu). */
    private static int otsu(int[] histogram) {
        long total = 0;
        double sum = 0;
        for (int i = 0; i < histogram.length; i++) {
            total += histogram[i];
            sum += (double) i * histogram[i];
        }
        double backgroundSum = 0;
        long background = 0;
        double best = -1;
        int threshold = 127;
        for (int i = 0; i < histogram.length; i++) {
            background += histogram[i];
            if (background == 0 || background == total) {
                continue;
            }
            backgroundSum += (double) i * histogram[i];
            double meanBackground = backgroundSum / background;
            double meanForeground = (sum - backgroundSum) / (total - background);
            double between = (double) background * (total - background) * (meanBackground - meanForeground) * (meanBackground - meanForeground);
            if (between > best) {
                best = between;
                threshold = i;
            }
        }
        return threshold;
    }

    private static double luminance(int argb) {
        return 0.299 * (argb >> 16 & 255) + 0.587 * (argb >> 8 & 255) + 0.114 * (argb & 255);
    }

    private static int clamp(double value) {
        return (int) Math.max(0, Math.min(255, Math.round(value)));
    }
}
