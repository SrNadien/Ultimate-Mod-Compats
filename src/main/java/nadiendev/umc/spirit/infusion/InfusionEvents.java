package nadiendev.umc.spirit.infusion;

import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.registry.SpiritAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class InfusionEvents {

    private static final int STEP = 10;

    private InfusionEvents() {
    }

    private static boolean enabled() {
        return SpiritConfig.ENABLED.get() && SpiritConfig.INFUSIONS_ENABLED.get();
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!enabled()) {
            return;
        }
        SpiritInfusions data = player.getData(SpiritAttachments.INFUSIONS);
        if (data.active().isEmpty()) {
            return;
        }
        InfusionType type = data.active().get();
        if (player.level().isClientSide) {
            if (type == InfusionType.CLIMB && data.isActive(type)) {
                climb(player);
            }
            return;
        }
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (!data.isActive(type)) {
            serverPlayer.setData(SpiritAttachments.INFUSIONS, data.withActive(null));
            serverPlayer.displayClientMessage(Component.translatable("message.umc.infusion_depleted", Component.translatable(type.translationKey())), true);
            return;
        }
        if (serverPlayer.tickCount % STEP != 0) {
            return;
        }
        int drain = SpiritConfig.DRAIN_PER_SECOND.get() * STEP / 20;
        if (drain > 0) {
            serverPlayer.setData(SpiritAttachments.INFUSIONS, data.withEnergy(type, Math.max(0, data.energy(type) - drain)));
        }
        switch (type) {
            case LEAP -> serverPlayer.addEffect(new MobEffectInstance(MobEffects.JUMP, 30, SpiritConfig.LEAP_JUMP_AMPLIFIER.get(), true, false, false));
            case SIGHT -> sight(serverPlayer);
            default -> {
            }
        }
    }

    private static void climb(Player player) {
        if (!player.horizontalCollision || player.isInWater() || player.isInLava() || player.getAbilities().flying) {
            return;
        }
        Vec3 motion = player.getDeltaMovement();
        double speed = player.isShiftKeyDown() ? 0.0D : SpiritConfig.CLIMB_SPEED.get();
        player.setDeltaMovement(motion.x, speed, motion.z);
        player.fallDistance = 0.0F;
    }

    private static void sight(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false, false));
        if (!SpiritConfig.SIGHT_GLOWS_ENTITIES.get()) {
            return;
        }
        double radius = SpiritConfig.SIGHT_RADIUS.get();
        List<LivingEntity> nearby = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius), entity -> entity != player && !(entity instanceof Player) && entity.distanceToSqr(player) <= radius * radius);
        for (LivingEntity entity : nearby) {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, true, false, false));
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!enabled() || !SpiritConfig.LEAP_NEGATES_FALL.get() || !(event.getEntity() instanceof Player player)) {
            return;
        }
        if (player.getData(SpiritAttachments.INFUSIONS).isActive(InfusionType.LEAP)) {
            event.setDamageMultiplier(0.0F);
        }
    }

    public static void cycle(ServerPlayer player) {
        if (!enabled()) {
            player.displayClientMessage(Component.translatable("message.umc.infusion_disabled"), true);
            return;
        }
        SpiritInfusions data = player.getData(SpiritAttachments.INFUSIONS);
        List<InfusionType> usable = InfusionType.all().stream().filter(type -> type.enabled() && data.energy(type) > 0).toList();
        InfusionType next = null;
        if (!usable.isEmpty()) {
            InfusionType current = data.active().orElse(null);
            if (current == null) {
                next = usable.getFirst();
            } else {
                int index = usable.indexOf(current);
                next = index >= 0 && index + 1 < usable.size() ? usable.get(index + 1) : null;
                if (index < 0) {
                    next = usable.getFirst();
                }
            }
        }
        player.setData(SpiritAttachments.INFUSIONS, data.withActive(next));
        if (next == null) {
            player.displayClientMessage(Component.translatable("message.umc.infusion_off"), true);
        } else {
            player.displayClientMessage(Component.translatable("message.umc.infusion_on", Component.translatable(next.translationKey())), true);
        }
    }
}
