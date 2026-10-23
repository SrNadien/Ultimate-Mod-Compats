package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;

public class ResearchStageBuilder {

    private static final List<String> KNOWLEDGE_TYPES = List.of("observation", "theory");

    private final JsonObject json;

    ResearchStageBuilder(JsonObject json) {
        this.json = json;
    }

    public ResearchStageBuilder text(String langKey) {
        json.addProperty("text", langKey);
        return this;
    }

    public ResearchStageBuilder recipe(String... recipeIds) {
        appendIds("recipes", recipeIds, "minecraft");
        return this;
    }

    public ResearchStageBuilder requiredResearch(String... entries) {
        appendIds("required_research", entries, ThaumaturgeIds.THAUMATURGE);
        return this;
    }

    public ResearchStageBuilder warp(int warp) {
        json.addProperty("warp", warp);
        return this;
    }

    public ResearchStageBuilder knowledge(String type, String category, int amount) {
        ThaumaturgeJson.append(json, "knowledge", knowledgeEntry(type, category, amount));
        return this;
    }

    public ResearchStageBuilder requiredKnowledge(String type, String category, int amount) {
        ThaumaturgeJson.append(json, "required_knowledge", knowledgeEntry(type, category, amount));
        return this;
    }

    public ResearchStageBuilder obtain(String itemsOrTag, int amount) {
        ThaumaturgeJson.append(json, "obtain", requirement(itemsOrTag, amount, null));
        return this;
    }

    public ResearchStageBuilder obtain(Context cx, String itemsOrTag, int amount, Object components) {
        ThaumaturgeJson.append(json, "obtain", requirement(itemsOrTag, amount, ThaumaturgeJson.object(cx, components)));
        return this;
    }

    public ResearchStageBuilder craft(String itemsOrTag, int amount) {
        ThaumaturgeJson.append(json, "craft", requirement(itemsOrTag, amount, null));
        return this;
    }

    public ResearchStageBuilder construct(Context cx, Object construct) {
        json.add("construct", ThaumaturgeJson.object(cx, construct));
        return this;
    }

    public ResearchStageBuilder raw(Context cx, String key, Object value) {
        json.add(key, ThaumaturgeJson.element(cx, value));
        return this;
    }

    @HideFromJS
    JsonObject build() {
        if (!json.has("text")) {
            throw new IllegalStateException("Cada etapa de investigacion necesita text(...)");
        }
        return json;
    }

    private void appendIds(String key, String[] values, String defaultNamespace) {
        for (String value : values) {
            ThaumaturgeJson.append(json, key, new JsonPrimitive(ThaumaturgeIds.withDefault(value, defaultNamespace).toString()));
        }
    }

    private static JsonObject knowledgeEntry(String type, String category, int amount) {
        if (!KNOWLEDGE_TYPES.contains(type)) {
            throw new IllegalArgumentException("Tipo de conocimiento desconocido " + type + ", usa observation o theory");
        }
        JsonObject entry = new JsonObject();
        entry.addProperty("type", type);
        entry.addProperty("category", ThaumaturgeIds.thaumaturgeString(category));
        entry.addProperty("amount", amount);
        return entry;
    }

    private static JsonObject requirement(String itemsOrTag, int amount, JsonObject components) {
        JsonObject entry = new JsonObject();
        entry.addProperty("items", itemsOrTag.startsWith("#") ? itemsOrTag : ThaumaturgeIds.withDefault(itemsOrTag, "minecraft").toString());
        entry.addProperty("amount", amount);
        if (components != null && !components.isEmpty()) {
            entry.add("components", components);
        }
        return entry;
    }
}
