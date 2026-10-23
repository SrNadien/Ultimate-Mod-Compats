package nadiendev.umc.compat.kubejs.mysticalagriculture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class MysticalRecipesKubeEvent implements KubeEvent {

    private static final String[] CRAFTING_PATTERN = {"MEM", "ESE", "MEM"};
    private static final Pattern COUNTED_ITEM = Pattern.compile("^(?:(\\d+)x\\s+)?(\\S+)$");
    private static final String SEEDS_SUFFIX = "_seeds";
    private static final String CONDITIONS = "neoforge:conditions";
    private static final Set<String> ALWAYS_LOADED = Set.of("minecraft", "neoforge", "c", "mysticalagriculture");

    private final RecipesKubeEvent recipes;
    private final Map<ResourceLocation, JsonElement> recipeJsons;

    public MysticalRecipesKubeEvent(RecipesKubeEvent recipes, Map<ResourceLocation, JsonElement> recipeJsons) {
        this.recipes = recipes;
        this.recipeJsons = recipeJsons;
    }

    public void seed(String seeds, Ingredient seedBase, Ingredient essence, Ingredient material) {
        seedCrafting(seeds, seedBase, essence, material);
        seedInfusion(seeds, seedBase, essence, material);
    }

    public void seedCrafting(String seeds, Ingredient seedBase, Ingredient essence, Ingredient material) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        json.addProperty("category", "misc");
        json.addProperty("group", "mysticalagriculture:seeds");
        JsonArray pattern = new JsonArray();
        for (String row : CRAFTING_PATTERN) {
            pattern.add(row);
        }
        json.add("pattern", pattern);
        JsonObject key = new JsonObject();
        key.add("M", encode(Ingredient.CODEC_NONEMPTY, material));
        key.add("E", encode(Ingredient.CODEC_NONEMPTY, essence));
        key.add("S", encode(Ingredient.CODEC_NONEMPTY, seedBase));
        json.add("key", key);
        json.add("result", stack(seeds));
        add(json, "seed_crafting", seeds);
    }

    public void seedInfusion(String seeds, Ingredient seedBase, Ingredient essence, Ingredient material) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "mysticalagriculture:infusion");
        json.add("input", encode(Ingredient.CODEC_NONEMPTY, seedBase));
        JsonArray ingredients = new JsonArray();
        for (int i = 0; i < 4; i++) {
            ingredients.add(encode(Ingredient.CODEC_NONEMPTY, material));
            ingredients.add(encode(Ingredient.CODEC_NONEMPTY, essence));
        }
        json.add("ingredients", ingredients);
        json.add("result", stack(seeds));
        add(json, "seed_infusion", seeds);
    }

    public void seedReprocessing(String seeds, String essence) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "mysticalagriculture:reprocessor");
        JsonObject input = new JsonObject();
        input.addProperty("item", itemId(seeds));
        json.add("input", input);
        json.add("result", stack(essence));
        add(json, "seed_reprocessing", seeds, itemId(essence));
    }

    public RecipesKubeEvent getRecipes() {
        return recipes;
    }

    private <T> JsonElement encode(Codec<T> codec, T value) {
        return codec.encodeStart(recipes.ops.json(), value).getOrThrow();
    }

    private static String itemId(String stack) {
        Matcher matcher = COUNTED_ITEM.matcher(stack.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid item '" + stack + "', expected 'namespace:item' or 'Nx namespace:item'");
        }
        return matcher.group(2);
    }

    private static JsonObject stack(String stack) {
        Matcher matcher = COUNTED_ITEM.matcher(stack.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid item '" + stack + "', expected 'namespace:item' or 'Nx namespace:item'");
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", matcher.group(2));
        json.addProperty("count", matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1)));
        return json;
    }

    private static JsonArray conditions(String... items) {
        Set<String> mods = new LinkedHashSet<>();
        for (String item : items) {
            String id = itemId(item);
            ResourceLocation location = ResourceLocation.parse(id);
            if (!ALWAYS_LOADED.contains(location.getNamespace())) {
                mods.add(location.getNamespace());
            }
            if (location.getPath().endsWith(SEEDS_SUFFIX)) {
                String crop = location.getPath().substring(0, location.getPath().length() - SEEDS_SUFFIX.length());
                MysticalCropCatalog.all().stream().filter(definition -> definition.id().equals(crop)).findFirst()
                        .ifPresent(definition -> mods.addAll(definition.requires()));
            }
        }
        JsonArray array = new JsonArray();
        mods.forEach(mod -> array.add(modLoaded(mod)));
        return array;
    }

    private static JsonObject modLoaded(String mod) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", mod);
        return condition;
    }

    private void add(JsonObject json, String folder, String... outputs) {
        JsonArray conditions = conditions(outputs);
        if (!conditions.isEmpty()) {
            json.add(CONDITIONS, conditions);
        }
        ResourceLocation item = ResourceLocation.parse(itemId(outputs[0]));
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID,
                "mystical/" + folder + "/" + item.getNamespace() + "/" + item.getPath());
        recipeJsons.put(id, json);
    }
}
