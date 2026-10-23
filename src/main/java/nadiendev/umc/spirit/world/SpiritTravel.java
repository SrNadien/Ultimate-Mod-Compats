package nadiendev.umc.spirit.world;

import java.util.Optional;
import nadiendev.umc.spirit.block.SpiritPortalBlock;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.registry.SpiritAttachments;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public final class SpiritTravel {

    private static final double PORTAL_HEIGHT = 3.0D / 16.0D;

    private SpiritTravel() {
    }

    public static boolean isInSpirit(ServerPlayer player) {
        return player.level().dimension() == SpiritWorld.LEVEL;
    }

    public static boolean enter(ServerPlayer player, BlockPos origin, long durationTicks) {
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.WORLD_ENABLED.get() || isInSpirit(player)) {
            return false;
        }
        ServerLevel spirit = player.server.getLevel(SpiritWorld.LEVEL);
        if (spirit == null) {
            player.displayClientMessage(Component.translatable("message.umc.spirit_world_missing"), true);
            return false;
        }
        BlockPos arrival = prepareArrival(spirit, origin.getX(), origin.getZ());
        long returnAt = durationTicks > 0 ? spirit.getGameTime() + durationTicks : -1L;
        player.setData(SpiritAttachments.VISIT, new SpiritVisit(player.level().dimension(), origin.immutable(), player.getYRot(), returnAt, -1L));
        boolean onGate = spirit.getBlockState(arrival).is(SpiritBlocks.SPIRIT_PORTAL.get());
        Vec3 target = Vec3.atBottomCenterOf(arrival).add(0.0D, onGate ? PORTAL_HEIGHT : 0.0D, 0.0D);
        travel(player, spirit, target, player.getYRot());
        player.displayClientMessage(Component.translatable("message.umc.entered_spirit_world"), true);
        return true;
    }

    public static boolean leave(ServerPlayer player) {
        if (!isInSpirit(player)) {
            return false;
        }
        Optional<SpiritVisit> visit = player.getExistingData(SpiritAttachments.VISIT);
        ServerLevel target = null;
        BlockPos pos = null;
        float yaw = player.getYRot();
        if (visit.isPresent()) {
            target = player.server.getLevel(visit.get().originDimension());
            pos = visit.get().originPos();
            yaw = visit.get().originYaw();
        }
        if (target == null) {
            target = player.server.getLevel(player.getRespawnDimension());
            pos = player.getRespawnPosition();
        }
        if (target == null || pos == null) {
            target = player.server.overworld();
            pos = target.getSharedSpawnPos();
        }
        Vec3 destination = safeStand(target, pos);
        player.removeData(SpiritAttachments.VISIT);
        travel(player, target, destination, yaw);
        player.displayClientMessage(Component.translatable("message.umc.left_spirit_world"), true);
        return true;
    }

    public static void rescue(ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();
        BlockPos arrival = prepareArrival(level, player.getBlockX(), player.getBlockZ());
        boolean onGate = level.getBlockState(arrival).is(SpiritBlocks.SPIRIT_PORTAL.get());
        Vec3 target = Vec3.atBottomCenterOf(arrival).add(0.0D, onGate ? PORTAL_HEIGHT : 0.0D, 0.0D);
        player.fallDistance = 0.0F;
        travel(player, level, target, player.getYRot());
    }

    private static void travel(ServerPlayer player, ServerLevel level, Vec3 target, float yaw) {
        player.fallDistance = 0.0F;
        player.changeDimension(new DimensionTransition(level, target, Vec3.ZERO, yaw, player.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND));
        player.setPortalCooldown(SpiritConfig.PORTAL_COOLDOWN_TICKS.get());
    }

    public static BlockPos prepareArrival(ServerLevel level, int x, int z) {
        level.getChunk(x >> 4, z >> 4);
        int top = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        int min = level.getMinBuildHeight();
        boolean ground = top > min + 1;
        int y = ground ? top : Math.min(level.getMaxBuildHeight() - 8, 72);
        BlockPos gate = new BlockPos(x, y, z);
        BlockPos floor = gate.below();
        if (!level.getBlockState(floor).isFaceSturdy(level, floor, Direction.UP)) {
            BlockState stone = SpiritBlocks.SPIRIT_STONE.get().defaultBlockState();
            for (BlockPos pos : BlockPos.betweenClosed(floor.offset(-1, 0, -1), floor.offset(1, 0, 1))) {
                level.setBlock(pos, stone, Block.UPDATE_ALL);
            }
        }
        for (int i = 0; i < 3; i++) {
            BlockPos pos = gate.above(i);
            BlockState state = level.getBlockState(pos);
            if (!state.isAir() && !state.is(SpiritBlocks.SPIRIT_PORTAL.get()) && state.getDestroySpeed(level, pos) >= 0.0F) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
        if (SpiritConfig.ARRIVAL_PLATFORM.get() && !level.getBlockState(gate).is(SpiritBlocks.SPIRIT_PORTAL.get())) {
            level.setBlock(gate, SpiritBlocks.SPIRIT_PORTAL.get().defaultBlockState().setValue(SpiritPortalBlock.ACTIVE, true), Block.UPDATE_ALL);
        }
        return gate;
    }

    private static Vec3 safeStand(ServerLevel level, BlockPos origin) {
        level.getChunkAt(origin);
        if (level.getBlockState(origin).is(SpiritBlocks.SPIRIT_PORTAL.get())) {
            return Vec3.atBottomCenterOf(origin).add(0.0D, PORTAL_HEIGHT, 0.0D);
        }
        int limit = level.getMaxBuildHeight() - 2;
        for (int dy = 0; dy < 24 && origin.getY() + dy < limit; dy++) {
            BlockPos pos = origin.above(dy);
            if (level.getBlockState(pos).getCollisionShape(level, pos).isEmpty() && level.getBlockState(pos.above()).getCollisionShape(level, pos.above()).isEmpty()) {
                return Vec3.atBottomCenterOf(pos);
            }
        }
        return Vec3.atBottomCenterOf(origin.above());
    }
}
