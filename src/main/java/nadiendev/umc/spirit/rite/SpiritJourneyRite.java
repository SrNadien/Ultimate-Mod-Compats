package nadiendev.umc.spirit.rite;

import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.world.SpiritTravel;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class SpiritJourneyRite extends Rite {

    public SpiritJourneyRite(BaseRiteParams baseParams, RiteParams params) {
        super(baseParams, params);
    }

    @Override
    protected boolean onStart(RiteParams params) {
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.WORLD_ENABLED.get() || !SpiritConfig.RITE_ENABLED.get()) {
            return cancel();
        }
        Entity caster = params.caster == null ? null : findEntity(params.caster);
        if (!(caster instanceof ServerPlayer player) || SpiritTravel.isInSpirit(player)) {
            return cancel();
        }
        level.sendParticles(ParticleTypes.SOUL, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 40, 0.8D, 0.8D, 0.8D, 0.05D);
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.MASTER, 1.0F, 0.5F);
        long duration = SpiritConfig.RITE_DURATION_SECONDS.get() * 20L;
        if (!SpiritTravel.enter(player, pos.above(), duration)) {
            player.sendSystemMessage(Component.translatable("message.umc.spirit_world_missing"));
            return cancel();
        }
        return false;
    }
}
