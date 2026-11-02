package nadiendev.umc.mysticalagriculture.jei;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.compat.jei.category.AwakeningCategory;
import com.blakebr0.mysticalagriculture.compat.jei.category.InfusionCategory;
import com.blakebr0.mysticalagriculture.compat.jei.category.ReprocessorCategory;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;

@JeiPlugin
public class TierAltarJeiPlugin implements IModPlugin {

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "tier_altars");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    private static boolean active() {
        return ModList.get().isLoaded(TierAltarRegistry.MYSTICAL_AGRICULTURE) && MysticalAgricultureConfig.get().tiersEnabled();
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        if (!active()) {
            return;
        }
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        Block[] infusion = TierAltarRegistry.infusionBlocks();
        Block[] awakening = TierAltarRegistry.awakeningBlocks();
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            registration.addRecipeCategories(
                    new TierInfusionJeiCategory(helper, tier, new ItemStack(infusion[tier - 1])),
                    new TierAwakeningJeiCategory(helper, tier, new ItemStack(awakening[tier - 1])));
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        if (!active() || level == null) {
            return;
        }
        RegistryAccess access = level.registryAccess();
        List<RecipeHolder<IInfusionRecipe>> infusion = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.INFUSION.get());
        List<RecipeHolder<IAwakeningRecipe>> awakening = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.AWAKENING.get());
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            int altarTier = tier;
            registration.addRecipes(TierInfusionJeiCategory.typeOf(tier), allowed(infusion, altarTier, access));
            registration.addRecipes(TierAwakeningJeiCategory.typeOf(tier), allowed(awakening, altarTier, access));
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!active()) {
            return;
        }
        Block[] infusion = TierAltarRegistry.infusionBlocks();
        Block[] awakening = TierAltarRegistry.awakeningBlocks();
        Block[] infusionPedestals = TierAltarRegistry.infusionPedestalBlocks();
        Block[] awakeningPedestals = TierAltarRegistry.awakeningPedestalBlocks();
        Block[] reprocessors = TierAltarRegistry.reprocessorBlocks();
        Block[] furnaces = TierAltarRegistry.furnaceBlocks();
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            registration.addRecipeCatalyst(new ItemStack(infusion[tier - 1]), TierInfusionJeiCategory.typeOf(tier));
            registration.addRecipeCatalyst(new ItemStack(awakening[tier - 1]), TierAwakeningJeiCategory.typeOf(tier));
            registration.addRecipeCatalyst(new ItemStack(infusionPedestals[tier - 1]), TierInfusionJeiCategory.typeOf(tier));
            registration.addRecipeCatalyst(new ItemStack(awakeningPedestals[tier - 1]), TierAwakeningJeiCategory.typeOf(tier));
            registration.addRecipeCatalyst(new ItemStack(reprocessors[tier - 1]), ReprocessorCategory.RECIPE_TYPE);
            registration.addRecipeCatalyst(new ItemStack(furnaces[tier - 1]), RecipeTypes.SMELTING);
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        ClientLevel level = Minecraft.getInstance().level;
        if (!active() || level == null) {
            return;
        }
        RegistryAccess access = level.registryAccess();
        List<IInfusionRecipe> hiddenInfusion = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.INFUSION.get()).stream()
                .map(RecipeHolder::value)
                .filter(recipe -> !TierRules.allows(TierRules.BASE_ALTAR, recipe.getResultItem(access)))
                .toList();
        List<IAwakeningRecipe> hiddenAwakening = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.AWAKENING.get()).stream()
                .map(RecipeHolder::value)
                .filter(recipe -> !TierRules.allows(TierRules.BASE_ALTAR, recipe.getResultItem(access)))
                .toList();
        runtime.getRecipeManager().hideRecipes(InfusionCategory.RECIPE_TYPE, hiddenInfusion);
        runtime.getRecipeManager().hideRecipes(AwakeningCategory.RECIPE_TYPE, hiddenAwakening);
    }

    private static <T extends Recipe<?>> List<T> allowed(List<RecipeHolder<T>> holders, int altarTier, RegistryAccess access) {
        return holders.stream()
                .map(RecipeHolder::value)
                .filter(recipe -> TierRules.allows(altarTier, recipe.getResultItem(access)))
                .toList();
    }
}
