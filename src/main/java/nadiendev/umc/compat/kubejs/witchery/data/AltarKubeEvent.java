package nadiendev.umc.compat.kubejs.witchery.data;

import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;

public class AltarKubeEvent extends WitcheryDataEvent {

    @HideFromJS
    public AltarKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public AltarUpgradeBuilder upgrade(String id) {
        return track(new AltarUpgradeBuilder(WitcheryIds.owned(id)));
    }

    public AltarPowerBuilder block(String block, int power, int limit) {
        return track(new AltarPowerBuilder(WitcheryIds.withDefault(block, "minecraft"), "altar/block", power, limit));
    }

    public AltarPowerBuilder tag(String blockTag, int power, int limit) {
        String tag = blockTag.startsWith("#") ? blockTag.substring(1) : blockTag;
        return track(new AltarPowerBuilder(WitcheryIds.withDefault(tag, "minecraft"), "altar/tag", power, limit));
    }
}
