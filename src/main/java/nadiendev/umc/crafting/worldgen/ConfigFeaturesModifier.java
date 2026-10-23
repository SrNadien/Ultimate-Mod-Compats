package nadiendev.umc.crafting.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.registry.CraftingBiomeModifiers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record ConfigFeaturesModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) implements BiomeModifier {

    public static final MapCodec<ConfigFeaturesModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConfigFeaturesModifier::biomes),
            PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(ConfigFeaturesModifier::features),
            GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(ConfigFeaturesModifier::step)
    ).apply(instance, ConfigFeaturesModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !biomes.contains(biome)) {
            return;
        }
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.PLANTS_ENABLED.get() || !CraftingConfig.WILD_PLANTS_ENABLED.get()) {
            return;
        }
        features.forEach(feature -> builder.getGenerationSettings().addFeature(step, feature));
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CraftingBiomeModifiers.FEATURES.get();
    }
}
