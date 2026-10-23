package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.favouriteless.enchanted.api.datagen.builders.recipe.DistillingRecipeBuilder;
import net.favouriteless.enchanted.api.datagen.builders.recipe.KettleRecipeBuilder;
import net.favouriteless.enchanted.api.datagen.builders.recipe.SpinningRecipeBuilder;
import net.favouriteless.enchanted.common.init.EItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class CraftingRecipes {

    private CraftingRecipes() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }

    private static void armor(RecipeOutput output, ItemLike result, String top, String middle, String bottom, ItemLike a, ItemLike b) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(top)
                .pattern(middle)
                .define('C', CraftingItems.WITCH_CLOTH.get())
                .unlockedBy("has_witch_cloth", InventoryChangeTrigger.TriggerInstance.hasItems(CraftingItems.WITCH_CLOTH.get()));
        if (bottom != null) {
            builder.pattern(bottom);
        }
        if (a != null) {
            builder.define('A', a);
        }
        if (b != null) {
            builder.define('B', b);
        }
        builder.save(output);
    }

    public static void build(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CraftingItems.WITCH_CLOTH.get(), 2)
                .pattern("TT")
                .pattern("TT")
                .define('T', CraftingItems.MOONTHREAD.get())
                .unlockedBy("has_moonthread", InventoryChangeTrigger.TriggerInstance.hasItems(CraftingItems.MOONTHREAD.get()))
                .save(output);

        armor(output, CraftingItems.WITCH_HAT.get(), " C ", " C ", "CCC", null, null);
        armor(output, CraftingItems.WITCH_ROBE.get(), "C C", "CCC", "CCC", null, null);
        armor(output, CraftingItems.WITCH_CLOAK.get(), "ACA", "CCC", "C C", Items.GOLD_NUGGET, null);
        armor(output, CraftingItems.WITCH_BOOTS.get(), "C C", "B B", null, null, Items.LEATHER);

        SpinningRecipeBuilder.create(CraftingItems.MOONTHREAD.get(), CraftingItems.MOONBLOOM.get(), Items.STRING, EItems.WHIFF_OF_MAGIC.get())
                .power(250)
                .duration(300)
                .save(output, id("spinning/moonthread"));

        KettleRecipeBuilder.of(new ItemStack(CraftingItems.WORMWOOD_TINCTURE.get()), 2000)
                .inputs(CraftingItems.WORMWOOD_SPRIG.get(), Items.SPIDER_EYE, EItems.WHIFF_OF_MAGIC.get())
                .requiresBottle()
                .cookColor(0x2F3B18)
                .finalColor(0x86A34A)
                .save(output, id("kettle/wormwood_tincture"));

        KettleRecipeBuilder.of(new ItemStack(CraftingItems.VERVAIN_TONIC.get()), 2500)
                .inputs(CraftingItems.VERVAIN_FLOWER.get(), Items.GLISTERING_MELON_SLICE, EItems.ODOUR_OF_PURITY.get())
                .requiresBottle()
                .cookColor(0x2E1B57)
                .finalColor(0x8B5BD6)
                .save(output, id("kettle/vervain_tonic"));

        DistillingRecipeBuilder.create(new ItemStack(EItems.CLAY_JAR.get(), 2), new ItemStack(CraftingItems.MOONBLOOM.get()), new ItemStack(CraftingItems.MUGWORT.get()))
                .results(new ItemStack(CraftingItems.MOONDEW.get(), 2), new ItemStack(EItems.WHIFF_OF_MAGIC.get()))
                .duration(300)
                .power(750)
                .save(output, id("distilling/moondew"));
    }
}
