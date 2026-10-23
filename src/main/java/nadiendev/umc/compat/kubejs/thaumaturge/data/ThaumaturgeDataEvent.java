package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

public abstract class ThaumaturgeDataEvent implements KubeEvent {

    private final KubeDataGenerator generator;
    private final List<ThaumaturgeDataEntry> entries = new ArrayList<>();

    protected ThaumaturgeDataEvent(KubeDataGenerator generator) {
        this.generator = generator;
    }

    @HideFromJS
    protected KubeDataGenerator generator() {
        return generator;
    }

    @HideFromJS
    protected RegistryOps<JsonElement> ops() {
        return generator.getRegistries().json();
    }

    @HideFromJS
    protected <T extends ThaumaturgeDataEntry> T track(T entry) {
        entries.add(entry);
        return entry;
    }

    @HideFromJS
    public void flush() {
        for (ThaumaturgeDataEntry entry : entries) {
            ResourceLocation path = entry.dataPath();
            try {
                generator.json(path, entry.toJson());
                ConsoleJS.SERVER.info("Thaumaturge: escrito data/" + path.getNamespace() + "/" + path.getPath() + ".json");
            } catch (RuntimeException ex) {
                ConsoleJS.SERVER.error("Thaumaturge: no se pudo generar " + path, ex);
            }
        }
        entries.clear();
    }
}
