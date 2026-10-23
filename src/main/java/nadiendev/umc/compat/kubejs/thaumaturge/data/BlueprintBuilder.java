package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class BlueprintBuilder extends JsonEntryBuilder {

    private final List<String[]> layers = new ArrayList<>();
    private final JsonObject keys = new JsonObject();

    BlueprintBuilder(ResourceLocation id, JsonObject json) {
        super(id, ThaumaturgeRegistries.BLUEPRINT, json);
    }

    public BlueprintBuilder layer(String... rows) {
        layers.add(rows);
        return this;
    }

    public BlueprintBuilder key(Context cx, String symbol, Object source, Object target) {
        JsonObject entry = new JsonObject();
        entry.add("source", ThaumaturgeJson.object(cx, source));
        entry.add("target", ThaumaturgeJson.object(cx, target));
        keys.add(single(symbol), entry);
        return this;
    }

    public BlueprintBuilder blockKey(String symbol, String source, String targetBlock) {
        keys.add(single(symbol), entry(source(source), block(targetBlock)));
        return this;
    }

    public BlueprintBuilder blockKey(String symbol, String source) {
        return keepKey(symbol, source);
    }

    public BlueprintBuilder keepKey(String symbol, String source) {
        JsonObject target = new JsonObject();
        target.addProperty("type", "keep");
        keys.add(single(symbol), entry(source(source), target));
        return this;
    }

    public BlueprintBuilder airKey(String symbol, String source) {
        JsonObject target = new JsonObject();
        target.addProperty("type", "air");
        keys.add(single(symbol), entry(source(source), target));
        return this;
    }

    @Override
    @HideFromJS
    protected void validate() {
        if (layers.isEmpty()) {
            throw new IllegalStateException("El plano " + id + " necesita al menos una capa: layer(...)");
        }
        int depth = layers.getFirst().length;
        if (depth == 0 || layers.getFirst()[0].isEmpty()) {
            throw new IllegalStateException("Las capas del plano " + id + " no pueden estar vacias");
        }
        int width = layers.getFirst()[0].length();
        Set<Character> used = new HashSet<>();
        JsonArray pattern = new JsonArray();
        for (String[] layer : layers) {
            if (layer.length != depth) {
                throw new IllegalStateException("Todas las capas del plano " + id + " deben tener las mismas filas");
            }
            JsonArray rows = new JsonArray();
            for (String row : layer) {
                if (row.length() != width) {
                    throw new IllegalStateException("Todas las filas del plano " + id + " deben medir lo mismo");
                }
                for (char symbol : row.toCharArray()) {
                    if (symbol != ' ') {
                        used.add(symbol);
                    }
                }
                rows.add(row);
            }
            pattern.add(rows);
        }
        for (char symbol : used) {
            if (!keys.has(String.valueOf(symbol))) {
                throw new IllegalStateException("El plano " + id + " usa el simbolo '" + symbol + "' sin definir su clave (blockKey/keepKey/airKey)");
            }
        }
        json.add("keys", keys);
        json.add("pattern", pattern);
        json.addProperty("x_size", width);
        json.addProperty("y_size", layers.size());
        json.addProperty("z_size", depth);
    }

    private static String single(String symbol) {
        if (symbol.length() != 1 || symbol.charAt(0) == ' ') {
            throw new IllegalArgumentException("El simbolo del plano debe ser un solo caracter distinto del espacio: '" + symbol + "'");
        }
        return symbol;
    }

    private static JsonObject source(String raw) {
        String text = raw.trim();
        if (text.startsWith("#")) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "tag");
            json.addProperty("tag", ThaumaturgeIds.withDefault(text.substring(1), "minecraft").toString());
            return json;
        }
        return block(text);
    }

    private static JsonObject block(String raw) {
        ResourceLocation blockId = ThaumaturgeIds.withDefault(raw, "minecraft");
        if (!BuiltInRegistries.BLOCK.containsKey(blockId)) {
            throw new IllegalArgumentException("El bloque " + blockId + " no existe, revisa el id en el plano");
        }
        JsonObject json = new JsonObject();
        json.addProperty("type", "block");
        json.addProperty("block", blockId.toString());
        return json;
    }

    private static JsonObject entry(JsonObject source, JsonObject target) {
        JsonObject entry = new JsonObject();
        entry.add("source", source);
        entry.add("target", target);
        return entry;
    }
}
