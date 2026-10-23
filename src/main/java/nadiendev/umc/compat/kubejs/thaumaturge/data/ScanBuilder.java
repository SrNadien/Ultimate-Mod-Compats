package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceLocation;

public class ScanBuilder extends JsonEntryBuilder {

    ScanBuilder(ResourceLocation id, JsonObject json) {
        super(id, ThaumaturgeRegistries.SCAN, json);
    }

    public ScanBuilder key(String key) {
        json.addProperty("key", ThaumaturgeIds.withDefault(key, ThaumaturgeIds.KUBEJS).toString());
        return this;
    }

    public ScanBuilder blocks(String... blocksOrTag) {
        json.add("blocks", holderSet(blocksOrTag));
        return this;
    }

    public ScanBuilder items(String... itemsOrTag) {
        json.add("items", holderSet(itemsOrTag));
        return this;
    }

    public ScanBuilder entities(String... entitiesOrTag) {
        json.add("entities", holderSet(entitiesOrTag));
        return this;
    }

    private static JsonElement holderSet(String[] values) {
        if (values.length == 1 && values[0].startsWith("#")) {
            return new JsonPrimitive("#" + ThaumaturgeIds.withDefault(values[0].substring(1), "minecraft"));
        }
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(ThaumaturgeIds.withDefault(value, "minecraft").toString());
        }
        return array;
    }

    @Override
    @HideFromJS
    protected void validate() {
        if (!json.has("key")) {
            json.addProperty("key", id.toString());
        }
    }
}
