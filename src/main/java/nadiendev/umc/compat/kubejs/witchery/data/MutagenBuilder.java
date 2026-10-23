package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.resources.ResourceLocation;

public class MutagenBuilder implements WitcheryDataEntry {

    private final ResourceLocation id;
    private final List<JsonObject> sets = new ArrayList<>();

    MutagenBuilder(ResourceLocation id) {
        this.id = id;
    }

    private MutagenBuilder add(String result, int weight, boolean extremis, String[] mutagens) {
        JsonObject set = new JsonObject();
        set.addProperty("result", WitcheryIds.withDefault(result, "minecraft").toString());
        set.addProperty("extremis", extremis);
        set.addProperty("weight", weight);
        JsonArray array = new JsonArray();
        for (String mutagen : mutagens) {
            array.add(WitcheryIds.withDefault(mutagen, "minecraft").toString());
        }
        set.add("mutagens", array);
        sets.add(set);
        return this;
    }

    public MutagenBuilder mutation(String result, int weight, String... mutagens) {
        return add(result, weight, false, mutagens);
    }

    public MutagenBuilder extremisMutation(String result, int weight, String... mutagens) {
        return add(result, weight, true, mutagens);
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return WitcheryIds.registryEntry(id, "mutagens");
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        if (sets.isEmpty()) {
            throw new IllegalStateException("El mutageno " + id + " no tiene mutaciones: usa mutation(...)");
        }
        JsonArray array = new JsonArray();
        sets.forEach(array::add);
        return array;
    }
}
