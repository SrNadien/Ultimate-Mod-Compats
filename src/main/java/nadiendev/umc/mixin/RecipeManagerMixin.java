package nadiendev.umc.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    private static final String SEED_GROUP = "mysticalagriculture:seeds";

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void umc$removeSeedCrafting(Map<ResourceLocation, JsonElement> recipes, ResourceManager manager, ProfilerFiller profiler, CallbackInfo ci) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.tiersEnabled() || !config.seedCraftingRemoved()) {
            return;
        }
        recipes.values().removeIf(json -> json instanceof JsonObject object
                && object.has("group")
                && object.get("group").isJsonPrimitive()
                && SEED_GROUP.equals(object.get("group").getAsString()));
    }
}
