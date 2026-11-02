package nadiendev.umc.mysticalagriculture.jei;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.compat.jei.category.AwakeningCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TierAwakeningJeiCategory extends AwakeningCategory {

    private final RecipeType<IAwakeningRecipe> type;
    private final ItemStack altar;
    private final IDrawable icon;

    public TierAwakeningJeiCategory(IGuiHelper helper, int tier, ItemStack altar) {
        super(helper);
        this.type = typeOf(tier);
        this.altar = altar;
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, altar);
    }

    public static RecipeType<IAwakeningRecipe> typeOf(int tier) {
        return RecipeType.create(UltimateModCompats.MODID, "awakening_tier_" + tier, IAwakeningRecipe.class);
    }

    @Override
    public RecipeType<IAwakeningRecipe> getRecipeType() {
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
