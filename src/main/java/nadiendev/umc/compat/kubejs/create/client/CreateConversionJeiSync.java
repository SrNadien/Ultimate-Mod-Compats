package nadiendev.umc.compat.kubejs.create.client;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.config.KubeCompatConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = UltimateModCompats.MODID, value = Dist.CLIENT)
public final class CreateConversionJeiSync {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateConversionJeiSync.class);
    private static final ResourceLocation CONVERSION_TYPE = ResourceLocation.fromNamespaceAndPath("create", "conversion");
    private static final String CATEGORY_CLASS = "com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory";
    private static final Set<ResourceLocation> ADDED = new HashSet<>();

    private CreateConversionJeiSync() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        if (!KubeCompatConfig.moduleEnabled("create") || !ModList.get().isLoaded("create") || !ModList.get().isLoaded("jei")) {
            return;
        }
        try {
            sync(event.getRecipeManager());
        } catch (ReflectiveOperationException | LinkageError | RuntimeException e) {
            LOGGER.warn("Could not sync Create mysterious conversions with JEI", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void sync(RecipeManager manager) throws ReflectiveOperationException {
        Field field = Class.forName(CATEGORY_CLASS).getField("RECIPES");
        List<RecipeHolder<?>> shown = (List<RecipeHolder<?>>) field.get(null);
        shown.removeIf(holder -> ADDED.contains(holder.id()));
        ADDED.clear();
        RecipeType<?> type = BuiltInRegistries.RECIPE_TYPE.getOptional(CONVERSION_TYPE).orElse(null);
        if (type == null) {
            return;
        }
        for (RecipeHolder<?> holder : manager.getRecipes()) {
            if (holder.value().getType() == type) {
                shown.add(holder);
                ADDED.add(holder.id());
            }
        }
        if (!ADDED.isEmpty()) {
            LOGGER.info("Added {} Create mysterious conversions to the JEI category", ADDED.size());
        }
    }
}
