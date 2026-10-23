package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class RawDataKubeEvent extends ThaumaturgeDataEvent {

    @HideFromJS
    public RawDataKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public RawEntryBuilder add(Context cx, String registry, String id, Object json) {
        String path = ThaumaturgeRegistries.require(registry);
        return track(new RawEntryBuilder(ThaumaturgeIds.owned(id), path, ThaumaturgeJson.object(cx, json)));
    }

    public RawEntryBuilder modify(String registry, String id) {
        String path = ThaumaturgeRegistries.require(registry);
        ResourceLocation location = ThaumaturgeIds.thaumaturge(id);
        JsonObject existing = ThaumaturgeJson.readResource(location, "thaumaturge/" + path);
        if (existing == null) {
            throw new IllegalArgumentException("No existe " + location + " en el registro " + path + " dentro de los recursos de Thaumaturge");
        }
        return track(new RawEntryBuilder(location, path, existing));
    }
}
