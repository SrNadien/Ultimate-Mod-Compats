package nadiendev.umc.compat.kubejs;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.core.RecipeManagerKJS;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.generator.KubeAssetGenerator;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.plugin.ClassFilter;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.ingredientaction.IngredientActionTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeMappingRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.recipe.schema.function.CustomRecipeSchemaFunctionRegistry;
import dev.latvian.mods.kubejs.recipe.schema.function.RecipeSchemaFunctionRegistry;
import dev.latvian.mods.kubejs.recipe.schema.postprocessing.RecipePostProcessorTypeRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.registry.ServerRegistryRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.RecordDefaultsRegistry;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.TypeDescriptionRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

public class UmcKubeJSPlugin implements KubeJSPlugin {

    private static void each(Consumer<UmcKubeModule> action) {
        KubeModules.enabled().forEach(action);
    }

    @Override
    public void init() {
        each(KubeJSPlugin::init);
    }

    @Override
    public void initStartup() {
        each(KubeJSPlugin::initStartup);
    }

    @Override
    public void afterInit() {
        each(KubeJSPlugin::afterInit);
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        each(module -> module.registerBuilderTypes(registry));
    }

    @Override
    public void registerServerRegistries(ServerRegistryRegistry registry) {
        each(module -> module.registerServerRegistries(registry));
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        each(module -> module.registerEvents(registry));
    }

    @Override
    public void registerClasses(ClassFilter filter) {
        each(module -> module.registerClasses(filter));
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        each(module -> module.registerBindings(bindings));
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        each(module -> module.registerTypeWrappers(registry));
    }

    @Override
    public void registerRecordDefaults(RecordDefaultsRegistry registry) {
        each(module -> module.registerRecordDefaults(registry));
    }

    @Override
    public void registerTypeDescriptions(TypeDescriptionRegistry registry) {
        each(module -> module.registerTypeDescriptions(registry));
    }

    @Override
    public void registerRecipeFactories(RecipeFactoryRegistry registry) {
        each(module -> module.registerRecipeFactories(registry));
    }

    @Override
    public void registerRecipeMappings(RecipeMappingRegistry registry) {
        each(module -> module.registerRecipeMappings(registry));
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        each(module -> module.registerRecipeComponents(registry));
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        each(module -> module.registerRecipeSchemas(registry));
    }

    @Override
    public void registerRecipeSchemaFunctionTypes(RecipeSchemaFunctionRegistry registry) {
        each(module -> module.registerRecipeSchemaFunctionTypes(registry));
    }

    @Override
    public void registerCustomRecipeSchemaFunctions(CustomRecipeSchemaFunctionRegistry registry) {
        each(module -> module.registerCustomRecipeSchemaFunctions(registry));
    }

    @Override
    public void registerRecipePostProcessors(RecipePostProcessorTypeRegistry registry) {
        each(module -> module.registerRecipePostProcessors(registry));
    }

    @Override
    public void registerIngredientActionTypes(IngredientActionTypeRegistry registry) {
        each(module -> module.registerIngredientActionTypes(registry));
    }

    @Override
    public void generateData(KubeDataGenerator generator) {
        each(module -> module.generateData(generator));
    }

    @Override
    public void generateAssets(KubeAssetGenerator generator) {
        each(module -> module.generateAssets(generator));
    }

    @Override
    public void generateLang(LangKubeEvent event) {
        each(module -> module.generateLang(event));
    }

    @Override
    public void clearCaches() {
        each(KubeJSPlugin::clearCaches);
    }

    @Override
    public void beforeRecipeLoading(RecipesKubeEvent event, RecipeManagerKJS manager, Map<ResourceLocation, JsonElement> recipeJsons) {
        each(module -> module.beforeRecipeLoading(event, manager, recipeJsons));
    }

    @Override
    public void beforeScriptsLoaded(ScriptManager manager) {
        each(module -> module.beforeScriptsLoaded(manager));
    }

    @Override
    public void afterScriptsLoaded(ScriptManager manager) {
        each(module -> module.afterScriptsLoaded(manager));
    }
}
