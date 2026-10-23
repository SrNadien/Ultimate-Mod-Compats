package nadiendev.umc.compat.kubejs.witchery.data;

import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;

public class ShapesKubeEvent extends WitcheryDataEvent {

    @HideFromJS
    public ShapesKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public ShapeBuilder create(String id) {
        return track(new ShapeBuilder(WitcheryIds.owned(id)));
    }
}
