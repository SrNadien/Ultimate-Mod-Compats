package nadiendev.umc.creatures.item;

import java.util.List;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureForms;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PurifyingTonicItem extends Item {

    public PurifyingTonicItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!CreaturesConfig.active() || !CreaturesConfig.CURE_ENABLED.get() || !CreaturesConfig.CURE_TONIC_ENABLED.get()) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.tonic.disabled"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        if (!CreatureForms.get(player).isCursed()) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.tonic.not_cursed"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            CreatureForms.cure(player);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
        }
        return stack;
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
