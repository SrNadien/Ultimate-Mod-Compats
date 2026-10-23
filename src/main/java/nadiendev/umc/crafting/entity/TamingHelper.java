package nadiendev.umc.crafting.entity;

import nadiendev.umc.crafting.config.SpeciesConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class TamingHelper {

    private TamingHelper() {
    }

    public static InteractionResult interact(TamableAnimal animal, Player player, InteractionHand hand, SpeciesConfig config) {
        ItemStack stack = player.getItemInHand(hand);
        boolean client = animal.level().isClientSide;

        if (animal.isTame()) {
            if (!animal.isOwnedBy(player)) {
                return InteractionResult.PASS;
            }
            if (config.isTameFood(stack)) {
                if (animal.getHealth() >= animal.getMaxHealth()) {
                    return InteractionResult.PASS;
                }
                if (!client) {
                    stack.consume(1, player);
                    animal.heal(4.0F);
                }
                return InteractionResult.sidedSuccess(client);
            }
            if (hand == InteractionHand.MAIN_HAND && stack.isEmpty()) {
                if (!client) {
                    boolean sit = !animal.isOrderedToSit();
                    animal.setOrderedToSit(sit);
                    animal.setInSittingPose(sit);
                    animal.setJumping(false);
                    animal.getNavigation().stop();
                    animal.setTarget(null);
                }
                return InteractionResult.sidedSuccess(client);
            }
            return InteractionResult.PASS;
        }

        if (config.isTameFood(stack)) {
            if (!client) {
                stack.consume(1, player);
                if (animal.getRandom().nextDouble() < config.tameChance.get()) {
                    animal.tame(player);
                    animal.getNavigation().stop();
                    animal.setTarget(null);
                    animal.setOrderedToSit(true);
                    animal.setInSittingPose(true);
                    animal.level().broadcastEntityEvent(animal, (byte) 7);
                } else {
                    animal.level().broadcastEntityEvent(animal, (byte) 6);
                }
            }
            return InteractionResult.sidedSuccess(client);
        }
        return InteractionResult.PASS;
    }
}
