package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class AspectsKubeEvent extends ThaumaturgeDataEvent {

    private static final ResourceLocation ITEM_ASPECTS = ThaumaturgeIds.thaumaturge("data_maps/item/base_aspects");
    private static final ResourceLocation ENTITY_ASPECTS = ThaumaturgeIds.thaumaturge("data_maps/entity_type/entity_aspects");
    private static final ResourceLocation BIOME_ASPECTS = ThaumaturgeIds.thaumaturge("data_maps/worldgen/biome/biome_aspects");

    private final DataMapFile items = new DataMapFile();
    private final DataMapFile entities = new DataMapFile();
    private final DataMapFile biomes = new DataMapFile();

    @HideFromJS
    public AspectsKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public AspectBuilder create(String id) {
        return track(new AspectBuilder(ThaumaturgeIds.owned(id), new JsonObject()));
    }

    public AspectsKubeEvent item(String itemOrTag, String... aspects) {
        items.values.add(key(itemOrTag), ThaumaturgeJson.aspects(aspects));
        return this;
    }

    public AspectsKubeEvent removeItem(String... itemsOrTags) {
        for (String entry : itemsOrTags) {
            items.remove.add(key(entry));
        }
        return this;
    }

    public AspectsKubeEvent entity(String entityType, String... aspects) {
        entities.values.add(ThaumaturgeIds.withDefault(entityType, "minecraft").toString(), ThaumaturgeJson.aspects(aspects));
        return this;
    }

    public AspectsKubeEvent removeEntity(String... entityTypes) {
        for (String entry : entityTypes) {
            entities.remove.add(ThaumaturgeIds.withDefault(entry, "minecraft").toString());
        }
        return this;
    }

    public AspectsKubeEvent biome(String biome, String... aspects) {
        JsonArray array = new JsonArray();
        for (String aspect : aspects) {
            array.add(ThaumaturgeIds.thaumaturgeString(aspect));
        }
        biomes.values.add(key(biome), array);
        return this;
    }

    @Override
    @HideFromJS
    public void flush() {
        super.flush();
        items.write(generator(), ITEM_ASPECTS);
        entities.write(generator(), ENTITY_ASPECTS);
        biomes.write(generator(), BIOME_ASPECTS);
    }

    private static String key(String raw) {
        return raw.startsWith("#") ? "#" + ThaumaturgeIds.withDefault(raw.substring(1), "minecraft") : ThaumaturgeIds.withDefault(raw, "minecraft").toString();
    }

    private static final class DataMapFile {

        private final JsonObject values = new JsonObject();
        private final JsonArray remove = new JsonArray();

        void write(KubeDataGenerator generator, ResourceLocation path) {
            if (values.isEmpty() && remove.isEmpty()) {
                return;
            }
            JsonObject json = new JsonObject();
            json.add("values", values);
            if (!remove.isEmpty()) {
                json.add("remove", remove);
            }
            generator.json(path, json);
        }
    }
}
