package nadiendev.umc.creatures.config;

import nadiendev.umc.core.config.UmcConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CreaturesConfig {

    public static ModConfigSpec.BooleanValue ENABLED;

    public static ModConfigSpec.BooleanValue WEREWOLF_ENABLED;
    public static ModConfigSpec.DoubleValue WEREWOLF_HEALTH_BONUS;
    public static ModConfigSpec.DoubleValue WEREWOLF_DAMAGE_BONUS;
    public static ModConfigSpec.DoubleValue WEREWOLF_SPEED_BONUS;
    public static ModConfigSpec.DoubleValue WEREWOLF_JUMP_BONUS;
    public static ModConfigSpec.DoubleValue WEREWOLF_STEP_BONUS;
    public static ModConfigSpec.DoubleValue WEREWOLF_KNOCKBACK_RESISTANCE;
    public static ModConfigSpec.BooleanValue WEREWOLF_NIGHT_VISION;
    public static ModConfigSpec.BooleanValue WEREWOLF_FORCE_FULL_MOON;
    public static ModConfigSpec.BooleanValue WEREWOLF_REVERT_AT_DAWN;
    public static ModConfigSpec.BooleanValue WEREWOLF_REQUIRE_NIGHT;
    public static ModConfigSpec.DoubleValue WEREWOLF_SILVER_MULTIPLIER;
    public static ModConfigSpec.IntValue WEREWOLF_SILVER_WEAKNESS_SECONDS;
    public static ModConfigSpec.DoubleValue WEREWOLF_EXHAUSTION;
    public static ModConfigSpec.IntValue WEREWOLF_TRANSFORM_COOLDOWN_SECONDS;
    public static ModConfigSpec.IntValue WEREWOLF_ABILITY_COOLDOWN_SECONDS;
    public static ModConfigSpec.DoubleValue WEREWOLF_POUNCE_STRENGTH;
    public static ModConfigSpec.DoubleValue WEREWOLF_POUNCE_DAMAGE;
    public static ModConfigSpec.DoubleValue WEREWOLF_POUNCE_RADIUS;
    public static ModConfigSpec.ConfigValue<String> WEREWOLF_REPELLENT_ITEM;
    public static ModConfigSpec.DoubleValue WEREWOLF_REPELLENT_RADIUS;
    public static ModConfigSpec.IntValue WEREWOLF_REPELLENT_WEAKNESS_SECONDS;

    public static ModConfigSpec.BooleanValue VAMPIRE_ENABLED;
    public static ModConfigSpec.DoubleValue VAMPIRE_HEALTH_BONUS;
    public static ModConfigSpec.DoubleValue VAMPIRE_DAMAGE_BONUS;
    public static ModConfigSpec.DoubleValue VAMPIRE_SPEED_BONUS;
    public static ModConfigSpec.DoubleValue VAMPIRE_JUMP_BONUS;
    public static ModConfigSpec.DoubleValue VAMPIRE_SAFE_FALL_BONUS;
    public static ModConfigSpec.BooleanValue VAMPIRE_NIGHT_VISION;
    public static ModConfigSpec.DoubleValue VAMPIRE_EXHAUSTION;
    public static ModConfigSpec.BooleanValue VAMPIRE_SUNLIGHT_BURNS;
    public static ModConfigSpec.IntValue VAMPIRE_SUNLIGHT_FIRE_SECONDS;
    public static ModConfigSpec.BooleanValue VAMPIRE_HELMET_PROTECTS;
    public static ModConfigSpec.BooleanValue VAMPIRE_RAIN_PROTECTS;
    public static ModConfigSpec.DoubleValue VAMPIRE_STAKE_BONUS_DAMAGE;
    public static ModConfigSpec.IntValue VAMPIRE_STAKE_SLOW_SECONDS;
    public static ModConfigSpec.ConfigValue<String> VAMPIRE_REPELLENT_ITEM;
    public static ModConfigSpec.DoubleValue VAMPIRE_REPELLENT_RADIUS;
    public static ModConfigSpec.IntValue VAMPIRE_REPELLENT_WEAKNESS_SECONDS;
    public static ModConfigSpec.DoubleValue VAMPIRE_DRAIN_RANGE;
    public static ModConfigSpec.DoubleValue VAMPIRE_DRAIN_DAMAGE;
    public static ModConfigSpec.DoubleValue VAMPIRE_DRAIN_HEAL_FACTOR;
    public static ModConfigSpec.IntValue VAMPIRE_DRAIN_FOOD;
    public static ModConfigSpec.IntValue VAMPIRE_TRANSFORM_COOLDOWN_SECONDS;
    public static ModConfigSpec.IntValue VAMPIRE_ABILITY_COOLDOWN_SECONDS;

    public static ModConfigSpec.BooleanValue INFECTION_ENABLED;
    public static ModConfigSpec.DoubleValue INFECTION_WEREWOLF_CHANCE;
    public static ModConfigSpec.DoubleValue INFECTION_VAMPIRE_CHANCE;
    public static ModConfigSpec.BooleanValue INFECTION_FROM_PLAYERS;
    public static ModConfigSpec.BooleanValue INFECTION_SKIP_CREATIVE;
    public static ModConfigSpec.BooleanValue LOSE_CURSE_ON_DEATH;

    public static ModConfigSpec.BooleanValue CURE_ENABLED;
    public static ModConfigSpec.BooleanValue CURE_TONIC_ENABLED;
    public static ModConfigSpec.BooleanValue CURE_RITE_ENABLED;
    public static ModConfigSpec.BooleanValue PACT_RITES_ENABLED;

    public static MobSettings WEREWOLF;
    public static MobSettings VAMPIRE;
    public static MobSettings GHOST;
    public static MobSettings IMP;
    public static MobSettings ENT;
    public static MobSettings ZOMBIE_SERVANT;
    public static MobSettings SKELETON_SERVANT;

    public static ModConfigSpec.BooleanValue WEREWOLF_SPAWN_FULL_MOON_ONLY;
    public static ModConfigSpec.BooleanValue WEREWOLF_USE_MODEL;
    public static ModConfigSpec.BooleanValue WEREWOLF_GLOWING_EYES;
    public static ModConfigSpec.ConfigValue<String> WEREWOLF_EYE_COLOR;
    public static ModConfigSpec.BooleanValue VAMPIRE_MOB_SUNLIGHT_BURNS;
    public static ModConfigSpec.BooleanValue VAMPIRE_MOB_LIFESTEAL;
    public static ModConfigSpec.BooleanValue VAMPIRE_USE_MODEL;
    public static ModConfigSpec.BooleanValue VAMPIRE_GLOWING_EYES;
    public static ModConfigSpec.ConfigValue<String> VAMPIRE_EYE_COLOR;
    public static ModConfigSpec.DoubleValue GHOST_PHYSICAL_MULTIPLIER;
    public static ModConfigSpec.IntValue GHOST_BLINDNESS_SECONDS;
    public static ModConfigSpec.BooleanValue IMP_FIREBALL_ENABLED;
    public static ModConfigSpec.IntValue IMP_FIREBALL_COOLDOWN_TICKS;
    public static ModConfigSpec.BooleanValue IMP_SETS_FIRE;
    public static ModConfigSpec.DoubleValue ENT_FIRE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue ENT_AXE_MULTIPLIER;
    public static ModConfigSpec.BooleanValue ENT_PROVOKED_BY_AXES;

    public static ModConfigSpec.BooleanValue NECROMANCY_ENABLED;
    public static ModConfigSpec.IntValue SERVANTS_MAX_PER_OWNER;
    public static ModConfigSpec.IntValue SERVANTS_LIFETIME_SECONDS;
    public static ModConfigSpec.BooleanValue SERVANTS_FRIENDLY_FIRE;
    public static ModConfigSpec.DoubleValue SERVANTS_FOLLOW_MIN;
    public static ModConfigSpec.DoubleValue SERVANTS_FOLLOW_MAX;
    public static ModConfigSpec.DoubleValue SERVANTS_TELEPORT_DISTANCE;
    public static ModConfigSpec.BooleanValue SCEPTER_ENABLED;
    public static ModConfigSpec.DoubleValue SCEPTER_RADIUS;
    public static ModConfigSpec.IntValue SCEPTER_COOLDOWN_TICKS;
    public static ModConfigSpec.BooleanValue HORN_ENABLED;
    public static ModConfigSpec.IntValue HORN_COUNT;
    public static ModConfigSpec.IntValue HORN_COOLDOWN_SECONDS;
    public static ModConfigSpec.IntValue HORN_GRAVE_DUST_COST;

    public static ModConfigSpec.BooleanValue DEMONOLOGY_ENABLED;
    public static ModConfigSpec.BooleanValue GRIMOIRE_ENABLED;
    public static ModConfigSpec.DoubleValue GRIMOIRE_HEALTH_COST;
    public static ModConfigSpec.IntValue GRIMOIRE_IMP_COUNT;
    public static ModConfigSpec.IntValue GRIMOIRE_COOLDOWN_SECONDS;
    public static ModConfigSpec.BooleanValue GRIMOIRE_REQUIRES_IMP_HORN;

    public static ModConfigSpec.BooleanValue RITES_ENABLED;

    public static ModConfigSpec.BooleanValue HUNTER_ENABLED;
    public static ModConfigSpec.DoubleValue HUNTER_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue HUNTER_DAMAGE_TAKEN_MULTIPLIER;
    public static ModConfigSpec.BooleanValue HUNTER_INFECTION_IMMUNE;

    public static void define(ModConfigSpec.Builder builder) {
        ENABLED = builder.comment("Enables every feature of the creatures module").define("enabled", true);

        builder.comment("Lycanthropy: the werewolf form of cursed players").push("werewolf");
        WEREWOLF_ENABLED = builder.comment("Enables lycanthropy and the werewolf form").define("enabled", true);
        WEREWOLF_HEALTH_BONUS = builder.comment("Extra maximum health while transformed").defineInRange("healthBonus", 10.0, 0.0, 100.0);
        WEREWOLF_DAMAGE_BONUS = builder.comment("Extra attack damage while transformed").defineInRange("damageBonus", 4.0, 0.0, 100.0);
        WEREWOLF_SPEED_BONUS = builder.comment("Movement speed bonus while transformed, as a fraction").defineInRange("speedBonus", 0.25, 0.0, 5.0);
        WEREWOLF_JUMP_BONUS = builder.comment("Extra jump strength while transformed").defineInRange("jumpBonus", 0.12, 0.0, 2.0);
        WEREWOLF_STEP_BONUS = builder.comment("Extra step height while transformed").defineInRange("stepHeightBonus", 0.4, 0.0, 4.0);
        WEREWOLF_KNOCKBACK_RESISTANCE = builder.comment("Knockback resistance while transformed").defineInRange("knockbackResistance", 0.3, 0.0, 1.0);
        WEREWOLF_NIGHT_VISION = builder.comment("Grants night vision while transformed").define("nightVision", true);
        WEREWOLF_FORCE_FULL_MOON = builder.comment("Cursed players are forced to transform on full moon nights").define("forceOnFullMoon", true);
        WEREWOLF_REVERT_AT_DAWN = builder.comment("A forced transformation ends at dawn").define("revertAtDawn", true);
        WEREWOLF_REQUIRE_NIGHT = builder.comment("The player can only transform voluntarily at night").define("requireNightToTransform", false);
        WEREWOLF_SILVER_MULTIPLIER = builder.comment("Damage multiplier taken from silver weapons").defineInRange("silverDamageMultiplier", 2.0, 1.0, 20.0);
        WEREWOLF_SILVER_WEAKNESS_SECONDS = builder.comment("Weakness duration applied by silver weapons").defineInRange("silverWeaknessSeconds", 6, 0, 600);
        WEREWOLF_EXHAUSTION = builder.comment("Extra food exhaustion per second while transformed").defineInRange("exhaustionPerSecond", 0.15, 0.0, 4.0);
        WEREWOLF_TRANSFORM_COOLDOWN_SECONDS = builder.comment("Cooldown between transformations").defineInRange("transformCooldownSeconds", 5, 0, 3600);
        WEREWOLF_ABILITY_COOLDOWN_SECONDS = builder.comment("Cooldown of the pounce ability").defineInRange("abilityCooldownSeconds", 8, 0, 3600);
        WEREWOLF_POUNCE_STRENGTH = builder.comment("Launch strength of the pounce").defineInRange("pounceStrength", 1.5, 0.0, 10.0);
        WEREWOLF_POUNCE_DAMAGE = builder.comment("Damage dealt around the landing spot of the pounce").defineInRange("pounceDamage", 6.0, 0.0, 200.0);
        WEREWOLF_POUNCE_RADIUS = builder.comment("Radius of the pounce landing shockwave").defineInRange("pounceRadius", 3.0, 0.5, 16.0);
        WEREWOLF_REPELLENT_ITEM = builder.comment("Item id that weakens werewolves when held nearby").define("repellentItem", "enchanted:wolfsbane_flower");
        WEREWOLF_REPELLENT_RADIUS = builder.comment("Radius of the repellent effect").defineInRange("repellentRadius", 4.0, 0.0, 32.0);
        WEREWOLF_REPELLENT_WEAKNESS_SECONDS = builder.comment("Weakness duration applied by the repellent").defineInRange("repellentWeaknessSeconds", 3, 0, 600);
        WEREWOLF_SPAWN_FULL_MOON_ONLY = builder.comment("Wild werewolves only spawn on full moon nights").define("spawnOnlyOnFullMoon", false);
        WEREWOLF_USE_MODEL = builder.comment("Use the custom werewolf model (needs the local model pack) for the transformed player and the wild mobs instead of the player skin with glowing eyes").define("useModel", false);
        WEREWOLF_GLOWING_EYES = builder.comment("The transformed player keeps their skin and gets glowing eyes").define("glowingEyes", true);
        WEREWOLF_EYE_COLOR = builder.comment("Color of the glowing eyes as a hex string RRGGBB").define("eyeColor", "33FF33");
        builder.pop();

        builder.comment("Vampirism: the vampire form of cursed players").push("vampire");
        VAMPIRE_ENABLED = builder.comment("Enables vampirism and the vampire form").define("enabled", true);
        VAMPIRE_HEALTH_BONUS = builder.comment("Extra maximum health while transformed").defineInRange("healthBonus", 4.0, 0.0, 100.0);
        VAMPIRE_DAMAGE_BONUS = builder.comment("Extra attack damage while transformed").defineInRange("damageBonus", 3.0, 0.0, 100.0);
        VAMPIRE_SPEED_BONUS = builder.comment("Movement speed bonus while transformed, as a fraction").defineInRange("speedBonus", 0.15, 0.0, 5.0);
        VAMPIRE_JUMP_BONUS = builder.comment("Extra jump strength while transformed").defineInRange("jumpBonus", 0.1, 0.0, 2.0);
        VAMPIRE_SAFE_FALL_BONUS = builder.comment("Extra blocks that can be fallen without damage while transformed").defineInRange("safeFallBonus", 6.0, 0.0, 100.0);
        VAMPIRE_NIGHT_VISION = builder.comment("Grants night vision while transformed").define("nightVision", true);
        VAMPIRE_EXHAUSTION = builder.comment("Extra food exhaustion per second while transformed").defineInRange("exhaustionPerSecond", 0.1, 0.0, 4.0);
        VAMPIRE_SUNLIGHT_BURNS = builder.comment("The vampire form burns under direct sunlight").define("sunlightBurns", true);
        VAMPIRE_SUNLIGHT_FIRE_SECONDS = builder.comment("Seconds on fire when exposed to sunlight").defineInRange("sunlightFireSeconds", 4, 1, 60);
        VAMPIRE_HELMET_PROTECTS = builder.comment("Wearing a helmet prevents sunlight from burning").define("helmetProtects", true);
        VAMPIRE_RAIN_PROTECTS = builder.comment("Rain prevents sunlight from burning").define("rainProtects", true);
        VAMPIRE_STAKE_BONUS_DAMAGE = builder.comment("Extra damage dealt to vampires by a wooden stake").defineInRange("stakeBonusDamage", 10.0, 0.0, 200.0);
        VAMPIRE_STAKE_SLOW_SECONDS = builder.comment("Slowness duration applied to a staked vampire").defineInRange("stakeSlownessSeconds", 5, 0, 600);
        VAMPIRE_REPELLENT_ITEM = builder.comment("Item id that weakens vampires when held nearby").define("repellentItem", "enchanted:garlic");
        VAMPIRE_REPELLENT_RADIUS = builder.comment("Radius of the repellent effect").defineInRange("repellentRadius", 4.0, 0.0, 32.0);
        VAMPIRE_REPELLENT_WEAKNESS_SECONDS = builder.comment("Weakness duration applied by the repellent").defineInRange("repellentWeaknessSeconds", 3, 0, 600);
        VAMPIRE_DRAIN_RANGE = builder.comment("Range of the blood drain ability").defineInRange("drainRange", 3.5, 1.0, 16.0);
        VAMPIRE_DRAIN_DAMAGE = builder.comment("Damage dealt by the blood drain").defineInRange("drainDamage", 5.0, 0.0, 200.0);
        VAMPIRE_DRAIN_HEAL_FACTOR = builder.comment("Fraction of the drain damage healed").defineInRange("drainHealFactor", 1.0, 0.0, 10.0);
        VAMPIRE_DRAIN_FOOD = builder.comment("Food restored by the blood drain").defineInRange("drainFood", 3, 0, 20);
        VAMPIRE_TRANSFORM_COOLDOWN_SECONDS = builder.comment("Cooldown between transformations").defineInRange("transformCooldownSeconds", 5, 0, 3600);
        VAMPIRE_ABILITY_COOLDOWN_SECONDS = builder.comment("Cooldown of the blood drain ability").defineInRange("abilityCooldownSeconds", 6, 0, 3600);
        VAMPIRE_MOB_SUNLIGHT_BURNS = builder.comment("Wild vampires burn under direct sunlight").define("mobSunlightBurns", true);
        VAMPIRE_MOB_LIFESTEAL = builder.comment("Wild vampires heal when they hit their target").define("mobLifesteal", true);
        VAMPIRE_USE_MODEL = builder.comment("Use the custom vampire model (needs the local model pack) for the transformed player and the wild mobs instead of the player skin with glowing eyes").define("useModel", false);
        VAMPIRE_GLOWING_EYES = builder.comment("The transformed player keeps their skin and gets glowing eyes").define("glowingEyes", true);
        VAMPIRE_EYE_COLOR = builder.comment("Color of the glowing eyes as a hex string RRGGBB").define("eyeColor", "FF1A1A");
        builder.pop();

        builder.comment("How the curses spread").push("infection");
        INFECTION_ENABLED = builder.comment("Creature attacks can curse players").define("enabled", true);
        INFECTION_WEREWOLF_CHANCE = builder.comment("Chance that a werewolf hit curses the victim").defineInRange("werewolfBiteChance", 0.3, 0.0, 1.0);
        INFECTION_VAMPIRE_CHANCE = builder.comment("Chance that a vampire hit curses the victim").defineInRange("vampireBiteChance", 0.25, 0.0, 1.0);
        INFECTION_FROM_PLAYERS = builder.comment("Transformed players can also curse other players").define("fromPlayers", false);
        INFECTION_SKIP_CREATIVE = builder.comment("Creative players cannot be cursed by bites").define("skipCreative", true);
        LOSE_CURSE_ON_DEATH = builder.comment("Dying lifts the curse").define("loseCurseOnDeath", false);
        builder.pop();

        builder.comment("Ways to lift a curse").push("cure");
        CURE_ENABLED = builder.comment("Enables every cure").define("enabled", true);
        CURE_TONIC_ENABLED = builder.comment("The purifying tonic lifts curses").define("tonic", true);
        CURE_RITE_ENABLED = builder.comment("The cleansing rite lifts curses").define("rite", true);
        PACT_RITES_ENABLED = builder.comment("Rites that voluntarily inflict a curse on the caster").define("pactRites", true);
        builder.pop();

        builder.comment("The hunter: a player that is set against werewolves and vampires").push("hunter");
        HUNTER_ENABLED = builder.comment("Enables the hunter state").define("enabled", true);
        HUNTER_DAMAGE_MULTIPLIER = builder.comment("Damage multiplier dealt by a hunter to werewolves and vampires").defineInRange("damageMultiplier", 1.5, 1.0, 20.0);
        HUNTER_DAMAGE_TAKEN_MULTIPLIER = builder.comment("Damage multiplier taken by a hunter from werewolves and vampires").defineInRange("damageTakenMultiplier", 0.7, 0.0, 1.0);
        HUNTER_INFECTION_IMMUNE = builder.comment("A hunter cannot be cursed by bites").define("immuneToInfection", true);
        builder.pop();

        builder.comment("Wild and summoned creatures").push("mobs");
        WEREWOLF = new MobSettings(builder, "werewolf", 38.0, 7.0, 0.33);
        VAMPIRE = new MobSettings(builder, "vampire", 30.0, 5.0, 0.3);
        GHOST = new MobSettings(builder, "ghost", 16.0, 3.0, 0.25);
        IMP = new MobSettings(builder, "imp", 14.0, 3.0, 0.3);
        ENT = new MobSettings(builder, "ent", 90.0, 14.0, 0.2);
        ZOMBIE_SERVANT = new MobSettings(builder, "zombie_servant", 24.0, 4.0, 0.26);
        SKELETON_SERVANT = new MobSettings(builder, "skeleton_servant", 18.0, 3.5, 0.28);
        GHOST_PHYSICAL_MULTIPLIER = builder.comment("Multiplier applied to physical damage taken by ghosts").defineInRange("ghost.physicalDamageMultiplier", 0.5, 0.0, 1.0);
        GHOST_BLINDNESS_SECONDS = builder.comment("Blindness applied by a ghost touch").defineInRange("ghost.blindnessSeconds", 3, 0, 60);
        IMP_FIREBALL_ENABLED = builder.comment("Imps throw small fireballs").define("imp.fireballs", true);
        IMP_FIREBALL_COOLDOWN_TICKS = builder.comment("Ticks between imp fireballs").defineInRange("imp.fireballCooldownTicks", 50, 5, 1200);
        IMP_SETS_FIRE = builder.comment("Imp melee hits set the target on fire").define("imp.meleeSetsFire", true);
        ENT_FIRE_MULTIPLIER = builder.comment("Damage multiplier taken by ents from fire").defineInRange("ent.fireMultiplier", 2.0, 1.0, 20.0);
        ENT_AXE_MULTIPLIER = builder.comment("Damage multiplier taken by ents from axes").defineInRange("ent.axeMultiplier", 1.5, 1.0, 20.0);
        ENT_PROVOKED_BY_AXES = builder.comment("Wild ents attack players that carry an axe").define("ent.provokedByAxes", true);
        builder.pop();

        builder.comment("Necromancy: undead servants").push("necromancy");
        NECROMANCY_ENABLED = builder.comment("Enables necromancy items and rites").define("enabled", true);
        SERVANTS_MAX_PER_OWNER = builder.comment("Maximum summoned servants per owner").defineInRange("maxServantsPerOwner", 8, 1, 100);
        SERVANTS_LIFETIME_SECONDS = builder.comment("Servants fade away after this many seconds (0 = never)").defineInRange("servantLifetimeSeconds", 600, 0, 86400);
        SERVANTS_FRIENDLY_FIRE = builder.comment("Servants can hurt their owner and each other").define("friendlyFire", false);
        SERVANTS_FOLLOW_MIN = builder.comment("Distance at which servants stop following").defineInRange("followMinDistance", 3.0, 1.0, 32.0);
        SERVANTS_FOLLOW_MAX = builder.comment("Distance at which servants start following").defineInRange("followStartDistance", 8.0, 2.0, 64.0);
        SERVANTS_TELEPORT_DISTANCE = builder.comment("Distance at which servants teleport to their owner").defineInRange("teleportDistance", 24.0, 4.0, 128.0);
        SCEPTER_ENABLED = builder.comment("Enables the necromancer scepter").define("scepter.enabled", true);
        SCEPTER_RADIUS = builder.comment("Radius of the scepter commands").defineInRange("scepter.radius", 40.0, 4.0, 256.0);
        SCEPTER_COOLDOWN_TICKS = builder.comment("Cooldown of the scepter").defineInRange("scepter.cooldownTicks", 40, 0, 6000);
        HORN_ENABLED = builder.comment("Enables the summoning horn").define("horn.enabled", true);
        HORN_COUNT = builder.comment("Servants summoned per horn blast").defineInRange("horn.count", 2, 1, 16);
        HORN_COOLDOWN_SECONDS = builder.comment("Cooldown of the horn").defineInRange("horn.cooldownSeconds", 60, 0, 3600);
        HORN_GRAVE_DUST_COST = builder.comment("Grave dust consumed per blast").defineInRange("horn.graveDustCost", 1, 0, 64);
        builder.pop();

        builder.comment("Demonology: bound demons").push("demonology");
        DEMONOLOGY_ENABLED = builder.comment("Enables demonology items and rites").define("enabled", true);
        GRIMOIRE_ENABLED = builder.comment("Enables the demonomicon").define("grimoire.enabled", true);
        GRIMOIRE_HEALTH_COST = builder.comment("Health paid to bind a demon").defineInRange("grimoire.healthCost", 6.0, 0.0, 100.0);
        GRIMOIRE_IMP_COUNT = builder.comment("Imps bound per use").defineInRange("grimoire.impCount", 1, 1, 8);
        GRIMOIRE_COOLDOWN_SECONDS = builder.comment("Cooldown of the demonomicon").defineInRange("grimoire.cooldownSeconds", 90, 0, 3600);
        GRIMOIRE_REQUIRES_IMP_HORN = builder.comment("Binding consumes one imp horn").define("grimoire.requiresImpHorn", true);
        builder.pop();

        builder.comment("Circle magic rites added by this module").push("rites");
        RITES_ENABLED = builder.comment("Enables the rites of this module").define("enabled", true);
        builder.pop();

    }

    private CreaturesConfig() {
    }

    public static boolean active() {
        return UmcConfig.SPEC.isLoaded() && ENABLED.get();
    }

    public static final class MobSettings {

        public final ModConfigSpec.BooleanValue enabled;
        public final ModConfigSpec.DoubleValue health;
        public final ModConfigSpec.DoubleValue damage;
        public final ModConfigSpec.DoubleValue speed;
        public final double defaultHealth;
        public final double defaultDamage;
        public final double defaultSpeed;

        MobSettings(ModConfigSpec.Builder builder, String name, double health, double damage, double speed) {
            builder.push(name);
            this.enabled = builder.comment("Enables this creature and its natural spawning").define("enabled", true);
            this.health = builder.comment("Maximum health").defineInRange("health", health, 1.0, 1024.0);
            this.damage = builder.comment("Attack damage").defineInRange("damage", damage, 0.0, 1024.0);
            this.speed = builder.comment("Movement speed").defineInRange("speed", speed, 0.01, 2.0);
            builder.pop();
            this.defaultHealth = health;
            this.defaultDamage = damage;
            this.defaultSpeed = speed;
        }

        public boolean isEnabled() {
            return UmcConfig.SPEC.isLoaded() && ENABLED.get() && enabled.get();
        }

        public double healthValue() {
            return UmcConfig.SPEC.isLoaded() ? health.get() : defaultHealth;
        }

        public double damageValue() {
            return UmcConfig.SPEC.isLoaded() ? damage.get() : defaultDamage;
        }

        public double speedValue() {
            return UmcConfig.SPEC.isLoaded() ? speed.get() : defaultSpeed;
        }
    }
}
