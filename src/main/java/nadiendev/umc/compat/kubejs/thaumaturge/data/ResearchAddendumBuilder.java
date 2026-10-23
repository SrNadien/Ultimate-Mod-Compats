package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;

public class ResearchAddendumBuilder {

    private final JsonObject json;

    ResearchAddendumBuilder(JsonObject json) {
        this.json = json;
    }

    public ResearchAddendumBuilder text(String langKey) {
        json.addProperty("text", langKey);
        return this;
    }

    public ResearchAddendumBuilder recipe(String... recipeIds) {
        for (String recipe : recipeIds) {
            ThaumaturgeJson.append(json, "recipes", new JsonPrimitive(ThaumaturgeIds.withDefault(recipe, "minecraft").toString()));
        }
        return this;
    }

    public ResearchAddendumBuilder requiredResearch(String... entries) {
        for (String entry : entries) {
            ThaumaturgeJson.append(json, "required_research", new JsonPrimitive(ThaumaturgeIds.thaumaturgeString(entry)));
        }
        return this;
    }

    @HideFromJS
    JsonObject build() {
        if (!json.has("text")) {
            throw new IllegalStateException("Cada apendice de investigacion necesita text(...)");
        }
        return json;
    }
}
