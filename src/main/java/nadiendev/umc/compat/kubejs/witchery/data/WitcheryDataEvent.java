package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.RegistryOps;

public abstract class WitcheryDataEvent implements KubeEvent {

    private final KubeDataGenerator generator;
    private final List<WitcheryDataEntry> entries = new ArrayList<>();

    protected WitcheryDataEvent(KubeDataGenerator generator) {
        this.generator = generator;
    }

    @HideFromJS
    protected RegistryOps<JsonElement> ops() {
        return generator.getRegistries().json();
    }

    @HideFromJS
    protected <T extends WitcheryDataEntry> T track(T entry) {
        entries.add(entry);
        return entry;
    }

    @HideFromJS
    public void flush() {
        for (WitcheryDataEntry entry : entries) {
            generator.json(entry.dataPath(), entry.toJson());
        }
        entries.clear();
    }
}
