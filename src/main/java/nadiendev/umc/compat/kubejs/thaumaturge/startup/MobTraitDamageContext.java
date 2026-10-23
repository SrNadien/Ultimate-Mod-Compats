package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class MobTraitDamageContext {

    public final LivingEntity mob;
    @Nullable
    public final LivingEntity other;
    public final DamageSource source;
    public final float amount;

    @HideFromJS
    public MobTraitDamageContext(LivingEntity mob, @Nullable LivingEntity other, DamageSource source, float amount) {
        this.mob = mob;
        this.other = other;
        this.source = source;
        this.amount = amount;
    }
}
