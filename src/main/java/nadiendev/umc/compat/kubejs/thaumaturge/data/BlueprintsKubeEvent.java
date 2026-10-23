package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;

public class BlueprintsKubeEvent extends ThaumaturgeDataEvent {

    @HideFromJS
    public BlueprintsKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public BlueprintBuilder create(String id) {
        return track(new BlueprintBuilder(ThaumaturgeIds.owned(id), new JsonObject()));
    }
}
