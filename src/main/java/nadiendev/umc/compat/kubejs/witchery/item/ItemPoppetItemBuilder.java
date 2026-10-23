package nadiendev.umc.compat.kubejs.witchery.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.favouriteless.enchanted.api.poppets.ItemPoppetEffect;
import net.favouriteless.enchanted.common.enchanted.poppet.PoppetColour;
import net.favouriteless.enchanted.common.enchanted.poppet.effects.RestoreItemPoppetEffect;
import net.favouriteless.enchanted.common.items.poppets.ItemPoppetItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPoppetItemBuilder extends ItemBuilder {

    private static final float DEFAULT_RESTORE = 0.1F;

    private transient PoppetColour colour = PoppetColour.EQUIPMENT;
    private transient Predicate<ItemStack> protects = stack -> stack.isDamageableItem();
    private transient float restore = DEFAULT_RESTORE;
    private transient Consumer<ItemStack> protectCallback;

    public ItemPoppetItemBuilder(ResourceLocation id) {
        super(id);
        texture("enchanted:item/poppet");
        this.maxStackSize = 1;
        this.maxDamage = 1;
    }

    public ItemPoppetItemBuilder poppetColour(long primary, long secondary) {
        this.colour = new PoppetColour((int) primary, (int) secondary);
        return this;
    }

    public ItemPoppetItemBuilder protects(Predicate<ItemStack> predicate) {
        this.protects = predicate;
        return this;
    }

    public ItemPoppetItemBuilder restoreMultiplier(float multiplier) {
        this.restore = multiplier;
        return this;
    }

    public ItemPoppetItemBuilder onProtect(Consumer<ItemStack> callback) {
        this.protectCallback = callback;
        return this;
    }

    @Override
    @HideFromJS
    public Item createObject() {
        ItemPoppetEffect effect = protectCallback == null
                ? new RestoreItemPoppetEffect(restore, protects)
                : new ItemPoppetEffect() {
                    @Override
                    public boolean canProtect(ItemStack stack) {
                        return protects.test(stack);
                    }

                    @Override
                    public void protect(ItemStack stack) {
                        protectCallback.accept(stack);
                    }
                };
        return new ItemPoppetItem(colour, effect, createItemProperties());
    }
}
