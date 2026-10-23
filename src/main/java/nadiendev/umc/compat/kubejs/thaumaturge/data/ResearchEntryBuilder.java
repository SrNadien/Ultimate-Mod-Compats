package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import java.util.function.Consumer;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class ResearchEntryBuilder extends JsonEntryBuilder {

    private static final List<String> META = List.of("round", "spiky", "reverse", "hidden", "autounlock", "hex");
    private static final int MIN_COMPLEXITY = 1;
    private static final int MAX_COMPLEXITY = 3;

    ResearchEntryBuilder(ResourceLocation id, JsonObject json) {
        super(id, ThaumaturgeRegistries.RESEARCH_ENTRY, json);
    }

    public ResearchEntryBuilder category(String category) {
        json.addProperty("category", ThaumaturgeIds.thaumaturgeString(category));
        return this;
    }

    public ResearchEntryBuilder name(String langKey) {
        json.addProperty("name", langKey);
        return this;
    }

    public ResearchEntryBuilder position(int column, int row) {
        json.addProperty("column", column);
        json.addProperty("row", row);
        return this;
    }

    public ResearchEntryBuilder parent(String... parents) {
        for (String parent : parents) {
            String body = parent.startsWith("~") ? parent.substring(1) : parent;
            String prefix = parent.startsWith("~") ? "~" : "";
            ThaumaturgeJson.append(json, "parents", new JsonPrimitive(prefix + withStageSuffix(body)));
        }
        return this;
    }

    public ResearchEntryBuilder clearParents() {
        json.remove("parents");
        return this;
    }

    public ResearchEntryBuilder sibling(String... siblings) {
        for (String sibling : siblings) {
            ThaumaturgeJson.append(json, "siblings", new JsonPrimitive(ThaumaturgeIds.thaumaturgeString(sibling)));
        }
        return this;
    }

    public ResearchEntryBuilder icon(String... icons) {
        for (String icon : icons) {
            boolean focus = icon.startsWith("focus:");
            String body = focus ? icon.substring("focus:".length()) : icon;
            ThaumaturgeJson.append(json, "icons", new JsonPrimitive((focus ? "focus:" : "") + ThaumaturgeIds.withDefault(body, "minecraft")));
        }
        return this;
    }

    public ResearchEntryBuilder clearIcons() {
        json.remove("icons");
        return this;
    }

    public ResearchEntryBuilder meta(String... flags) {
        for (String flag : flags) {
            if (!META.contains(flag)) {
                throw new IllegalArgumentException("Meta de investigacion desconocida " + flag + ", usa " + META);
            }
            ThaumaturgeJson.append(json, "meta", new JsonPrimitive(flag));
        }
        return this;
    }

    public ResearchEntryBuilder clearMeta() {
        json.remove("meta");
        return this;
    }

    public ResearchEntryBuilder complexity(int complexity) {
        if (complexity < MIN_COMPLEXITY || complexity > MAX_COMPLEXITY) {
            throw new IllegalArgumentException("La complejidad va de " + MIN_COMPLEXITY + " a " + MAX_COMPLEXITY);
        }
        json.addProperty("complexity", complexity);
        return this;
    }

    public ResearchEntryBuilder noteAspect(String... aspects) {
        for (String aspect : aspects) {
            ThaumaturgeJson.append(json, "note_aspects", ThaumaturgeJson.aspect(aspect));
        }
        return this;
    }

    public ResearchEntryBuilder clearNoteAspects() {
        json.remove("note_aspects");
        return this;
    }

    public ResearchEntryBuilder stage(Consumer<ResearchStageBuilder> definition) {
        ResearchStageBuilder stage = new ResearchStageBuilder(new JsonObject());
        definition.accept(stage);
        ThaumaturgeJson.append(json, "stages", stage.build());
        return this;
    }

    public ResearchEntryBuilder clearStages() {
        json.remove("stages");
        return this;
    }

    public ResearchEntryBuilder addendum(Consumer<ResearchAddendumBuilder> definition) {
        ResearchAddendumBuilder addendum = new ResearchAddendumBuilder(new JsonObject());
        definition.accept(addendum);
        ThaumaturgeJson.append(json, "addenda", addendum.build());
        return this;
    }

    public ResearchEntryBuilder clearAddenda() {
        json.remove("addenda");
        return this;
    }

    public ResearchEntryBuilder raw(Context cx, String key, Object value) {
        json.add(key, ThaumaturgeJson.element(cx, value));
        return this;
    }

    @Override
    @HideFromJS
    protected void validate() {
        for (String required : List.of("category", "name", "column", "row")) {
            if (!json.has(required)) {
                throw new IllegalStateException("La entrada de investigacion " + id + " necesita " + required);
            }
        }
        JsonArray stages = json.has("stages") ? json.getAsJsonArray("stages") : new JsonArray();
        if (stages.isEmpty()) {
            throw new IllegalStateException("La entrada de investigacion " + id + " necesita al menos una etapa: stage(s => s.text(...))");
        }
    }

    private static String withStageSuffix(String body) {
        int at = body.lastIndexOf('@');
        String stage = at >= 0 ? body.substring(at) : "";
        String entry = at >= 0 ? body.substring(0, at) : body;
        return ThaumaturgeIds.thaumaturge(entry) + stage;
    }
}
