package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import java.util.function.Function;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.arsnouveau.ArsNouveauFractalTabs;
import nadiendev.umc.arsnouveau.ArsNouveauRegistry;
import nadiendev.umc.arsnouveau.ArsNouveauStats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** Lang, models, blockstates, loot, tags and recipes of the Ars Nouveau source jars. */
public class ArsNouveauDataGenCompat implements DataModule {

    /** The fill models of the jar of Ars Nouveau, from the state with fill 0 to the one with fill 11. */
    private static final String[] FILL_MODELS = {"source_jar0", "source_jar_sliver", "source_jar10", "source_jar20", "source_jar30",
            "source_jar40", "source_jar50", "source_jar60", "source_jar70", "source_jar80", "source_jar90", "source_jar100"};
    /** What the "ars_nouveau:source" item property has to reach for the item to show each fill model. */
    private static final float[] ITEM_STEPS = {0.01f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ArsNouveauFractalTabs.ID), "Ars Nouveau");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add("block." + UltimateModCompats.MODID + "." + ArsNouveauRegistry.jarId(material), material.english() + " Source Jar");
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(ArsNouveauFractalTabs.ID), "Ars Nouveau");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add("block." + UltimateModCompats.MODID + "." + ArsNouveauRegistry.jarId(material), "Tarro de fuente de " + material.spanish());
        }
    }

    @Override
    public void blockTags(Function<TagKey<Block>, IntrinsicTagAppender<Block>> tags) {
        IntrinsicTagAppender<Block> pickaxe = tags.apply(BlockTags.MINEABLE_WITH_PICKAXE);
        for (Block block : ArsNouveauRegistry.blocks()) {
            pickaxe.add(block);
        }
    }

    @Override
    public void blockLoot(UmcBlockLoot loot) {
        DataComponentType<?> contents = DataComponentRegistry.BLOCK_FILL_CONTENTS.get();
        for (Block block : ArsNouveauRegistry.blocks()) {
            loot.custom(block, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block)
                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                    .include(contents)))));
        }
    }

    @Override
    public void json(UmcJsonSink sink) {
        for (UmcMaterial material : UmcMaterial.values()) {
            String id = ArsNouveauRegistry.jarId(material);
            JsonObject variants = new JsonObject();
            for (int fill = 0; fill < FILL_MODELS.length; fill++) {
                String model = id + "/" + FILL_MODELS[fill];
                JsonObject variant = new JsonObject();
                variant.addProperty("model", UltimateModCompats.MODID + ":block/" + model);
                variants.add("fill=" + fill, variant);
                sink.assets("models/block/" + model, fillModel(id, FILL_MODELS[fill]));
            }
            JsonObject blockState = new JsonObject();
            blockState.add("variants", variants);
            sink.assets("blockstates/" + id, blockState);
            sink.assets("models/item/" + id, itemModel(id));
            sink.data("recipe/ars_nouveau/" + id, recipe(material, id));
        }
    }

    private static JsonObject fillModel(String id, String fillModel) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "ars_nouveau:block/source_jar/" + fillModel);
        JsonObject textures = new JsonObject();
        textures.addProperty("2", UltimateModCompats.MODID + ":block/" + id);
        model.add("textures", textures);
        return model;
    }

    private static JsonObject itemModel(String id) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", UltimateModCompats.MODID + ":block/" + id + "/" + FILL_MODELS[0]);
        JsonArray overrides = new JsonArray();
        for (int step = 0; step < ITEM_STEPS.length; step++) {
            JsonObject predicate = new JsonObject();
            predicate.addProperty("ars_nouveau:source", ITEM_STEPS[step]);
            JsonObject override = new JsonObject();
            override.add("predicate", predicate);
            override.addProperty("model", UltimateModCompats.MODID + ":block/" + id + "/" + FILL_MODELS[step + 1]);
            overrides.add(override);
        }
        model.add("overrides", overrides);
        return model;
    }

    /** The jar is made on the enchanting apparatus from a source jar and eight ingots, and keeps the source in it. */
    private static JsonObject recipe(UmcMaterial material, String id) {
        JsonObject recipe = new JsonObject();
        JsonArray conditions = new JsonArray();
        conditions.add(modLoaded("ars_nouveau"));
        conditions.add(modLoaded(material.mod()));
        JsonObject compat = new JsonObject();
        compat.addProperty("type", UltimateModCompats.MODID + ":compat");
        compat.addProperty("compat", CompatSwitches.ARS_NOUVEAU);
        conditions.add(compat);
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "ars_nouveau:enchanting_apparatus");
        recipe.addProperty("keepNbtOfReagent", true);
        JsonArray pedestals = new JsonArray();
        for (int index = 0; index < 8; index++) {
            JsonObject ingot = new JsonObject();
            ingot.addProperty("item", material.ingot());
            pedestals.add(ingot);
        }
        recipe.add("pedestalItems", pedestals);
        JsonObject reagent = new JsonObject();
        reagent.addProperty("item", "ars_nouveau:source_jar");
        recipe.add("reagent", reagent);
        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", UltimateModCompats.MODID + ":" + id);
        recipe.add("result", result);
        recipe.addProperty("sourceCost", ArsNouveauStats.CRAFT_SOURCE);
        return recipe;
    }

    private static JsonObject modLoaded(String mod) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", mod);
        return condition;
    }
}
