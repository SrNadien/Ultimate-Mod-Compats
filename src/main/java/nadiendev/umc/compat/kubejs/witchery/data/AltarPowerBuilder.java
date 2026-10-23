package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.resources.ResourceLocation;

public class AltarPowerBuilder implements WitcheryDataEntry {

    private final ResourceLocation target;
    private final String registryPath;
    private int power;
    private int limit;

    AltarPowerBuilder(ResourceLocation target, String registryPath, int power, int limit) {
        this.target = target;
        this.registryPath = registryPath;
        this.power = power;
        this.limit = limit;
    }

    public AltarPowerBuilder power(int power) {
        this.power = power;
        return this;
    }

    public AltarPowerBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return WitcheryIds.registryEntry(target, registryPath);
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("power", power);
        json.addProperty("limit", limit);
        return json;
    }
}
