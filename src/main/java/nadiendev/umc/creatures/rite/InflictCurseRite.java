package nadiendev.umc.creatures.rite;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.form.CreatureKind;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class InflictCurseRite extends Rite {

    private final CreatureKind kind;

    public InflictCurseRite(BaseRiteParams baseParams, RiteParams params, CreatureKind kind) {
        super(baseParams, params);
        this.kind = kind;
    }

    @Override
    protected boolean onStart(RiteParams params) {
        if (!CreaturesConfig.active() || !CreaturesConfig.RITES_ENABLED.get() || !CreaturesConfig.PACT_RITES_ENABLED.get() || params.caster == null) {
            return cancel();
        }
        ServerPlayer caster = level.getServer().getPlayerList().getPlayer(params.caster);
        if (caster == null) {
            return cancel();
        }
        if (CreatureForms.get(caster).isCursed()) {
            caster.displayClientMessage(Component.translatable("message.umc.creatures.rite.already_cursed"), true);
            return cancel();
        }
        if (!CreatureForms.infect(caster, kind)) {
            return cancel();
        }
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 50, 1.2, 0.8, 1.2, 0.05);
        return false;
    }
}
