package nadiendev.umc.mysticalagriculture.catalog;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MysticalCropCatalog {

    private static final String RESOURCE_TYPE = "mysticalagriculture:resource";

    private static final Map<String, String> ESSENCE_RESULTS = Map.ofEntries(
            Map.entry("prosperity_shard", "mysticalagriculture:prosperity_shard"),
            Map.entry("allthemodium", "allthemodium:allthemodium_ingot"),
            Map.entry("unobtainium", "allthemodium:unobtainium_ingot"),
            Map.entry("vibranium", "allthemodium:vibranium_ingot"),
            Map.entry("andesite_alloy", "create:andesite_alloy"),
            Map.entry("arcane_gold_ingot", "eidolon:arcane_gold_ingot"),
            Map.entry("blaze_gold", "justdirethings:blazegold_ingot"),
            Map.entry("celestigem", "justdirethings:celestigem"),
            Map.entry("eclipse_alloy", "justdirethings:eclipsealloy_ingot"),
            Map.entry("ferricore", "justdirethings:ferricore_ingot"),
            Map.entry("cognition", "experienceobelisk:cognitive_alloy"),
            Map.entry("dark_gem", "evilcraft:dark_gem"),
            Map.entry("deorum_ingot", "forbidden_arcanus:deorum_ingot"),
            Map.entry("ender_ingot", "extendedcrafting:ender_ingot"),
            Map.entry("redstoneingot", "extendedcrafting:redstone_ingot"),
            Map.entry("naquadah", "mekanism_extras:ingot_naquadah"),
            Map.entry("nadienite_ingot", "ultimatearmoury:nadienite_ingot"),
            Map.entry("chromium", "mysticalexpansion:etherium_ingot"),
            Map.entry("source_gem", "ars_nouveau:source_gem"),
            Map.entry("flux_dust", "fluxnetworks:flux_dust"),
            Map.entry("sky_steel_ingot", "megacells:sky_steel_ingot"),
            Map.entry("enchanted_ingot", "excessive_utilities:enchanted_ingot"),
            Map.entry("demon_ingot", "excessive_utilities:demon_ingot"),
            Map.entry("evil_infused_iron_ingot", "excessive_utilities:evil_infused_iron_ingot"),
            Map.entry("quantum_alloy", "advanced_ae:quantum_alloy"),
            Map.entry("unstable_ingot", "excessive_utilities:semi_unstable_nugget"),
            Map.entry("entro", "extendedae:entro_crystal"),
            Map.entry("entro_ingot", "extendedae:entro_crystal"),
            Map.entry("thaumaturge_crystal_aer", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_aqua", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_ignis", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_ordo", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_perditio", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_terra", "thaumaturge:essentia_crystal"),
            Map.entry("thaumaturge_crystal_vitium", "thaumaturge:essentia_crystal"),
            Map.entry("crystal_matrix", "avaritia:crystal_matrix_ingot")
    );

    private static final Map<String, String> ESSENCE_ASPECTS = Map.of(
            "thaumaturge_crystal_aer", "thaumaturge:aer",
            "thaumaturge_crystal_aqua", "thaumaturge:aqua",
            "thaumaturge_crystal_ignis", "thaumaturge:ignis",
            "thaumaturge_crystal_ordo", "thaumaturge:ordo",
            "thaumaturge_crystal_perditio", "thaumaturge:perditio",
            "thaumaturge_crystal_terra", "thaumaturge:terra",
            "thaumaturge_crystal_vitium", "thaumaturge:vitium"
    );

    private static final int ASPECT_CRYSTALS_PER_CRAFT = 4;

    private static final List<MysticalCropDefinition> ALL = List.of(
            crop("allthemodium", "mysticalagradditions:6", false, "allthemodium:allthemodium_block", List.of("allthemodium", "mysticalagradditions"), List.of(), "Allthemodium", "Allthemodium"),
            crop("andesite_alloy", "mysticalagriculture:5", false, "create:andesite_alloy_block", List.of("create"), List.of(), "Andesite Alloy", "Aleación de andesita"),
            crop("arcane_gold_ingot", "mysticalagriculture:5", false, "eidolon:arcane_gold_block", List.of("eidolon"), List.of(), "Arcane Gold", "Oro arcano"),
            crop("black_quartz", "mysticalagriculture:3", true, "c:gems/black_quartz", List.of(), List.of("actuallyadditions", "occultism"), "Black Quartz", "Cuarzo negro"),
            crop("blaze_gold", "mysticalagradditions:6", false, "justdirethings:blazegold_block", List.of("justdirethings", "mysticalagradditions"), List.of(), "Blaze Gold", "Oro de blaze"),
            crop("cajita_feliz", "mysticalagriculture:1", false, "ultimatefoods:cajita_feliz", List.of("ultimatefoods"), List.of(), "Happy Box", "Cajita feliz"),
            crop("celestigem", "mysticalagradditions:6", false, "justdirethings:celestigem_block", List.of("justdirethings", "mysticalagradditions"), List.of(), "Celestigem", "Gema celestial"),
            crop("chaos_shard", "mysticalagradditions:6", false, "draconicevolution:chaos_shard", List.of("draconicevolution", "mysticalagradditions"), List.of(), "Chaos Shard", "Fragmento del caos"),
            crop("chromium", "mysticalagradditions:6", false, "mysticalexpansion:etherium_ingot_block", List.of("mysticalexpansion", "mysticalagradditions"), List.of(), "Chromium", "Cromo"),
            crop("cognition", "mysticalagriculture:5", false, "experienceobelisk:cognitive_alloy_block", List.of("experienceobelisk"), List.of(), "Cognitive Alloy", "Aleación cognitiva"),
            crop("crystal_matrix", "mysticalagriculture:5", false, "avaritia:crystal_matrix", List.of("avaritia"), List.of(), "Crystal Matrix", "Matriz de cristal"),
            crop("cuarzorosa", "mysticalagradditions:6", false, "create:polished_rose_quartz", List.of("create", "mysticalagradditions"), List.of(), "Rose Quartz", "Cuarzo rosa"),
            crop("dark_gem", "mysticalagriculture:3", false, "evilcraft:dark_block", List.of("evilcraft"), List.of(), "Dark Gem", "Gema oscura"),
            crop("deorum_ingot", "mysticalagriculture:4", false, "forbidden_arcanus:deorum_block", List.of("forbidden_arcanus"), List.of(), "Deorum", "Deorum"),
            crop("eclipse_alloy", "mysticalagradditions:6", false, "justdirethings:eclipsealloy_block", List.of("justdirethings", "mysticalagradditions"), List.of(), "Eclipse Alloy", "Aleación de eclipse"),
            crop("elevatium_ingot", "mysticalagriculture:4", false, "ultimatearmoury:ascendium_ingot", List.of("ultimatearmoury"), List.of(), "Elevatium", "Elevatium"),
            crop("ender_ingot", "mysticalagriculture:5", false, "extendedcrafting:ender_ingot_block", List.of("extendedcrafting"), List.of(), "Ender Ingot", "Lingote de ender"),
            crop("entro", "mysticalagriculture:5", false, "extendedae:entro_block", List.of("extendedae"), List.of(), "Entro", "Entro"),
            crop("ferricore", "mysticalagradditions:6", false, "justdirethings:ferricore_block", List.of("justdirethings", "mysticalagradditions"), List.of(), "FerriCore", "FerriCore"),
            crop("flux_dust", "mysticalagriculture:1", false, "fluxnetworks:flux_block", List.of("fluxnetworks"), List.of(), "Flux", "Flux"),
            crop("imperium_essence", "mysticalagriculture:4", false, "mysticalagriculture:imperium_essence", List.of(), List.of(), "Imperium", "Imperium"),
            crop("insanium_essence", "mysticalagradditions:6", false, "mysticalagriculture:supremium_essence", List.of("mysticalagradditions"), List.of(), "Insanium", "Insanium"),
            crop("kivi", "mysticalagriculture:2", false, "xycraft_world:kivi", List.of("xycraft_world"), List.of(), "Kivi", "Kivi"),
            crop("nadienite_ingot", "mysticalagriculture:5", false, "ultimatearmoury:nadienite_block_1x", List.of("ultimatearmoury"), List.of(), "Nadienite", "Nadienite"),
            crop("naquadah", "mysticalagradditions:6", false, "mekanism_extras:block_naquadah", List.of("mekanism_extras", "mysticalagradditions"), List.of(), "Naquadah", "Naquadah"),
            crop("pink_slime", "mysticalagriculture:4", false, "industrialforegoing:pink_slime_ingot", List.of("industrialforegoing"), List.of(), "Pink Slime", "Slime rosa"),
            crop("predicciondematrix", "mysticalagriculture:3", false, "hostilenetworks:prediction_matrix", List.of("hostilenetworks"), List.of(), "Prediction Matrix", "Matriz de predicción"),
            crop("prometheum", "mysticalagriculture:4", false, "oritech:prometheum_ingot", List.of("oritech"), List.of(), "Prometheum", "Prometeo"),
            crop("prosperity_shard", "mysticalagriculture:5", false, "mysticalagriculture:prosperity_block", List.of(), List.of(), "Prosperity Shard", "Fragmento de prosperidad"),
            crop("prudentium_essence", "mysticalagriculture:2", false, "mysticalagriculture:prudentium_essence", List.of(), List.of(), "Prudentium", "Prudentium"),
            crop("redstoneingot", "mysticalagriculture:2", false, "extendedcrafting:redstone_ingot_block", List.of("extendedcrafting"), List.of(), "Redstone Ingot", "Lingote de redstone"),
            crop("sky_steel_ingot", "mysticalagriculture:5", false, "megacells:sky_steel_block", List.of("megacells"), List.of(), "Sky Steel", "Acero celeste"),
            crop("source_gem", "mysticalagriculture:2", false, "ars_nouveau:source_gem_block", List.of("ars_nouveau"), List.of(), "Source Gem", "Gema de fuente"),
            crop("spirit_gem", "mysticalagriculture:3", false, "occultism:spirit_campfire", List.of("occultism"), List.of(), "Spirit Gem", "Gema espiritual"),
            crop("super_cacotas", "mysticalagriculture:5", false, "ultimate_utilities:super_caca", List.of("ultimate_utilities"), List.of(), "Super Cacotas", "Súper cacotas"),
            crop("supremium_essence_esc", "mysticalagriculture:5", false, "mysticalagriculture:supremium_essence", List.of(), List.of(), "Supremium", "Supremium"),
            crop("tertium_essence", "mysticalagriculture:3", false, "mysticalagriculture:tertium_essence", List.of(), List.of(), "Tertium", "Tertium"),
            crop("thaumaturge_alumentum", "mysticalagriculture:4", false, "thaumaturge:alumentum", List.of("thaumaturge"), List.of(), "Alumentum", "Alumentum"),
            crop("thaumaturge_amber", "mysticalagriculture:3", false, "thaumaturge:amber", List.of("thaumaturge"), List.of(), "Amber", "Ámbar"),
            crop("thaumaturge_brass", "mysticalagriculture:2", false, "thaumaturge:ingot_brass", List.of("thaumaturge"), List.of(), "Thaumaturge Brass", "Latón taumatúrgico"),
            crop("thaumaturge_crystal_aer", "mysticalagriculture:3", false, "thaumaturge:crystal_aer", List.of("thaumaturge"), List.of(), "Aer Crystal", "Cristal de Aer"),
            crop("thaumaturge_crystal_aqua", "mysticalagriculture:3", false, "thaumaturge:crystal_aqua", List.of("thaumaturge"), List.of(), "Aqua Crystal", "Cristal de Aqua"),
            crop("thaumaturge_crystal_ignis", "mysticalagriculture:3", false, "thaumaturge:crystal_ignis", List.of("thaumaturge"), List.of(), "Ignis Crystal", "Cristal de Ignis"),
            crop("thaumaturge_crystal_ordo", "mysticalagriculture:3", false, "thaumaturge:crystal_ordo", List.of("thaumaturge"), List.of(), "Ordo Crystal", "Cristal de Ordo"),
            crop("thaumaturge_crystal_perditio", "mysticalagriculture:4", false, "thaumaturge:crystal_perditio", List.of("thaumaturge"), List.of(), "Perditio Crystal", "Cristal de Perditio"),
            crop("thaumaturge_crystal_terra", "mysticalagriculture:3", false, "thaumaturge:crystal_terra", List.of("thaumaturge"), List.of(), "Terra Crystal", "Cristal de Terra"),
            crop("thaumaturge_crystal_vitium", "mysticalagriculture:4", false, "thaumaturge:crystal_vitium", List.of("thaumaturge"), List.of(), "Vitium Crystal", "Cristal de Vitium"),
            crop("thaumaturge_quicksilver", "mysticalagriculture:2", false, "thaumaturge:quicksilver", List.of("thaumaturge"), List.of(), "Quicksilver", "Mercurio"),
            crop("thaumaturge_salis_mundus", "mysticalagriculture:2", false, "thaumaturge:salis_mundus", List.of("thaumaturge"), List.of(), "Salis Mundus", "Salis Mundus"),
            crop("thaumaturge_thaumium", "mysticalagriculture:3", false, "thaumaturge:ingot_thaumium", List.of("thaumaturge"), List.of(), "Thaumium", "Taumio"),
            crop("thaumaturge_void_metal", "mysticalagriculture:4", false, "thaumaturge:ingot_void", List.of("thaumaturge"), List.of(), "Void Metal", "Metal del vacío"),
            crop("unobtainium", "mysticalagradditions:6", true, "c:storage_blocks/unobtainium", List.of("allthemodium", "mysticalagradditions"), List.of(), "Unobtainium", "Unobtainium"),
            crop("vibranium", "mysticalagradditions:6", true, "c:storage_blocks/vibranium", List.of("allthemodium", "mysticalagradditions"), List.of(), "Vibranium", "Vibranio"),
            crop("xychorium_gem", "mysticalagriculture:3", true, "c:gems/xychorium", List.of("xycraft_world"), List.of(), "Xychorium Gem", "Gema de xychorium"),
            crop("darkstone", "mysticalagriculture:3", false, "forbidden_arcanus:darkstone", List.of("forbidden_arcanus"), List.of(), "Darkstone", "Piedra oscura"),
            crop("crimson_iron", "mysticalagriculture:3", true, "c:ingots/crimson_iron", List.of("occultism"), List.of(), "Crimson Iron", "Hierro carmesí"),
            crop("azure_silver", "mysticalagriculture:3", true, "c:ingots/azure_silver", List.of("occultism"), List.of(), "Azure Silver", "Plata azur"),
            crop("unexplored_wood", "mysticalagriculture:2", true, "regions_unexplored:logs", List.of("regions_unexplored"), List.of(), "Unexplored Wood", "Madera inexplorada"),
            crop("amberite_ingot", "mysticalagriculture:2", false, "ultimatearmoury:amberite_ingot", List.of("ultimatearmoury"), List.of(), "Amberite", "Amberita"),
            crop("lustrous_dust", "mysticalagriculture:2", false, "ultimatearmoury:lustrous_dust", List.of("ultimatearmoury"), List.of(), "Lustrous Dust", "Polvo lustroso"),
            crop("tenebrite_ingot", "mysticalagriculture:3", false, "ultimatearmoury:tenebrite_ingot", List.of("ultimatearmoury"), List.of(), "Tenebrite", "Tenebrita"),
            crop("gorium_ingot", "mysticalagriculture:3", false, "ultimatearmoury:gorium_ingot", List.of("ultimatearmoury"), List.of(), "Gorium", "Gorio"),
            crop("luminite_ingot", "mysticalagriculture:3", false, "ultimatearmoury:luminite_ingot", List.of("ultimatearmoury"), List.of(), "Luminite", "Luminita"),
            crop("viridite_gem", "mysticalagriculture:3", false, "ultimatearmoury:viridite_gem", List.of("ultimatearmoury"), List.of(), "Viridite", "Viridita"),
            crop("osmirite_ingot", "mysticalagriculture:4", false, "ultimatearmoury:osmirite_ingot", List.of("ultimatearmoury"), List.of(), "Osmirite", "Osmirita"),
            crop("umbrite_ingot", "mysticalagriculture:4", false, "ultimatearmoury:umbrite_ingot", List.of("ultimatearmoury"), List.of(), "Umbrite", "Umbrita"),
            crop("ionite_ingot", "mysticalagriculture:4", false, "ultimatearmoury:ionite_ingot", List.of("ultimatearmoury"), List.of(), "Ionite", "Ionita"),
            crop("pyrestone_crystal", "mysticalagriculture:4", false, "ultimatearmoury:pyrestone_crystal", List.of("ultimatearmoury"), List.of(), "Pyrestone Crystal", "Cristal de pirestona"),
            crop("aurelite_crystal", "mysticalagriculture:4", false, "ultimatearmoury:aurelite_crystal", List.of("ultimatearmoury"), List.of(), "Aurelite Crystal", "Cristal de aurelita"),
            crop("infernium_ingot", "mysticalagriculture:5", false, "ultimatearmoury:infernium_ingot", List.of("ultimatearmoury"), List.of(), "Infernium", "Infernium"),
            crop("quantum_infused_dust", "mysticalagriculture:4", false, "advanced_ae:quantum_infused_dust", List.of("advanced_ae"), List.of(), "Quantum Infused Dust", "Polvo infundido cuántico"),
            crop("entro_ingot", "mysticalagriculture:4", false, "extendedae:entro_ingot", List.of("extendedae"), List.of(), "Entro Ingot", "Lingote de entro"),
            crop("unstable_ingot", "mysticalagriculture:4", false, "excessive_utilities:unstable_ingot", List.of("excessive_utilities"), List.of(), "Unstable Ingot", "Lingote inestable"),
            crop("quantum_alloy", "mysticalagriculture:5", false, "advanced_ae:quantum_alloy_block", List.of("advanced_ae"), List.of(), "Quantum Alloy", "Aleación cuántica"),
            crop("glass", "mysticalagriculture:3", false, "minecraft:glass", List.of(), List.of(), "Glass", "Vidrio"),
            crop("enchanted_ingot", "mysticalagradditions:6", false, "excessive_utilities:block_of_enchanted_metal", List.of("excessive_utilities", "mysticalagradditions"), List.of(), "Enchanted Ingot", "Lingote encantado"),
            crop("demon_ingot", "mysticalagradditions:6", false, "excessive_utilities:block_of_demon_metal", List.of("excessive_utilities", "mysticalagradditions"), List.of(), "Demon Ingot", "Lingote demoníaco"),
            crop("evil_infused_iron_ingot", "mysticalagradditions:6", false, "excessive_utilities:block_of_evil_infused_iron", List.of("excessive_utilities", "mysticalagradditions"), List.of(), "Evil Infused Iron", "Hierro infundido con maldad"),
            crop("bedrockium_ingot", "mysticalextendedtier:10", false, "excessive_utilities:bedrockium_ingot", List.of("excessive_utilities", "mysticalextendedtier"), List.of(), "Bedrockium Ingot", "Lingote de bedrockium"),
            crop("soul_dust", "mysticalagriculture:2", false, "mysticalagriculture:soul_dust", List.of(), List.of(), "Soul Dust", "Polvo de alma"),
            crop("soulstone_cobble", "mysticalagriculture:2", false, "mysticalagriculture:soulstone_cobble", List.of(), List.of(), "Soulstone Cobblestone", "Adoquín de piedra de alma"),
            crop("ornium_essence", "mysticalextendedtier:7", false, "mysticalagradditions:insanium_essence", List.of("mysticalagradditions", "mysticalextendedtier"), List.of(), "Ornium", "Ornium"),
            crop("lightium_essence", "mysticalextendedtier:8", false, "mysticalextendedtier:ornium_essence", List.of("mysticalextendedtier"), List.of(), "Lightium", "Lightium"),
            crop("tornium_essence", "mysticalextendedtier:9", false, "mysticalextendedtier:lightium_essence", List.of("mysticalextendedtier"), List.of(), "Tornium", "Tornium"),
            crop("ouranium_essence", "mysticalextendedtier:10", false, "mysticalextendedtier:tornium_essence", List.of("mysticalextendedtier"), List.of(), "Ouranium", "Ouranium")
    );

    private MysticalCropCatalog() {
    }

    /** The Thaumaturge aspect of the vis crystal that the essence of a crop gives, if it gives one. */
    public static java.util.Optional<String> essenceAspect(String cropId) {
        return java.util.Optional.ofNullable(ESSENCE_ASPECTS.get(cropId));
    }

    public static int aspectCrystalsPerCraft() {
        return ASPECT_CRYSTALS_PER_CRAFT;
    }

    public static List<MysticalCropDefinition> all() {
        return ALL;
    }

    private static MysticalCropDefinition crop(String id, String tier, boolean tagIngredient, String ingredient,
                                               List<String> requires, List<String> requiresAny, String english, String spanish) {
        Map<String, String> names = new LinkedHashMap<>();
        names.put("en_us", english);
        names.put("es_es", spanish);
        String essenceResult = ESSENCE_RESULTS.getOrDefault(id, tagIngredient ? null : ingredient);
        return new MysticalCropDefinition(id, tier, RESOURCE_TYPE, tagIngredient, ingredient, requires, requiresAny, Map.copyOf(names), essenceResult);
    }
}
