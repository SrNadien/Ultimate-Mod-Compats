package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.resources.ResourceLocation;

public class RitesKubeEvent extends WitcheryDataEvent {

    private static final String UNREACHABLE_DIMENSION = "umc:disabled_rite";

    private final KubeDataGenerator generator;

    @HideFromJS
    public RitesKubeEvent(KubeDataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    public RiteBuilder create(String id) {
        return track(new RiteBuilder(WitcheryIds.owned(id), ops()));
    }

    public RitesKubeEvent disable(String... ids) {
        for (String raw : ids) {
            ResourceLocation id = WitcheryIds.enchanted(raw);
            JsonObject json = new JsonObject();
            JsonArray items = new JsonArray();
            JsonObject barrier = new JsonObject();
            barrier.addProperty("id", "minecraft:barrier");
            barrier.addProperty("count", 1);
            items.add(barrier);
            json.add("items", items);
            JsonObject shapes = new JsonObject();
            shapes.addProperty(WitcheryIds.ENCHANTED + ":small_circle", WitcheryIds.ENCHANTED + ":ritual_chalk");
            json.add("shapes", shapes);
            JsonArray dimensions = new JsonArray();
            dimensions.add(UNREACHABLE_DIMENSION);
            json.add("dimensions", dimensions);
            JsonObject factory = new JsonObject();
            factory.addProperty("type", WitcheryIds.ENCHANTED + ":sanctity");
            json.add("factory", factory);
            generator.json(WitcheryIds.registryEntry(id, "circle_magic/rite"), json);
        }
        return this;
    }
}
