package nadiendev.umc.spirit.config;

import java.util.List;
import nadiendev.umc.core.config.UmcConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class SpiritConfig {


    public static ModConfigSpec.BooleanValue ENABLED;

    public static ModConfigSpec.BooleanValue WORLD_ENABLED;
    public static ModConfigSpec.IntValue PORTAL_ACTIVE_SECONDS;
    public static ModConfigSpec.IntValue PORTAL_DUST_COST;
    public static ModConfigSpec.IntValue PORTAL_COOLDOWN_TICKS;
    public static ModConfigSpec.IntValue RETURN_DELAY_SECONDS;
    public static ModConfigSpec.BooleanValue ARRIVAL_PLATFORM;
    public static ModConfigSpec.IntValue VOID_RESCUE_HEIGHT;
    public static ModConfigSpec.BooleanValue RITE_ENABLED;
    public static ModConfigSpec.IntValue RITE_DURATION_SECONDS;
    public static ModConfigSpec.BooleanValue NIGHTMARE_SPAWNS_ENABLED;
    public static ModConfigSpec.IntValue NIGHTMARE_LIFETIME_SECONDS;
    public static ModConfigSpec.DoubleValue NIGHTMARE_HEALTH;
    public static ModConfigSpec.DoubleValue NIGHTMARE_DAMAGE;
    public static ModConfigSpec.DoubleValue NIGHTMARE_SPEED;
    public static ModConfigSpec.IntValue NIGHTMARE_CURSE_SECONDS;

    public static ModConfigSpec.BooleanValue DREAMS_ENABLED;
    public static ModConfigSpec.IntValue DREAM_SCAN_RADIUS;
    public static ModConfigSpec.DoubleValue NIGHTMARE_CHANCE;
    public static ModConfigSpec.DoubleValue CATCHER_NIGHTMARE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue GOOD_DREAM_CHANCE;
    public static ModConfigSpec.DoubleValue CATCHER_GOOD_DREAM_BONUS;
    public static ModConfigSpec.IntValue MAX_CATCHERS;
    public static ModConfigSpec.BooleanValue LOOM_ENABLED;
    public static ModConfigSpec.DoubleValue LOOM_GOOD_DREAM_BONUS;
    public static ModConfigSpec.DoubleValue JOURNEY_CHANCE;
    public static ModConfigSpec.IntValue JOURNEY_SECONDS;
    public static ModConfigSpec.IntValue NIGHTMARE_COUNT;
    public static ModConfigSpec.IntValue NIGHTMARE_EFFECT_SECONDS;
    public static ModConfigSpec.IntValue RESTFUL_EFFECT_SECONDS;
    public static ModConfigSpec.IntValue GOOD_DREAM_XP;
    public static ModConfigSpec.ConfigValue<List<? extends String>> GOOD_DREAM_REWARDS;

    public static ModConfigSpec.BooleanValue INFUSIONS_ENABLED;
    public static ModConfigSpec.BooleanValue LEAP_ENABLED;
    public static ModConfigSpec.IntValue LEAP_JUMP_AMPLIFIER;
    public static ModConfigSpec.BooleanValue LEAP_NEGATES_FALL;
    public static ModConfigSpec.BooleanValue CLIMB_ENABLED;
    public static ModConfigSpec.DoubleValue CLIMB_SPEED;
    public static ModConfigSpec.BooleanValue SIGHT_ENABLED;
    public static ModConfigSpec.IntValue SIGHT_RADIUS;
    public static ModConfigSpec.BooleanValue SIGHT_GLOWS_ENTITIES;
    public static ModConfigSpec.IntValue ESSENCE_SECONDS;
    public static ModConfigSpec.IntValue MAX_ENERGY_SECONDS;
    public static ModConfigSpec.IntValue DRAIN_PER_SECOND;
    public static ModConfigSpec.BooleanValue WORLD_GAIN_ENABLED;
    public static ModConfigSpec.IntValue WORLD_GAIN_INTERVAL_SECONDS;
    public static ModConfigSpec.IntValue WORLD_GAIN_SECONDS;
    public static ModConfigSpec.BooleanValue HUD_ENABLED;
    public static ModConfigSpec.IntValue HUD_X;
    public static ModConfigSpec.IntValue HUD_Y;

    public static ModConfigSpec.BooleanValue COVENS_ENABLED;
    public static ModConfigSpec.IntValue COVEN_MAX_MEMBERS;
    public static ModConfigSpec.IntValue COVEN_NAME_MAX_LENGTH;
    public static ModConfigSpec.ConfigValue<String> COVEN_FOUND_COST_ITEM;
    public static ModConfigSpec.IntValue COVEN_FOUND_COST_COUNT;
    public static ModConfigSpec.BooleanValue COVEN_OPEN_JOIN;
    public static ModConfigSpec.BooleanValue POWER_BONUS_ENABLED;
    public static ModConfigSpec.IntValue POWER_BONUS_RADIUS;
    public static ModConfigSpec.DoubleValue POWER_BONUS_PER_MEMBER;
    public static ModConfigSpec.IntValue POWER_BONUS_MAX_MEMBERS;
    public static ModConfigSpec.BooleanValue BOND_ENABLED;
    public static ModConfigSpec.IntValue BOND_RADIUS;
    public static ModConfigSpec.IntValue BOND_MIN_MEMBERS;
    public static ModConfigSpec.IntValue BOND_MAX_AMPLIFIER;

    public static void define(ModConfigSpec.Builder builder) {
        ENABLED = builder.comment("Enables every feature of the spirit module").define("enabled", true);

        builder.push("world");
        WORLD_ENABLED = builder.comment("Enables the spirit world and its portals").define("enabled", true);
        PORTAL_ACTIVE_SECONDS = builder.comment("Seconds a spirit portal stays open after being charged").defineInRange("portal_active_seconds", 30, 1, 3600);
        PORTAL_DUST_COST = builder.comment("Spirit dust consumed to charge a spirit portal").defineInRange("portal_dust_cost", 1, 0, 64);
        PORTAL_COOLDOWN_TICKS = builder.comment("Ticks before an entity can use a portal again after travelling").defineInRange("portal_cooldown_ticks", 100, 0, 6000);
        RETURN_DELAY_SECONDS = builder.comment("Seconds the return key takes to bring the player back from the spirit world").defineInRange("return_delay_seconds", 5, 0, 120);
        ARRIVAL_PLATFORM = builder.comment("Builds a small platform and a return gate where the player arrives in the spirit world").define("arrival_platform", true);
        VOID_RESCUE_HEIGHT = builder.comment("Height under which players in the spirit world are pulled back to safe ground").defineInRange("void_rescue_height", 4, -64, 256);
        RITE_ENABLED = builder.comment("Enables the Enchanted rite that sends the caster to the spirit world").define("rite_enabled", true);
        RITE_DURATION_SECONDS = builder.comment("Seconds the rite keeps the caster in the spirit world, 0 for unlimited").defineInRange("rite_duration_seconds", 300, 0, 86400);
        NIGHTMARE_SPAWNS_ENABLED = builder.comment("Allows nightmares to spawn naturally in the spirit world").define("nightmare_spawns_enabled", true);
        NIGHTMARE_LIFETIME_SECONDS = builder.comment("Seconds a summoned dream nightmare lasts, 0 for unlimited").defineInRange("nightmare_lifetime_seconds", 120, 0, 3600);
        NIGHTMARE_HEALTH = builder.comment("Nightmare maximum health").defineInRange("nightmare_health", 24.0D, 1.0D, 1024.0D);
        NIGHTMARE_DAMAGE = builder.comment("Nightmare attack damage").defineInRange("nightmare_damage", 5.0D, 0.0D, 1024.0D);
        NIGHTMARE_SPEED = builder.comment("Nightmare movement speed").defineInRange("nightmare_speed", 0.27D, 0.01D, 2.0D);
        NIGHTMARE_CURSE_SECONDS = builder.comment("Seconds of the nightmare effect a nightmare inflicts on hit, 0 to disable").defineInRange("nightmare_curse_seconds", 8, 0, 600);
        builder.pop();

        builder.push("dreams");
        DREAMS_ENABLED = builder.comment("Enables dream weaving").define("enabled", true);
        DREAM_SCAN_RADIUS = builder.comment("Radius around the bed searched for dream catchers and looms").defineInRange("scan_radius", 6, 1, 16);
        NIGHTMARE_CHANCE = builder.comment("Base chance of a nightmare after a night of sleep").defineInRange("nightmare_chance", 0.15D, 0.0D, 1.0D);
        CATCHER_NIGHTMARE_MULTIPLIER = builder.comment("Nightmare chance multiplier applied once per nearby dream catcher").defineInRange("catcher_nightmare_multiplier", 0.4D, 0.0D, 1.0D);
        GOOD_DREAM_CHANCE = builder.comment("Base chance of a good dream after a night of sleep").defineInRange("good_dream_chance", 0.10D, 0.0D, 1.0D);
        CATCHER_GOOD_DREAM_BONUS = builder.comment("Good dream chance added per nearby dream catcher").defineInRange("catcher_good_dream_bonus", 0.15D, 0.0D, 1.0D);
        MAX_CATCHERS = builder.comment("Maximum number of dream catchers that count").defineInRange("max_catchers", 4, 0, 32);
        LOOM_ENABLED = builder.comment("Enables the dream loom").define("loom_enabled", true);
        LOOM_GOOD_DREAM_BONUS = builder.comment("Good dream chance added by a loaded dream loom, which also removes nightmares").defineInRange("loom_good_dream_bonus", 0.6D, 0.0D, 1.0D);
        JOURNEY_CHANCE = builder.comment("Chance that a good dream becomes a journey to the spirit world").defineInRange("journey_chance", 0.3D, 0.0D, 1.0D);
        JOURNEY_SECONDS = builder.comment("Seconds a dream journey lasts").defineInRange("journey_seconds", 60, 5, 3600);
        NIGHTMARE_COUNT = builder.comment("Nightmares summoned by a bad dream").defineInRange("nightmare_count", 1, 1, 8);
        NIGHTMARE_EFFECT_SECONDS = builder.comment("Seconds of the nightmare effect after a bad dream").defineInRange("nightmare_effect_seconds", 60, 0, 3600);
        RESTFUL_EFFECT_SECONDS = builder.comment("Seconds of the restful dream effect after a good dream").defineInRange("restful_effect_seconds", 300, 0, 7200);
        GOOD_DREAM_XP = builder.comment("Experience points granted by a good dream").defineInRange("good_dream_xp", 20, 0, 10000);
        GOOD_DREAM_REWARDS = builder.comment("Reward items of good dreams as item;count;weight").defineListAllowEmpty("good_dream_rewards",
                List.of("umc:spirit_dust;2;40", "umc:dream_thread;1;30", "minecraft:experience_bottle;3;20", "minecraft:amethyst_shard;4;20", "umc:leap_essence;1;5", "umc:climb_essence;1;5", "umc:sight_essence;1;5"),
                () -> "minecraft:apple;1;1", value -> value instanceof String);
        builder.pop();

        builder.push("infusions");
        INFUSIONS_ENABLED = builder.comment("Enables dimensional infusions").define("enabled", true);
        LEAP_ENABLED = builder.comment("Enables the spirit leap infusion").define("leap_enabled", true);
        LEAP_JUMP_AMPLIFIER = builder.comment("Jump boost amplifier while spirit leap is active").defineInRange("leap_jump_amplifier", 2, 0, 10);
        LEAP_NEGATES_FALL = builder.comment("Spirit leap cancels fall damage").define("leap_negates_fall", true);
        CLIMB_ENABLED = builder.comment("Enables the wall walking infusion").define("climb_enabled", true);
        CLIMB_SPEED = builder.comment("Climbing speed of wall walking in blocks per tick").defineInRange("climb_speed", 0.2D, 0.01D, 1.0D);
        SIGHT_ENABLED = builder.comment("Enables the spirit sight infusion").define("sight_enabled", true);
        SIGHT_RADIUS = builder.comment("Radius in which spirit sight outlines living creatures").defineInRange("sight_radius", 24, 1, 128);
        SIGHT_GLOWS_ENTITIES = builder.comment("Spirit sight makes nearby creatures glow").define("sight_glows_entities", true);
        ESSENCE_SECONDS = builder.comment("Seconds of energy given by one essence").defineInRange("essence_seconds", 120, 1, 86400);
        MAX_ENERGY_SECONDS = builder.comment("Maximum seconds of energy stored per infusion").defineInRange("max_energy_seconds", 900, 1, 86400);
        DRAIN_PER_SECOND = builder.comment("Energy ticks drained per second by the active infusion").defineInRange("drain_per_second", 20, 0, 400);
        WORLD_GAIN_ENABLED = builder.comment("Players slowly gather random infusion energy while in the spirit world").define("world_gain_enabled", true);
        WORLD_GAIN_INTERVAL_SECONDS = builder.comment("Seconds between energy gains in the spirit world").defineInRange("world_gain_interval_seconds", 30, 1, 3600);
        WORLD_GAIN_SECONDS = builder.comment("Seconds of energy gained each time in the spirit world").defineInRange("world_gain_seconds", 15, 1, 3600);
        HUD_ENABLED = builder.comment("Shows the infusion HUD").define("hud_enabled", true);
        HUD_X = builder.comment("HUD horizontal offset").defineInRange("hud_x", 6, 0, 4000);
        HUD_Y = builder.comment("HUD vertical offset").defineInRange("hud_y", 6, 0, 4000);
        builder.pop();

        builder.push("covens");
        COVENS_ENABLED = builder.comment("Enables covens").define("enabled", true);
        COVEN_MAX_MEMBERS = builder.comment("Maximum members of a coven").defineInRange("max_members", 8, 2, 64);
        COVEN_NAME_MAX_LENGTH = builder.comment("Maximum length of a coven name").defineInRange("name_max_length", 32, 3, 64);
        COVEN_FOUND_COST_ITEM = builder.comment("Item consumed to found a coven at an altar, empty for none").define("found_cost_item", "umc:spirit_dust");
        COVEN_FOUND_COST_COUNT = builder.comment("Amount of the founding item consumed").defineInRange("found_cost_count", 3, 0, 64);
        COVEN_OPEN_JOIN = builder.comment("Anyone can join a coven at its altar without an invitation").define("open_join", false);
        POWER_BONUS_ENABLED = builder.comment("Nearby coven members recharge Enchanted altars faster").define("power_bonus_enabled", true);
        POWER_BONUS_RADIUS = builder.comment("Radius around an altar in which coven members count").defineInRange("power_bonus_radius", 16, 1, 128);
        POWER_BONUS_PER_MEMBER = builder.comment("Altar power added per second for each nearby coven member").defineInRange("power_bonus_per_member", 10.0D, 0.0D, 100000.0D);
        POWER_BONUS_MAX_MEMBERS = builder.comment("Maximum members counted for the altar bonus").defineInRange("power_bonus_max_members", 8, 1, 64);
        BOND_ENABLED = builder.comment("Coven members near each other receive the coven bond effect").define("bond_enabled", true);
        BOND_RADIUS = builder.comment("Radius in which coven members bond").defineInRange("bond_radius", 12, 1, 128);
        BOND_MIN_MEMBERS = builder.comment("Members needed together for the bond").defineInRange("bond_min_members", 2, 2, 64);
        BOND_MAX_AMPLIFIER = builder.comment("Maximum amplifier of the coven bond effect").defineInRange("bond_max_amplifier", 2, 0, 10);
        builder.pop();

    }

    private SpiritConfig() {
    }
}
