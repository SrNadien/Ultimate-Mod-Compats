package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceLocation;

public class AspectBuilder extends JsonEntryBuilder {

    private static final int COMPOUND_COMPONENTS = 2;

    AspectBuilder(ResourceLocation id, JsonObject json) {
        super(id, ThaumaturgeRegistries.ASPECT, json);
        json.addProperty("tag", id.getPath());
        json.addProperty("color", 0xFFFFFF);
    }

    public AspectBuilder tag(String tag) {
        json.addProperty("tag", tag);
        return this;
    }

    public AspectBuilder color(int rgb) {
        json.addProperty("color", rgb & 0xFFFFFF);
        return this;
    }

    public AspectBuilder chatColor(String code) {
        json.addProperty("chat_color", code);
        return this;
    }

    public AspectBuilder components(String first, String second) {
        JsonArray array = new JsonArray();
        array.add(ThaumaturgeIds.thaumaturgeString(first));
        array.add(ThaumaturgeIds.thaumaturgeString(second));
        json.add("components", array);
        return this;
    }

    public AspectBuilder primal() {
        json.remove("components");
        return this;
    }

    public AspectBuilder texture(String texture) {
        json.addProperty("texture", ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.KUBEJS).toString());
        return this;
    }

    public AspectBuilder blend(int blend) {
        json.addProperty("blend", blend);
        return this;
    }

    @Override
    @HideFromJS
    protected void validate() {
        if (json.has("components") && json.getAsJsonArray("components").size() != COMPOUND_COMPONENTS) {
            throw new IllegalStateException("El aspecto " + id + " debe tener 0 o 2 componentes");
        }
    }
}
