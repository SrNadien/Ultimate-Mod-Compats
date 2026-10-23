package nadiendev.umc.crafting.config;

import java.util.List;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.crafting.util.EffectSpec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CraftingConfig {

    public static ModConfigSpec.BooleanValue ENABLED;

    public static ModConfigSpec.BooleanValue FAMILIARS_ENABLED;
    public static ModConfigSpec.BooleanValue RITES_ENABLED;
    public static ModConfigSpec.IntValue FAMILIAR_BONUS_INTERVAL;
    public static ModConfigSpec.IntValue FAMILIAR_BONUS_RANGE;
    public static SpeciesConfig TOAD;
    public static SpeciesConfig OWL;
    public static SpeciesConfig RAVEN;
    public static SpeciesConfig HOUND;

    public static ModConfigSpec.BooleanValue CLOTHING_ENABLED;
    public static ModConfigSpec.IntValue CLOTHING_EFFECT_INTERVAL;
    public static ModConfigSpec.ConfigValue<List<? extends String>> HAT_EFFECTS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> ROBE_EFFECTS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> CLOAK_EFFECTS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> BOOTS_EFFECTS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> SET_EFFECTS;
    public static ModConfigSpec.BooleanValue ALTAR_BONUS_ENABLED;
    public static ModConfigSpec.IntValue ALTAR_BONUS_MIN_PIECES;
    public static ModConfigSpec.DoubleValue ALTAR_BONUS_POWER;
    public static ModConfigSpec.IntValue ALTAR_BONUS_RADIUS;
    public static ModConfigSpec.IntValue ALTAR_BONUS_INTERVAL;
    public static ModConfigSpec.BooleanValue CURSE_RESISTANCE_ENABLED;
    public static ModConfigSpec.IntValue CURSE_MIN_PIECES;
    public static ModConfigSpec.IntValue CURSE_CHECK_INTERVAL;
    public static ModConfigSpec.DoubleValue CURSE_REMOVE_CHANCE;
    public static ModConfigSpec.BooleanValue POTION_BONUS_ENABLED;
    public static ModConfigSpec.IntValue POTION_MIN_PIECES;
    public static ModConfigSpec.DoubleValue POTION_DURATION_MULTIPLIER;
    public static ModConfigSpec.IntValue POTION_MAX_DURATION;
    public static ModConfigSpec.BooleanValue CLOAK_FALL_ENABLED;
    public static ModConfigSpec.DoubleValue CLOAK_FALL_REDUCTION;

    public static ModConfigSpec.BooleanValue PLANTS_ENABLED;
    public static ModConfigSpec.BooleanValue WILD_PLANTS_ENABLED;
    public static ModConfigSpec.DoubleValue CROP_GROWTH_CHANCE;
    public static ModConfigSpec.BooleanValue SEED_DROP_ENABLED;
    public static ModConfigSpec.DoubleValue SEED_DROP_CHANCE;
    public static ModConfigSpec.BooleanValue SEED_DROP_HOE_ONLY;
    public static ModConfigSpec.ConfigValue<List<? extends String>> SEED_DROP_BLOCKS;

    public static void define(ModConfigSpec.Builder builder) {
        ENABLED = builder.comment("Enables every feature of the crafting module").define("enabled", true);

        builder.push("familiars");
        FAMILIARS_ENABLED = builder.comment("Enables the extra familiars").define("enabled", true);
        RITES_ENABLED = builder.comment("Enables the species specific binding rites").define("ritesEnabled", true);
        FAMILIAR_BONUS_INTERVAL = builder.comment("Ticks between refreshes of the familiar bonuses").defineInRange("bonusInterval", 80, 10, 1200);
        FAMILIAR_BONUS_RANGE = builder.comment("The familiar must be within this many blocks of its owner to grant bonuses, 0 disables the check").defineInRange("bonusRange", 64, 0, 1024);
        TOAD = new SpeciesConfig(builder, "toad", 20.0D, List.of("minecraft:spider_eye", "minecraft:fermented_spider_eye"), List.of("minecraft:jump_boost,1", "minecraft:water_breathing,0"), 6, 1, 2, true);
        OWL = new SpeciesConfig(builder, "owl", 16.0D, List.of("minecraft:rabbit", "minecraft:chicken"), List.of("minecraft:night_vision,0"), 5, 1, 1, true);
        RAVEN = new SpeciesConfig(builder, "raven", 14.0D, List.of("minecraft:rotten_flesh", "minecraft:wheat_seeds"), List.of("minecraft:luck,0", "minecraft:speed,0"), 5, 1, 3, true);
        HOUND = new SpeciesConfig(builder, "hound", 40.0D, List.of(), List.of("minecraft:strength,0", "minecraft:resistance,0"), 0, 1, 1, false);
        builder.pop();

        builder.push("clothing");
        CLOTHING_ENABLED = builder.comment("Enables the bonuses of the witch clothing").define("enabled", true);
        CLOTHING_EFFECT_INTERVAL = builder.comment("Ticks between refreshes of the clothing effects").defineInRange("effectInterval", 80, 10, 1200);
        HAT_EFFECTS = builder.comment("Effects granted by the witch hat, format: effect_id,amplifier")
                .defineListAllowEmpty("hatEffects", List.of(), () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);
        ROBE_EFFECTS = builder.comment("Effects granted by the witch robe, format: effect_id,amplifier")
                .defineListAllowEmpty("robeEffects", List.of(), () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);
        CLOAK_EFFECTS = builder.comment("Effects granted by the witch cloak, format: effect_id,amplifier")
                .defineListAllowEmpty("cloakEffects", List.of(), () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);
        BOOTS_EFFECTS = builder.comment("Effects granted by the witch boots, format: effect_id,amplifier")
                .defineListAllowEmpty("bootsEffects", List.of("minecraft:jump_boost,0"), () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);
        SET_EFFECTS = builder.comment("Effects granted by a complete set (hat, robe or cloak, and boots), format: effect_id,amplifier")
                .defineListAllowEmpty("setEffects", List.of("minecraft:luck,0"), () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);

        builder.push("altar");
        ALTAR_BONUS_ENABLED = builder.comment("Altars near a wearer recharge faster").define("enabled", true);
        ALTAR_BONUS_MIN_PIECES = builder.comment("Pieces needed (1 to 3) for the altar bonus, it scales with the pieces worn").defineInRange("minPieces", 1, 1, 3);
        ALTAR_BONUS_POWER = builder.comment("Altar power added per check with a full set").defineInRange("power", 30.0D, 0.0D, 100000.0D);
        ALTAR_BONUS_RADIUS = builder.comment("Radius in blocks around the wearer where altars are boosted").defineInRange("radius", 16, 1, 64);
        ALTAR_BONUS_INTERVAL = builder.comment("Ticks between altar boosts").defineInRange("interval", 20, 1, 1200);
        builder.pop();

        builder.push("curses");
        CURSE_RESISTANCE_ENABLED = builder.comment("Wearers slowly shake off curses").define("enabled", true);
        CURSE_MIN_PIECES = builder.comment("Pieces needed (1 to 3) for the curse resistance, it scales with the pieces worn").defineInRange("minPieces", 2, 1, 3);
        CURSE_CHECK_INTERVAL = builder.comment("Ticks between curse resistance checks").defineInRange("interval", 200, 20, 24000);
        CURSE_REMOVE_CHANCE = builder.comment("Chance per check, with a full set, to weaken or remove each curse").defineInRange("chance", 0.15D, 0.0D, 1.0D);
        builder.pop();

        builder.push("potions");
        POTION_BONUS_ENABLED = builder.comment("Beneficial potion effects last longer on wearers").define("enabled", true);
        POTION_MIN_PIECES = builder.comment("Pieces needed (1 to 3) for the potion bonus").defineInRange("minPieces", 3, 1, 3);
        POTION_DURATION_MULTIPLIER = builder.comment("Duration multiplier applied to beneficial effects").defineInRange("durationMultiplier", 1.25D, 1.0D, 10.0D);
        POTION_MAX_DURATION = builder.comment("Effects longer than this many ticks are not extended").defineInRange("maxDuration", 36000, 20, 1000000);
        builder.pop();

        builder.push("cloak");
        CLOAK_FALL_ENABLED = builder.comment("The cloak reduces fall damage").define("enabled", true);
        CLOAK_FALL_REDUCTION = builder.comment("Fraction of fall damage removed while wearing the cloak").defineInRange("reduction", 0.5D, 0.0D, 1.0D);
        builder.pop();
        builder.pop();

        builder.push("plants");
        PLANTS_ENABLED = builder.comment("Enables the witch plants").define("enabled", true);
        WILD_PLANTS_ENABLED = builder.comment("Generates wild moonbloom and mugwort in the world").define("wildPlants", true);
        CROP_GROWTH_CHANCE = builder.comment("Chance that a random tick advances a witch crop").defineInRange("cropGrowthChance", 1.0D, 0.0D, 1.0D);
        SEED_DROP_ENABLED = builder.comment("Breaking grass can drop witch seeds").define("seedDrops", true);
        SEED_DROP_CHANCE = builder.comment("Chance of a seed drop when breaking grass").defineInRange("seedDropChance", 0.08D, 0.0D, 1.0D);
        SEED_DROP_HOE_ONLY = builder.comment("Seeds only drop when the grass is broken with a hoe").define("seedDropHoeOnly", false);
        SEED_DROP_BLOCKS = builder.comment("Blocks that can drop witch seeds, format: block_id")
                .defineListAllowEmpty("seedDropBlocks", List.of("minecraft:short_grass", "minecraft:tall_grass", "minecraft:fern"), () -> "minecraft:short_grass", CraftingConfig::isIdString);
        builder.pop();

    }

    private CraftingConfig() {
    }

    static boolean isEffectSpec(Object value) {
        return value instanceof String string && EffectSpec.parse(string) != null;
    }

    static boolean isIdString(Object value) {
        return value instanceof String string && ResourceLocation.tryParse(string) != null;
    }

    public static boolean active() {
        return ENABLED.get();
    }
}
