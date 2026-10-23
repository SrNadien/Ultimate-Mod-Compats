package nadiendev.umc.crafting.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.config.SpeciesConfig;
import nadiendev.umc.crafting.registry.CraftingBiomeModifiers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record ConfigSpawnsModifier(HolderSet<Biome> biomes, EntityType<?> entityType, String species) implements BiomeModifier {

    public static final MapCodec<ConfigSpawnsModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConfigSpawnsModifier::biomes),
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(ConfigSpawnsModifier::entityType),
            Codec.STRING.fieldOf("species").forGetter(ConfigSpawnsModifier::species)
    ).apply(instance, ConfigSpawnsModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !biomes.contains(biome)) {
            return;
        }
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.FAMILIARS_ENABLED.get()) {
            return;
        }
        SpeciesConfig config = config();
        if (config == null || !config.enabled.get() || !config.spawnEnabled.get()) {
            return;
        }
        int min = Math.min(config.spawnMinGroup.get(), config.spawnMaxGroup.get());
        int max = Math.max(config.spawnMinGroup.get(), config.spawnMaxGroup.get());
        builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(entityType, config.spawnWeight.get(), min, max));
    }

    private SpeciesConfig config() {
        return switch (species) {
            case "toad" -> CraftingConfig.TOAD;
            case "owl" -> CraftingConfig.OWL;
            case "raven" -> CraftingConfig.RAVEN;
            default -> null;
        };
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CraftingBiomeModifiers.SPAWNS.get();
    }
}
