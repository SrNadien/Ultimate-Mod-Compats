package nadiendev.umc.creatures.item;

import java.util.List;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.ServantSpawner;
import nadiendev.umc.creatures.entity.SummonableMob;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class NecromancerScepterItem extends Item {

    public NecromancerScepterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        ServerLevel serverLevel = (ServerLevel) level;
        if (!CreaturesConfig.active() || !CreaturesConfig.NECROMANCY_ENABLED.get() || !CreaturesConfig.SCEPTER_ENABLED.get()) {
            player.displayClientMessage(Component.translatable("message.umc.creatures.disabled"), true);
            return InteractionResultHolder.fail(stack);
        }
        List<SummonableMob> servants = ServantSpawner.owned(serverLevel, player, CreaturesConfig.SCEPTER_RADIUS.get());
        if (servants.isEmpty()) {
            player.displayClientMessage(Component.translatable("message.umc.scepter.none"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (player.isShiftKeyDown()) {
            servants.forEach(SummonableMob::expire);
            level.playSound(null, player.blockPosition(), CreaturesSounds.BANISH.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable("message.umc.scepter.dismiss", servants.size()), true);
        } else {
            for (SummonableMob servant : servants) {
                servant.setStaying(false);
                servant.setTarget(null);
                servant.randomTeleport(player.getX() + (level.random.nextDouble() - 0.5) * 4.0, player.getY(), player.getZ() + (level.random.nextDouble() - 0.5) * 4.0, true);
                serverLevel.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY() + 1.0, servant.getZ(), 8, 0.3, 0.5, 0.3, 0.02);
            }
            level.playSound(null, player.blockPosition(), CreaturesSounds.SUMMON.get(), SoundSource.PLAYERS, 1.0F, 1.2F);
            player.displayClientMessage(Component.translatable("message.umc.scepter.recall", servants.size()), true);
        }
        player.getCooldowns().addCooldown(this, CreaturesConfig.SCEPTER_COOLDOWN_TICKS.get());
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (!CreaturesConfig.active() || !CreaturesConfig.NECROMANCY_ENABLED.get() || !CreaturesConfig.SCEPTER_ENABLED.get()) {
            return InteractionResult.PASS;
        }
        ServerLevel level = (ServerLevel) player.level();
        if (target instanceof SummonableMob mob && player.getUUID().equals(mob.getOwnerUUID())) {
            mob.setStaying(!mob.isStaying());
            player.displayClientMessage(Component.translatable(mob.isStaying() ? "message.umc.servant.stay" : "message.umc.servant.follow"), true);
            return InteractionResult.CONSUME;
        }
        List<SummonableMob> servants = ServantSpawner.owned(level, player, CreaturesConfig.SCEPTER_RADIUS.get());
        int commanded = 0;
        for (SummonableMob servant : servants) {
            if (servant.canAttack(target)) {
                servant.setStaying(false);
                servant.setTarget(target);
                commanded++;
            }
        }
        if (commanded == 0) {
            return InteractionResult.PASS;
        }
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, target.getX(), target.getY() + target.getBbHeight() + 0.3, target.getZ(), 10, 0.2, 0.1, 0.2, 0.02);
        level.playSound(null, player.blockPosition(), CreaturesSounds.SUMMON.get(), SoundSource.PLAYERS, 0.8F, 1.6F);
        player.displayClientMessage(Component.translatable("message.umc.scepter.attack", commanded), true);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip"));
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip.sneak"));
    }
}
