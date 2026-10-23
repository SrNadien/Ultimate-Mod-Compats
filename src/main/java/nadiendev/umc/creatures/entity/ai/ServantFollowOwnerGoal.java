package nadiendev.umc.creatures.entity.ai;

import java.util.EnumSet;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.SummonableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ServantFollowOwnerGoal extends Goal {

    private final SummonableMob mob;
    private final double speed;
    private LivingEntity owner;
    private int recalc;

    public ServantFollowOwnerGoal(SummonableMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!mob.isServant() || mob.isStaying()) {
            return false;
        }
        LivingEntity candidate = mob.getOwner();
        if (candidate == null || !candidate.isAlive() || candidate.isSpectator() || candidate.level() != mob.level()) {
            return false;
        }
        double start = CreaturesConfig.SERVANTS_FOLLOW_MAX.get();
        if (mob.getTarget() != null && mob.distanceToSqr(candidate) < CreaturesConfig.SERVANTS_TELEPORT_DISTANCE.get() * CreaturesConfig.SERVANTS_TELEPORT_DISTANCE.get()) {
            return false;
        }
        if (mob.distanceToSqr(candidate) < start * start) {
            return false;
        }
        owner = candidate;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (owner == null || !owner.isAlive() || mob.isStaying() || !mob.isServant()) {
            return false;
        }
        double stop = CreaturesConfig.SERVANTS_FOLLOW_MIN.get();
        return mob.distanceToSqr(owner) > stop * stop;
    }

    @Override
    public void start() {
        recalc = 0;
    }

    @Override
    public void stop() {
        owner = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        mob.getLookControl().setLookAt(owner, 10.0F, mob.getMaxHeadXRot());
        if (--recalc > 0) {
            return;
        }
        recalc = 10;
        double teleport = CreaturesConfig.SERVANTS_TELEPORT_DISTANCE.get();
        if (mob.distanceToSqr(owner) >= teleport * teleport) {
            teleportNear();
        } else {
            mob.moveToward(owner, speed);
        }
    }

    private void teleportNear() {
        for (int i = 0; i < 12; i++) {
            double x = owner.getX() + mob.getRandom().nextInt(7) - 3;
            double z = owner.getZ() + mob.getRandom().nextInt(7) - 3;
            double y = owner.getY() + mob.getRandom().nextInt(3) - 1;
            if (mob.randomTeleport(x, y, z, true)) {
                mob.getNavigation().stop();
                return;
            }
        }
    }
}
