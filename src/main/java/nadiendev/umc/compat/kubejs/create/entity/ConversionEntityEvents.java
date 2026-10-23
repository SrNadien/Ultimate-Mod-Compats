package nadiendev.umc.compat.kubejs.create.entity;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.config.KubeCompatConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class ConversionEntityEvents {

    private ConversionEntityEvents() {
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide() || event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!KubeCompatConfig.moduleEnabled("create")) {
            return;
        }
        ResourceLocation target = BuiltInRegistries.ENTITY_TYPE.getKey(event.getTarget().getType());
        ItemStack held = event.getItemStack();
        for (ConversionEntityRegistry.Entry entry : ConversionEntityRegistry.entries()) {
            if (!entry.entity().equals(target) || !entry.input().test(held)) {
                continue;
            }
            Player player = event.getEntity();
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            ItemStack result = entry.result().copy();
            if (!player.getInventory().add(result)) {
                player.drop(result, false);
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        ConversionEntityPayload payload = new ConversionEntityPayload(ConversionEntityRegistry.entries());
        event.getRelevantPlayers().forEach(player -> PacketDistributor.sendToPlayer(player, payload));
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ConversionEntityRegistry.clear();
    }
}
