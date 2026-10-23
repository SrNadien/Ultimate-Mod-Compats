package nadiendev.umc.compat.kubejs.create.kube;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.util.KubeResourceLocation;
import dev.latvian.mods.rhino.Context;
import nadiendev.umc.compat.kubejs.create.entity.ConversionEntityRegistry;
import nadiendev.umc.compat.kubejs.create.schema.CreateSchemas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ConversionKubeRecipe extends KubeRecipe {

    private static final String HAUNTING = "create:haunting";
    private static final String SUFFIX = "_haunting";

    public KubeRecipe entity(String entity) {
        ConversionEntityRegistry.add(ResourceLocation.parse(entity),
                getValue(CreateSchemas.CONVERSION_INGREDIENTS).get(0),
                getValue(CreateSchemas.CONVERSION_RESULTS).get(0).copy());
        return this;
    }

    public KubeRecipe haunting(Context cx) {
        RecipeScriptContext scx = new RecipeScriptContext.Impl(cx, this);
        JsonArray ingredients = new JsonArray();
        for (Ingredient ingredient : getValue(CreateSchemas.CONVERSION_INGREDIENTS)) {
            ingredients.add(Ingredient.CODEC_NONEMPTY.encodeStart(scx.ops().json(), ingredient).getOrThrow());
        }
        JsonArray results = new JsonArray();
        for (ItemStack result : getValue(CreateSchemas.CONVERSION_RESULTS)) {
            results.add(ItemStack.CODEC.encodeStart(scx.ops().json(), result).getOrThrow());
        }
        JsonObject json = new JsonObject();
        json.addProperty("type", HAUNTING);
        json.add("ingredients", ingredients);
        json.add("results", results);
        KubeRecipe twin = type.event.custom(cx, json);
        ResourceLocation id = getOrCreateId();
        twin.id(new KubeResourceLocation(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + SUFFIX)));
        return this;
    }
}
