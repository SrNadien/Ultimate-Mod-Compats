package nadiendev.umc.datagen.providers.guide;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class GuideJson {

    private GuideJson() {
    }

    public static JsonObject object() {
        return new JsonObject();
    }

    public static JsonObject object(String key, Object value) {
        JsonObject object = new JsonObject();
        put(object, key, value);
        return object;
    }

    public static JsonObject with(JsonObject object, String key, Object value) {
        put(object, key, value);
        return object;
    }

    public static JsonArray array(Object... values) {
        JsonArray array = new JsonArray();
        for (Object value : values) {
            if (value instanceof JsonElement element) {
                array.add(element);
            } else if (value instanceof String string) {
                array.add(string);
            } else if (value instanceof Number number) {
                array.add(number);
            } else if (value instanceof Boolean bool) {
                array.add(bool);
            }
        }
        return array;
    }

    private static void put(JsonObject object, String key, Object value) {
        if (value instanceof JsonElement element) {
            object.add(key, element);
        } else if (value instanceof String string) {
            object.addProperty(key, string);
        } else if (value instanceof Number number) {
            object.addProperty(key, number);
        } else if (value instanceof Boolean bool) {
            object.addProperty(key, bool);
        }
    }

    public static JsonObject icon(String id) {
        JsonObject icon = new JsonObject();
        icon.addProperty("count", 1);
        icon.addProperty("id", id);
        return icon;
    }
}
