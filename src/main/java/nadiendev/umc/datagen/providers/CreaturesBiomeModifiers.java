package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class CreaturesBiomeModifiers {

    private CreaturesBiomeModifiers() {
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderSet<Biome> forests = biomes.getOrThrow(BiomeTags.IS_FOREST);
        HolderSet<Biome> taigas = biomes.getOrThrow(BiomeTags.IS_TAIGA);
        HolderSet<Biome> nether = biomes.getOrThrow(BiomeTags.IS_NETHER);
        HolderSet<Biome> gloomy = HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST), biomes.getOrThrow(Biomes.SWAMP), biomes.getOrThrow(Biomes.MANGROVE_SWAMP));

        spawn(context, "werewolf_forest", forests, CreaturesEntities.WEREWOLF.get(), 6, 1, 2);
        spawn(context, "werewolf_taiga", taigas, CreaturesEntities.WEREWOLF.get(), 6, 1, 2);
        spawn(context, "vampire_gloomy", gloomy, CreaturesEntities.VAMPIRE.get(), 5, 1, 1);
        spawn(context, "ghost_gloomy", gloomy, CreaturesEntities.GHOST.get(), 8, 1, 1);
        spawn(context, "imp_nether", nether, CreaturesEntities.IMP.get(), 12, 1, 3);
        spawn(context, "ent_forest", forests, CreaturesEntities.ENT.get(), 3, 1, 1);
    }

    private static void spawn(BootstrapContext<BiomeModifier> context, String name, HolderSet<Biome> biomes, EntityType<?> type, int weight, int min, int max) {
        ResourceKey<BiomeModifier> key = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name));
        context.register(key, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(biomes, new MobSpawnSettings.SpawnerData(type, weight, min, max)));
    }
}
