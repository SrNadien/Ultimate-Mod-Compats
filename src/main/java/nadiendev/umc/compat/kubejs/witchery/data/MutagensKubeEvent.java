package nadiendev.umc.compat.kubejs.witchery.data;

import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;

public class MutagensKubeEvent extends WitcheryDataEvent {

    @HideFromJS
    public MutagensKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public MutagenBuilder create(String id) {
        return track(new MutagenBuilder(WitcheryIds.owned(id)));
    }
}
