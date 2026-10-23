package nadiendev.umc.spirit.item;

import java.util.List;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.infusion.InfusionType;
import nadiendev.umc.spirit.infusion.SpiritInfusions;
import nadiendev.umc.spirit.registry.SpiritAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class InfusionEssenceItem extends Item {

    private final InfusionType type;

    public InfusionEssenceItem(InfusionType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public InfusionType type() {
        return type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.INFUSIONS_ENABLED.get() || !type.enabled()) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.infusion_disabled"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        SpiritInfusions current = player.getData(SpiritAttachments.INFUSIONS);
        int max = SpiritConfig.MAX_ENERGY_SECONDS.get() * 20;
        if (current.energy(type) >= max) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.infusion_full", Component.translatable(type.translationKey())), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide) {
            int gained = SpiritConfig.ESSENCE_SECONDS.get() * 20;
            player.setData(SpiritAttachments.INFUSIONS, current.withEnergy(type, Math.min(max, current.energy(type) + gained)));
            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1.0F, 1.2F);
            player.displayClientMessage(Component.translatable("message.umc.infusion_gained", Component.translatable(type.translationKey())), true);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.umc.essence", SpiritConfig.ESSENCE_SECONDS.get()).withStyle(ChatFormatting.GRAY));
    }
}
