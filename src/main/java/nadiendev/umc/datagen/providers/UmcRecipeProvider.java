package nadiendev.umc.datagen.providers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class UmcRecipeProvider extends RecipeProvider {

    private final List<DataModule> modules;

    public UmcRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, List<DataModule> modules) {
        super(output, registries);
        this.modules = modules;
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        modules.forEach(module -> module.recipes(output));
    }
}
