package nadiendev.umc.creatures.item;

import java.util.List;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.ServantSpawner;
import nadiendev.umc.creatures.entity.UndeadServantEntity;
import nadiendev.umc.creatures.registry.CreaturesEntities;
import nadiendev.umc.creatures.registry.CreaturesItems;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SummoningHornItem extends Item {

    public SummoningHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 30;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!CreaturesConfig.active() || !CreaturesConfig.NECROMANCY_ENABLED.get() || !CreaturesConfig.HORN_ENABLED.get()) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.creatures.disabled"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide) {
            int cost = CreaturesConfig.HORN_GRAVE_DUST_COST.get();
            if (!player.getAbilities().instabuild && countDust(player) < cost) {
                player.displayClientMessage(Component.translatable("message.umc.horn.no_dust", cost), true);
                return InteractionResultHolder.fail(stack);
            }
            if (ServantSpawner.remainingSlots((ServerLevel) level, player) <= 0) {
                player.displayClientMessage(Component.translatable("message.umc.servants.limit"), true);
                return InteractionResultHolder.fail(stack);
            }
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return stack;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        int cost = CreaturesConfig.HORN_GRAVE_DUST_COST.get();
        if (!player.getAbilities().instabuild) {
            if (countDust(player) < cost) {
                return stack;
            }
            consumeDust(player, cost);
        }
        int spawned = 0;
        for (int i = 0; i < CreaturesConfig.HORN_COUNT.get(); i++) {
            EntityType<UndeadServantEntity> type = level.random.nextBoolean() ? CreaturesEntities.ZOMBIE_SERVANT.get() : CreaturesEntities.SKELETON_SERVANT.get();
            spawned += ServantSpawner.summonMany(serverLevel, type, player.position(), 3.0, player, 1);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(2).value(), SoundSource.PLAYERS, 1.2F, 0.7F);
        level.playSound(null, player.blockPosition(), CreaturesSounds.SUMMON.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.translatable("message.umc.horn.summoned", spawned), true);
        player.getCooldowns().addCooldown(this, CreaturesConfig.HORN_COOLDOWN_SECONDS.get() * 20);
        return stack;
    }

    private static int countDust(Player player) {
        int total = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(CreaturesItems.GRAVE_DUST.get())) {
                total += stack.getCount();
            }
        }
        return total;
    }

    private static void consumeDust(Player player, int amount) {
        int remaining = amount;
        for (ItemStack stack : player.getInventory().items) {
            if (remaining <= 0) {
                return;
            }
            if (stack.is(CreaturesItems.GRAVE_DUST.get())) {
                int taken = Math.min(remaining, stack.getCount());
                stack.shrink(taken);
                remaining -= taken;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip"));
    }
}
