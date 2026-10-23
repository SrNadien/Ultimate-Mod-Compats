package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryJson;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RiteBuilder implements WitcheryDataEntry {

    public static final int NIGHT_START = 13000;
    public static final int NIGHT_END = 23000;
    public static final int DAY_END = 12000;

    private static final List<String> WEATHERS = List.of("none", "clear", "raining", "thundering");

    private final ResourceLocation id;
    private final RegistryOps<JsonElement> ops;
    private final List<ItemStack> items = new ArrayList<>();
    private final Map<String, String> shapes = new LinkedHashMap<>();
    private final List<String> entities = new ArrayList<>();
    private final List<String> dimensions = new ArrayList<>();
    private int power;
    private int tickPower;
    private String weather;
    private int[] time;
    private JsonObject factory;

    RiteBuilder(ResourceLocation id, RegistryOps<JsonElement> ops) {
        this.id = id;
        this.ops = ops;
    }

    public RiteBuilder item(ItemStack stack) {
        items.add(stack);
        return this;
    }

    public RiteBuilder items(ItemStack... stacks) {
        items.addAll(List.of(stacks));
        return this;
    }

    public RiteBuilder circle(String shape, String chalk) {
        shapes.put(WitcheryIds.enchantedString(shape), WitcheryIds.enchantedString(chalk));
        return this;
    }

    public RiteBuilder smallCircle(String chalk) {
        return circle("small_circle", chalk);
    }

    public RiteBuilder mediumCircle(String chalk) {
        return circle("medium_circle", chalk);
    }

    public RiteBuilder largeCircle(String chalk) {
        return circle("large_circle", chalk);
    }

    public RiteBuilder entity(String... entityTypes) {
        for (String type : entityTypes) {
            entities.add(WitcheryIds.withDefault(type, "minecraft").toString());
        }
        return this;
    }

    public RiteBuilder dimension(String... dimensionIds) {
        for (String dimension : dimensionIds) {
            dimensions.add(WitcheryIds.withDefault(dimension, "minecraft").toString());
        }
        return this;
    }

    public RiteBuilder power(int power) {
        this.power = power;
        return this;
    }

    public RiteBuilder tickPower(int tickPower) {
        this.tickPower = tickPower;
        return this;
    }

    public RiteBuilder weather(String weather) {
        String normalized = weather.toLowerCase(Locale.ROOT);
        if (!WEATHERS.contains(normalized)) {
            throw new IllegalArgumentException("Clima de rito desconocido " + weather + ", usa none, clear, raining o thundering");
        }
        this.weather = normalized;
        return this;
    }

    public RiteBuilder time(int from, int to) {
        this.time = new int[] {from, to};
        return this;
    }

    public RiteBuilder night() {
        return time(NIGHT_START, NIGHT_END);
    }

    public RiteBuilder day() {
        return time(0, DAY_END);
    }

    public RiteBuilder factory(Context cx, String type, Object params) {
        JsonObject json = typed(type);
        WitcheryJson.object(cx, params).entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        this.factory = json;
        return this;
    }

    public RiteBuilder factory(String type) {
        this.factory = typed(type);
        return this;
    }

    public RiteBuilder createItem(ItemStack... stacks) {
        return withFactory("create_item", json -> json.add("items", stackArray(stacks)));
    }

    public RiteBuilder entityBoundCreateItem(ItemStack... stacks) {
        return withFactory("entity_bound_create_item", json -> json.add("items", stackArray(stacks)));
    }

    public RiteBuilder locationBoundCreateItem(ItemStack... stacks) {
        return withFactory("location_bound_create_item", json -> json.add("items", stackArray(stacks)));
    }

    public RiteBuilder duplicateItem(String item, int count) {
        return withFactory("duplicate_item", json -> {
            json.addProperty("item", WitcheryIds.withDefault(item, "minecraft").toString());
            json.addProperty("count", count);
        });
    }

    public RiteBuilder applyCurse(String curse) {
        return withFactory("apply_curse", json -> json.addProperty("curse", WitcheryIds.enchantedString(curse)));
    }

    public RiteBuilder removeCurse(String curse) {
        return withFactory("remove_curse", json -> json.addProperty("curse", WitcheryIds.enchantedString(curse)));
    }

    public RiteBuilder command(String... commands) {
        return commandSequence(0, List.of(List.of(commands)));
    }

    public RiteBuilder commandSequence(int delayTicks, List<List<String>> steps) {
        return withFactory("command", json -> {
            JsonArray outer = new JsonArray();
            for (List<String> step : steps) {
                JsonArray inner = new JsonArray();
                step.forEach(inner::add);
                outer.add(inner);
            }
            json.add("commands", outer);
            json.addProperty("delay", delayTicks);
        });
    }

    public RiteBuilder protection(int radius) {
        return withFactory("protection", json -> json.addProperty("radius", radius));
    }

    public RiteBuilder protection(int radius, int durationTicks, boolean blocksPlayers) {
        return withFactory("protection", json -> {
            json.addProperty("radius", radius);
            json.addProperty("duration", durationTicks);
            json.addProperty("blocks_players", blocksPlayers);
        });
    }

    public RiteBuilder fertility(int radius, double bonemealChance) {
        return withFactory("fertility", json -> {
            json.addProperty("radius", radius);
            json.addProperty("bonemeal_chance", bonemealChance);
        });
    }

    public RiteBuilder forest(int radius, int spacing) {
        return withFactory("forest", json -> {
            json.addProperty("radius", radius);
            json.addProperty("spacing", spacing);
        });
    }

    public RiteBuilder blight(int radius, double decayChance, double zombieChance) {
        return withFactory("blight", json -> {
            json.addProperty("radius", radius);
            json.addProperty("decay_chance", decayChance);
            json.addProperty("zombie_chance", zombieChance);
        });
    }

    public RiteBuilder broiling(double burnChance) {
        return withFactory("broiling", json -> json.addProperty("burn_chance", burnChance));
    }

    public RiteBuilder transposeBlocks(String blockTag, ItemStack tool) {
        String tag = blockTag.startsWith("#") ? blockTag.substring(1) : blockTag;
        return withFactory("transpose_blocks", json -> {
            json.addProperty("tag", WitcheryIds.withDefault(tag, "minecraft").toString());
            json.add("tool", WitcheryJson.stack(ops, tool));
        });
    }

    public RiteBuilder skyWrath() {
        return factory("sky_wrath");
    }

    public RiteBuilder sanctity() {
        return factory("sanctity");
    }

    public RiteBuilder imprisonment() {
        return factory("imprisonment");
    }

    public RiteBuilder totalEclipse() {
        return factory("total_eclipse");
    }

    public RiteBuilder summonEntity() {
        return factory("summon_entity");
    }

    public RiteBuilder bindFamiliar() {
        return factory("bind_familiar");
    }

    public RiteBuilder summonFamiliar() {
        return factory("summon_familiar");
    }

    public RiteBuilder bindTalisman() {
        return factory("bind_talisman");
    }

    public RiteBuilder transposeCaster() {
        return factory("transpose_caster");
    }

    private static JsonObject typed(String type) {
        JsonObject json = new JsonObject();
        json.addProperty("type", WitcheryIds.enchantedString(type));
        return json;
    }

    private RiteBuilder withFactory(String type, Consumer<JsonObject> fill) {
        JsonObject json = typed(type);
        fill.accept(json);
        this.factory = json;
        return this;
    }

    private JsonArray stackArray(ItemStack[] stacks) {
        return WitcheryJson.stacks(ops, List.of(stacks));
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return WitcheryIds.registryEntry(id, "circle_magic/rite");
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        if (factory == null) {
            throw new IllegalStateException("El rito " + id + " no tiene factory: usa factory(...), createItem(...), applyCurse(...), etc.");
        }
        JsonObject json = new JsonObject();
        json.add("items", WitcheryJson.stacks(ops, items));
        JsonObject shapeJson = new JsonObject();
        shapes.forEach(shapeJson::addProperty);
        json.add("shapes", shapeJson);
        if (!entities.isEmpty()) {
            JsonArray array = new JsonArray();
            entities.forEach(array::add);
            json.add("entities", array);
        }
        if (power > 0) {
            json.addProperty("power", power);
        }
        if (tickPower > 0) {
            json.addProperty("tick_power", tickPower);
        }
        if (weather != null) {
            json.addProperty("weather", weather);
        }
        if (time != null) {
            JsonArray array = new JsonArray();
            array.add(time[0]);
            array.add(time[1]);
            json.add("time", array);
        }
        if (!dimensions.isEmpty()) {
            JsonArray array = new JsonArray();
            dimensions.forEach(array::add);
            json.add("dimensions", array);
        }
        json.add("factory", factory);
        return json;
    }
}
