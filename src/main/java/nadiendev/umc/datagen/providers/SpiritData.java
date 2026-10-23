package nadiendev.umc.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.function.BiConsumer;
import java.util.function.Function;
import nadiendev.umc.core.EnchantedIds;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import nadiendev.umc.spirit.registry.SpiritItems;
import nadiendev.umc.spirit.registry.SpiritWorld;
import nadiendev.umc.spirit.rite.SpiritJourneyFactory;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SpiritData implements DataModule {

    @Override
    public void gather(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeClient(), new SpiritBlockStates(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeClient(), new SpiritItemModels(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
    }

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(SpiritBlocks.SPIRIT_STONE.get(), "Spirit Stone");
        lang.add(SpiritBlocks.SPIRIT_SOIL.get(), "Spirit Soil");
        lang.add(SpiritBlocks.SPIRIT_LOG.get(), "Spirit Log");
        lang.add(SpiritBlocks.SPIRIT_LEAVES.get(), "Spirit Leaves");
        lang.add(SpiritBlocks.SPIRIT_PLANKS.get(), "Spirit Planks");
        lang.add(SpiritBlocks.SPIRIT_BLOOM.get(), "Spirit Bloom");
        lang.add(SpiritBlocks.SPIRIT_GRASS.get(), "Spirit Grass");
        lang.add(SpiritBlocks.SPIRIT_PORTAL.get(), "Spirit Portal");
        lang.add(SpiritBlocks.DREAM_CATCHER.get(), "Dream Catcher");
        lang.add(SpiritBlocks.DREAM_LOOM.get(), "Dream Loom");
        lang.add(SpiritBlocks.COVEN_ALTAR.get(), "Coven Altar");
        lang.add(SpiritItems.SPIRIT_DUST.get(), "Spirit Dust");
        lang.add(SpiritItems.DREAM_THREAD.get(), "Dream Thread");
        lang.add(SpiritItems.LEAP_ESSENCE.get(), "Essence of Leaping");
        lang.add(SpiritItems.CLIMB_ESSENCE.get(), "Essence of Clinging");
        lang.add(SpiritItems.SIGHT_ESSENCE.get(), "Essence of Sight");
        lang.add(SpiritItems.NIGHTMARE_SPAWN_EGG.get(), "Nightmare Spawn Egg");
        lang.add("entity.umc.nightmare", "Nightmare");
        lang.add("effect.umc.nightmare", "Nightmare");
        lang.add("effect.umc.restful_dream", "Restful Dream");
        lang.add("effect.umc.coven_bond", "Coven Bond");
        lang.add("infusion.umc.leap", "Spirit Leap");
        lang.add("infusion.umc.climb", "Wall Walking");
        lang.add("infusion.umc.sight", "Spirit Sight");
        lang.add("key.categories.umc_spirit", "Ultimate Mod Compats: Spirit");
        lang.add("key.umc.cycle_infusion", "Cycle Infusion");
        lang.add("key.umc.spirit_return", "Return From the Spirit World");
        lang.add("hud.umc.spirit_time", "Spirit world: %s s");
        lang.add("tooltip.umc.essence", "Grants %s seconds of infusion energy");
        lang.add("message.umc.portal_needs_dust", "The portal needs %s spirit dust to open");
        lang.add("message.umc.portal_inert", "The portal is dormant. Feed it spirit dust");
        lang.add("message.umc.loom_full", "The loom already holds all the threads it can");
        lang.add("message.umc.loom_threads", "Dream threads woven: %s/%s");
        lang.add("message.umc.infusion_disabled", "Infusions are disabled");
        lang.add("message.umc.infusion_full", "Your %s energy is already full");
        lang.add("message.umc.infusion_gained", "You absorb energy: %s");
        lang.add("message.umc.infusion_depleted", "%s has run dry");
        lang.add("message.umc.infusion_on", "Infusion active: %s");
        lang.add("message.umc.infusion_off", "No infusion active");
        lang.add("message.umc.entered_spirit_world", "The veil parts and you step into the spirit world");
        lang.add("message.umc.left_spirit_world", "You return to the world of the living");
        lang.add("message.umc.spirit_world_missing", "The spirit world does not answer");
        lang.add("message.umc.returning", "Returning in %s s");
        lang.add("message.umc.not_in_spirit_world", "You are not in the spirit world");
        lang.add("message.umc.return_cancelled", "Return cancelled");
        lang.add("message.umc.good_dream", "You wake up from a lovely dream");
        lang.add("message.umc.dream_journey", "Your dream carries you beyond the veil...");
        lang.add("message.umc.nightmare", "You wake drenched in cold sweat. Something followed you out of the dream");
        lang.add("message.umc.coven.disabled", "Covens are disabled");
        lang.add("message.umc.coven.bad_name", "A coven name needs between %s and %s characters");
        lang.add("message.umc.coven.already_member", "You already belong to a coven");
        lang.add("message.umc.coven.name_taken", "A coven called %s already exists");
        lang.add("message.umc.coven.created", "You have founded the coven %s");
        lang.add("message.umc.coven.not_member", "You do not belong to a coven");
        lang.add("message.umc.coven.not_leader", "Only the coven leader can do that");
        lang.add("message.umc.coven.target_member", "%s is already in your coven");
        lang.add("message.umc.coven.target_busy", "%s already belongs to a coven");
        lang.add("message.umc.coven.invited", "You invited %s");
        lang.add("message.umc.coven.invite_received", "You were invited to the coven %s by %s. Use /umc coven join or visit its altar");
        lang.add("message.umc.coven.unknown", "There is no coven called %s");
        lang.add("message.umc.coven.not_invited", "You were not invited to %s");
        lang.add("message.umc.coven.full", "The coven %s is full");
        lang.add("message.umc.coven.joined", "%s joined the coven %s");
        lang.add("message.umc.coven.left", "You left the coven %s");
        lang.add("message.umc.coven.member_left", "%s left the coven");
        lang.add("message.umc.coven.target_not_member", "%s is not in your coven");
        lang.add("message.umc.coven.kicked", "You were expelled from the coven %s");
        lang.add("message.umc.coven.member_kicked", "%s was expelled from the coven");
        lang.add("message.umc.coven.disbanded", "The coven %s has been disbanded");
        lang.add("message.umc.coven.info_title", "Coven %s (%s/%s)");
        lang.add("message.umc.coven.info_members", "Members: %s");
        lang.add("message.umc.coven.altar_bound", "This altar now belongs to the coven %s");
        lang.add("message.umc.coven.default_name", "Coven of %s");
        lang.add("message.umc.coven.needs_item", "Founding a coven requires %s x %s");
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(SpiritBlocks.SPIRIT_STONE.get(), "Piedra espiritual");
        lang.add(SpiritBlocks.SPIRIT_SOIL.get(), "Tierra espiritual");
        lang.add(SpiritBlocks.SPIRIT_LOG.get(), "Tronco espiritual");
        lang.add(SpiritBlocks.SPIRIT_LEAVES.get(), "Hojas espirituales");
        lang.add(SpiritBlocks.SPIRIT_PLANKS.get(), "Tablones espirituales");
        lang.add(SpiritBlocks.SPIRIT_BLOOM.get(), "Flor espiritual");
        lang.add(SpiritBlocks.SPIRIT_GRASS.get(), "Hierba espiritual");
        lang.add(SpiritBlocks.SPIRIT_PORTAL.get(), "Portal espiritual");
        lang.add(SpiritBlocks.DREAM_CATCHER.get(), "Atrapasueños");
        lang.add(SpiritBlocks.DREAM_LOOM.get(), "Telar de sueños");
        lang.add(SpiritBlocks.COVEN_ALTAR.get(), "Altar de aquelarre");
        lang.add(SpiritItems.SPIRIT_DUST.get(), "Polvo espiritual");
        lang.add(SpiritItems.DREAM_THREAD.get(), "Hilo de sueños");
        lang.add(SpiritItems.LEAP_ESSENCE.get(), "Esencia de salto");
        lang.add(SpiritItems.CLIMB_ESSENCE.get(), "Esencia de adherencia");
        lang.add(SpiritItems.SIGHT_ESSENCE.get(), "Esencia de visión");
        lang.add(SpiritItems.NIGHTMARE_SPAWN_EGG.get(), "Huevo generador de Pesadilla");
        lang.add("entity.umc.nightmare", "Pesadilla");
        lang.add("effect.umc.nightmare", "Pesadilla");
        lang.add("effect.umc.restful_dream", "Sueño reparador");
        lang.add("effect.umc.coven_bond", "Vínculo de aquelarre");
        lang.add("infusion.umc.leap", "Salto espiritual");
        lang.add("infusion.umc.climb", "Caminar por paredes");
        lang.add("infusion.umc.sight", "Visión espiritual");
        lang.add("key.categories.umc_spirit", "Ultimate Mod Compats: Espíritu");
        lang.add("key.umc.cycle_infusion", "Cambiar infusión");
        lang.add("key.umc.spirit_return", "Volver del mundo espiritual");
        lang.add("hud.umc.spirit_time", "Mundo espiritual: %s s");
        lang.add("tooltip.umc.essence", "Otorga %s segundos de energía de infusión");
        lang.add("message.umc.portal_needs_dust", "El portal necesita %s de polvo espiritual para abrirse");
        lang.add("message.umc.portal_inert", "El portal está dormido. Aliméntalo con polvo espiritual");
        lang.add("message.umc.loom_full", "El telar ya tiene todos los hilos que puede sostener");
        lang.add("message.umc.loom_threads", "Hilos de sueño tejidos: %s/%s");
        lang.add("message.umc.infusion_disabled", "Las infusiones están desactivadas");
        lang.add("message.umc.infusion_full", "Tu energía de %s ya está llena");
        lang.add("message.umc.infusion_gained", "Absorbes energía: %s");
        lang.add("message.umc.infusion_depleted", "%s se ha agotado");
        lang.add("message.umc.infusion_on", "Infusión activa: %s");
        lang.add("message.umc.infusion_off", "Ninguna infusión activa");
        lang.add("message.umc.entered_spirit_world", "El velo se abre y entras en el mundo espiritual");
        lang.add("message.umc.left_spirit_world", "Regresas al mundo de los vivos");
        lang.add("message.umc.spirit_world_missing", "El mundo espiritual no responde");
        lang.add("message.umc.returning", "Volviendo en %s s");
        lang.add("message.umc.not_in_spirit_world", "No estás en el mundo espiritual");
        lang.add("message.umc.return_cancelled", "Regreso cancelado");
        lang.add("message.umc.good_dream", "Despiertas de un sueño maravilloso");
        lang.add("message.umc.dream_journey", "Tu sueño te lleva más allá del velo...");
        lang.add("message.umc.nightmare", "Despiertas empapado en sudor frío. Algo te siguió fuera del sueño");
        lang.add("message.umc.coven.disabled", "Los aquelarres están desactivados");
        lang.add("message.umc.coven.bad_name", "El nombre de un aquelarre debe tener entre %s y %s caracteres");
        lang.add("message.umc.coven.already_member", "Ya perteneces a un aquelarre");
        lang.add("message.umc.coven.name_taken", "Ya existe un aquelarre llamado %s");
        lang.add("message.umc.coven.created", "Has fundado el aquelarre %s");
        lang.add("message.umc.coven.not_member", "No perteneces a ningún aquelarre");
        lang.add("message.umc.coven.not_leader", "Solo la líder o el líder del aquelarre puede hacer eso");
        lang.add("message.umc.coven.target_member", "%s ya está en tu aquelarre");
        lang.add("message.umc.coven.target_busy", "%s ya pertenece a un aquelarre");
        lang.add("message.umc.coven.invited", "Has invitado a %s");
        lang.add("message.umc.coven.invite_received", "%s te ha invitado al aquelarre %s. Usa /umc coven join o visita su altar");
        lang.add("message.umc.coven.unknown", "No existe ningún aquelarre llamado %s");
        lang.add("message.umc.coven.not_invited", "No has sido invitado a %s");
        lang.add("message.umc.coven.full", "El aquelarre %s está lleno");
        lang.add("message.umc.coven.joined", "%s se ha unido al aquelarre %s");
        lang.add("message.umc.coven.left", "Has abandonado el aquelarre %s");
        lang.add("message.umc.coven.member_left", "%s ha abandonado el aquelarre");
        lang.add("message.umc.coven.target_not_member", "%s no está en tu aquelarre");
        lang.add("message.umc.coven.kicked", "Has sido expulsado del aquelarre %s");
        lang.add("message.umc.coven.member_kicked", "%s ha sido expulsado del aquelarre");
        lang.add("message.umc.coven.disbanded", "El aquelarre %s ha sido disuelto");
        lang.add("message.umc.coven.info_title", "Aquelarre %s (%s/%s)");
        lang.add("message.umc.coven.info_members", "Miembros: %s");
        lang.add("message.umc.coven.altar_bound", "Este altar ahora pertenece al aquelarre %s");
        lang.add("message.umc.coven.default_name", "Aquelarre de %s");
        lang.add("message.umc.coven.needs_item", "Fundar un aquelarre requiere %s x %s");
    }

    private static ResourceLocation enchanted(String path) {
        return ResourceLocation.fromNamespaceAndPath("enchanted", path);
    }

    private static Item enchantedItem(String path) {
        return BuiltInRegistries.ITEM.get(enchanted(path));
    }

    private static ShapedRecipeBuilder shaped(ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, count);
    }

    private static void finish(ShapedRecipeBuilder builder, ItemLike trigger, RecipeOutput output) {
        builder.unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(trigger)).save(output);
    }

    @Override
    public void recipes(RecipeOutput output) {
        finish(shaped(SpiritBlocks.SPIRIT_PLANKS.get(), 4).pattern("L").define('L', SpiritBlocks.SPIRIT_LOG.get()), SpiritBlocks.SPIRIT_LOG.get(), output);

        finish(shaped(SpiritItems.SPIRIT_DUST.get(), 4)
                .pattern(" A ")
                .pattern("GSG")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('G', Items.GLOWSTONE_DUST)
                .define('S', enchantedItem("spirit_of_otherwhere")), enchantedItem("spirit_of_otherwhere"), output);

        finish(shaped(SpiritItems.DREAM_THREAD.get(), 2)
                .pattern("SDS")
                .define('S', Items.STRING)
                .define('D', SpiritItems.SPIRIT_DUST.get()), SpiritItems.SPIRIT_DUST.get(), output);

        finish(shaped(SpiritBlocks.DREAM_CATCHER.get(), 1)
                .pattern(" S ")
                .pattern("TDT")
                .pattern("F F")
                .define('S', Items.STICK)
                .define('T', Items.STRING)
                .define('D', SpiritItems.DREAM_THREAD.get())
                .define('F', Items.FEATHER), SpiritItems.DREAM_THREAD.get(), output);

        finish(shaped(SpiritBlocks.DREAM_LOOM.get(), 1)
                .pattern("TTT")
                .pattern("PSP")
                .pattern("PPP")
                .define('T', SpiritItems.DREAM_THREAD.get())
                .define('P', ItemTags.PLANKS)
                .define('S', Items.STICK), SpiritItems.DREAM_THREAD.get(), output);

        finish(shaped(SpiritBlocks.SPIRIT_PORTAL.get(), 1)
                .pattern("ADA")
                .pattern("OEO")
                .pattern("OOO")
                .define('A', Items.AMETHYST_SHARD)
                .define('D', SpiritItems.SPIRIT_DUST.get())
                .define('O', Items.OBSIDIAN)
                .define('E', Items.ENDER_PEARL), SpiritItems.SPIRIT_DUST.get(), output);

        finish(shaped(SpiritBlocks.COVEN_ALTAR.get(), 1)
                .pattern("DAD")
                .pattern("OTO")
                .pattern("OOO")
                .define('D', SpiritItems.SPIRIT_DUST.get())
                .define('A', Items.AMETHYST_BLOCK)
                .define('T', enchantedItem("attuned_stone"))
                .define('O', Items.OBSIDIAN), SpiritItems.SPIRIT_DUST.get(), output);

        finish(shaped(SpiritItems.LEAP_ESSENCE.get(), 1)
                .pattern(" F ")
                .pattern("SDS")
                .define('F', Items.FEATHER)
                .define('S', Items.SLIME_BALL)
                .define('D', SpiritItems.SPIRIT_DUST.get()), SpiritItems.SPIRIT_DUST.get(), output);

        finish(shaped(SpiritItems.CLIMB_ESSENCE.get(), 1)
                .pattern(" E ")
                .pattern("SDS")
                .define('E', Items.SPIDER_EYE)
                .define('S', Items.STRING)
                .define('D', SpiritItems.SPIRIT_DUST.get()), SpiritItems.SPIRIT_DUST.get(), output);

        finish(shaped(SpiritItems.SIGHT_ESSENCE.get(), 1)
                .pattern(" E ")
                .pattern("GDG")
                .define('E', Items.ENDER_EYE)
                .define('G', Items.GLOWSTONE_DUST)
                .define('D', SpiritItems.SPIRIT_DUST.get()), SpiritItems.SPIRIT_DUST.get(), output);
    }

    @Override
    public void blockTags(Function<TagKey<Block>, IntrinsicTagAppender<Block>> tags) {
        tags.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(SpiritBlocks.SPIRIT_STONE.get(), SpiritBlocks.SPIRIT_PORTAL.get(), SpiritBlocks.COVEN_ALTAR.get());
        tags.apply(BlockTags.MINEABLE_WITH_AXE).add(SpiritBlocks.SPIRIT_LOG.get(), SpiritBlocks.SPIRIT_PLANKS.get(), SpiritBlocks.DREAM_LOOM.get());
        tags.apply(BlockTags.MINEABLE_WITH_SHOVEL).add(SpiritBlocks.SPIRIT_SOIL.get());
        tags.apply(BlockTags.MINEABLE_WITH_HOE).add(SpiritBlocks.SPIRIT_LEAVES.get());
        tags.apply(BlockTags.LOGS).add(SpiritBlocks.SPIRIT_LOG.get());
        tags.apply(BlockTags.LOGS_THAT_BURN).add(SpiritBlocks.SPIRIT_LOG.get());
        tags.apply(BlockTags.LEAVES).add(SpiritBlocks.SPIRIT_LEAVES.get());
        tags.apply(BlockTags.PLANKS).add(SpiritBlocks.SPIRIT_PLANKS.get());
        tags.apply(BlockTags.SMALL_FLOWERS).add(SpiritBlocks.SPIRIT_BLOOM.get());
        tags.apply(BlockTags.REPLACEABLE_BY_TREES).add(SpiritBlocks.SPIRIT_GRASS.get(), SpiritBlocks.SPIRIT_BLOOM.get());
    }

    @Override
    public void itemTags(Function<TagKey<Item>, IntrinsicTagAppender<Item>> tags) {
        tags.apply(ItemTags.LOGS).add(SpiritBlocks.SPIRIT_LOG_ITEM.get());
        tags.apply(ItemTags.LOGS_THAT_BURN).add(SpiritBlocks.SPIRIT_LOG_ITEM.get());
        tags.apply(ItemTags.LEAVES).add(SpiritBlocks.SPIRIT_LEAVES_ITEM.get());
        tags.apply(ItemTags.PLANKS).add(SpiritBlocks.SPIRIT_PLANKS_ITEM.get());
        tags.apply(ItemTags.SMALL_FLOWERS).add(SpiritBlocks.SPIRIT_BLOOM_ITEM.get());
    }

    @Override
    public void blockLoot(UmcBlockLoot loot) {
        loot.self(SpiritBlocks.SPIRIT_STONE.get());
        loot.self(SpiritBlocks.SPIRIT_SOIL.get());
        loot.self(SpiritBlocks.SPIRIT_LOG.get());
        loot.self(SpiritBlocks.SPIRIT_PLANKS.get());
        loot.self(SpiritBlocks.SPIRIT_BLOOM.get());
        loot.self(SpiritBlocks.SPIRIT_PORTAL.get());
        loot.self(SpiritBlocks.DREAM_CATCHER.get());
        loot.self(SpiritBlocks.DREAM_LOOM.get());
        loot.self(SpiritBlocks.COVEN_ALTAR.get());
        loot.custom(SpiritBlocks.SPIRIT_LEAVES.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)))
                        .add(LootItem.lootTableItem(SpiritBlocks.SPIRIT_LEAVES.get())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.05F))
                        .add(LootItem.lootTableItem(SpiritItems.SPIRIT_DUST.get()))));
        loot.custom(SpiritBlocks.SPIRIT_GRASS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.25F))
                        .add(LootItem.lootTableItem(SpiritItems.SPIRIT_DUST.get()))));
    }

    @Override
    public void entityLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(ResourceKey.create(Registries.LOOT_TABLE, SpiritWorld.id("entities/nightmare")), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(SpiritItems.SPIRIT_DUST.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.06F))
                        .add(LootItem.lootTableItem(SpiritItems.LEAP_ESSENCE.get())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.06F))
                        .add(LootItem.lootTableItem(SpiritItems.CLIMB_ESSENCE.get())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.06F))
                        .add(LootItem.lootTableItem(SpiritItems.SIGHT_ESSENCE.get()))));
    }

    @Override
    public void registries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, SpiritWorldGen::configuredFeatures)
                .add(Registries.PLACED_FEATURE, SpiritWorldGen::placedFeatures)
                .add(Registries.BIOME, SpiritWorldGen::biomes)
                .add(Registries.DIMENSION_TYPE, SpiritWorldGen::dimensionTypes)
                .add(Registries.NOISE_SETTINGS, SpiritWorldGen::noiseSettings)
                .add(Registries.LEVEL_STEM, SpiritWorldGen::levelStems);
    }

    @Override
    public void json(UmcJsonSink sink) {
        JsonObject rite = new JsonObject();
        JsonArray items = new JsonArray();
        items.add(riteItem("umc:spirit_dust", 3));
        items.add(riteItem("enchanted:spirit_of_otherwhere", 1));
        items.add(riteItem("enchanted:ender_dew", 1));
        rite.add("items", items);
        JsonObject shapes = new JsonObject();
        shapes.addProperty("enchanted:small_circle", "enchanted:otherwhere_chalk");
        rite.add("shapes", shapes);
        rite.addProperty("power", 1500);
        JsonObject factory = new JsonObject();
        factory.addProperty("type", SpiritJourneyFactory.ID.toString());
        rite.add("factory", factory);
        sink.data(EnchantedIds.NAMESPACE, "enchanted/circle_magic/rite/spirit_journey", rite);
    }

    private static JsonObject riteItem(String id, int count) {
        JsonObject item = new JsonObject();
        item.addProperty("id", id);
        item.addProperty("count", count);
        return item;
    }
}
