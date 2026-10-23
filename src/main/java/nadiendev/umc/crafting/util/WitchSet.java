package nadiendev.umc.crafting.util;

import nadiendev.umc.crafting.item.WitchArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record WitchSet(boolean hat, boolean robe, boolean cloak, boolean boots) {

    public static WitchSet of(LivingEntity entity) {
        WitchArmorItem.Piece head = pieceOf(entity.getItemBySlot(EquipmentSlot.HEAD));
        WitchArmorItem.Piece chest = pieceOf(entity.getItemBySlot(EquipmentSlot.CHEST));
        WitchArmorItem.Piece feet = pieceOf(entity.getItemBySlot(EquipmentSlot.FEET));
        return new WitchSet(
                head == WitchArmorItem.Piece.HAT,
                chest == WitchArmorItem.Piece.ROBE,
                chest == WitchArmorItem.Piece.CLOAK,
                feet == WitchArmorItem.Piece.BOOTS);
    }

    private static WitchArmorItem.Piece pieceOf(ItemStack stack) {
        return stack.getItem() instanceof WitchArmorItem item ? item.getPiece() : null;
    }

    public int pieces() {
        return (hat ? 1 : 0) + (robe || cloak ? 1 : 0) + (boots ? 1 : 0);
    }

    public boolean complete() {
        return pieces() == 3;
    }

    public double fraction() {
        return pieces() / 3.0D;
    }
}
