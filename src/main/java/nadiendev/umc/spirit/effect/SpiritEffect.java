package nadiendev.umc.spirit.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SpiritEffect extends MobEffect {

    private final int healInterval;

    public SpiritEffect(MobEffectCategory category, int color, int healInterval) {
        super(category, color);
        this.healInterval = healInterval;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (healInterval > 0 && entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return healInterval > 0 && duration % healInterval == 0;
    }
}
