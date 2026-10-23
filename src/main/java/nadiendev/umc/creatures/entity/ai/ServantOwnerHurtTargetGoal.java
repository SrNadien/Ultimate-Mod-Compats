package nadiendev.umc.creatures.entity.ai;

import java.util.EnumSet;
import nadiendev.umc.creatures.entity.SummonableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class ServantOwnerHurtTargetGoal extends TargetGoal {

    private final SummonableMob servant;
    private LivingEntity victim;
    private int timestamp;

    public ServantOwnerHurtTargetGoal(SummonableMob servant) {
        super(servant, false);
        this.servant = servant;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!servant.isServant() || servant.isStaying()) {
            return false;
        }
        LivingEntity owner = servant.getOwner();
        if (owner == null) {
            return false;
        }
        victim = owner.getLastHurtMob();
        int stamp = owner.getLastHurtMobTimestamp();
        return stamp != timestamp && victim != null && this.canAttack(victim, TargetingConditions.DEFAULT) && servant.canAttack(victim);
    }

    @Override
    public void start() {
        servant.setTarget(victim);
        LivingEntity owner = servant.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtMobTimestamp();
        }
        super.start();
    }
}
