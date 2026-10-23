package nadiendev.umc.creatures.entity.ai;

import java.util.EnumSet;
import nadiendev.umc.creatures.entity.SummonableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class ServantOwnerHurtByTargetGoal extends TargetGoal {

    private final SummonableMob servant;
    private LivingEntity attacker;
    private int timestamp;

    public ServantOwnerHurtByTargetGoal(SummonableMob servant) {
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
        attacker = owner.getLastHurtByMob();
        int stamp = owner.getLastHurtByMobTimestamp();
        return stamp != timestamp && attacker != null && this.canAttack(attacker, TargetingConditions.DEFAULT) && servant.canAttack(attacker);
    }

    @Override
    public void start() {
        servant.setTarget(attacker);
        LivingEntity owner = servant.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}
