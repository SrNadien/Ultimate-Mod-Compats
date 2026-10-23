package nadiendev.umc.spirit.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpiritWorld {

    public static final ResourceLocation ID = id("spirit_world");

    public static final ResourceKey<Level> LEVEL = ResourceKey.create(Registries.DIMENSION, ID);
    public static final ResourceKey<LevelStem> STEM = ResourceKey.create(Registries.LEVEL_STEM, ID);
    public static final ResourceKey<DimensionType> TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ID);
    public static final ResourceKey<NoiseGeneratorSettings> NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, ID);

    public static final ResourceKey<Biome> SPIRIT_MEADOW = biome("spirit_meadow");
    public static final ResourceKey<Biome> SPIRIT_WOODS = biome("spirit_woods");
    public static final ResourceKey<Biome> WHISPER_BARRENS = biome("whisper_barrens");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_TREE = configured("spirit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_GRASS = configured("spirit_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BLOOM = configured("spirit_bloom_patch");

    public static final ResourceKey<PlacedFeature> TREES_SPARSE = placed("spirit_trees_sparse");
    public static final ResourceKey<PlacedFeature> TREES_DENSE = placed("spirit_trees_dense");
    public static final ResourceKey<PlacedFeature> GRASS = placed("spirit_grass_patch");
    public static final ResourceKey<PlacedFeature> GRASS_DENSE = placed("spirit_grass_patch_dense");
    public static final ResourceKey<PlacedFeature> BLOOM = placed("spirit_bloom_patch");
    public static final ResourceKey<PlacedFeature> BLOOM_RARE = placed("spirit_bloom_patch_rare");

    private SpiritWorld() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }

    private static ResourceKey<Biome> biome(String name) {
        return ResourceKey.create(Registries.BIOME, id(name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(name));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id(name));
    }
}
