package nadiendev.umc.spirit.coven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.registry.SpiritEffects;
import net.favouriteless.enchanted.common.blocks.entity.AltarBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CovenEvents {

    private CovenEvents() {
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CovenData data = CovenData.get(player.server);
            data.ofPlayer(player.getUUID()).ifPresent(coven -> data.renameMember(coven, player.getUUID(), player.getGameProfile().getName()));
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = event.getServer();
        if (server.getTickCount() % 20 != 0 || !SpiritConfig.ENABLED.get() || !SpiritConfig.COVENS_ENABLED.get()) {
            return;
        }
        boolean bond = SpiritConfig.BOND_ENABLED.get();
        boolean power = SpiritConfig.POWER_BONUS_ENABLED.get();
        if (!bond && !power) {
            return;
        }
        CovenData data = CovenData.get(server);
        Map<UUID, List<ServerPlayer>> online = new HashMap<>();
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.isSpectator()) {
                continue;
            }
            Optional<Coven> coven = data.ofPlayer(player.getUUID());
            coven.ifPresent(value -> online.computeIfAbsent(value.id(), key -> new ArrayList<>()).add(player));
        }
        for (List<ServerPlayer> members : online.values()) {
            if (bond) {
                applyBond(members);
            }
            if (power) {
                boostAltars(members);
            }
        }
    }

    private static void applyBond(List<ServerPlayer> members) {
        int radius = SpiritConfig.BOND_RADIUS.get();
        int minMembers = SpiritConfig.BOND_MIN_MEMBERS.get();
        int maxAmplifier = SpiritConfig.BOND_MAX_AMPLIFIER.get();
        double radiusSqr = (double) radius * radius;
        for (ServerPlayer player : members) {
            int together = 1;
            for (ServerPlayer other : members) {
                if (other != player && other.level() == player.level() && other.distanceToSqr(player) <= radiusSqr) {
                    together++;
                }
            }
            if (together >= minMembers) {
                int amplifier = Math.min(together - minMembers, maxAmplifier);
                player.addEffect(new MobEffectInstance(SpiritEffects.COVEN_BOND, 100, amplifier, true, false, true));
            }
        }
    }

    private static void boostAltars(List<ServerPlayer> members) {
        int radius = SpiritConfig.POWER_BONUS_RADIUS.get();
        double radiusSqr = (double) radius * radius;
        int maxMembers = SpiritConfig.POWER_BONUS_MAX_MEMBERS.get();
        double perMember = SpiritConfig.POWER_BONUS_PER_MEMBER.get();
        Set<AltarBlockEntity> altars = new HashSet<>();
        for (ServerPlayer player : members) {
            collectAltars((ServerLevel) player.level(), player, radius, radiusSqr, altars);
        }
        for (AltarBlockEntity altar : altars) {
            int near = 0;
            for (ServerPlayer player : members) {
                if (player.level() == altar.getLevel() && player.blockPosition().distSqr(altar.getBlockPos()) <= radiusSqr) {
                    near++;
                }
            }
            if (near > 0) {
                AltarPower.add(altar, Math.min(near, maxMembers) * perMember);
            }
        }
    }

    private static void collectAltars(ServerLevel level, ServerPlayer player, int radius, double radiusSqr, Set<AltarBlockEntity> out) {
        ChunkPos center = player.chunkPosition();
        int chunks = (radius >> 4) + 1;
        for (int dx = -chunks; dx <= chunks; dx++) {
            for (int dz = -chunks; dz <= chunks; dz++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(center.x + dx, center.z + dz);
                if (chunk == null) {
                    continue;
                }
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof AltarBlockEntity altar && altar.getBlockPos().distSqr(player.blockPosition()) <= radiusSqr) {
                        out.add(altar);
                    }
                }
            }
        }
    }
}
