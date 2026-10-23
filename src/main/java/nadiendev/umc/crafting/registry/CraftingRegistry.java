package nadiendev.umc.crafting.registry;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class CraftingRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateModCompats.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, UltimateModCompats.MODID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, UltimateModCompats.MODID);
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, UltimateModCompats.MODID);

    private CraftingRegistry() {
    }

    public static void register(IEventBus modBus) {
        CraftingBlocks.init();
        CraftingItems.init();
        CraftingEntities.init();
        CraftingArmorMaterials.init();
        CraftingBiomeModifiers.init();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        ENTITY_TYPES.register(modBus);
        ARMOR_MATERIALS.register(modBus);
        BIOME_MODIFIERS.register(modBus);
    }
}
