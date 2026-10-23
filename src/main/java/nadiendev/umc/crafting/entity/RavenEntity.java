package nadiendev.umc.crafting.entity;

import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.config.SpeciesConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class RavenEntity extends BirdEntity {

    public RavenEntity(EntityType<? extends RavenEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected SpeciesConfig species() {
        return CraftingConfig.RAVEN;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.55F;
    }
}
