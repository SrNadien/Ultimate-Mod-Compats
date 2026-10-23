package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class ResearchCategoryBuilder extends JsonEntryBuilder {

    ResearchCategoryBuilder(ResourceLocation id, JsonObject json) {
        super(id, ThaumaturgeRegistries.RESEARCH_CATEGORY, json);
    }

    public ResearchCategoryBuilder icon(String texture) {
        json.addProperty("icon", ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.KUBEJS).toString());
        return this;
    }

    public ResearchCategoryBuilder background(String texture) {
        json.addProperty("background", ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.THAUMATURGE).toString());
        return this;
    }

    public ResearchCategoryBuilder overlayBackground(String texture) {
        json.addProperty("overlay_background", ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.THAUMATURGE).toString());
        return this;
    }

    public ResearchCategoryBuilder requiredResearch(String entry) {
        json.addProperty("required_research", ThaumaturgeIds.thaumaturgeString(entry));
        return this;
    }

    public ResearchCategoryBuilder index(int index) {
        json.addProperty("index", index);
        return this;
    }

    public ResearchCategoryBuilder formula(String... aspects) {
        json.add("formula", ThaumaturgeJson.aspects(aspects));
        return this;
    }

    @Override
    @HideFromJS
    protected void validate() {
        for (String required : List.of("icon", "background")) {
            if (!json.has(required)) {
                throw new IllegalStateException("La categoria de investigacion " + id + " necesita " + required + "(...)");
            }
        }
        if (!json.has("formula")) {
            json.add("formula", new JsonArray());
        }
    }
}
