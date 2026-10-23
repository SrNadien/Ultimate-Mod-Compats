package nadiendev.umc.compat.kubejs.generatorgalore.fuel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.Context;
import net.minecraft.world.item.crafting.Ingredient;

public final class FuelJson {

    private FuelJson() {
    }

    public static JsonElement ingredient(Context cx, Object value) {
        if (value instanceof Ingredient ingredient) {
            return Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow();
        }
        if (value instanceof CharSequence text) {
            return reference(text.toString().trim(), "item");
        }
        return JsonUtils.of(cx, value);
    }

    public static JsonElement fluid(Context cx, Object value) {
        if (value instanceof CharSequence text) {
            return reference(text.toString().trim(), "fluid");
        }
        return JsonUtils.of(cx, value);
    }

    private static JsonObject reference(String text, String field) {
        JsonObject object = new JsonObject();
        if (text.startsWith("#")) {
            object.addProperty("tag", text.substring(1));
        } else {
            object.addProperty(field, text);
        }
        return object;
    }
}
