package nadiendev.umc.crafting.config;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class SpeciesConfig {

    public final ModConfigSpec.BooleanValue enabled;
    public final ModConfigSpec.DoubleValue maxHealth;
    public final ModConfigSpec.DoubleValue tameChance;
    public final ModConfigSpec.ConfigValue<List<? extends String>> tameFoods;
    public final ModConfigSpec.ConfigValue<List<? extends String>> bonusEffects;
    public final ModConfigSpec.BooleanValue spawnEnabled;
    public final ModConfigSpec.IntValue spawnWeight;
    public final ModConfigSpec.IntValue spawnMinGroup;
    public final ModConfigSpec.IntValue spawnMaxGroup;
    public final ModConfigSpec.DoubleValue spawnChance;

    SpeciesConfig(ModConfigSpec.Builder builder, String name, double health, List<String> foods, List<String> effects, int weight, int minGroup, int maxGroup, boolean hasWild) {
        builder.push(name);
        enabled = builder.comment("Enables the " + name + " familiar and its bonuses").define("enabled", true);
        maxHealth = builder.comment("Maximum health of the " + name).defineInRange("maxHealth", health, 1.0D, 1024.0D);
        bonusEffects = builder.comment("Effects granted to the owner while the " + name + " familiar is bound, format: effect_id,amplifier")
                .defineListAllowEmpty("bonusEffects", effects, () -> "minecraft:luck,0", CraftingConfig::isEffectSpec);
        if (hasWild) {
            tameChance = builder.comment("Chance to tame the wild " + name + " each time it is fed").defineInRange("tameChance", 0.33D, 0.0D, 1.0D);
            tameFoods = builder.comment("Items that tame and heal the " + name + ", format: item_id")
                    .defineListAllowEmpty("tameFoods", foods, () -> "minecraft:wheat_seeds", CraftingConfig::isIdString);
            spawnEnabled = builder.comment("Enables natural spawning of the wild " + name).define("spawnEnabled", true);
            spawnWeight = builder.comment("Spawn weight of the wild " + name).defineInRange("spawnWeight", weight, 1, 1000);
            spawnMinGroup = builder.comment("Minimum group size of the wild " + name).defineInRange("spawnMinGroup", minGroup, 1, 32);
            spawnMaxGroup = builder.comment("Maximum group size of the wild " + name).defineInRange("spawnMaxGroup", maxGroup, 1, 32);
            spawnChance = builder.comment("Extra chance for a spawn attempt of the wild " + name + " to succeed").defineInRange("spawnChance", 1.0D, 0.0D, 1.0D);
        } else {
            tameChance = null;
            tameFoods = null;
            spawnEnabled = null;
            spawnWeight = null;
            spawnMinGroup = null;
            spawnMaxGroup = null;
            spawnChance = null;
        }
        builder.pop();
    }

    public boolean isTameFood(ItemStack stack) {
        if (tameFoods == null || stack.isEmpty()) {
            return false;
        }
        for (String id : tameFoods.get()) {
            ResourceLocation location = ResourceLocation.tryParse(id);
            if (location == null) {
                continue;
            }
            Item item = BuiltInRegistries.ITEM.get(location);
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
}
