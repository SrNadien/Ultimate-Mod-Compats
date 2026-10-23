package nadiendev.umc.compat.kubejs.ae2.kube;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.Context;
import net.minecraft.world.item.ItemStack;

public class Ae2KubeRecipe extends KubeRecipe {

    protected RecipeScriptContext scx(Context cx) {
        return new RecipeScriptContext.Impl(cx, this);
    }

    protected JsonObject objectCopy(RecipeKey<JsonElement> key) {
        JsonElement current = getValue(key);
        return current instanceof JsonObject object ? object.deepCopy() : new JsonObject();
    }

    protected JsonObject stateJson(Context cx, String id, Object properties) {
        JsonObject state = new JsonObject();
        state.addProperty("id", id);
        if (properties != null) {
            JsonElement json = JsonUtils.of(cx, properties);
            if (json instanceof JsonObject object && !object.isEmpty()) {
                state.add("properties", object);
            }
        }
        return state;
    }

    protected JsonElement itemJson(Context cx, Object item) {
        RecipeScriptContext scx = scx(cx);
        ItemStack stack = ItemStackComponent.ITEM_STACK.instance().wrap(scx, item);
        return ItemStack.CODEC.encodeStart(scx.ops().json(), stack).getOrThrow();
    }
}
