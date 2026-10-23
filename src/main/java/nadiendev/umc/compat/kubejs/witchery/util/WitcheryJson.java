package nadiendev.umc.compat.kubejs.witchery.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.Context;
import java.util.Collection;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;

public final class WitcheryJson {

    private WitcheryJson() {
    }

    public static JsonObject stack(RegistryOps<JsonElement> ops, ItemStack stack) {
        return ItemStack.CODEC.encodeStart(ops, stack).getOrThrow(IllegalArgumentException::new).getAsJsonObject();
    }

    public static JsonArray stacks(RegistryOps<JsonElement> ops, Collection<ItemStack> stacks) {
        JsonArray array = new JsonArray();
        stacks.forEach(stack -> array.add(stack(ops, stack)));
        return array;
    }

    public static JsonObject object(Context cx, Object from) {
        if (from == null) {
            return new JsonObject();
        }
        return JsonUtils.objectOf(cx, from);
    }
}
