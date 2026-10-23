package nadiendev.umc.datagen.providers;

import nadiendev.umc.creatures.registry.CreaturesItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class CreaturesRecipes {

    private CreaturesRecipes() {
    }

    public static void build(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.SILVER_INGOT.get())
                .pattern(" Q ")
                .pattern("QIQ")
                .pattern(" Q ")
                .define('Q', Items.QUARTZ)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_quartz", InventoryChangeTrigger.TriggerInstance.hasItems(Items.QUARTZ))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CreaturesItems.SILVER_SWORD.get())
                .pattern("S")
                .pattern("S")
                .pattern("T")
                .define('S', CreaturesItems.SILVER_INGOT.get())
                .define('T', Items.STICK)
                .unlockedBy("has_silver_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.SILVER_INGOT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CreaturesItems.WOODEN_STAKE.get())
                .pattern("L")
                .pattern("S")
                .define('L', ItemTags.LOGS)
                .define('S', Items.STICK)
                .unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.GRAVE_DUST.get(), 2)
                .pattern("BS")
                .pattern("SB")
                .define('B', Items.BONE_MEAL)
                .define('S', Items.SOUL_SAND)
                .unlockedBy("has_soul_sand", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SOUL_SAND))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.PURIFYING_TONIC.get())
                .pattern(" G ")
                .pattern("WBS")
                .define('G', enchanted("garlic"))
                .define('W', enchanted("wolfsbane_flower"))
                .define('B', Items.GLASS_BOTTLE)
                .define('S', CreaturesItems.SILVER_INGOT.get())
                .unlockedBy("has_silver_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.SILVER_INGOT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.LUNAR_TALISMAN.get())
                .pattern(" S ")
                .pattern("SFS")
                .pattern(" S ")
                .define('S', CreaturesItems.SILVER_INGOT.get())
                .define('F', CreaturesItems.WEREWOLF_FANG.get())
                .unlockedBy("has_werewolf_fang", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.WEREWOLF_FANG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.BLOOD_SIGIL.get())
                .pattern(" R ")
                .pattern("RFR")
                .pattern(" R ")
                .define('R', Items.REDSTONE)
                .define('F', CreaturesItems.VAMPIRE_FANG.get())
                .unlockedBy("has_vampire_fang", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.VAMPIRE_FANG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.SUMMONING_HORN.get())
                .pattern("BGB")
                .pattern("BEB")
                .pattern(" B ")
                .define('B', Items.BONE)
                .define('G', CreaturesItems.GRAVE_DUST.get())
                .define('E', CreaturesItems.ECTOPLASM.get())
                .unlockedBy("has_ectoplasm", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.ECTOPLASM.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreaturesItems.DEMONOMICON.get())
                .pattern(" D ")
                .pattern("HKH")
                .pattern(" O ")
                .define('D', enchanted("demonic_blood"))
                .define('H', CreaturesItems.IMP_HORN.get())
                .define('K', Items.BOOK)
                .define('O', Items.OBSIDIAN)
                .unlockedBy("has_imp_horn", InventoryChangeTrigger.TriggerInstance.hasItems(CreaturesItems.IMP_HORN.get()))
                .save(output);
    }

    private static ItemLike enchanted(String path) {
        Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath("enchanted", path)).orElseThrow();
        return item;
    }
}
