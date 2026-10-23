package nadiendev.umc.creatures.rite;

import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.ServantSpawner;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class SummonMinionsRite extends Rite {

    private final EntityType<?> entity;
    private final int count;

    public SummonMinionsRite(BaseRiteParams baseParams, RiteParams params, EntityType<?> entity, int count) {
        super(baseParams, params);
        this.entity = entity;
        this.count = count;
    }

    @Override
    protected boolean onStart(RiteParams params) {
        if (!CreaturesConfig.active() || !CreaturesConfig.RITES_ENABLED.get() || !CreaturesConfig.NECROMANCY_ENABLED.get() || params.caster == null) {
            return cancel();
        }
        ServerPlayer caster = level.getServer().getPlayerList().getPlayer(params.caster);
        if (caster == null) {
            return cancel();
        }
        if (ServantSpawner.remainingSlots(level, caster) <= 0) {
            caster.displayClientMessage(Component.translatable("message.umc.servants.limit"), true);
            return cancel();
        }
        Vec3 center = Vec3.atCenterOf(pos).add(0.0, 1.0, 0.0);
        int spawned = ServantSpawner.summonMany(level, entity, center, 2.5, caster, count);
        if (spawned == 0) {
            return cancel();
        }
        level.playSound(null, pos, CreaturesSounds.SUMMON.get(), SoundSource.MASTER, 1.5F, 0.8F);
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z, 40, 1.2, 0.6, 1.2, 0.05);
        caster.displayClientMessage(Component.translatable("message.umc.creatures.rite.summoned", spawned), true);
        return false;
    }
}
