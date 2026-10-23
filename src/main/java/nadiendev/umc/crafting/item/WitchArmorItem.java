package nadiendev.umc.crafting.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class WitchArmorItem extends ArmorItem {

    public enum Piece {
        HAT,
        ROBE,
        CLOAK,
        BOOTS
    }

    private final Piece piece;

    public WitchArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Piece piece, Item.Properties properties) {
        super(material, type, properties);
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.umc.witch_clothing." + piece.name().toLowerCase()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.umc.witch_clothing.set").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
