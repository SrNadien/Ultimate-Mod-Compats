package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceLocation;

public abstract class JsonEntryBuilder implements ThaumaturgeDataEntry {

    protected final ResourceLocation id;
    protected final JsonObject json;
    private final String registryPath;

    protected JsonEntryBuilder(ResourceLocation id, String registryPath, JsonObject json) {
        this.id = id;
        this.registryPath = registryPath;
        this.json = json;
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return ThaumaturgeIds.registryEntry(id, registryPath);
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        validate();
        return json;
    }

    @HideFromJS
    protected void validate() {
    }
}
