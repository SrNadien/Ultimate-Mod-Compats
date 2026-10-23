package nadiendev.umc.compat.kubejs.create.client;

import java.util.List;
import java.util.Optional;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryDecorator;
import mezz.jei.api.registration.IAdvancedRegistration;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.config.KubeCompatConfig;
import nadiendev.umc.compat.kubejs.create.entity.ConversionEntityClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.fml.ModList;

@JeiPlugin
public class CreateConversionJeiPlugin implements IModPlugin {

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "create_conversion");
    private static final ResourceLocation CATEGORY = ResourceLocation.fromNamespaceAndPath("create", "mystery_conversion");
    private static final String TRANSLATION_KEY = "umc.jei.conversion_entity";
    private static final int TEXT_Y = 40;
    private static final int TEXT_COLOR = 0xFF555555;

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        if (!KubeCompatConfig.moduleEnabled("create") || !ModList.get().isLoaded("create")) {
            return;
        }
        RecipeType<RecipeHolder<Recipe<?>>> type = RecipeType.createRecipeHolderType(CATEGORY);
        registration.addRecipeCategoryDecorator(type, new EntityDecorator());
    }

    private static final class EntityDecorator implements IRecipeCategoryDecorator<RecipeHolder<Recipe<?>>> {

        @Override
        public void draw(RecipeHolder<Recipe<?>> holder, IRecipeCategory<RecipeHolder<Recipe<?>>> category, IRecipeSlotsView slotsView,
                         GuiGraphics graphics, double mouseX, double mouseY) {
            entity(holder.value()).ifPresent(type -> {
                Component text = Component.translatable(TRANSLATION_KEY, type.getDescription());
                int width = category.getWidth();
                graphics.drawString(Minecraft.getInstance().font, text,
                        (width - Minecraft.getInstance().font.width(text)) / 2, TEXT_Y, TEXT_COLOR, false);
            });
        }

        private static Optional<EntityType<?>> entity(Recipe<?> recipe) {
            if (Minecraft.getInstance().level == null) {
                return Optional.empty();
            }
            List<Ingredient> inputs = recipe.getIngredients();
            ItemStack result = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
            return ConversionEntityClientData.entityFor(inputs, result).flatMap(BuiltInRegistries.ENTITY_TYPE::getOptional);
        }
    }
}
