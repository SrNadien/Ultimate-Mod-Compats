package nadiendev.umc.crafting.event;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class PlantEvents {

    private PlantEvents() {
    }

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.PLANTS_ENABLED.get() || !CraftingConfig.SEED_DROP_ENABLED.get()) {
            return;
        }
        ServerLevel level = event.getLevel();
        BlockState state = event.getState();
        if (!isSeedSource(state)) {
            return;
        }
        if (CraftingConfig.SEED_DROP_HOE_ONLY.get() && !(event.getTool().getItem() instanceof HoeItem)) {
            return;
        }
        if (level.random.nextDouble() >= CraftingConfig.SEED_DROP_CHANCE.get()) {
            return;
        }
        ItemStack seeds = new ItemStack(level.random.nextBoolean() ? CraftingItems.WORMWOOD_SEEDS.get() : CraftingItems.VERVAIN_SEEDS.get());
        BlockPos pos = event.getPos();
        event.getDrops().add(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, seeds));
    }

    private static boolean isSeedSource(BlockState state) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        for (String id : CraftingConfig.SEED_DROP_BLOCKS.get()) {
            if (key.toString().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
