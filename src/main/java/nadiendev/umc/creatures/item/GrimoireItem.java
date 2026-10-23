package nadiendev.umc.creatures.item;

import java.util.List;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.ServantSpawner;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import nadiendev.umc.creatures.registry.CreaturesItems;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class GrimoireItem extends Item {

    public GrimoireItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        ServerLevel serverLevel = (ServerLevel) level;
        if (!CreaturesConfig.active() || !CreaturesConfig.DEMONOLOGY_ENABLED.get() || !CreaturesConfig.GRIMOIRE_ENABLED.get()) {
            player.displayClientMessage(Component.translatable("message.umc.creatures.disabled"), true);
            return InteractionResultHolder.fail(stack);
        }
        boolean creative = player.getAbilities().instabuild;
        float cost = CreaturesConfig.GRIMOIRE_HEALTH_COST.get().floatValue();
        if (!creative && player.getHealth() <= cost) {
            player.displayClientMessage(Component.translatable("message.umc.grimoire.too_weak"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (ServantSpawner.remainingSlots(serverLevel, player) <= 0) {
            player.displayClientMessage(Component.translatable("message.umc.servants.limit"), true);
            return InteractionResultHolder.fail(stack);
        }
        boolean needsHorn = CreaturesConfig.GRIMOIRE_REQUIRES_IMP_HORN.get() && !creative;
        ItemStack horn = needsHorn ? findHorn(player) : ItemStack.EMPTY;
        if (needsHorn && horn.isEmpty()) {
            player.displayClientMessage(Component.translatable("message.umc.grimoire.no_horn"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (!creative) {
            player.hurt(player.damageSources().magic(), cost);
            if (needsHorn) {
                horn.shrink(1);
            }
        }
        int spawned = ServantSpawner.summonMany(serverLevel, CreaturesEntities.IMP.get(), player.position().add(0.0, 1.0, 0.0), 2.0, player, CreaturesConfig.GRIMOIRE_IMP_COUNT.get());
        serverLevel.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 1.0, player.getZ(), 20, 0.5, 0.6, 0.5, 0.05);
        level.playSound(null, player.blockPosition(), CreaturesSounds.SUMMON.get(), SoundSource.PLAYERS, 1.0F, 0.6F);
        player.displayClientMessage(Component.translatable("message.umc.grimoire.bound", spawned), true);
        player.getCooldowns().addCooldown(this, CreaturesConfig.GRIMOIRE_COOLDOWN_SECONDS.get() * 20);
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    private static ItemStack findHorn(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(CreaturesItems.IMP_HORN.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip"));
    }
}
