package nadiendev.umc.compat.kubejs.excessiveutilities.component;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;

public record NestedComponent<T>(RecipeComponent<T> parentComponent, String objectName) implements RecipeComponentWithParent<T> {

    @Override
    public RecipeComponentType<?> type() {
        return parentComponent.type();
    }

    @Override
    public void writeToJson(KubeRecipe recipe, RecipeComponentValue<T> value, JsonObject json) {
        JsonObject child = json.has(objectName) && json.get(objectName).isJsonObject() ? json.getAsJsonObject(objectName) : new JsonObject();
        parentComponent.writeToJson(recipe, value, child);
        json.add(objectName, child);
    }

    @Override
    public void readFromJson(KubeRecipe recipe, RecipeComponentValue<T> value, JsonObject json) {
        if (json.has(objectName) && json.get(objectName).isJsonObject()) {
            parentComponent.readFromJson(recipe, value, json.getAsJsonObject(objectName));
        }
    }

    @Override
    public String toString() {
        return parentComponent + "@" + objectName;
    }
}
