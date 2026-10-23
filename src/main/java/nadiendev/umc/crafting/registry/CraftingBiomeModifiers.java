package nadiendev.umc.crafting.registry;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.crafting.worldgen.ConfigFeaturesModifier;
import nadiendev.umc.crafting.worldgen.ConfigSpawnsModifier;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class CraftingBiomeModifiers {

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConfigFeaturesModifier>> FEATURES = CraftingRegistry.BIOME_MODIFIERS.register("config_features", () -> ConfigFeaturesModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConfigSpawnsModifier>> SPAWNS = CraftingRegistry.BIOME_MODIFIERS.register("config_spawns", () -> ConfigSpawnsModifier.CODEC);

    private CraftingBiomeModifiers() {
    }

    public static void init() {
    }
}
