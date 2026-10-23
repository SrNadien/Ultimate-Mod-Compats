package nadiendev.umc.creatures.entity;

import java.util.List;
import nadiendev.umc.creatures.config.CreaturesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ServantSpawner {

    private ServantSpawner() {
    }

    public static List<SummonableMob> owned(ServerLevel level, Player owner, double radius) {
        AABB area = owner.getBoundingBox().inflate(radius);
        return level.getEntitiesOfClass(SummonableMob.class, area, mob -> mob.isAlive() && owner.getUUID().equals(mob.getOwnerUUID()));
    }

    public static int remainingSlots(ServerLevel level, Player owner) {
        return Math.max(0, CreaturesConfig.SERVANTS_MAX_PER_OWNER.get() - owned(level, owner, 128.0).size());
    }

    public static SummonableMob summon(ServerLevel level, EntityType<?> type, Vec3 center, double radius, Player owner) {
        if (!(type.create(level) instanceof SummonableMob entity)) {
            return null;
        }
        Vec3 spot = findSpot(level, center, radius, entity);
        entity.moveTo(spot.x, spot.y, spot.z, level.random.nextFloat() * 360.0F, 0.0F);
        entity.finalizeSpawn(level, level.getCurrentDifficultyAt(BlockPos.containing(spot)), MobSpawnType.MOB_SUMMONED, null);
        entity.bindTo(owner, CreaturesConfig.SERVANTS_LIFETIME_SECONDS.get());
        level.addFreshEntityWithPassengers(entity);
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, spot.x, spot.y + entity.getBbHeight() * 0.5, spot.z, 14, 0.3, 0.5, 0.3, 0.03);
        return entity;
    }

    public static int summonMany(ServerLevel level, EntityType<?> type, Vec3 center, double radius, Player owner, int count) {
        int spawned = 0;
        int slots = remainingSlots(level, owner);
        for (int i = 0; i < count && i < slots; i++) {
            if (summon(level, type, center, radius, owner) != null) {
                spawned++;
            }
        }
        return spawned;
    }

    private static Vec3 findSpot(ServerLevel level, Vec3 center, double radius, SummonableMob entity) {
        boolean flier = entity instanceof GhostEntity || entity instanceof ImpEntity;
        for (int attempt = 0; attempt < 12; attempt++) {
            double x = center.x + (level.random.nextDouble() * 2.0 - 1.0) * radius;
            double z = center.z + (level.random.nextDouble() * 2.0 - 1.0) * radius;
            BlockPos base = BlockPos.containing(x, center.y, z);
            for (int dy = 2; dy >= -3; dy--) {
                BlockPos pos = base.above(dy);
                BlockState below = level.getBlockState(pos.below());
                if (level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir() && (flier || below.isSolid())) {
                    return new Vec3(pos.getX() + 0.5, pos.getY() + (flier ? 0.5 : 0.0), pos.getZ() + 0.5);
                }
            }
        }
        return center;
    }
}
