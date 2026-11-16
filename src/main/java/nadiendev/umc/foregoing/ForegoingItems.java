package nadiendev.umc.foregoing;

import com.buuz135.industrial.item.addon.ProcessingAddonItem;
import com.buuz135.industrial.item.addon.RangeAddonItem;
import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.item.AugmentWrapper;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

/** The pieces that need Industrial Foregoing and Titanium classes. Only loaded when Industrial Foregoing is installed. */
final class ForegoingItems {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");

    private ForegoingItems() {
    }

    static Item addon() {
        return new Item(new Item.Properties().stacksTo(16).rarity(Rarity.EPIC)) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                AugmentWrapper.getAugment(stack).forEach((type, value) ->
                        tooltip.add(Component.literal(type + ": " + FORMAT.format(value)).withStyle(ChatFormatting.GRAY)));
            }
        };
    }

    static void components(ModifyDefaultComponentsEvent event, UmcMaterial material, Item range, Item speed, Item efficiency, Item processing) {
        ForegoingStats stats = ForegoingStats.of(material);
        set(event, range, RangeAddonItem.RANGE.getType(), stats.range());
        set(event, speed, AugmentTypes.SPEED.getType(), stats.speed());
        set(event, efficiency, AugmentTypes.EFFICIENCY.getType(), stats.efficiency());
        set(event, processing, ProcessingAddonItem.PROCESSING.getType(), stats.processing());
    }

    private static void set(ModifyDefaultComponentsEvent event, Item item, String type, float value) {
        event.modify(item, builder -> builder.set(AugmentWrapper.ATTACHMENT.get(), Map.of(type, value)));
    }
}
