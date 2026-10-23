package nadiendev.umc.creatures.rite;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureForms;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CureCurseRite extends Rite {

    public CureCurseRite(BaseRiteParams baseParams, RiteParams params) {
        super(baseParams, params);
    }

    @Override
    protected boolean onStart(RiteParams params) {
        if (!CreaturesConfig.active() || !CreaturesConfig.RITES_ENABLED.get() || !CreaturesConfig.CURE_ENABLED.get() || !CreaturesConfig.CURE_RITE_ENABLED.get()) {
            return cancel();
        }
        ServerPlayer caster = params.caster == null ? null : level.getServer().getPlayerList().getPlayer(params.caster);
        ServerPlayer patient = params.target == null ? caster : level.getServer().getPlayerList().getPlayer(params.target);
        if (patient == null) {
            patient = caster;
        }
        if (patient == null) {
            return cancel();
        }
        if (!CreatureForms.cure(patient)) {
            if (caster != null) {
                caster.displayClientMessage(Component.translatable("message.umc.creatures.rite.not_cursed"), true);
            }
            return cancel();
        }
        Player reported = patient;
        level.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 60, 1.2, 1.0, 1.2, 0.06);
        if (caster != null && reported != caster) {
            caster.displayClientMessage(Component.translatable("message.umc.creatures.rite.cured_other", reported.getDisplayName()), true);
        }
        return false;
    }
}
