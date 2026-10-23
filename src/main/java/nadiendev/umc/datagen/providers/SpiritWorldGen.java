package nadiendev.umc.datagen.providers;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.OptionalLong;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import nadiendev.umc.spirit.registry.SpiritEntities;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class SpiritWorldGen {

    private SpiritWorldGen() {
    }

    public static void configuredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        TreeConfiguration tree = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(SpiritBlocks.SPIRIT_LOG.get()),
                new StraightTrunkPlacer(5, 3, 1),
                BlockStateProvider.simple(SpiritBlocks.SPIRIT_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(SpiritBlocks.SPIRIT_SOIL.get()))
                .ignoreVines()
                .build();
        context.register(SpiritWorld.CONFIGURED_TREE, new ConfiguredFeature<>(Feature.TREE, tree));
        context.register(SpiritWorld.CONFIGURED_GRASS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, patch(32, SpiritBlocks.SPIRIT_GRASS.get().defaultBlockState())));
        context.register(SpiritWorld.CONFIGURED_BLOOM, new ConfiguredFeature<>(Feature.RANDOM_PATCH, patch(12, SpiritBlocks.SPIRIT_BLOOM.get().defaultBlockState())));
    }

    private static RandomPatchConfiguration patch(int tries, BlockState state) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state))));
    }

    public static void placedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> tree = features.getOrThrow(SpiritWorld.CONFIGURED_TREE);
        Holder<ConfiguredFeature<?, ?>> grass = features.getOrThrow(SpiritWorld.CONFIGURED_GRASS);
        Holder<ConfiguredFeature<?, ?>> bloom = features.getOrThrow(SpiritWorld.CONFIGURED_BLOOM);
        BlockPredicateFilter onSoil = BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), SpiritBlocks.SPIRIT_SOIL.get()));

        context.register(SpiritWorld.TREES_SPARSE, placed(tree, PlacementUtils.countExtra(0, 0.1F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, onSoil, BiomeFilter.biome()));
        context.register(SpiritWorld.TREES_DENSE, placed(tree, PlacementUtils.countExtra(5, 0.2F, 2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, onSoil, BiomeFilter.biome()));
        context.register(SpiritWorld.GRASS, placed(grass, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        context.register(SpiritWorld.GRASS_DENSE, placed(grass, CountPlacement.of(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        context.register(SpiritWorld.BLOOM, placed(bloom, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        context.register(SpiritWorld.BLOOM_RARE, placed(bloom, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static PlacedFeature placed(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return new PlacedFeature(feature, List.of(modifiers));
    }

    public static void biomes(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(SpiritWorld.SPIRIT_MEADOW, biome(placed, carvers, 0xB9E7F5, 0x8CC8E0, 0x6FC4F0, 0x244E7A, 8, SpiritWorld.TREES_SPARSE, SpiritWorld.GRASS_DENSE, SpiritWorld.BLOOM));
        context.register(SpiritWorld.SPIRIT_WOODS, biome(placed, carvers, 0xA9B5F0, 0x7F8CD8, 0x7F9CF0, 0x2A3A8A, 10, SpiritWorld.TREES_DENSE, SpiritWorld.GRASS, SpiritWorld.BLOOM_RARE));
        context.register(SpiritWorld.WHISPER_BARRENS, biome(placed, carvers, 0xD0D4F0, 0xA8B0D8, 0x9CC0E8, 0x3A4A7A, 12, SpiritWorld.BLOOM_RARE));
    }

    private static Biome biome(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers, int fog, int sky, int water, int waterFog, int nightmareWeight, ResourceKey<PlacedFeature>... features) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placed, carvers);
        for (ResourceKey<PlacedFeature> feature : features) {
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placed.getOrThrow(feature));
        }
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(SpiritEntities.NIGHTMARE.get(), nightmareWeight, 1, 2));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(fog)
                .skyColor(sky)
                .waterColor(water)
                .waterFogColor(waterFog)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.SOUL, 0.004F))
                .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D))
                .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.0F)
                .specialEffects(effects)
                .mobSpawnSettings(spawns.build())
                .generationSettings(generation.build())
                .build();
    }

    public static void dimensionTypes(BootstrapContext<DimensionType> context) {
        context.register(SpiritWorld.TYPE, new DimensionType(
                OptionalLong.of(13000L),
                true,
                false,
                false,
                false,
                1.0D,
                false,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                SpiritWorld.ID,
                0.2F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
    }

    public static void noiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        DensityFunction hills = DensityFunctions.noise(noises.getOrThrow(Noises.SURFACE), 0.6D, 0.0D);
        DensityFunction blobs = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_CHEESE), 1.5D, 1.5D);
        DensityFunction gradient = DensityFunctions.yClampedGradient(0, 128, 1.5D, -1.5D);
        DensityFunction density = DensityFunctions.add(gradient, DensityFunctions.add(
                DensityFunctions.mul(DensityFunctions.constant(0.5D), hills),
                DensityFunctions.mul(DensityFunctions.constant(0.3D), blobs)));
        DensityFunction terrain = DensityFunctions.interpolated(density);
        DensityFunction zero = DensityFunctions.zero();
        NoiseRouter router = new NoiseRouter(
                zero,
                zero,
                zero,
                zero,
                DensityFunctions.noise(noises.getOrThrow(Noises.TEMPERATURE), 3.0D, 0.0D),
                DensityFunctions.noise(noises.getOrThrow(Noises.VEGETATION), 2.5D, 0.0D),
                zero,
                zero,
                zero,
                zero,
                terrain,
                terrain,
                zero,
                zero,
                zero);
        SurfaceRules.RuleSource soil = SurfaceRules.state(SpiritBlocks.SPIRIT_SOIL.get().defaultBlockState());
        SurfaceRules.RuleSource rules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, soil),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, soil));
        context.register(SpiritWorld.NOISE, new NoiseGeneratorSettings(
                NoiseSettings.create(0, 256, 1, 2),
                SpiritBlocks.SPIRIT_STONE.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                router,
                rules,
                List.of(),
                34,
                false,
                false,
                false,
                false));
    }

    public static void levelStems(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> types = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> settings = context.lookup(Registries.NOISE_SETTINGS);
        MultiNoiseBiomeSource source = MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                Pair.of(point(-2.0F, -0.25F), biomes.getOrThrow(SpiritWorld.WHISPER_BARRENS)),
                Pair.of(point(-0.25F, 0.25F), biomes.getOrThrow(SpiritWorld.SPIRIT_MEADOW)),
                Pair.of(point(0.25F, 2.0F), biomes.getOrThrow(SpiritWorld.SPIRIT_WOODS)))));
        context.register(SpiritWorld.STEM, new LevelStem(types.getOrThrow(SpiritWorld.TYPE), new NoiseBasedChunkGenerator(source, settings.getOrThrow(SpiritWorld.NOISE))));
    }

    private static Climate.ParameterPoint point(float temperatureMin, float temperatureMax) {
        Climate.Parameter any = Climate.Parameter.span(-2.0F, 2.0F);
        return new Climate.ParameterPoint(Climate.Parameter.span(temperatureMin, temperatureMax), any, any, any, any, any, 0L);
    }
}
