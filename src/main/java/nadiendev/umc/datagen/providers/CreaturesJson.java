package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import nadiendev.umc.creatures.rite.factory.CureCurseFactory;
import nadiendev.umc.creatures.rite.factory.InflictCurseFactory;
import nadiendev.umc.creatures.rite.factory.SummonMinionsFactory;

public final class CreaturesJson {

    private static final String NIGHT_START = "13000";

    private CreaturesJson() {
    }

    public static void build(UmcJsonSink sink) {
        rites(sink);
        sounds(sink);
        entityTags(sink);
    }

    private static void rites(UmcJsonSink sink) {
        rite(sink, "summon_zombie_servants", 2000, "enchanted:medium_circle", "enchanted:nether_chalk", null,
                minions("umc:zombie_servant", 3), "umc:grave_dust", 2, "minecraft:rotten_flesh", 3);
        rite(sink, "summon_skeleton_servants", 2000, "enchanted:medium_circle", "enchanted:nether_chalk", null,
                minions("umc:skeleton_servant", 3), "umc:grave_dust", 2, "minecraft:bone", 4);
        rite(sink, "summon_ghost", 3000, "enchanted:medium_circle", "enchanted:otherwhere_chalk", null,
                minions("umc:ghost", 1), "umc:ectoplasm", 2, "enchanted:hint_of_rebirth", 1);
        rite(sink, "summon_imps", 3500, "enchanted:medium_circle", "enchanted:nether_chalk", null,
                minions("umc:imp", 2), "umc:imp_horn", 1, "enchanted:demonic_blood", 1, "minecraft:blaze_powder", 2);
        rite(sink, "summon_ent", 4000, "enchanted:medium_circle", "enchanted:ritual_chalk", null,
                minions("umc:ent", 1), "enchanted:ent_twig", 1, "minecraft:oak_sapling", 1, "minecraft:bone_meal", 2);
        rite(sink, "forge_necromancer_scepter", 4500, "enchanted:medium_circle", "enchanted:nether_chalk", null,
                createItem("umc:necromancer_scepter"), "minecraft:blaze_rod", 1, "umc:ectoplasm", 3, "umc:grave_dust", 3, "enchanted:soul_of_the_world", 1);
        rite(sink, "cleanse_curse", 2000, "enchanted:small_circle", "enchanted:ritual_chalk", null,
                simple(CureCurseFactory.ID.toString()), "umc:silver_ingot", 1, "enchanted:garlic", 1, "enchanted:wolfsbane_flower", 1, "enchanted:odour_of_purity", 1);
        rite(sink, "pact_of_the_moon", 2500, "enchanted:medium_circle", "enchanted:nether_chalk", NIGHT_START,
                curse("werewolf"), "umc:werewolf_fang", 1, "enchanted:tongue_of_dog", 1, "minecraft:bone", 2);
        rite(sink, "pact_of_blood", 2500, "enchanted:medium_circle", "enchanted:nether_chalk", NIGHT_START,
                curse("vampire"), "umc:vampire_fang", 1, "enchanted:demonic_blood", 1, "minecraft:redstone", 2);
    }

    private static JsonObject minions(String entity, int count) {
        JsonObject factory = new JsonObject();
        factory.addProperty("type", SummonMinionsFactory.ID.toString());
        factory.addProperty("entity", entity);
        factory.addProperty("count", count);
        return factory;
    }

    private static JsonObject curse(String kind) {
        JsonObject factory = new JsonObject();
        factory.addProperty("type", InflictCurseFactory.ID.toString());
        factory.addProperty("curse", kind);
        return factory;
    }

    private static JsonObject simple(String type) {
        JsonObject factory = new JsonObject();
        factory.addProperty("type", type);
        return factory;
    }

    private static JsonObject createItem(String item) {
        JsonObject factory = new JsonObject();
        factory.addProperty("type", "enchanted:create_item");
        JsonArray items = new JsonArray();
        items.add(stack(item, 1));
        factory.add("items", items);
        return factory;
    }

    private static JsonObject stack(String id, int count) {
        JsonObject stack = new JsonObject();
        stack.addProperty("id", id);
        stack.addProperty("count", count);
        return stack;
    }

    private static void rite(UmcJsonSink sink, String name, int power, String shape, String chalk, String nightStart, JsonObject factory, Object... items) {
        JsonObject rite = new JsonObject();
        JsonArray list = new JsonArray();
        for (int i = 0; i < items.length; i += 2) {
            list.add(stack((String) items[i], (Integer) items[i + 1]));
        }
        rite.add("items", list);
        JsonObject shapes = new JsonObject();
        shapes.addProperty(shape, chalk);
        rite.add("shapes", shapes);
        rite.addProperty("power", power);
        if (nightStart != null) {
            JsonArray time = new JsonArray();
            time.add(Integer.parseInt(nightStart));
            time.add(23000);
            rite.add("time", time);
        }
        rite.add("factory", factory);
        sink.data(EnchantedIds.NAMESPACE, "enchanted/circle_magic/rite/" + name, rite);
    }

    private static void sounds(UmcJsonSink sink) {
        JsonObject root = new JsonObject();
        for (CreaturesSounds.SoundSpec spec : CreaturesSounds.SPECS) {
            JsonObject event = new JsonObject();
            event.addProperty("subtitle", "subtitles." + UltimateModCompats.MODID + "." + spec.name());
            JsonArray sounds = new JsonArray();
            JsonObject sound = new JsonObject();
            sound.addProperty("name", spec.vanillaEvent());
            sound.addProperty("type", "event");
            sound.addProperty("volume", spec.volume());
            sound.addProperty("pitch", spec.pitch());
            sounds.add(sound);
            event.add("sounds", sounds);
            root.add(spec.name(), event);
        }
        sink.assets("sounds", root);
    }

    private static void entityTags(UmcJsonSink sink) {
        String werewolf = "umc:werewolf";
        String vampire = "umc:vampire";
        String ghost = "umc:ghost";
        String imp = "umc:imp";
        String zombie = "umc:zombie_servant";
        String skeleton = "umc:skeleton_servant";
        sink.data("tags/entity_type/werewolves", tag(List.of(werewolf)));
        sink.data("tags/entity_type/vampires", tag(List.of(vampire)));
        sink.data("tags/entity_type/servants", tag(List.of(zombie, skeleton)));
        List<String> undead = List.of(ghost, zombie, skeleton, vampire);
        sink.raw("data/minecraft/tags/entity_type/undead.json", tag(undead));
        sink.raw("data/minecraft/tags/entity_type/sensitive_to_smite.json", tag(undead));
        sink.raw("data/minecraft/tags/entity_type/inverted_healing_and_harm.json", tag(undead));
        sink.raw("data/minecraft/tags/entity_type/fall_damage_immune.json", tag(List.of(ghost, imp)));
    }

    private static JsonObject tag(List<String> values) {
        JsonObject tag = new JsonObject();
        tag.addProperty("replace", false);
        JsonArray array = new JsonArray();
        values.forEach(array::add);
        tag.add("values", array);
        return tag;
    }
}
