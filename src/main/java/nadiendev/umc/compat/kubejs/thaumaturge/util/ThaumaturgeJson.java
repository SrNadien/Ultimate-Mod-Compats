package nadiendev.umc.compat.kubejs.thaumaturge.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.Context;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;

public final class ThaumaturgeJson {

    private static final Pattern AMOUNT_PREFIX = Pattern.compile("^(\\d+)\\s*x\\s+(.+)$");

    private ThaumaturgeJson() {
    }

    public static JsonObject object(Context cx, Object from) {
        if (from == null) {
            return new JsonObject();
        }
        return JsonUtils.objectOf(cx, from);
    }

    public static JsonElement element(Context cx, Object from) {
        return JsonUtils.of(cx, from);
    }

    public static JsonObject aspect(String raw) {
        String text = raw.trim();
        int amount = 1;
        Matcher matcher = AMOUNT_PREFIX.matcher(text);
        if (matcher.matches()) {
            amount = Integer.parseInt(matcher.group(1));
            text = matcher.group(2);
        }
        JsonObject json = new JsonObject();
        json.addProperty("aspect", ThaumaturgeIds.thaumaturgeString(text));
        json.addProperty("amount", amount);
        return json;
    }

    public static JsonArray aspects(String[] raw) {
        JsonArray array = new JsonArray();
        for (String entry : raw) {
            array.add(aspect(entry));
        }
        return array;
    }

    public static JsonArray strings(String[] values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    public static JsonArray ids(String[] values, String defaultNamespace) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(ThaumaturgeIds.withDefault(value, defaultNamespace).toString());
        }
        return array;
    }

    public static void append(JsonObject json, String key, JsonElement value) {
        JsonArray array = json.has(key) ? json.getAsJsonArray(key) : new JsonArray();
        array.add(value);
        json.add(key, array);
    }

    public static JsonObject readResource(ResourceLocation id, String directory) {
        IModFileInfo info = ModList.get().getModFileById(id.getNamespace());
        if (info == null) {
            return null;
        }
        List<String> parts = new ArrayList<>(List.of("data", id.getNamespace()));
        parts.addAll(List.of(directory.split("/")));
        parts.add(id.getPath() + ".json");
        Path file = info.getFile().findResource(parts.toArray(new String[0]));
        if (!Files.isRegularFile(file)) {
            return null;
        }
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo leer " + file, ex);
        }
    }
}
