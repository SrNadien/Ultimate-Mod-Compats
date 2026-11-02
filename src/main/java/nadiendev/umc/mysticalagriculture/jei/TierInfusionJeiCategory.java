package nadiendev.umc.mysticalagriculture.jei;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.compat.jei.category.InfusionCategory;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.constants.VanillaTypes;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TierInfusionJeiCategory extends InfusionCategory {

    private final RecipeType<IInfusionRecipe> type;
    private final ItemStack altar;
    private final IDrawable icon;

    public TierInfusionJeiCategory(IGuiHelper helper, int tier, ItemStack altar) {
        super(helper);
        this.type = typeOf(tier);
        this.altar = altar;
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, altar);
    }

    public static RecipeType<IInfusionRecipe> typeOf(int tier) {
        return RecipeType.create(UltimateModCompats.MODID, "infusion_tier_" + tier, IInfusionRecipe.class);
    }

    @Override
    public RecipeType<IInfusionRecipe> getRecipeType() {
        return type;
    }

    @Override
    public Component getTitle() {
        return altar.getHoverName();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}
