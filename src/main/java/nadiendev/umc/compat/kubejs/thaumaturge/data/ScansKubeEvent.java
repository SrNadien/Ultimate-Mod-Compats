package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;

public class ScansKubeEvent extends ThaumaturgeDataEvent {

    @HideFromJS
    public ScansKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public ScanBuilder create(String id) {
        return track(new ScanBuilder(ThaumaturgeIds.owned(id), new JsonObject()));
    }
}
