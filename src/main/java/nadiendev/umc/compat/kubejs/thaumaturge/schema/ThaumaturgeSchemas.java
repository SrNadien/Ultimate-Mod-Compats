package nadiendev.umc.compat.kubejs.thaumaturge.schema;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import dev.latvian.mods.kubejs.util.TinyMap;
import java.util.List;
import nadiendev.umc.compat.kubejs.thaumaturge.component.AspectAmount;
import nadiendev.umc.compat.kubejs.thaumaturge.component.ResearchGate;
import nadiendev.umc.compat.kubejs.thaumaturge.component.ThaumaturgeComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface ThaumaturgeSchemas {

    int MAX_INFUSION_COMPONENTS = 64;
    int MAX_WORKBENCH_INGREDIENTS = 9;
    int MAX_INSTABILITY = 100;
    int MAX_PRIMAL_CRYSTALS = 6;

    RecipeComponent<ItemStack> ITEM = ItemStackComponent.ITEM_STACK.instance();
    RecipeComponent<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.instance();
    RecipeComponent<String> STRING = StringComponent.STRING.instance();
    RecipeComponent<AspectAmount> ASPECT = ThaumaturgeComponents.of(ThaumaturgeComponents.ASPECT);
    RecipeComponent<ResearchGate> RESEARCH_GATE = ThaumaturgeComponents.of(ThaumaturgeComponents.RESEARCH);
    RecipeComponent<JsonObject> JSON_OBJECT = ThaumaturgeComponents.of(ThaumaturgeComponents.JSON_OBJECT);

    static RecipeKey<ResearchGate> research() {
        return RESEARCH_GATE.otherKey("research").defaultOptional();
    }

    static RecipeKey<List<AspectAmount>> aspects() {
        return ASPECT.asList().otherKey("aspects");
    }

    static RecipeKey<List<AspectAmount>> crystals() {
        return ASPECT.asList().withBounds(IntBounds.of(0, MAX_PRIMAL_CRYSTALS)).otherKey("crystals").optional(List.of()).alwaysWrite();
    }

    RecipeKey<ItemStack> ARCANE_RESULT = ITEM.outputKey("result");
    RecipeKey<List<String>> ARCANE_PATTERN = STRING.asList().otherKey("pattern");
    RecipeKey<TinyMap<Character, Ingredient>> ARCANE_KEY = INGREDIENT.asPatternKey().inputKey("key");
    RecipeKey<Integer> ARCANE_VIS = NumberComponent.intRange(1, Integer.MAX_VALUE).otherKey("vis");
    RecipeKey<List<AspectAmount>> ARCANE_CRYSTALS = crystals();
    RecipeKey<ResearchGate> ARCANE_RESEARCH = research();
    RecipeKey<String> ARCANE_GROUP = STRING.otherKey("group").defaultOptional();

    RecipeSchema ARCANE_SHAPED = new RecipeSchema(ARCANE_RESULT, ARCANE_PATTERN, ARCANE_KEY, ARCANE_VIS, ARCANE_CRYSTALS, ARCANE_RESEARCH, ARCANE_GROUP)
            .constructor(ARCANE_RESULT, ARCANE_PATTERN, ARCANE_KEY, ARCANE_VIS);

    RecipeKey<List<Ingredient>> ARCANE_INGREDIENTS = INGREDIENT.asList().withBounds(IntBounds.of(1, MAX_WORKBENCH_INGREDIENTS)).inputKey("ingredients");

    RecipeSchema ARCANE_SHAPELESS = new RecipeSchema(ARCANE_RESULT, ARCANE_INGREDIENTS, ARCANE_VIS, ARCANE_CRYSTALS, ARCANE_RESEARCH, ARCANE_GROUP)
            .constructor(ARCANE_RESULT, ARCANE_INGREDIENTS, ARCANE_VIS);

    RecipeKey<Ingredient> CATALYST = INGREDIENT.inputKey("catalyst");
    RecipeKey<List<AspectAmount>> ASPECTS = aspects();

    RecipeSchema CRUCIBLE = new RecipeSchema(ARCANE_RESULT, CATALYST, ASPECTS, research())
            .constructor(ARCANE_RESULT, CATALYST, ASPECTS);

    RecipeKey<List<Ingredient>> INFUSION_COMPONENTS = INGREDIENT.asList().withBounds(IntBounds.of(1, MAX_INFUSION_COMPONENTS)).inputKey("components");
    RecipeKey<ItemStack> INFUSION_RESULT = ITEM.outputKey("result").defaultOptional();
    RecipeKey<Integer> INFUSION_INSTABILITY = NumberComponent.intRange(0, MAX_INSTABILITY).otherKey("instability").optional(0);
    RecipeKey<JsonObject> INFUSION_PATCH = JSON_OBJECT.otherKey("catalyst_patch").defaultOptional();

    RecipeSchema INFUSION = new RecipeSchema(INFUSION_RESULT, CATALYST, INFUSION_COMPONENTS, ASPECTS, INFUSION_INSTABILITY, INFUSION_PATCH, research())
            .constructor(INFUSION_RESULT, CATALYST, INFUSION_COMPONENTS, ASPECTS)
            .constructor(CATALYST, INFUSION_COMPONENTS, ASPECTS);

    RecipeKey<String> ENCHANTMENT = STRING.otherKey("enchantment");
    RecipeKey<Ingredient> DISPLAY_CATALYST = INGREDIENT.inputKey("display_catalyst");

    RecipeSchema INFUSION_ENCHANTMENT = new RecipeSchema(ENCHANTMENT, INFUSION_COMPONENTS, ASPECTS, DISPLAY_CATALYST, research())
            .constructor(ENCHANTMENT, INFUSION_COMPONENTS, ASPECTS, DISPLAY_CATALYST);

    RecipeKey<Ingredient> PER_LEVEL = INGREDIENT.inputKey("per_level");

    RecipeSchema RUNIC_AUGMENT = new RecipeSchema(INFUSION_COMPONENTS, PER_LEVEL, ASPECTS, DISPLAY_CATALYST, research())
            .constructor(INFUSION_COMPONENTS, PER_LEVEL, ASPECTS, DISPLAY_CATALYST);

    RecipeKey<String> DUST_BLOCK = STRING.otherKey("target");
    RecipeKey<String> DUST_TAG = STRING.otherKey("target_tag");
    RecipeKey<String> DUST_BLUEPRINT = STRING.otherKey("blueprint");

    RecipeSchema DUST_TRIGGER_SIMPLE = new RecipeSchema(ARCANE_RESULT, DUST_BLOCK, research())
            .constructor(ARCANE_RESULT, DUST_BLOCK);

    RecipeSchema DUST_TRIGGER_TAG = new RecipeSchema(ARCANE_RESULT, DUST_TAG, research())
            .constructor(ARCANE_RESULT, DUST_TAG);

    RecipeSchema DUST_TRIGGER_MULTIBLOCK = new RecipeSchema(ARCANE_RESULT, DUST_BLUEPRINT, research())
            .constructor(ARCANE_RESULT, DUST_BLUEPRINT);

    RecipeSchema SPECIAL = new RecipeSchema()
            .constructor();

}
