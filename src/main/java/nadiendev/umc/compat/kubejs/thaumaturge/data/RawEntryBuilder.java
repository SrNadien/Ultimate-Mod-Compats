package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.Context;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class RawEntryBuilder extends JsonEntryBuilder {

    RawEntryBuilder(ResourceLocation id, String registryPath, JsonObject json) {
        super(id, registryPath, json);
    }

    public RawEntryBuilder set(Context cx, String key, Object value) {
        json.add(key, ThaumaturgeJson.element(cx, value));
        return this;
    }

    public RawEntryBuilder remove(String key) {
        json.remove(key);
        return this;
    }

    public RawEntryBuilder replaceWith(Context cx, Object value) {
        JsonObject replacement = ThaumaturgeJson.object(cx, value);
        json.keySet().stream().toList().forEach(json::remove);
        replacement.entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        return this;
    }
}
