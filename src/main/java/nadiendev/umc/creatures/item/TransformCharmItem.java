package nadiendev.umc.creatures.item;

import java.util.List;
import nadiendev.umc.creatures.form.CreatureCurse;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.form.CreatureKind;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class TransformCharmItem extends Item {

    private final CreatureKind kind;

    public TransformCharmItem(CreatureKind kind, Properties properties) {
        super(properties);
        this.kind = kind;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        CreatureCurse curse = CreatureForms.get(player);
        if (curse.kind() != kind) {
            player.displayClientMessage(Component.translatable("message.umc.charm.wrong_curse"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (player instanceof ServerPlayer serverPlayer) {
            CreatureForms.toggle(serverPlayer);
        }
        return InteractionResultHolder.sidedSuccess(stack, false);
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
