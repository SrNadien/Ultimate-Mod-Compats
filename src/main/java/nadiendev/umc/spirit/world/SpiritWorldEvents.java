package nadiendev.umc.spirit.world;

import java.util.List;
import java.util.Optional;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.infusion.InfusionType;
import nadiendev.umc.spirit.infusion.SpiritInfusions;
import nadiendev.umc.spirit.registry.SpiritAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class SpiritWorldEvents {

    private SpiritWorldEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player) || !SpiritTravel.isInSpirit(player)) {
            return;
        }
        if (player.tickCount % 10 == 0 && player.getY() < SpiritConfig.VOID_RESCUE_HEIGHT.get() && !player.isSpectator()) {
            SpiritTravel.rescue(player);
            return;
        }
        Optional<SpiritVisit> visit = player.getExistingData(SpiritAttachments.VISIT);
        if (visit.isPresent() && tickVisit(player, visit.get())) {
            return;
        }
        gainEnergy(player);
    }

    private static boolean tickVisit(ServerPlayer player, SpiritVisit visit) {
        long now = player.level().getGameTime();
        if (visit.returnAt() >= 0 && now >= visit.returnAt()) {
            SpiritTravel.leave(player);
            return true;
        }
        if (visit.countdownEnd() >= 0) {
            if (now >= visit.countdownEnd()) {
                SpiritTravel.leave(player);
                return true;
            }
            if (player.tickCount % 20 == 0) {
                long seconds = (visit.countdownEnd() - now + 19) / 20;
                player.displayClientMessage(Component.translatable("message.umc.returning", seconds), true);
            }
        }
        return false;
    }

    private static void gainEnergy(ServerPlayer player) {
        if (!SpiritConfig.INFUSIONS_ENABLED.get() || !SpiritConfig.WORLD_GAIN_ENABLED.get()) {
            return;
        }
        int interval = SpiritConfig.WORLD_GAIN_INTERVAL_SECONDS.get() * 20;
        if (player.tickCount % interval != 0) {
            return;
        }
        List<InfusionType> available = InfusionType.all().stream().filter(InfusionType::enabled).toList();
        if (available.isEmpty()) {
            return;
        }
        InfusionType type = available.get(player.getRandom().nextInt(available.size()));
        SpiritInfusions current = player.getData(SpiritAttachments.INFUSIONS);
        int max = SpiritConfig.MAX_ENERGY_SECONDS.get() * 20;
        int amount = Math.min(max, current.energy(type) + SpiritConfig.WORLD_GAIN_SECONDS.get() * 20);
        if (amount != current.energy(type)) {
            player.setData(SpiritAttachments.INFUSIONS, current.withEnergy(type, amount));
            player.displayClientMessage(Component.translatable("message.umc.infusion_gained", Component.translatable(type.translationKey())), true);
        }
    }

    public static void startReturn(ServerPlayer player) {
        Optional<SpiritVisit> existing = player.getExistingData(SpiritAttachments.VISIT);
        if (!SpiritTravel.isInSpirit(player)) {
            player.displayClientMessage(Component.translatable("message.umc.not_in_spirit_world"), true);
            return;
        }
        int delay = SpiritConfig.RETURN_DELAY_SECONDS.get();
        if (delay <= 0) {
            SpiritTravel.leave(player);
            return;
        }
        SpiritVisit visit = existing.orElseGet(() -> fallbackVisit(player));
        if (visit.countdownEnd() >= 0) {
            player.setData(SpiritAttachments.VISIT, visit.withCountdown(-1L));
            player.displayClientMessage(Component.translatable("message.umc.return_cancelled"), true);
        } else {
            player.setData(SpiritAttachments.VISIT, visit.withCountdown(player.level().getGameTime() + delay * 20L));
        }
    }

    private static SpiritVisit fallbackVisit(ServerPlayer player) {
        ServerLevel level = player.server.getLevel(player.getRespawnDimension());
        if (level == null || player.getRespawnPosition() == null) {
            level = player.server.overworld();
            return new SpiritVisit(level.dimension(), level.getSharedSpawnPos(), 0.0F, -1L, -1L);
        }
        return new SpiritVisit(level.dimension(), player.getRespawnPosition(), player.getRespawnAngle(), -1L, -1L);
    }
}
